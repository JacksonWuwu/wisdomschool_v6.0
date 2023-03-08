<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width,user-scalable=no">
    <title>课程列表</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h4 class="heading">课程列表</h4>

    <div class="all-content">
        <div class="form-group">
                <#list clbumCourseVos as vo>
                    <div class="col-md-4 col-xs-12">
                        <a href="" style="font-size:18px;font-weight: 700;"></a>
                        <div class="information center-block"><a style="font-size:18px;font-weight: 700;">
                            <div class="infotitle">
                                <a style="font-size:18px;font-weight: 700;">${vo.course.name}</a>
                            </div>
                        <#--<img class="img-responsive center-block" src="/img/course/course1.jpg">
                            <div style="text-align: left;font-size:18px;font-weight: 700;"">
                                <label></label><p>${vo.course.period}</p>
                                <label></label><p>${vo.course.credit}</p>
                                <label></label><p>${vo.course.courseInfo}</p>
                                <label></label><p>${vo.course.courseUserNum}</p>
                            </div>-->
                            <div style="text-align: right;margin:10px;">
                                <a href="/wx/exam/chapterList?cid=${vo.course.id}&&openId=${openId}" class="btn btn-warning btn-rounded">进入章节</a>
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