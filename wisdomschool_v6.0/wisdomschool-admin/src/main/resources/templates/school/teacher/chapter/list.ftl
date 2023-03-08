<link href="/js/plugins/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/icon.css">
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/bootstrap/easyui.css">
<script src="/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
<div class="container-div layout-container-div clearfix">

    <div class="row">

        <div class="ibox-title">
<#--            <@shiro.hasPermission name="module:chapter:view">-->
            <button class="btn btn-default pull-right pb-5" onclick="chapterlist('${id}')"><i class="fa fa-pencil"></i> 编辑
            </button>
            <button class="btn btn-default pull-right pb-1" onclick="kuaijin('${id}')"><i class="fa fa-pencil"></i>全快进
            </button>
<#--            </@shiro.hasPermission>-->
        </div>
        <div id="inline-outline-layout-container" style="width: 100%;height:600px;">
            <div data-options="region:'west',title:'章节目录', collapsible: false,tools: []"
                 style="width:30%;height: 600px;">
                <div class="col-menu-left-outline">
                    <div class="col-menu-outline">
                        <div id="outline-tree" class="ztree">
                        </div>
                    </div>
                </div>
            </div>

            <div data-options="region:'center'">
                <div id="outlineArea"></div>
            </div>
        </div>
    </div>
</div><!--/.container-div-->


<script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
    var courseId ="${id}";
    var chapterId = "";//节ID
    $(function () {
        $('#inline-outline-layout-container').layout();
        initTree();
    });

    function initTree() {
        let url = "/teacher/chapter/chapterTreeData/${id}";
        let options = {
            id: "outline-tree",
            url: url,
            expandLevel: 0,
            onClick: zTreeOnClick,
            onExpand: nodeOnExpand,
            onCollapse: nodeOnCollapse
        };
        $.tree.init(options);
    }

    function zTreeOnClick(event, treeId, treeNode) {
        let treeObj = $.fn.zTree.getZTreeObj("outline-tree");
        let nodes = treeObj.getSelectedNodes();
        //判断选中的是否为子节点
        //若为父节点flag = true
        let flag = false;
        if (nodes.length > 0) {
            flag = nodes[0].isParent;
        }
        if (flag) {
            $("#outlineArea").empty();
        } else {
            chapterId = treeNode.id;
            chapterName = treeNode.name;
            $.common.loadPage('outlineArea', "/teacher/chapter/chapterResource/" + chapterId + "/${id}");
        }
    };

    function nodeOnExpand(event, treeId, treeNode) {
    }

    function nodeOnCollapse(event, treeId, treeNode) {
    }

    // function setHeight() {
    //     let m = $('#inline-outline-layout-container');
    //     if (m.length === undefined || m.length === 0) {
    //         return;
    //     }
    //     let w = m.layout('panel', 'west');	// get the west panel
    //
    //     let oldWestHeight = w.panel('panel').outerHeight();
    //
    //     w.panel('resize', {height: 'auto'});
    //
    //     let newWestHeight = w.panel('panel').outerHeight();
    //
    //     let westHeight = newWestHeight - oldWestHeight;
    //     m.layout('resize', {
    //         height: (m.height() + westHeight + 300)
    //     });
    // }

    // 查看章节信息
    function chapterlist(cid) {
        let url = "/jiaowu/chapter/get/"+cid;
        $.common.loadPage('main-page-container',url);
    }

    function kuaijin(cid) {
        let url = "/teacher/chapter/toeditALLVideoRecourse/"+cid;
        $.modal.open("设置视频全快进", url)
    }

</script>


