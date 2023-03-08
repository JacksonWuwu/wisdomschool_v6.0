define(function (require, exports, module) {
    require("page");
    require("blockUI");
    require("common");


    let prefix = base_path + "/user/course";

    function initPagerEvent() {
        console.log("6666666666666666666666666666666666666")
        //属性 用于加载分页数据
        let contentItem = [{
            key: 'course.id',
            replace: prefix + '/detail/{%}',
            attr: 'href'
        }, {
            key: 'thumbnailPath',
            replace: '/storage/showCondensedPicture?fileId={%}',
            attr: 'src'
        }, {
            key: 'course.name'
        }, {
            key: 'sysUser.userName'
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
});
