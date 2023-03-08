<link href="/js/plugins/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/icon.css">
<link rel="stylesheet" href="/js/plugins/jquery-easyui/themes/bootstrap/easyui.css">
<script src="/js/plugins/jquery-easyui/jquery.easyui.min.js"></script>
<div class="container-div">
    <#--    test    -->
    <div class="row">
        <div id="inline-outline-layout-container" style="width: 100%;height:600px;">
            <div data-options="region:'west',title:'试卷目录', collapsible: false,tools: []"
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

        <script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
        <script type="text/javascript">
            var testPagerId = "";
            var clbumId = "";
            $(function () {
                $('#inline-outline-layout-container').layout();
                initTree();
            });

            function initTree() {
                let url = "/school/onlineExam/courseExam/studentPagerTree/${cid}";
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
                    //父节点
                    $("#outlineArea").empty();
                } else {
                    //子节点
                    clbumId = treeNode.id;
                    testPagerId = treeNode.pId;
                    treeNode.name;
                    $.common.loadPage('outlineArea', "/school/onlineExam/courseExam/studentPagerContent/" + ${cid} + "/" + testPagerId + "/" + clbumId);
                }
            };

            function nodeOnExpand(event, treeId, treeNode) {
            }

            function nodeOnCollapse(event, treeId, treeNode) {
            }

            function setHeight() {
                let m = $('#inline-outline-layout-container');
                if (m.length === undefined || m.length === 0) {
                    return;
                }
                let w = m.layout('panel', 'west');	// get the west panel

                let oldWestHeight = w.panel('panel').outerHeight();

                w.panel('resize', {height: 'auto'});

                let newWestHeight = w.panel('panel').outerHeight();

                let westHeight = newWestHeight - oldWestHeight;
                m.layout('resize', {
                    height: (m.height() + westHeight + 300)
                });
            }

        </script>
</div>
