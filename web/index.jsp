<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="content-type" content="text/html; charset=utf-8">
    <title>Aosp Insight</title>
    <!-- 引入 echarts.js -->
    <!-- 这里是加载刚下好的echarts.js，注意路径 -->
    <script src="js/echarts.js" charset="UTF-8"></script>
    <script src="js/jquery-3.3.1.js" charset="UTF-8"></script>
    <!-- use baidu cdn -->
    <!--script src="http://echarts.baidu.com/build/dist/echarts.js"></script-->
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 1000px;height:600px;MARGIN-RIGHT:auto;MARGIN-LEFT:auto"></div>
<script type="text/javascript" charset="UTF-8">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    var arrMonthDate=[];

    var arrDeletedLines=[];
    var arrAddedLines=[];
    var arrChangedLines=[];

    function getProjectChangedLines(){
        $.ajax({
            type:"post",
            async:false,
            url:"ProjectSummary.json",
            data:{},
            dataType:"json",
            success:function(result){
                console.log(result);
                if (result) {
                    for (var i = 0; i < result.length; i++) {
                        arrMonthDate.push(result[i].projectSummarySince);
                        arrDeletedLines.push(result[i].projectSummaryDeleted);
                        arrAddedLines.push(result[i].projectSummaryAdded);
                        arrChangedLines.push(result[i].projectChangedLines);
                    }
                }
            }
        });
        return arrDeletedLines,arrAddedLines,arrChangedLines;
    }
    getProjectChangedLines();
    option = {
        title : {
            text: 'AOSP 代码变化量',
            subtext: '/framewroks/base'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['代码增加量','代码减少量','代码变化量']
        },
        toolbox: {
            show : true,
            feature : {
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        dataZoom: [
            {
                type: 'slider',
                show: true,
                xAxisIndex: [0],
                start: 1,
                end: 50
            },
            {
                type: 'slider',
                show: true,
                yAxisIndex: [0],
                left: '93%',
                start: 29,
                end: 36
            }
        ],
        xAxis : [
            {
                type : 'category',
                data : arrMonthDate
                //data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'代码增加量',
                type:'bar',
                data:arrAddedLines,
                //data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
            },
            {
                name:'代码减少量',
                type:'bar',
                data:arrDeletedLines,
                //data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);

</script>
</body>
</html>
