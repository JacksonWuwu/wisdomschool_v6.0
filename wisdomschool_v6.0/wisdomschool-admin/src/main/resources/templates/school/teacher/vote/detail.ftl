
<div id="container" style="height: 100%"></div>

<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/echarts@5/dist/echarts.min.js"></script>

<#--&ndash;&gt;-->
<#--<#include "/common/stretch.ftl"/>-->
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript">
    window.onload=function(){
        // 初始化内容
        j();
    }
    var myStu;
    function updatesuzimodalokstu(attendanceid) {
        myStu = setInterval(function(){j() }, 5*1000);
    }

    function j(){
        var echartsPie = echarts.init(document.getElementById('container'));
        $.ajax({
            url: "/teacher/vote/detailadate/${id}",
            type: 'get',
            datatype: 'json',
            success: function (datas) {
                console.log(datas);
                var data= $.parseJSON(datas);    //将传递过来的json字符串转化为对象
                console.log(data);
                echartsPie.setOption({
                    title : {
                        text: '月内点击量数据',
                        subtext: '饼图',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{b} <br/>{c} : {d} %"      //a 系列名称，b 数据项名称，c 数值，d 百分比
                    },
                    legend: {
                        orient : 'vertical',
                        x : 'left',
                        data:data.name
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
                            name:'点击量',
                            type:'pie',
                            radius : '55%',//饼图的半径大小
                            center: ['50%', '60%'],//饼图的位置
                            data:data
                        }
                    ]
                })
            }
                // option = {
                //     title: {
                //         text: '投票详细',
                //         subtext: '',
                //         left: 'center'
                //     },
                //     tooltip: {
                //         trigger: 'item'
                //     },
                //     legend: {
                //         orient: 'vertical',
                //         left: 'left',
                //     },
                //     series: [
                //         {
                //             name: '投票人数',
                //             type: 'pie',
                //             radius: '50%',
                //             data : data ,
                //             emphasis: {
                //                 itemStyle: {
                //                     shadowBlur: 10,
                //                     shadowOffsetX: 0,
                //                     shadowColor: 'rgba(0, 0, 0, 0.5)'
                //                 }
                //             }
                //         }
                //     ]
                // };
                //
                // if (option && typeof option === 'object') {
                //     myChart.setOption(option);
                // }
            // }
    // });


    })}



</script>
