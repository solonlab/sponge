// CodeMirror, copyright (c) by Marijn Haverbeke and others
// Distributed under an MIT license: http://codemirror.net/LICENSE

(function (mod) {
    if (typeof exports == "object" && typeof module == "object") // CommonJS
        mod(require("../../lib/codemirror"));
    else if (typeof define == "function" && define.amd) // AMD
        define(["../../lib/codemirror"], mod);
    else // Plain browser env
        mod(CodeMirror);
})(function (CodeMirror) {
    var Pos = CodeMirror.Pos;

    function forEach(arr, f) {
        for (var i = 0, e = arr.length; i < e; ++i) f(arr[i]);
    }

    function arrayContains(arr, item) {
        if (!Array.prototype.indexOf) {
            var i = arr.length;
            while (i--) {
                if (arr[i] === item) {
                    return true;
                }
            }
            return false;
        }
        return arr.indexOf(item) != -1;
    }

    function scriptHint(editor, keywords, getToken, options) {
        // Find the token at the cursor
        var cur = editor.getCursor(), token = getToken(editor, cur);
        if (/\b(?:string|comment)\b/.test(token.type)) return;
        token.state = CodeMirror.innerMode(editor.getMode(), token.state).state;

        // If it's not a 'word-style' token, ignore the token.
        if (!/^[\w$_]*$/.test(token.string)) {
            token = {
                start: cur.ch, end: cur.ch, string: "", state: token.state,
                type: token.string == "." ? "property" : null
            };
        } else if (token.end > cur.ch) {
            token.end = cur.ch;
            token.string = token.string.slice(0, cur.ch - token.start);
        }

        var tprop = token;
        // If it is a property, find out what it is a property of.
        while (tprop.type == "property") {
            tprop = getToken(editor, Pos(cur.line, tprop.start));
            if (tprop.string != ".") return;
            tprop = getToken(editor, Pos(cur.line, tprop.start));
            if (!context) var context = [];
            context.push(tprop);
        }
        return {
            list: getCompletions(token, context, keywords, options),
            from: Pos(cur.line, token.start),
            to: Pos(cur.line, token.end)
        };
    }

    //sql提示。
    var tables;
    var defaultTable;
    var keywords;
    var identifierQuote;
    var CONS = {
        QUERY_DIV: ";",
        ALIAS_KEYWORD: "AS"
    };
    var Pos = CodeMirror.Pos, cmpPos = CodeMirror.cmpPos;

    function isArray(val) {
        return Object.prototype.toString.call(val) == "[object Array]"
    }

    function getKeywords(editor) {
        var mode = editor.doc.modeOption;
        return CodeMirror.resolveMode(mode).keywords;
    }

    function getIdentifierQuote(editor) {
        var mode = editor.doc.modeOption;
        return CodeMirror.resolveMode(mode).identifierQuote || "`";
    }

    function getText(item) {
        return typeof item == "string" ? item : item.text;
    }

    function wrapTable(name, value) {
        if (isArray(value)) value = {columns: value}
        if (!value.text) value.text = name
        return value
    }

    function parseTables(input) {
        var result = {}
        if (isArray(input)) {
            for (var i = input.length - 1; i >= 0; i--) {
                var item = input[i]
                result[getText(item).toUpperCase()] = wrapTable(getText(item), item)
            }
        } else if (input) {
            for (var name in input)
                result[name.toUpperCase()] = wrapTable(name, input[name])
        }
        return result
    }

    function getTable(name) {
        return tables[name.toUpperCase()]
    }

    function shallowClone(object) {
        var result = {};
        for (var key in object) if (object.hasOwnProperty(key))
            result[key] = object[key];
        return result;
    }

    function match(string, word) {
        var len = string.length;
        var sub = getText(word).substr(0, len);
        return string.toUpperCase() === sub.toUpperCase();
    }

    function addMatches(result, search, wordlist, formatter) {
        if (isArray(wordlist)) {
            for (var i = 0; i < wordlist.length; i++)
                if (match(search, wordlist[i])) result.push(formatter(wordlist[i]))
        } else {
            for (var word in wordlist) if (wordlist.hasOwnProperty(word)) {
                var val = wordlist[word]
                if (!val || val === true)
                    val = word
                else
                    val = val.displayText ? {text: val.text, displayText: val.displayText} : val.text
                if (match(search, val)) result.push(formatter(val))
            }
        }
    }

    function cleanName(name) {
        // Get rid name from identifierQuote and preceding dot(.)
        if (name.charAt(0) == ".") {
            name = name.substr(1);
        }
        // replace doublicated identifierQuotes with single identifierQuotes
        // and remove single identifierQuotes
        var nameParts = name.split(identifierQuote + identifierQuote);
        for (var i = 0; i < nameParts.length; i++)
            nameParts[i] = nameParts[i].replace(new RegExp(identifierQuote, "g"), "");
        return nameParts.join(identifierQuote);
    }

    function insertIdentifierQuotes(name) {
        var nameParts = getText(name).split(".");
        for (var i = 0; i < nameParts.length; i++)
            nameParts[i] = identifierQuote +
                // doublicate identifierQuotes
                nameParts[i].replace(new RegExp(identifierQuote, "g"), identifierQuote + identifierQuote) +
                identifierQuote;
        var escaped = nameParts.join(".");
        if (typeof name == "string") return escaped;
        name = shallowClone(name);
        name.text = escaped;
        return name;
    }

    function nameCompletion(cur, token, result, editor) {
        // Try to complete table, column names and return start position of completion
        var useIdentifierQuotes = false;
        var nameParts = [];
        var start = token.start;
        var cont = true;
        while (cont) {
            cont = (token.string.charAt(0) == ".");
            useIdentifierQuotes = useIdentifierQuotes || (token.string.charAt(0) == identifierQuote);

            start = token.start;
            nameParts.unshift(cleanName(token.string));

            token = editor.getTokenAt(Pos(cur.line, token.start));
            if (token.string == ".") {
                cont = true;
                token = editor.getTokenAt(Pos(cur.line, token.start));
            }
        }

        // Try to complete table names
        var string = nameParts.join(".");
        addMatches(result, string, tables, function (w) {
            return useIdentifierQuotes ? insertIdentifierQuotes(w) : w;
        });

        // Try to complete columns from defaultTable
        addMatches(result, string, defaultTable, function (w) {
            return useIdentifierQuotes ? insertIdentifierQuotes(w) : w;
        });

        // Try to complete columns
        string = nameParts.pop();
        var table = nameParts.join(".");

        var alias = false;
        var aliasTable = table;
        // Check if table is available. If not, find table by Alias
        if (!getTable(table)) {
            var oldTable = table;
            table = findTableByAlias(table, editor);
            if (table !== oldTable) alias = true;
        }

        var columns = getTable(table);
        if (columns && columns.columns)
            columns = columns.columns;

        if (columns) {
            addMatches(result, string, columns, function (w) {
                var tableInsert = table;
                if (alias == true) tableInsert = aliasTable;
                if (typeof w == "string") {
                    w = tableInsert + "." + w;
                } else {
                    w = shallowClone(w);
                    w.text = tableInsert + "." + w.text;
                }
                return useIdentifierQuotes ? insertIdentifierQuotes(w) : w;
            });
        }

        return start;
    }

    function eachWord(lineText, f) {
        var words = lineText.split(/\s+/)
        for (var i = 0; i < words.length; i++)
            if (words[i]) f(words[i].replace(/[,;]/g, ''))
    }

    function findTableByAlias(alias, editor) {
        var doc = editor.doc;
        var fullQuery = doc.getValue();
        var aliasUpperCase = alias.toUpperCase();
        var previousWord = "";
        var table = "";
        var separator = [];
        var validRange = {
            start: Pos(0, 0),
            end: Pos(editor.lastLine(), editor.getLineHandle(editor.lastLine()).length)
        };

        //add separator
        var indexOfSeparator = fullQuery.indexOf(CONS.QUERY_DIV);
        while (indexOfSeparator != -1) {
            separator.push(doc.posFromIndex(indexOfSeparator));
            indexOfSeparator = fullQuery.indexOf(CONS.QUERY_DIV, indexOfSeparator + 1);
        }
        separator.unshift(Pos(0, 0));
        separator.push(Pos(editor.lastLine(), editor.getLineHandle(editor.lastLine()).text.length));

        //find valid range
        var prevItem = null;
        var current = editor.getCursor()
        for (var i = 0; i < separator.length; i++) {
            if ((prevItem == null || cmpPos(current, prevItem) > 0) && cmpPos(current, separator[i]) <= 0) {
                validRange = {start: prevItem, end: separator[i]};
                break;
            }
            prevItem = separator[i];
        }

        var query = doc.getRange(validRange.start, validRange.end, false);

        for (var i = 0; i < query.length; i++) {
            var lineText = query[i];
            eachWord(lineText, function (word) {
                var wordUpperCase = word.toUpperCase();
                if (wordUpperCase === aliasUpperCase && getTable(previousWord))
                    table = previousWord;
                if (wordUpperCase !== CONS.ALIAS_KEYWORD)
                    previousWord = word;
            });
            if (table) break;
        }
        return table;
    }

    function javascriptHint(editor, options) {
        return scriptHint(editor, javascriptKeywords,
            function (e, cur) {
                return e.getTokenAt(cur);
            },
            options);
    };
    CodeMirror.registerHelper("hint", "javascript", javascriptHint);

    function getCoffeeScriptToken(editor, cur) {
        // This getToken, it is for coffeescript, imitates the behavior of
        // getTokenAt method in jstl.js, that is, returning "property"
        // type and treat "." as indepenent token.
        var token = editor.getTokenAt(cur);
        if (cur.ch == token.start + 1 && token.string.charAt(0) == '.') {
            token.end = token.start;
            token.string = '.';
            token.type = "property";
        }
        else if (/^\.[\w$_]*$/.test(token.string)) {
            token.type = "property";
            token.start++;
            token.string = token.string.replace(/\./, '');
        }
        return token;
    }

    function coffeescriptHint(editor, options) {
        return scriptHint(editor, coffeescriptKeywords, getCoffeeScriptToken, options);
    }

    CodeMirror.registerHelper("hint", "coffeescript", coffeescriptHint);

    var stringProps = ("charAt charCodeAt indexOf lastIndexOf substring substr slice trim trimLeft trimRight " +
        "toUpperCase toLowerCase split concat match replace search").split(" ");
    var arrayProps = ("length concat join splice push pop shift unshift slice reverse sort indexOf " +
        "lastIndexOf every some filter forEach map reduce reduceRight ").split(" ");
    var funcProps = "prototype apply call bind".split(" ");
    var javascriptKeywords = ("break case catch continue debugger default delete do else false finally for function " +
        "if in instanceof new null return switch throw true try typeof var void while with require include water rock log context ALTER AND AS ASC BETWEEN BY COUNT CREATE  DESC DISTINCT DROP FROM GROUP HAVING IN INSERT INTO IS JOIN LEFT RIGHT INNER LIKE NOT ON OR ORDER SELECT SET TABLE UNION UPDATE VALUES WHERE LIMIT TRUNCATE").split(" ");
    var coffeescriptKeywords = ("and break catch class continue delete do else extends false finally for " +
        "if in instanceof isnt new no not null of off on or return switch then throw true try typeof until void while with yes").split(" ");

    function forAllProps(obj, callback) {
        if (!Object.getOwnPropertyNames || !Object.getPrototypeOf) {
            for (var name in obj) callback(name)
        } else {
            for (var o = obj; o; o = Object.getPrototypeOf(o))
                Object.getOwnPropertyNames(o).forEach(callback)
        }
    }

    function getCompletions(token, context, keywords, options) {
        var found = [], start = token.string, global = options && options.globalScope || window;

        function maybeAdd(str) {
            if (str.lastIndexOf(start, 0) == 0 && !arrayContains(found, str)) found.push(str);
        }

        function gatherCompletions(obj) {
            if (typeof obj == "string") forEach(stringProps, maybeAdd);
            else if (obj instanceof Array) forEach(arrayProps, maybeAdd);
            else if (obj instanceof Function) forEach(funcProps, maybeAdd);
            forAllProps(obj, maybeAdd)
        }

        if (context && context.length) {
            // If this is a property, see if it belongs to some object we can
            // find in the current environment.
            var obj = context.pop(), base;
            if (obj.type && obj.type.indexOf("variable") === 0) {
                if (options && options.additionalContext)
                    base = options.additionalContext[obj.string];
                if (!options || options.useGlobalScope !== false)
                    base = base || global[obj.string];
            } else if (obj.type == "string") {
                base = "";
            } else if (obj.type == "atom") {
                base = 1;
            } else if (obj.type == "function") {
                if (global.jQuery != null && (obj.string == '$' || obj.string == 'jQuery') &&
                    (typeof global.jQuery == 'function'))
                    base = global.jQuery();
                else if (global._ != null && (obj.string == '_') && (typeof global._ == 'function'))
                    base = global._();
            }
            while (base != null && context.length)
                base = base[context.pop().string];
            if (base != null) gatherCompletions(base);
        } else {
            // If not, just look in the global object and any local scope
            // (reading into JS mode internals to get at the local and global variables)
            for (var v = token.state.localVars; v; v = v.next) maybeAdd(v.name);
            for (var v = token.state.globalVars; v; v = v.next) maybeAdd(v.name);
            if (!options || options.useGlobalScope !== false)
                gatherCompletions(global);
            forEach(keywords, maybeAdd);
        }
        return found;
    }
});
