<#include "/common/style.ftl"/>

<#include "/common/stretch.ftl"/>
<style type="text/css">
    #questionArea {
        min-height: 200px;
        overflow-y: auto;
        max-height: 300px;

    }
     caption {caption-side:bottom;}
</style>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="height:400px"></div>
<div id="questionArea" style="text-align: center;">
    <table border="1" width="100%">
        <caption>题目分析</caption>
        <tr>
            <th>题目</th>
            <th>分数</th>
            <th>答题总人数</th>
            <th>答错人数</th>
        </tr>
        <#list testpaperQuestionsList as q>
            <tr>
                <td>${q.title}</td>
                <td>${q.questionScore}</td>
                <td>${q.doneStu}</td>
                <td>${q.errorDone}</td>
            </tr>
        </#list>
    </table>
</div>
<!-- ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    let paperId = "${id}";
    let cid = "${cid}";
    let datai = [];
    let valueScore = new Array();
    let testName = "${testPaper.testName}";
    let testheadline = "${testPaper.headline}";
    let name = new Array();
    let valueV = new Array();
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });
    // 使用
    require(
        [
            'echarts',
            'echarts/chart/funnel',
            'echarts/chart/pie',
            'echarts/chart/bar'
            // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('main'));

            option = {
                title : {
                    text: testheadline,
                    subtext: testName,
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient : 'vertical',
                    x : 'left',
                    data:['差','较差','中等','良好','优秀']
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        magicType : {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                calculable : true,
                series : [
                    {
                        name:'比例',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:
                            (function () {
                                let jsondata = [];
                                let valueScore = new Array();
                                let quesitonList;
                                $.ajax({
                                    type: "POST",
                                    url: "/school/onlineExam/testPaperOne/getChartData?paperId=" + paperId,
                                    dataType: 'json',
                                    async: false,//将同步标志改为false，代表执行完后续代码才返回结果
                                    error: function (data) {
                                        console.log("data:" + data);
                                    },
                                    success: function (data) {
                                        valueScore = data.score;
                                        valueV = data.spaceName;
                                        for (let i = 0; i < valueScore.length; i++) {
                                            let datavalue = {};
                                            let value = valueScore[i];
                                            let name = valueV[i];
                                            datavalue["value"] = value;
                                            datavalue["name"] = name;
                                            jsondata.push(datavalue);
                                        }
                                    }
                                });
                                return jsondata;
                            })()
                    }
                ]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

</script>
</body>
