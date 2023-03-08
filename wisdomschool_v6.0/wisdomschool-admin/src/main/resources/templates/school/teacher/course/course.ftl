<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/icon.css">
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/bootstrap/easyui.css">
<div class="container-div inline-layout-container-div clearfix">
    <div id="inline-main-layout-container" style="width: 100%;height:600px;">
        <div data-options="region:'west',title:'${teacherCourse.course.name}',collapsible:false" style="width:20%;">
            <div class="col-menu-left">
                <div class="col-menu">
                    <div class="col-menu-top">
                        <img src="/showCondensedPicture?fileId=${teacherCourse.thumbnailPath}">
                    </div>
                    <a href="" onclick="CourseInfo()" class="col-menu-item">
                        <i class="fa fa-graduation-cap"></i> 课程信息</a>
                    <a href="/teacher/lead/get/${teacherCourse.course.id}" class="col-menu-item">
                        <i class="fa fa-tasks"></i> 开篇导学</a>
                    <a href="" onclick="teachingProgram()" class="col-menu-item">
                        <i class="fa fa-tasks"></i> 教学大纲</a>
                    <a href="/teacher/outpeizhi/get/${teacherCourse.course.id}" class="col-menu-item">
                        <i class="fa fa-tasks"></i> 环境配置</a>
                    <a href="/teacher/chapter/get/${teacherCourse.course.id}" class="col-menu-item">
                        <i class="fa fa-tasks"></i> 章节目录</a>
                    <a href="/school/onlineExam/userTest/${teacherCourse.course.id}" class="col-menu-item">
                        <i class="fa fa-book"></i> 课程作业</a>
                    <a href="javascript:void(0);" class="col-menu-item-pad">
                        <i class="fa fa-sticky-note-o"></i> 课程考试</a>
                    <div class="item-sub">
                        <a href="/school/onlineExam/courseExam/${teacherCourse.course.id}" class="col-menu-item">
                            试卷资源</a>
                        <a href="/school/onlineExam/courseExam/studentPaper/${teacherCourse.course.id}" class="col-menu-item">
                            学生试卷</a>
                    </div>
                    <a href="javascript:void(0);" class="col-menu-item-pad"><i class="fa fa-file-archive-o"></i>
                        课程资源</a>
                    <div class="item-sub">
                        <a href="/recourse/type/${teacherCourse.course.id}"
                           class="col-menu-item">资源类型</a>
                        <a href="/recourse/source/${teacherCourse.course.id}/list"
                           class="col-menu-item">课程资源</a>
                        <a href="/school/onlineExam/myQuestion/${teacherCourse.course.id}" class="col-menu-item">我的题库</a>
                        <a href="/school/onlineExam/publicQuestion/${teacherCourse.course.id}" class="col-menu-item">题库中心</a>
                        <a href="/school/onlineExam/testPaper/${teacherCourse.course.id}" class="col-menu-item">试卷资源</a>
                        <a href="/school/onlineExam/myTitleType/find/${teacherCourse.course.id}" class="col-menu-item">课程题型</a>
                    </div>
                    <a href="/teacher/course/toStatis/${teacherCourse.course.id}"
                       class="col-menu-item"><i class="fa fa-bar-chart"></i> 学习统计</a>
                    <a href="" class="col-menu-item"><i class=" fa fa-pencil-square-o"></i> 测试中心</a>
                    <a href="/teacher/course/forum/toList?cid=${teacherCourse.course.id}" class="col-menu-item"><i
                                class="fa fa-commenting-o"></i> 讨论专区</a>
                </div>
            </div>
        </div>
        <div data-options="region:'center'" style="width: 80%;">
            <div id="inline-main-tabs-container" style="width:100%;">
            </div>
        </div>
    </div>
    <div style="text-align: center">
        <a href="javascript:void(0);" class="btn btn-default"
           onclick="$.common.loadPage('main-page-container', '/teacher/course');"><i class="fa fa-reply"></i>
            返回我的课程</a>
    </div>
</div><!--/.container-div-->
<script type="text/javascript" src="/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
    function courseInfo(){
        window.location.href="/teacher/course/get/${teacherCourse.course.id}/1?token=${token}";
    }

    function teachingProgram(){
        window.location.href="/teacher/outline/get/${teacherCourse.course.id}?token=${token}";
    }

    $(function () {
        $('#inline-main-layout-container').layout();
        initTabs();
        initMenuClick();
    });

    function initTabs() {
        $('#inline-main-tabs-container').tabs({
            border: true
        });
    }


    function setHeight() {
        let m = $('#inline-main-layout-container');
        let c = m.layout('panel', 'center');	// get the center panel
        let w = m.layout('panel', 'west');	// get the west panel

        let oldCenterHeight = c.panel('panel').outerHeight();
        let oldWestHeight = w.panel('panel').outerHeight();

        c.panel('resize', {height: 'auto'});
        w.panel('resize', {height: 'auto'});

        let newCenterHeight = c.panel('panel').outerHeight();
        let newWestHeight = w.panel('panel').outerHeight();

        let centerHeight = newCenterHeight - oldCenterHeight;
        let westHeight = newWestHeight - oldWestHeight;
        m.layout('resize', {
            height: (m.height() + (newCenterHeight > newWestHeight ? centerHeight : westHeight) + 50)
        });
    }

    function initMenuClick() {
        $(".col-menu-item").each(function (i, v) {
            $(v).on("click", function (e) {
                //不存在则添加标签页
                let index = $(v).html();
                if (!$('#inline-main-tabs-container').tabs("exists", index)) {
                    $('#inline-main-tabs-container').tabs('add', {
                        title: index,
                        content: '',
                        closable: true,
                        href: $(v).attr("href")
                    });
                } else {
                    $('#inline-main-tabs-container').tabs('select', index);
                }
                e.preventDefault();
            })
        })
        $(".col-menu-item-pad").click(function () {
            $(this).next(".item-sub").slideToggle()
                .siblings(".item-sub:visible").slideUp();
        });
    }



</script>
