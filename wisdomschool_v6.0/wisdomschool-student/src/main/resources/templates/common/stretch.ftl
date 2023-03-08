<!--[if !IE]> -->
<script src="/js/jquery.min.js"></script>
<script src="/js/qrcode/qrcode.js"></script>
<script src="/js/dropify/dropify.min.js"></script>
<!-- <![endif]-->

<!--[if IE]>
<script src="/js/jquery-1.11.3.min.js"></script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='js/ace/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>
<script src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/bootbox.min.js"></script>
<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="/js/excanvas.min.js"></script>
<![endif]-->

<!-- ace scripts -->
<script type="text/javascript" src="/js/ace/js/ace-elements.min.js"></script>
<script type="text/javascript" src="/js/ace/js/ace.min.js"></script>
<script type="text/javascript" src="/js/plugins/bootstrap-treetable/bootstrap-treetable.js"></script>
<!-- bootstrap-table 表格插件 -->
<script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/js/plugins/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
<script src="/js/plugins/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js"></script>
<script src="/js/plugins/bootstrap-table/extensions/toolbar/bootstrap-table-toolbar.min.js"></script>

<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
<script type="text/javascript" src="/js/plugins/layui/layui.js"></script>
<script type="text/javascript" src="/js/plugins/blockUI/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/messages_zh.min.js"></script>
<script type="text/javascript" src="/js/plugins/jquery-validation/jquery.validate.extend.js"></script>
<script type="text/javascript" src="/js/plugins/select/select2.js"></script>
<script type="text/javascript" src="/js/plugins/bootstrap-fileinput/js/fileinput.js"></script>
<script type="text/javascript" src="/js/plugins/bootstrap-fileinput/js/locales/zh.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/admin/index.js"></script>
<script type="text/javascript" src="/js/ace/js/ace.min.js"></script>
<script type="text/javascript" src="/js/page.js"></script>

<script type="text/javascript">
    var token = localStorage.getItem("token");
    var expiredDate = localStorage.getItem("expiredDate");
    function TinitPagerEvent() {
        //属性 用于加载分页数据
        let TcontentItem = [{
            key: 'course.id',
            replace: 'redirect:/teacher/course/{%}',
            attr: 'href'
        }, {
            key: 'thumbnailPath',
            replace: '/showCondensedPicture?fileId={%}?token'+token,
            attr: 'src'
        }, {
            key: 'course.name'
        }];
        let TpagerOptions = {
            loadURL: '/teacher/course/list',
            item: TcontentItem
        };
        let page = $.fn.startPage(TpagerOptions);
    }

    function TclickLoad(e) {
        let that = e.currentTarget;
        let url = $.common.trim(that.href);
        if (url !== '#' && url !== '' && url !== undefined) {
            $.common.loadPage('main-page-container', url);
        }
        e.preventDefault();
    }

    $(document).ready(function () {
        TinitPagerEvent();
    })


    function SclickLoad(item) {
        let wp = window;
        wp.location.href='/admin/'+item+'?token='+token;
    }

    function logout(){
        $.ajax({
            type: "get",
            url: "/admin/sysLogin/logout",
            success: function (res) {
                console.log(res)
                localStorage.clear();
                window.location.href="/loginIndex"
            },error: function(res){
                localStorage.clear();
                window.location.href="/loginIndex"
                console.log(res)
            }
        });
    }



    document.onmousemove = function() {
        window.lastMove = new Date().getTime();
    }
    window.lastMove = new Date().getTime();
    window.setInterval(function() {
        var now = new Date().getTime();
        if (expiredDate>now){
            console.log("计时中")
            if (expiredDate-now<600*1000){
                console.log("快过期了")
                if(now - lastMove <1) {
                    refreshToken()
                    console.log("刷新token了")
                }
            }

        }else {
            console.log("过期")
        }
    }, 1000)

    function refreshToken(){
        $.ajax({
            type: "get",
            url: "/admin/sysLogin/tokenRefresh",
            success: function (res) {
                localStorage.setItem("token",res.data.token);
                localStorage.setItem("expiredDate",res.data.expiredDate);
                token = localStorage.getItem("token");
                expiredDate = localStorage.getItem("expiredDate");
            },error: function(res){
                localStorage.clear();
            }
        });
    }
</script>





