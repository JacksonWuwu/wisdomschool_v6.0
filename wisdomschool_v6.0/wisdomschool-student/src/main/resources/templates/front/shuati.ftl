<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>刷题</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon"
          href="static/plugins/home/img/favicon.ico"
          type="image/x-icon"/>
    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link rel="stylesheet"
          href="/js/plugins/uploadimage/css/imgareaselect-default.css">
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle1.css"
          type="text/css" rel="stylesheet">
    <link href="/css/dati.css"
          type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script
            src="/js/plugins/uploadimage/js/jquery.imgareaselect.min.js"></script>
    <script src="/js/plugins/uploadimage/js/image.js"></script>
    <#include '/front/inc/commonJs.ftl'/>
    <style>
        .item{
            margin-left: 5px;
        }
        .title{
            line-height: 40px;
        }
        .hide{
            display: none;
        }
        .con{
            margin-left: 40px;
        }
    </style>

</head>
<body>
<#include '/front/inc/header.ftl'/>
    <div class="container-fluid">
        <div  style="margin: 0 auto;background-color: #fff;border-radius: 3px;width: 1200px;">
            <div style="height: auto;">
                <ul id="ul1" role="tablist">
                    <li class="li1 active" onclick="setClass2(this);" name="item" style="width: 400px;border-bottom:4px solid  #5cb85c">
                        <a href="#zhangjieshuati" name="ca" onclick="setClass1(this);"
                           role="tab" data-toggle="tab" style="width: 400px">章节刷题</a>
                    </li>
                    <li class="li1" onclick="setClass2(this);" name="item" style="width: 400px;border-bottom:4px solid  #5cb85c">
                        <a href="#zhinengxuanti" name="ca" onclick="setClass1(this);"
                           role="tab" data-toggle="tab" style="width: 400px">智能刷题</a>
                    </li>
                    <li class="li1"  onclick="setClass2(this);" name="item" style="width: 400px;border-bottom:4px solid  #5cb85c"><a
                                href="#shijuanxuanti" name="ca" onclick="setClass1(this);" role="tab"
                                data-toggle="tab" style="width: 400px">试卷刷题</a></li>
                </ul>
            </div>
            <div class="tab-content">
                <div class="tab-pane active" id="zhangjieshuati">
                    <div style="width: 1200px">
                        <h4 style="margin: 5px 30px;">
                            <span style="color: #1d9cba;" class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;章节刷题
                        </h4>
                        <div id="xuanzezhangjie" class="left" style="width: 300px;height: auto;background: #FFFFFF">
                            <span style="display:inline-block;
                                         text-align:center;
                                         margin: 5px 5px;
                                         height: 20px;
                                         line-height:20px;
                                         color: #5cb85c;
                                         font-size: 18px;
                                         font-weight:bold"
                                  class="glyphicon glyphicon-record">&nbsp;${course.name}</span>
                            <#list chapter as c>
                                <div class="item">
                                    <div class="title" onmouseover ="show(this)">
                                            <span onclick="shuatishowRefresh(${c.id})" class="glyphicon glyphicon-plus-sign" style="color: #1d9cba;height: 40px;line-height:40px;">
                                                <span style="color: black;text-align: center;">${c.name}</span>
                                            </span>
                                    </div>
                                    <div class="con hide">
                                        <#if c.children??>
                                            <#list c.children as child>
                                                <div>${child.name}</div>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <div id="zhangjieshijuanshow"  style="float: right;border-radius:3px;width: 895px;height: auto;background: #FFFFFF;color: #C0C0C0;font-size: 30px;text-align: center;line-height: 500px">
                            <div id="tishizhangjieshijuanshow" style="color: #C0C0C0;font-size: 30px;text-align: center;height: 500px;line-height: 500px">
                                请选择章节
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="zhinengxuanti">
                    <div style="width: 1200px">
                        <h4 style="margin: 5px 30px;">
                            <span style="color: #1d9cba;" class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;智能刷题
                        </h4>
                        <div id="xuanzefanwei" class="left" style="width: 300px;height: auto;background: #FFFFFF">
                            <span style="display:inline-block;
                                         text-align:center;
                                         margin: 5px 5px;
                                         height: 20px;
                                         line-height:20px;
                                         color: #5cb85c;
                                         font-size: 18px;
                                         font-weight:bold"
                                  class="glyphicon glyphicon-record">&nbsp;${course.name}</span>
                            <#list chapter as c>
                                <div class="item">
                                    <div class="title" onmouseover ="show(this)">
                                            <span onclick="shuatishowRefresh(${c.id})" class="glyphicon glyphicon-plus-sign" style="color: #1d9cba;height: 40px;line-height:40px;">
                                                <span style="color: black;text-align: center;">${c.name}</span>
                                            </span>
                                    </div>
                                    <div class="con hide">
                                        <#if c.children??>
                                            <#list c.children as child>
                                                <div>${child.name}</div>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </#list>
                        </div>
                        <div id="fanweishow"  style="float: right;width: 895px;height: auto;background: #FFFFFF">
                            <span class="glyphicon glyphicon-th-list" style="margin-top: 5px;color: #1d9cba;font-size: 18px">&nbsp;选择考察范围</span>
                            <span style="float: right">
                                <a href="" style="color: #1d9cba;margin-right: 10px">撤销操作</a>
                                <a href="" style="color: #1d9cba;margin-right: 10px">清空</a>
                            </span>
                            <div style="width: 895px;height: 200px">
                                <#--考察范围展示-->
                            </div>
                        </div>
                        <div id="shijuantiaojian"  style="margin-top:5px;float: right;width: 895px;height: auto;background: #FFFFFF">
                            <span class="glyphicon glyphicon-th-list" style="margin-top: 5px;color: #1d9cba;font-size: 18px">&nbsp;设置试卷条件</span>
                            <div style="margin-top:50px;width: 895px;height: 50px">
                                <span style="margin-left: 20px">
                                    试卷难度：
                                    <select style="width: 150px;">
                                        <option value="" selected>不限</option>
                                        <option value="">较易</option>
                                        <option value="">容易</option>
                                        <option value="">中等</option>
                                        <option value="">较难</option>
                                        <option value="">困难</option>
                                    </select>
                                </span>
                            </div>
                        </div>

                        <div id="tixingshuliang"  style="margin-top:5px;float: right;width: 895px;height: auto;background: #FFFFFF">
                            <span class="glyphicon glyphicon-th-list" style="margin-top: 5px;color: #1d9cba;font-size: 18px">&nbsp;设置题型及数量</span>
                            <div style="margin-top:50px;margin-left:20px;width: 895px;height: auto">
                                选择题
                                <div style="display: inline-block;width: 160px">
                                    <input style="width: 32px; height: 32px; border: 2px white; float: left;" type="button" value="-" onclick="reductionOf(this)" />
                                    <input style="text-align: center; width: 70px; height: 32px; float: left;" data-min="0" type="text" value="0" onclick="checkNumber(this)" />
                                    <input style="width: 32px; height: 32px; border: 2px white;" type="button" value="+" onclick="add(this)" />
                                </div>
                                简单题
                                <div style="display: inline-block;width: 160px">
                                    <input style="width: 32px; height: 32px; border: 2px white; float: left;" type="button" value="-" onclick="reductionOf(this)" />
                                    <input style="text-align: center; width: 70px; height: 32px; float: left;" data-min="0"  type="text" value="0" onclick="checkNumber(this)" />
                                    <input style="width: 32px; height: 32px; border: 2px white;" type="button" value="+" onclick="add(this)" />
                                </div>
                                填空题
                                <div style="display: inline-block;width: 160px">
                                    <input style="width: 32px; height: 32px; border: 2px white; float: left;" type="button" value="-" onclick="reductionOf(this)" />
                                    <input style="text-align: center; width: 70px; height: 32px; float: left;" data-min="0"  type="text" value="0" onclick="checkNumber(this)" />
                                    <input style="width: 32px; height: 32px; border: 2px white;" type="button" value="+" onclick="add(this)" />
                                </div>
                                程序设计题
                                <div style="display: inline-block;width: 160px">
                                    <input style="width: 32px; height: 32px; border: 2px white; float: left;" type="button" value="-" onclick="reductionOf(this)" />
                                    <input style="text-align: center; width: 70px; height: 32px; float: left;" data-min="0"  type="text" value="0" onclick="checkNumber(this)" />
                                    <input style="width: 32px; height: 32px; border: 2px white;" type="button" value="+" onclick="add(this)" />
                                </div>
                            </div>
                            <div style="margin-top: 50px;color: #1d9cba;">---------------------------------------------------------------------------------------------------------------------------------------------------</div>
                            <div style="margin-top: 10px;margin-bottom: 10px;text-align: center">
                                <a href="/user/personal/shuati" class="btn btn-warning btn-rounded">智能选题</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="shijuanxuanti">
                    <div style="width: 1200px">
                        <h4 style="margin: 5px 30px;">
                            <span style="color: #1d9cba;" class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;试卷刷题
                        </h4>
                        <div style="float: right;width: 1200px;height: auto;background: #FFFFFF">
                            <span class="glyphicon glyphicon-th-list" style="margin-top: 5px;color: #1d9cba;font-size: 18px">&nbsp;选择难度</span>
                            <div style="margin-top:5px;width: 1200px;height: auto">
                                <span style="margin-left: 20px;margin-top: 20px;">
                                    试卷难度：
                                    <select style="width: 150px;">
                                        <option value="" selected>不限</option>
                                        <option value="">较易</option>
                                        <option value="">容易</option>
                                        <option value="">中等</option>
                                        <option value="">较难</option>
                                        <option value="">困难</option>
                                    </select>
                                </span>
                                <div style="margin: 20px 20px;padding-bottom: 10px;">
                                    <span style="float: left">
                                        <a href="" style="color: #1d9cba;margin-right: 10px">最新上传↓</a>
                                        <a href="" style="color: #1d9cba;margin-right: 10px">使用最多↓</a>
                                    </span>
                                    <span class="glyphicon glyphicon-list-alt" style="color: #1d9cba;float: right">
                                        共计888份试卷
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div style="margin-top:5px;padding-top:10px;padding-left:20px;padding-right:20px;float: right;width: 1200px;height: auto;background: #FFFFFF;padding-bottom: 20px;">
                            <div id="list-thead" style="width: 1160px;height: 50px;background: #1d9cba;padding-left: 15px;">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;color: white;font-size: 15px;">
                                    试卷标题
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;color: white;font-size: 15px;text-align: center;">
                                    题数
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;color: white;font-size: 15px;text-align: center;">
                                    浏览数
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;color: white;font-size: 15px;text-align: center;">
                                    更新日期
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;color: white;font-size: 15px;text-align: center;">
                                    操作
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                            <div style="width: 1160px;height: 50px;padding-left: 15px;border-bottom:1px solid  #1d9cba">
                                <div style="display:inline-block;width: 600px;height: 50px;line-height: 50px;font-size: 15px;">
                                    Spring MVC期末练习卷
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    24
                                </div>
                                <div style="display:inline-block;width: 80px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    8
                                </div>
                                <div style="display:inline-block;width: 200px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    2019-09-26
                                </div>
                                <div style="display:inline-block;width: 120px;height: 50px;line-height: 50px;font-size: 15px;text-align: center;">
                                    <a href="" style="color: #1d9cba;">开始刷题</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <script>
            var count=0;
            function shuatishowRefresh(chid) {
                count=0;
                $.ajax({
                    type: 'post', //可选get
                    url: '${request.contextPath}/user/brushQuestion', //这里是接收数据的程序
                    data: 'chid='+chid, //传给后台的数据，多个参数用&连接
                    success: function(msg) {
                        console.log(msg);
                        console.log(${shuatis});
                        document.getElementById("zhangjieshijuanshow").innerHTML ="";
                        document.getElementById("zhangjieshijuanshow").style.height="auto"
                        //这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
                        document.getElementById("zhangjieshijuanshow").innerHTML = msg;
                        //$("#duoduo").innerHTML = msg;
                    },
                    error: function() {
                        document.getElementById("zhangjieshijuanshow").innerHTML ="";
                        document.getElementById("zhangjieshijuanshow").style.height=500+"px"
                        document.getElementById("zhangjieshijuanshow").innerHTML="此章节没有题目";
                    }
                });
            }
            function showanswer(questionId) {
                count=count+1;
                console.log("!!!!!!!!!!!!!!!!!!!!!!"+count);
                document.getElementById(questionId+"answer").style.display="block";
            }
            function ObjData(key,value){
                this.Key=key;
                this.Value=value;
            }
            function chapterConsummation(listsize,myQuestionsIds) {
                var MCQCorrectTotal=0;
                console.log(myQuestionsIds);
                var  answers=[];
                var str = myQuestionsIds.split(',');
                for(var i=0;i<=str.length;i++) {
                    var obj = document.getElementsByName("oneselect" + str[i]);
                    if (obj.length != 0) {
                        for (var j = 0; j < obj.length; j++) {
                            if (obj[j].getAttribute("class") == "single") {
                                if (obj[j].checked) {
                                    console.log("选择题答案" + obj[j].value);
                                    var row1 = {};
                                    row1.titleId = str[i];
                                    row1.stuanswer = obj[j].value;
                                    answers.push(row1);
                                }
                            }
                        }
                    }
                }
                for(var i=0;i<=str.length;i++){
                    var obj = document.getElementsByName("manyselect"+str[i]);
                    var row1 = {};
                    var MCQanswer="";
                    if(obj.length!=0){
                        for(var j=0; j<obj.length; j ++){
                            if(obj[j].getAttribute("class")=="checkbox"){
                                if(obj[j].checked){
                                    MCQanswer=MCQanswer+obj[j].value+",";
                                }
                            }
                        }
                        console.log("多选题选中答案"+MCQanswer);
                        row1.titleId=str[i];
                        row1.stuanswer=MCQanswer;
                        answers.push(row1);
                    }
                }
                for(var i=0;i<=str.length;i++) {
                    var obj = document.getElementsByName("judge" + str[i]);
                    if (obj.length != 0) {
                        for (var j = 0; j < obj.length; j++) {
                            if (obj[j].getAttribute("class") == "judge") {
                                if (obj[j].checked) {
                                    console.log("判断题答案" + obj[j].value);
                                    var row1 = {};
                                    row1.titleId = str[i];
                                    row1.stuanswer = obj[j].value;
                                    answers.push(row1);
                                }
                            }
                        }
                    }
                }
                var shortAnswerQquestion=document.getElementsByName("shortAnswerQquestion");
                for(var n=0;n<shortAnswerQquestion.length;n++){
                    if(shortAnswerQquestion[n].value!=""){
                        console.log("简答题答案"+shortAnswerQquestion[n].value);
                        var row2 = {};
                        row2.titleId=shortAnswerQquestion[n].id;
                        row2.stuanswer=shortAnswerQquestion[n].value;
                        answers.push(row2);
                    }
                    //answers.set(shortAnswerQquestion[n].id,shortAnswerQquestion[n].value);
                }
                console.log(answers);
                console.log(JSON.stringify(answers));
                console.log(answers.length+"----------"+listsize);
                if(answers.length<listsize){
                    document.getElementById("chapterAlert").style.display="block";
                }else {
                    $.ajax({
                        type : 'POST',
                        contentType : 'application/json;charset=utf-8',
                        url : '${request.contextPath}/user/submitShuati',
                        processData : false,
                        dataType : 'json',
                        data : JSON.stringify(answers),
                        success : function(data) {
                            if(data=="1"){
                                //根据后台返回值确定是否操作成功
                                var answers = document.getElementsByClassName("showanswer");
                                for(var i=0;i<answers.length;i++){
                                    answers[i].style.display="block";
                                }
                                var corrects = document.getElementsByClassName("correct");
                                for(var i=0;i<corrects.length;i++){
                                    corrects[i].style.color="#FF0000";
                                    var MCQTrueOption=corrects[i].getAttribute("for");
                                    if(document.getElementById(MCQTrueOption).checked){
                                        if(document.getElementById(MCQTrueOption).getAttribute("class")!="checkbox"){
                                            MCQCorrectTotal=MCQCorrectTotal+1;
                                        }
                                    }
                                }
                                console.log("选择题做对数"+MCQCorrectTotal);
                                document.getElementById("consummation").style.display="none";
                                document.getElementById("chapterAlert").style.display="block";
                                document.getElementById("chapterAlert").style.marginBottom="20px";
                                document.getElementById("chapterAlert").innerHTML="恭喜！本章节题目已刷完！\n 单选题一共做对了"+MCQCorrectTotal+"道";
                                // document.getElementById("zhangjieshijuanshow").innerHTML ="";
                                // document.getElementById("zhangjieshijuanshow").style.height=500+"px"
                                // document.getElementById("zhangjieshijuanshow").innerHTML="恭喜！本章节题目已刷完！";
                            }
                        }
                    });
                }

                // if(count!=listsize){
                //     console.log("继续刷题");
                //     document.getElementById("chapterAlert").style.display="block";
                // }else {
                //     console.log("刷题完成");
                //     count=0;
                //     document.getElementById("zhangjieshijuanshow").innerHTML ="";
                //     document.getElementById("zhangjieshijuanshow").style.height=500+"px"
                //     document.getElementById("zhangjieshijuanshow").innerHTML="恭喜！本章节题目已刷完！";
                // }
            }
            function show(self) {
                $(self).next().removeClass('hide');
                $(self).parent().siblings().children('.con').addClass('hide');
            }
            //减数量
            function reductionOf(obj) {
                //减前判断
                if ($(obj).next().val() == '') {
                    $(obj).next().val(0);
                }else if ($(obj).next().val()>0){
                    $(obj).next().val(parseInt($(obj).next().val()) - 1);//数值减
                    $(obj).next().val($(obj).next().val());//赋值给框
                }

            };
            //加数量
            function add(obj) {
                //加前判断
                if ($(obj).prev().val() == '') {
                    $(obj).prev().val(0);
                }
                $(obj).prev().val(parseInt($(obj).prev().val()) + 1);//数值加
                $(obj).prev().val($(obj).prev().val());//赋值给框
            };
            //校验数字格式（只能输入正整数）
            function checkNumber(obj) {
                var reg = /^[0-9]\d*$/;
                if(!reg.test($(obj).val()) || $(obj).val()==''){
                    $(obj).val(0);
                }
            }
        </script>

</body>
</html>
