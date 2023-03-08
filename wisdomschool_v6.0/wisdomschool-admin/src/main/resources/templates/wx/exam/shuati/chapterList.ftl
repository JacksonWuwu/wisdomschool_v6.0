<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>章节列表</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h4 class="heading">章节列表</h4>

    <div class="all-content">
        <div class="form-group">
                <#list chapterList as c>
                    <div class="col-md-4 col-xs-12">
                        <a href="" style="font-size:18px;font-weight: 700;"></a>
                        <div class="information center-block"><a style="font-size:18px;font-weight: 700;">
                            <div class="infotitle">
                                <a style="font-size:18px;font-weight: 700;">${c.title}</a>
                            </div>
                            <div class="infotitle">
                                <a style="font-size:18px;font-weight: 700;">${c.name}</a>
                            </div>
                        <#--<img class="img-responsive center-block" src="/img/course/course1.jpg">
                            <div style="text-align: left;font-size:18px;font-weight: 700;"">
                                <label></label><p>${vo.course.period}</p>
                                <label></label><p>${vo.course.credit}</p>
                                <label></label><p>${vo.course.courseInfo}</p>
                                <label></label><p>${vo.course.courseUserNum}</p>
                            </div>-->
                            <div style="text-align: right;margin:10px;">
                                <a href="/wx/exam/chapterShuati?chid=${c.id}&&openId=${openId}" class="btn btn-warning btn-rounded">开始练习</a>
                            </div>
                        </div>
                    </div>
                </#list>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</html>