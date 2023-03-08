<style type="text/css">
    .chapter_resource_wrap .ztree * {
        font-size: 10pt;
        font-family: "Microsoft Yahei", Verdana, Simsun, "Segoe UI Web Light", "Segoe UI Light", "Segoe UI Web Regular", "Segoe UI", "Segoe UI Symbol", "Helvetica Neue", Arial
    }

    .chapter_resource_wrap .ztree li ul {
        margin: 0;
        padding: 0
    }

    .chapter_resource_wrap .ztree li {
        line-height: 0px;
    }

    .chapter_resource_wrap .ztree li a {
        width: 100%;
        height: 30px;
        padding-top: 0px;
    }

    .chapter_resource_wrap .ztree li a:hover {
        text-decoration: none;
        background-color: #E7E7E7;
    }

    .chapter_resource_wrap .ztree li a span.button.switch {
        visibility: hidden
    }

    .chapter_resource_wrap .ztree.showIcon li a span.button.switch {
        visibility: visible
    }

    .chapter_resource_wrap .ztree li a.curSelectedNode {
        background-color: #D4D4D4;
        border: 0;
        height: 30px;
    }

    .chapter_resource_wrap .ztree li span {
        line-height: 30px;
    }

    .chapter_resource_wrap .ztree li span.button {
        margin-top: -7px;
    }

    .chapter_resource_wrap .ztree li span.button.switch {
        width: 16px;
        height: 16px;
    }

    .chapter_resource_wrap .ztree li a.level0 span {
        font-size: 150%;
        font-weight: bold;
    }

    .chapter_resource_wrap .ztree li span.button.switch.level0 {
        width: 0px;
        height: 0px
    }

    .chapter_resource_wrap .ztree li span.button.switch.level1 {
        width: 20px;
        height: 20px
    }

    .chapter_resource_wrap .ztree li span.button.noline_open {
        background-position: 0 0;
    }

    .chapter_resource_wrap .ztree li span.button.noline_close {
        background-position: -18px 0;
    }

    .chapter_resource_wrap .ztree li span.button.noline_open.level0 {
        background-position: 0 -18px;
    }

    .chapter_resource_wrap .ztree li span.button.noline_close.level0 {
        background-position: -18px -18px;
    }

    .chapter_resource_wrap .ztree li a.level0 {
        background-color: rgba(102, 163, 210, 0.02);
        /*border: 1px silver solid;*/
    }
</style>
<div class="chapter_resource_wrap">
    <div class="zTreeDemoBackground">
        <ul id="chapterTree" class="ztree"></ul>
    </div>
</div>

