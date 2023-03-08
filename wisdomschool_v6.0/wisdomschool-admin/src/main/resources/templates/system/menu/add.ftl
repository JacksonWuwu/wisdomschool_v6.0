<#include "/common/style.ftl"/>
<body class="white-bg">
    <div class="container-div clearfix">
        <div class="wrapper wrapper-content animated fadeInRight ibox-content">
            <form class="form-horizontal m" id="form-menu-add">
                <input id="treeId" name="parentId" type="hidden" value="${menu.menuId}" />
                <div class="form-group">
                    <label class="col-sm-3 control-label ">上级菜单：</label>
                    <div class="col-sm-8">
                        <input class="form-control" type="text" onclick="selectMenuTree()" id="treeName" readonly="true" value="${menu.menuName}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">菜单类型：</label>
                    <div class="col-sm-8">
                        <label class="radio-box" for="radioM"><input id="radioM" type="radio" name="menuType" value="M" /> 目录 </label>
                        <label class="radio-box" for="radioC"><input id="radioC" type="radio" name="menuType" value="C" /> 菜单 </label>
                        <label class="radio-box" for="radioF"><input id="radioF" type="radio" name="menuType" value="F" /> 按钮 </label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">菜单名称：</label>
                    <div class="col-sm-8">
                        <input class="form-control" type="text" name="menuName" id="menuName">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">请求地址：</label>
                    <div class="col-sm-8">
                        <input id="url" name="url" class="form-control" type="text">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">权限标识：</label>
                    <div class="col-sm-8">
                        <input id="perms" name="perms" class="form-control" type="text">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">显示排序：</label>
                    <div class="col-sm-8">
                        <input class="form-control" type="number" name="orderNum">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">图标：</label>
                    <div class="col-sm-7">
                        <input id="icon" data-load-action="load-content" name="icon" class="form-control" type="text" placeholder="选择图标">
                        <div class="ms-parent" style="width: 100%;">
                            <div class="panel-drop animated flipInX" style="display: none;max-height:200px;overflow-y:auto">
                                <div id="load-panel" data-include="/icon"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-1"><i id="icon-show"></i></div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">菜单状态：</label>
                    <div class="col-sm-8">
                        <@dictRadio "sys_show_hide"/>
                    </div>
                </div>
            </form>
        </div>
    </div><!--/.container-div-->
    <#include "/common/stretch.ftl"/>
    <script type="text/javascript">
       let prefix = "${ctx}/system/menu";

       $("#form-menu-add").validate({
            rules:{
                menuType:{
                    required:true,
                },
                menuName:{
                    required:true,
                    remote: {
                        url: prefix + "/checkMenuNameUnique",
                        type: "post",
                        dataType: "json",
                        data: {
                            "parentId": function() {
                                return $("input[name='parentId']").val();
                            },
                            "menuName" : function() {
                                return $.common.trim($("#menuName").val());
                            }
                        },
                        dataFilter: function(data, type) {
                            return $.validate.unique(data);
                        }
                    }
                },
                orderNum:{
                    required:true,
                    digits:true
                },
            },
            messages: {
                "menuName": {
                    remote: "菜单已经存在"
                }
            }
        });

       /**
        *
        * 表单处理函数
        * */
        function submitHandler() {
            if ($.validate.form()) {
                $.operate.saveModal(prefix + "/add", $('#form-menu-add').serialize());
            }
            /* $# 刷新——未测试 */
            $.treeTable.refresh
        }

        /**
         * 图标回调函数
         * */
       function iconCallBack() {
           $(".panel-drop").find(".ico-list i").on("click", function() {
               let iconClazz = $(this).attr('class');
               $('#icon').val(iconClazz);
               $('#icon-show').attr('class', iconClazz);
               $(".panel-drop").hide();
           });
       }

        $(function() {
            $.common.initBind();
            $("#form-menu-add").click(function(event) {
                let obj = event.srcElement || event.target;
                if (!$(obj).is("input[name='icon']")) {
                    $(".icon-drop").hide();
                }
            });
            $('input').on('ifChecked', function(event){
                let menuType = $(event.target).val();
                if (menuType == "M") {
                    $("#url").parents(".form-group").hide();
                    $("#perms").parents(".form-group").hide();
                    $("#icon").parents(".form-group").show();
                } else if (menuType == "C") {
                    $("#url").parents(".form-group").show();
                    $("#perms").parents(".form-group").show();
                    $("#icon").parents(".form-group").hide();
                } else if (menuType == "F") {
                    $("#url").parents(".form-group").hide();
                    $("#perms").parents(".form-group").show();
                    $("#icon").parents(".form-group").hide();
                }
            });
            /*
            加载图标
             */
            $.form.loadContent(iconCallBack);
        });

        /*菜单管理-新增-选择菜单树*/
        function selectMenuTree() {
            let treeId = $("#treeId").val();
            let menuId = treeId > 0 ? treeId : 1;
            let url = prefix + "/selectMenuTree/" + menuId;
            let options = {
                title: '菜单选择',
                width: "380",
                url: url,
                callBack: doSubmit
            };
            $.modal.openOptions(options);
        }

        function doSubmit(index, layero){
            let body = layer.getChildFrame('body', index);
            $("#treeId").val(body.find('#treeId').val());
            $("#treeName").val(body.find('#treeName').val());
            layer.close(index);
        }
    </script>
</body>