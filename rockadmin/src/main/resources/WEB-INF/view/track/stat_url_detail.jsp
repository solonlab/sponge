<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="/tags" %>
<!DOCTYPE HTML>
<html class="frm">
<head>
    <title>${app} - 价值跟踪</title>
    <link rel="shortcut icon" type="image/x-icon" href="/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8 "/>
    <link rel="stylesheet" href="${css}/main.css"/>
    <script src="/_session/domain.js"></script>
    <script src="${js}/lib.js"></script>
    <script src="https://static.kdz6.cn/lib/echarts.all.min.js"></script>
    <script src="${js}/china.js"></script>
    <script>
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

    </script>
    <style>
        tbody td{text-align: left;}
        datagrid b{color: #8D8D8D;font-weight: normal}
    </style>
</head>
<body>

<main class="sml">
    <div><h2>url=${url_name}/${param_name}${vd}</h2><br /></div>
    <block>
        <div style="padding: 5px;overflow: hidden;">
            近况：<ct:stateselector items="PV,UV,IP" clientID="stat" onSelect="state_select"/>
            <a style="float: right;cursor: pointer;" id="hidden" onclick="isShow();">收起</a>
        </div>
        <div id="charts" style="width:calc(100% - 13px);height: 200px;cursor: default;"></div>
    </block>

    <block>
        <div style="padding: 5px;overflow: hidden;">
            <label>趋势：</label>
            <a style="float: right;cursor: pointer;" id="hidden1" onclick="isShow1();">收起</a>
        </div>
        <div id="charts30" style="width:calc(100% - 13px);height: 200px;cursor: default;"></div>
    </block>


</main>
<ct:footer/>
</body>
<script>

    $(function () {
        get30Charts(${url_id},${vi},'${vd}');
        getCharts(0,${url_id},${vi},'${vd}');
    });

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
            data:['PV','UV','IP']
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
            }
        ]
    };
    charts30.setOption(option1);

    function get30Charts(url_id,vi,vd) {
        var daysList=[];
        var pv_list=[];
        var uv_list=[];
        var ip_list=[];
        $.ajax({
            url:"/track/stat/ajax/get30dayscharts",
            data:{
                "id":url_id,
                "type":1,
                "vi":vi,
                "vd":vd
            },
            success:function(data){

                $.each(data.daysList,function(n,value){
                    daysList.push(value);
                });
                $.each(data.pv_list,function(n,value){
                    pv_list.push(value);
                });

                $.each(data.uv_list,function(n,value){
                    uv_list.push(value);
                });

                $.each(data.ip_list,function(n,value){
                    ip_list.push(value);
                });

                charts30.setOption({
                    title: {
                        text: ''
                    },
                    xAxis: {
                        data: daysList
                    },
                    series: [
                        {
                            // 根据名字对应到相应的系列
                            name: 'PV',
                            data: pv_list
                        },
                        {
                            name: 'UV',
                            data: uv_list
                        },
                        {
                            name: 'IP',
                            data: ip_list
                        }
                    ]
                });
            }
        });
    };
    /////////////////////////////////////////

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
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            axisLabel :{interval: 3},
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

    function getCharts(type,url_id,vi,vd) {
        var hoursList=[];
        var todays=[];
        var yesterdays=[];
        var beforeYesterdays=[];

        $.ajax({
            url:"/track/stat/ajax/getcharts",
            data:{
                "id":url_id,
                "type":type,
                "queryType":1,
                "vi":vi,
                "vd":vd
            },
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

</script>
</html>