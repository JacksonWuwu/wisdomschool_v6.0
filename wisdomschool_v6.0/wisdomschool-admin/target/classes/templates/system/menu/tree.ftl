<#include "/common/style.ftl"/>
<link href="/js/plugins/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
<style>
    body {
        height: auto;
        font-family: "Microsoft YaHei";
    }

    button {
        font-family: "SimSun", "Helvetica Neue", Helvetica, Arial;
    }
</style>
<body class="hold-transition box box-main white-bg">
<input id="treeId" name="treeId" type="hidden" value="${menu.menuId}"/>
<input id="treeName" name="treeName" type="hidden" value="${menu.menuName}"/>
<div class="wrapper">
    <div class="treeShowHideButton" onclick="$.tree.toggleSearch();">
		<label id="btnShow" title="显示搜索" style="display:none;">︾</label>
		<label id="btnHide" title="隐藏搜索">︽</label>
	</div>
	<div class="treeSearchInput" id="search">
		<label for="keyword">关键字：</label><input type="text" class="empty" id="keyword" maxlength="50">
		<button class="btn" id="btn" onclick="$.tree.searchNode()"> 搜索 </button>
	</div>
	<div class="treeExpandCollapse">
		<a href="#" onclick="$.tree.expand()">展开</a> /
		<a href="#" onclick="$.tree.collapse()">折叠</a>
	</div>
	<div id="tree" class="ztree treeselect"></div>
</div>
<#include "/common/stretch.ftl"/>
<script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
    let prefix = "${ctx}/system/menu";
    $(function () {
        let url = prefix + "/menuTreeData";
        let options = {
            url: url,
            expandLevel: 1,
            onClick: zOnClick
        };
        $.tree.init(options);
        $.common.initBind();
    });

    function zOnClick(event, treeId, treeNode) {
        treeId = treeNode.id;
        let treeName = treeNode.name;
        $("#treeId").val(treeId);
        $("#treeName").val(treeName);
    }
</script>
</body>
