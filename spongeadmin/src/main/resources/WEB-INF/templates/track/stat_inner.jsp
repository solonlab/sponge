<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>

<!DOCTYPE HTML>
<html class="frm10">
<head>
    <title>${app} - 数据统计</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="//mirror.noear.org/lib/echarts.all.min.js"></script>
    <script src="${js}/china.js"></script>
    <script src="${js}/macarons.js"></script>
    <style>
        tbody td{text-align: left;}
        datagrid b{color: #8D8D8D;font-weight: normal}
    </style>
    <base target="_blank">
</head>
<body>

<block>
        <div style="padding: 5px;overflow: hidden;">
            近况：<ct:stateselector items="PV,UV,IP,UV+" clientID="stat" onSelect="state_select"/>
            <a style="float: right;cursor: pointer;" id="hidden" onclick="isShow();">收起</a>
        </div>
        <div id="charts" style="width:100%;height: 200px;cursor: default;"></div>
</block>

<block>
        <div style="padding: 5px;overflow: hidden;">
            <label>趋势：</label>
            <a style="float: right;cursor: pointer;" id="hidden1" onclick="isShow1();">收起</a>
        </div>
        <div id="charts30" style="width:100%;height: 200px;cursor: default;"></div>

</block>


<block>
        <div style="padding: 5px;overflow: hidden;">
            系统：<ct:stateselector items="PV,UV,IP,UV+" clientID="stat2" onSelect="state_select2"/>
            <a style="float: right;cursor: pointer;" id="hidden3" onclick="isShow3();">收起</a>
        </div>
        <div id="platform_charts" style="width:600px;height: 250px;margin: auto;;cursor: default;"></div>

</block>
<block>
        <div style="padding: 5px;overflow: hidden;">
            终端：<ct:stateselector items="PV,UV,IP,UV+" clientID="stat3" onSelect="state_select3"/>
            <a style="float: right;cursor: pointer;" id="hidden4" onclick="isShow4();">收起</a>
        </div>
        <div id="client_charts" style="width:100%;height: 250px;margin: auto;;cursor: default;"></div>
</block>


<block>
        <div style="padding: 5px;overflow: hidden;">
            地域：<ct:stateselector items="PV,UV,IP,UV+" clientID="stat1" onSelect="state_select1"/>
            <a style="float: right;cursor: pointer;" id="hidden2" onclick="isShow2();">收起</a>
        </div>
        <div id="map_charts" style="width:600px;height: 400px;margin: auto;;cursor: default;"></div>

</block>

<block>
    <div style="padding: 5px;">
        详情：<ct:stateselector items="${tabname}"  clientID="list" onSelect="list_state_select"/>
    </div>
    <datagrid></datagrid>
</block>
</body>
<script>
    var stateVal = 0;
    var tagID = ${tag_id};

    $(function () {
        get30Charts(${tag_id});
        getCharts(${stateVal},${tag_id});
        getMapCharts(${stateVal},${tag_id});
        list_state_select(0);
        getPlatformCharts(${stateVal},${tag_id});
        getClientCharts(${stateVal},${tag_id});
    });

    function list_state_select(val,e) {
        $.get('/track/stat/inner/table?tag_id='+tagID+'&_state='+val,function (rst) {
            $('datagrid').html(rst);
        })
    }

    function state_select(val,e) {
        getCharts(val,tagID);
        stateVal = val;
    };
    function state_select1(val,e) {
        getMapCharts(val,tagID);
        stateVal = val;
    };
    function state_select2(val,e) {
        getPlatformCharts(val,tagID);
        stateVal = val;
    };

    function state_select3(val,e) {
        getClientCharts(val,tagID);
        stateVal = val;
    };

    function isShow() {
        $('#charts').toggle();
        var text = $("#hidden").text();
        if (text == '收起') {
            $("#hidden").text("展开");
        } else {
            $("#hidden").text("收起");
        }
    };
    function isShow1() {
        $('#charts30').toggle();
        var text1 = $("#hidden1").text();
        if (text1 == '收起') {
            $("#hidden1").text("展开");
        } else {
            $("#hidden1").text("收起");
        }
    };

    function isShow2() {
        $('#map_charts').toggle();
        var text = $("#hidden2").text();
        if (text == '收起') {
            $("#hidden2").text("展开");
        } else {
            $("#hidden2").text("收起");
        }
    };

    function isShow3() {
        $('#platform_charts').toggle();
        var text = $("#hidden2").text();
        if (text == '收起') {
            $("#hidden3").text("展开");
        } else {
            $("#hidden3").text("收起");
        }
    };

    function isShow4() {
        $('#client_charts').toggle();
        var text = $("#hidden4").text();
        if (text == '收起') {
            $("#hidden4").text("展开");
        } else {
            $("#hidden4").text("收起");
        }
    };

    //30天走势图
    var charts30=echarts.init(document.getElementById("charts30"));
    option1 = {
        title: {
            text: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['PV','UV','IP','UV+']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                dataView : {show: true, readOnly: false},
                saveAsImage : {show: true}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            axisLabel :{interval: 5},
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name:'PV',
                type:'line',
                data:[]
            },
            {
                name:'UV',
                type:'line',
                data:[]
            },
            {
                name:'IP',
                type:'line',
                data:[]
            },
            {
                name:'UV+',
                type:'line',
                data:[]
            }
        ]
    };
    charts30.setOption(option1);

    function get30Charts(tag_id) {

        $.ajax({
            url:"/track/stat/ajax/get30dayscharts",
            data:{"id":tag_id,"type":0},
            success:function(data){

                charts30.setOption({
                    title: {
                        text: ''
                    },
                    xAxis: {
                        data: data.daysList
                    },
                    series: [
                        {
                            // 根据名字对应到相应的系列
                            name: 'PV',
                            data: data.pv_list
                        },
                        {
                            name: 'UV',
                            data: data.uv_list
                        },
                        {
                            name: 'IP',
                            data: data.ip_list
                        },
                        {
                            name: 'UV+',
                            data: data.uv2_list
                        }
                    ]
                });
            }
        });
    };

    /////////////////////////////////////////


    //小时分析图
    var myChart=echarts.init(document.getElementById("charts"));
    option = {
        title: {
            text: ''
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['今日','昨日','前日']
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        toolbox: {
            feature: {
                dataView : {show: true, readOnly: false},
                saveAsImage : {show: true}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            //x轴间隔3个单位显示
            axisLabel :{
                interval: 3
            },
            data: []
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name:'今日',
                type:'line',
                data:[]
            },
            {
                name:'昨日',
                type:'line',
                data:[]
            },
            {
                name:'前日',
                type:'line',
                data:[]
            }
        ]
    };
    myChart.setOption(option);

    function getCharts(type,tag_id) {
        var hoursList=[];
        var todays=[];
        var yesterdays=[];
        var beforeYesterdays=[];
        $.ajax({
            url:"/track/stat/ajax/getcharts",
            data:{"id":tag_id,"type":type,"queryType":0},
            success:function(data){
                $.each(data.hoursList,function(n,value){
                    hoursList.push(value);
                });

                $.each(data.todays,function(n,value){
                    todays.push(value);
                });

                $.each(data.yesterdays,function(n,value){
                    yesterdays.push(value);
                });

                $.each(data.beforeYesterdays,function(n,value){
                    beforeYesterdays.push(value);
                });

                myChart.setOption({
                    title: {
                        text: ''
                    },
                    xAxis: {
                        data: hoursList
                    },
                    series: [
                        {
                            // 根据名字对应到相应的系列
                            name: '今日',
                            data: todays
                        },
                        {
                            name: '昨日',
                            data: yesterdays
                        },
                        {
                            name: '前日',
                            data: beforeYesterdays
                        }
                    ]
                });
            }
        });
    };

    var mapChart = echarts.init(document.getElementById('map_charts'));
    var optionMap = {
        title : {
            text: '',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            x:'left',
            data:['']
        },
        dataRange: {
            min: 0,
            max: 0,
            x: 'left',
            y: 'bottom',
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true,
            color: ['#fdd8b2', '#eb776a', '#e00e00']
        },
        toolbox: {
            show: true,
            orient : 'vertical',
            x: 'right',
            y: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        roamController: {
            show: true,
            x: 'right',
            mapTypeControl: {
                'china': true
            }
        },
        series : [
            {
                name: '区域',
                type: 'map',
                mapType: 'china',
                roam: false,
                itemStyle:{
                    normal:{label:{show:true}},
                    emphasis:{label:{show:true}}
                },
                data:[]
            }
        ]
    };

    mapChart.setOption(optionMap);
    function getMapCharts(type,tag_id) {
        var yesterdays;
        var max;
        $.ajax({
            url:"/track/stat/ajax/getCitycharts",
            data:{"id":tag_id,"type":type,"queryType":0},
            success:function(data) {
                yesterdays = data.yesterdays;
                max = data.max;
                mapChart.setOption({
                    title : {
                        text: '',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        x:'left',
                        data:['']
                    },
                    dataRange: {
                        min: 0,
                        max: max,
                        x: 'left',
                        y: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true,
                        color: ['#fd0008', '#eb776a', '#fdd8b2']
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        x: 'right',
                        y: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    roamController: {
                        show: true,
                        x: 'right',
                        mapTypeControl: {
                            'china': true
                        }
                    },
                    series : [
                        {
                            name: '区域',
                            type: 'map',
                            mapType: 'china',
                            roam: false,
                            itemStyle:{
                                normal:{label:{show:true}},
                                emphasis:{label:{show:true}}
                            },
                            data:yesterdays
                        }
                    ]
                })
            }})};


    mapChart.on('mouseover', function (params) {
        var dataIndex = params.dataIndex;
    });

///////////////////////////////////

    var platform = echarts.init(document.getElementById('platform_charts'),'macarons');
    var platformMap = {
        title : {
            text: '',
            subtext: '',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            data: ['未知','iPhone','iPad','Android','Windows','Mac','Linux']
        },
        series : [
            {
                name: '系统',
                type: 'pie',
                radius : '75%',
                center: ['48%', '48%'],
                data:[
                    {value:0, name:'未知'},
                    {value:0, name:'iPhone'},
                    {value:0, name:'iPad'},
                    {value:0, name:'Android'},
                    {value:0, name:'Windows'},
                    {value:0, name:'Mac'},
                    {value:0, name:'Linux'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    platform.setOption(platformMap);
    function getPlatformCharts(type,tag_id) {
        $.ajax({
            url:"/track/stat/ajax/getPlatformCharts",
            data:{"tag_id":tag_id,"type":type},
            success:function(data) {

                platform.setOption({
                    title : {
                        text: '',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'right',
                        data: data.titles
                    },
                    series : [
                        {
                            name: '系统',
                            type: 'pie',
                            radius : '75%',
                            center: ['48%', '48%'],
                            data:data.datas,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                })
            }
        })
    };

    ////终端////////////////////////////////////////////
    var client = echarts.init(document.getElementById('client_charts'));
    var clientMap = {
        color: ['#1F1F20'],
        tooltip : {
            trigger: 'item',
            axisPointer : {
                type : 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '14%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : [],
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    interval:0,
                    rotate:40
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'',
                type:'bar',
                barWidth: '40%',
                data:[],
                itemStyle: {
                    normal: {
                        //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                        color: function(params) {
                            // build a color map as your need.
                            var colorList = [
                                '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                            ];
                            return colorList[params.dataIndex]
                        }
                    }
                },
            }
        ]
    };

    client.setOption(clientMap);
    function getClientCharts(type,tag_id) {
        $.ajax({
            url:"/track/stat/ajax/getClientCharts",
            data:{"tag_id":tag_id,"type":type},
            success:function(data) {

                client.setOption({
                    color: ['#1F1F20'],
                    tooltip : {
                        trigger: 'item',
                        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '14%',
                        containLabel: true
                    },
                    xAxis : [
                        {
                            type : 'category',
                            data : data.titles,
                            axisTick: {
                                alignWithLabel: true
                            },
                            axisLabel: {
                                interval:0,
                                rotate:40
                            }
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'终端',
                            type:'bar',
                            barWidth: '40%',
                            data:data.datas,
                            itemStyle: {
                                normal: {
                                    //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                                    color: function(params) {
                                        // build a color map as your need.
                                        var colorList = [
                                            '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                            '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                            '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                        ];
                                        return colorList[params.dataIndex]
                                    }
                                }
                            },
                        }
                    ]
                })
            }
        })
    };

    parent.window.onMenuHide=function () {
        charts30.resize();
        myChart.resize();
        mapChart.resize();
    };
</script>
</html>