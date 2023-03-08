
<link href="/js/plugins/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/icon.css">
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/bootstrap/easyui.css">
<#--<script src="/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>-->
<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-preview-add">
            <div class="form-group">
                <label class="col-sm-3 control-label"><i class="text-danger">*</i>预习名称:</label>
                <div class="col-sm-8">
                    <input id="previewname" name="previewname" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">章节</label>
                <div class="col-sm-8">
                    <div class="col-menu-left-outline">
                        <div class="col-menu-outline">
                        <div id="menuTrees" class="ztree"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">章节资源</label>
                <div class="col-sm-8">
                    <div data-options="region:'center'">
                    <div id="outlineArea"></div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->

<script src="/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
<#include "/common/stretch.ftl"/>
<script type="text/javascript">

    var chapterId = "";//节ID
    // var chapterids;

    $(function () {
        $.common.initBind();
    });
    let prefix = "/teacher/preview";
    $(function() {
        let url = "/teacher/preview/${tcid}/chapterTreeData";
        let options = {
            id: "menuTrees",
            url: url,
            check: { enable: true, nocheckInherit: true, chkboxType: { "Y": "ps", "N": "ps" } },
            expandLevel: 0,
            onClick: zTreeOnClick
        };
        $.tree.init(options);
    });

    function zTreeOnClick(event, treeId, treeNode) {
        let treeObj = $.fn.zTree.getZTreeObj("menuTrees");
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
            $.common.loadPage('outlineArea', "/teacher/preview/chapterResource/" + chapterId + "/${id}");
        }
    };

    function submitHandler() {

            add();
    }


    function add() {
        let treeObj = $.fn.zTree.getZTreeObj("menuTrees");
        //  chapterids = $.tree.getCheckedNodes();
        chapterids=treeObj.getCheckedNodes();
        nodes=treeObj.getCheckedNodes(true);
        v="";
        for(var i=0;i<nodes.length;i++){
            v+=nodes[i].id + ",";
        }
        $.operate.saveModal(prefix + "/addpreview/${tcid}", $('#form-preview-add').serialize()+"&chapterids="+v);
    }

</script>
</body>
