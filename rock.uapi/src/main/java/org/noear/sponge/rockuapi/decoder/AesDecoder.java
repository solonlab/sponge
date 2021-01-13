package org.noear.sponge.rockuapi.decoder;

import org.noear.sponge.rock.models.AppModel;
import org.noear.solon.core.handle.Context;
import org.noear.water.utils.EncryptUtils;

import java.net.URLDecoder;

public class AesDecoder implements Decoder {
    @Override
    public String tryDecode(Context context, AppModel app, String text) throws Exception {
        if (text.indexOf("{") < 0 && text.indexOf("<") < 0) {
            if (text.indexOf('%') >= 0) {
                text = new String(URLDecoder.decode(text, "UTF-8"));
            }

            return EncryptUtils.aesDecrypt(text, app.app_key, null);
        }

        return text;
    }
}
