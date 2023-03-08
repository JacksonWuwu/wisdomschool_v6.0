<div class="container-div layout-container-div clearfix">
    <div class="row">
        <div class="pagerContent pic-list">
            <div class="pagerMainContent" style="display: none;">
                <a key="course.id" class="pic-list-constant-item" onclick="clickLoad(event)">
                    <div class="">
                        <img key="thumbnailPath" class="pic-list-constant-img">
                        <div class="cover-info">
                            <i class="fa fa-list-ul"></i> &nbsp;<h4 key="course.name"></h4>
                        </div>
                    </div>
                </a>
            </div>
            <div id="m-content">
            </div>
        </div>
    </div>
</div><!--/.container-div-->
<script src="/js/page.js"></script>
<script type="text/javascript">
    let prefix = "/teacher/course";


    function initPagerEvent() {

        //属性 用于加载分页数据
        let contentItem = [{
            key: 'course.id',
            replace: '/school/onlineExam/course/get/{%}/0',
            attr: 'href'
        }, {
            key: 'thumbnailPath',
            replace:  '/showCondensedPicture?fileId={%}',
            attr: 'src'
        }, {
            key: 'course.name'
        }];

        let pagerOptions = {
            loadURL: prefix + '/list',
            item: contentItem
        };
        let page = $.fn.startPage(pagerOptions);
    }

    function clickLoad(e) {
        let that = e.currentTarget;
        let url = $.common.trim(that.href);
        if (url !== '#' && url !== '' && url !== undefined) {
            $.common.loadPage('main-page-container', url);
        }
        e.preventDefault();
    }

    $(function () {
        initPagerEvent();
    });
</script>
