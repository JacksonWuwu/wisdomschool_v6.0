<#include "/common/style.ftl"/>
<div class="container-div container-video-div layout-container-div clearfix">
    <div class="row">
        <div id="inline-outline-layout-container" style="width: 100%;height:300px;">
            <div class="btn-group-sm hidden-xs" id="video-toolbar" role="group">
            </div>
            <video style="display:none" controls="controls"  name="media" id="divVideo" >
                <source type="video/mp4">
            </video>
            <div class="col-sm-12 select-table table-striped">
                <table id="bootstrap-video-table" data-mobile-responsive="true"></table>
            </div>
        </div>
    </div>
</div><!--/.container-div-->

<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    let prefix = "/recourse/videoresource";
    let allowOfficeExt = {
        'pptx': '', 'ppt': '', 'doc': '', 'docx': '', 'xlsx': '',
        'xls': ''
    };
    let type = <#if (type == 1)>'视频'
    <#elseif (type == 2) >'课件'
    <#elseif (type == 3) >'习题'
    <#else >'考试'//考试系统
    </#if>
    var interval;
    function select(id, attrId) {
        // cid: 章节id
        // rid：资源id
        console.log(type)
        if (type === '视频') {
            document.getElementById("divVideo").src= "${storage}/resource/getResource?fid=" + attrId;
            $.modal.loading("加载数据");
            interval = setInterval(function () {
                var video = document.getElementById("divVideo");
                var length = video.duration;
                var duration = length;
                if (!isNaN(length)) {
                    console.log(length);
                    var hour = parseInt(duration / 3600);
                    var minute = parseInt(duration % 3600 / 60);
                    var sec = parseInt(duration % 60);
                    console.log("视频长度为：" + duration + "   " + hour + ":" + minute + ":" + sec);

                    clearInterval(interval);
                    $.modal.closeLoading();
                    submit(id, length * 1000);
                }

            }, 1000);
        } else {
            submit(id, 0);
        }
    }

    function submit(id, length) {
        $.modal.confirm("确定添加该资源吗？", function () {
            // type： 资源类型，1、视频，2、课件，3、习题，4、考试
            if (!$.common.isEmpty(id)) {
                $.operate.postJson('/teacher/chapter/saveChapterResource', {
                    chapterId:${chapter.id}, courseId: '${chapter.cid}', rid: id, type: ${type}, length: length
                }, true, function (result) {
                    if (result === undefined) {
                        return;
                    }
                    if (result.code === web_status.SUCCESS) {
                        $.modal.msgSuccess('操作成功');

                        //关闭iframe页面
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);


                        window.parent.refreshWindow(${chapter.id});
                    } else {
                        $.modal.msgError(result.msg);
                    }
                })
            }
        });
    }

    $(function () {
        // 根据课程id来获取资源列表
        let options = {
            url: <#if (type == 1)>prefix + "/list/${chapter.cid}"
                <#elseif (type == 2)>"/recourse/fileresource/list/${chapter.cid}"
                <#elseif (type == 3)>"/school/onlineExam/testPaper/list/${chapter.cid}"
                <#else>"/school/onlineExam/testPaperOne/list/${chapter.cid}"</#if>,
            content: "bootstrap-video-table",
            toolbar: "video-toolbar",
            modalName: type + "资源管理",
            columns: [{
                checkbox: true
            },
                {
                    field: 'id',
                    title: '编号'
                },

                {
                    field: 'name',
                    title: type + '名称',
                    formatter: function (value, row, index) {
                        <#if (type == 1)>
                        return "<a onclick='window.open(\"/recourse/video/" + row.attrId + " \")'>" + value + "</a>";
                        <#else >
                        if (allowOfficeExt[row.ext] !== undefined) {
                            return '<a onclick="officeView(\'' + row.attrId + '\')">' + value + '</a>';
                        } else if (row.ext === 'pdf') {
                            return '<a onclick="pdfView(\'' + row.attrId + '\')">' + value + '</a>';
                        }
                        return '<a onclick="download(\'' + row.attrId + '\')">' + value + '</a>';
                        </#if>
                    }
                },
                {
                    field: 'ext',
                    title: '文件格式'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    visible: false
                },
                {
                    field: 'count',
                    title: '引用次数'
                },
                {
                    field: 'count',
                    title: '观看次数'
                },
                {
                    field: 'remark',
                    title: '备注'
                },
                {
                    title: '操作',
                    align: 'center',
                    formatter: function (value, row, index) {
                        let actions = [];
                        <@shiro.hasPermission name="teacher:course:view">
                        actions.push('<a class="btn btn-success btn-xs " href="#" onclick="select(' + row.id + ', \'' + row.attrId + '\')"><i class="fa fa-edit"></i>选择</a> ');
                        </@shiro.hasPermission>
                        return actions.join('');
                    }
                }]
        };
        $.table.init(options);
        $.common.initBind();
    });
</script>