<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">

    <!--
    let IDMark_A = "_a";
    let _curMenu_ = null, _zTree_Menu_ = null;
    let _setting_ = {
        view: {
            showLine: false,
            showIcon: false,
            selectedMulti: false,
            dblClickExpand: false,
            addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick
        }
    };

    let _zNodes_ = [
        {id: 1, pId: 0, name: "", open: false},
        {id: 2, pId: 0, name: "", open: false},
        {id: 3, pId: 0, name: "", open: false},
        {id: 4, pId: 0, name: "考试", open: true},//考试题系统
        <#list resources as rs>
        {
            id: ${rs.id},
            pId:${rs.resourceType},
            name: '${rs.name}.${rs.ext}',
            ext: '${rs.ext}',
            rid: '${rs.rid}',
            index: '${rs_index}',
            size: '${resources?size}'
        },
        </#list>
    ];

    let allowOfficeExt = {
        'pptx': '', 'ppt': '', 'doc': '', 'docx': '', 'xlsx': '',
        'xls': ''
    };
    /*
         { id:11, pId:1, name:"视频1"},
         { id:111, pId:1, name:"视频2"},
         { id:112, pId:1, name:"视频3"},
         { id:99, pId:9, name:"课件1"},
         { id:999, pId:9, name:"课件2"},
         { id:31, pId:3, name:"试卷1"},
         { id:32, pId:3, name:"试卷2"}*/

    // 重新加载页面
    function refreshWindow(chapterId) {
        //  不调用
        $.common.loadPage('outlineArea', "/teacher/chapter/courseResource/" + chapterId + "/${courseId}");
    }

    function addDiyDom(treeId, treeNode) {
        let spaceWidth = 5;
        let aObj = $("#" + treeNode.tId + IDMark_A);
        let switchObj = $("#" + treeNode.tId + "_switch"),
            icoObj = $("#" + treeNode.tId + "_ico");
        switchObj.remove();
        icoObj.before(switchObj);

        if (treeNode.level > 1) {
            let spaceStr = "<span style='display: inline-block;width: " + (spaceWidth * treeNode.level) + "px'></span>";
            switchObj.before(spaceStr);
        }
         if (treeNode.id === 1) {
        if ($("#diyBtn1_" + treeNode.id).length > 0) return;
        let editStr = "";
        aObj.append(editStr);
    } else if (treeNode.id === 2) {
        if ($("#diyBtn2_" + treeNode.id).length > 0) return;
        let editStr = "";
        aObj.append(editStr);
    } else if (treeNode.id === 3) {
        if ($("#diyBtn3_" + treeNode.id).length > 0) return;
        let editStr = "";
        aObj.append(editStr);
    } else if (treeNode.id === 4) {
        if ($("#diyBtn4_" + treeNode.id).length > 0) return;
        let editStr = "<button id='diyBtn4_" + treeNode.id + "' onclick='addExam()' " +
            "style='margin:3px 5px 0 5px;float:right;'>添加考试</button>";
        aObj.append(editStr);
    }//考试系统
    else {
        // if ($("#diyBtn4_" + treeNode.id).length > 0) return;
        if ($("#diyBtn5_" + treeNode.id).length > 0) return;
        let delStr = "<button id='diyBtn4_" + treeNode.id + "' onclick='delResource(" + treeNode.rid + ")' " +
            "style='margin:3px 75px 0 15px;float: right;'>删除</button>";
        let opt = '';
        // 视频
        console.log(treeNode);
        if (treeNode.pId === 1) {
            opt = '<btn class="btn btn-primary" style="margin: 0 10px;float: right;" onclick="window.open(\'/recourse/video/' + treeNode.rid + '\')">预览</btn>';
        } else if (treeNode.pId === 3) {
            // opt = '<btn class="btn btn-primary" style="margin: 0 10px" onclick="openwindow(\'' + treeNode.rid + '\')">开始测试</btn>'
            // 习题操作
        } else if (treeNode.pId === 4) {
            // opt = '<btn class="btn btn-primary" style="margin: 0 10px" onclick="openwindow(\'' + treeNode.rid + '\')">开始测试</btn>'
            // 习题操作
        }else if (allowOfficeExt[treeNode.ext] !== undefined && treeNode.ext == 'pdf') {
            opt = '<btn class="btn btn-primary" style="margin: 0 10px;float: right;" onclick="pdfView(\'' + treeNode.rid + '\')">预览</btn>';
        } else {
            opt = '<btn class="btn btn-primary" style="margin: 0 10px;float: right;" onclick="downLoad(\'' + treeNode.rid + '\')">下载</btn>';
        }
        aObj.append(delStr);
        aObj.append(opt);
    }
}

    function beforeClick(treeId, node) {
        return false;
    }
    function pdfView(filePath) {
        $.ajax({
            url: '/recourse/downloadFile/' + filePath,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    window.open("${storage}/recourse/fileresource/review?file=${storage}/resource/handle/" + result.fid);
                } else {
                    alert("错误：无法下载该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });
    }

    //rid：资源id
    //cid：章节id
    function delResource(rid) {
        $.modal.confirm("删除改资源会删除学生提交记录，请确认？", function () {
            $.operate.getJson('/teacher/chapter/resource/remove', {
                cid: ${cid},
                rid: rid
            }, true, function (result) {
                if (result.code === web_status.SUCCESS) {
                    $.modal.msgSuccess('操作成功');
                } else {
                    $.modal.msgError('操作失败');
                }
                refreshWindow(${cid});
            }, function () {
            });
        });
    }
    function downLoad(fileId) {
        $.ajax({
            url: '/recourse/downloadFile/' + fileId,
            type: 'get',
            dataType: 'json',
            success: function (result) {
                if (result.code === 0) {
                    window.location.href = "${storage}/downloadFile?fileId=" + result.fid;
                } else {
                    alert("错误：无法下载该文件。");
                }
            },
            error: function () {
                alert("错误：请求失败，请刷新重试。");
            }
        });
    }
    //cid：章节id
    function addVideo() {
        //根据章节来获取课程，返回该课程的视频资源列表
        $.modal.open("添加章节视频", '/teacher/chapter/resource/list/${cid}/1')
    }

    //cid：章节id
    function addFile() {
        $.modal.open("添加章节课件", '/teacher/chapter/resource/list/${cid}/2')
    }

    //cid：章节id
    function addPaper() {

        $.modal.open("添加章节习题", '/teacher/chapter/resource/list/${cid}/3')

    }
    //cid:考试id
    function addExam() {

        $.modal.open("添加章节考试", '/teacher/chapter/resource/list/${cid}/4')

    }
    function openwindow(id) {
        var chapid = "${cid}"
        window.open('/school/onlineExam/testChapterPaper/stuToTest?id=' + id + '&&chapid=' + chapid, '_blank', 'height=800, width=600');

    }

    $(function () {
        let _treeObj_ = $("#chapterTree");
        $.fn.zTree.init(_treeObj_, _setting_, _zNodes_);
        _zTree_Menu_ = $.fn.zTree.getZTreeObj("chapterTree");
        _zTree_Menu_.selectNode(_curMenu_);

        _treeObj_.hover(function () {
            if (!_treeObj_.hasClass("showIcon")) {
                _treeObj_.addClass("showIcon");
            }
        }, function () {
            _treeObj_.removeClass("showIcon");
        });
    });
    //-->
</script>
