seajs.config({
    alias: {
        'plugins': 'dist/js/plugins',

        'main': 'modules/main',
        'authc': 'modules/authc',
        'editor': 'modules/editor',
        'profile': 'modules/profile',
        'comment': 'modules/comment',
        'favors': 'modules/favors',
        'follow': 'modules/follow',
        'post': 'modules/post',
        'password': 'modules/password',
        'detail': 'modules/detail',
        'jphiz': 'modules/jphiz',
        'login': 'modules/login',
        'course': 'modules/course',
        'forum': 'modules/forum',
        'answer': 'modules/answer',
        'courseDetail': 'modules/courseDetail',
        'commentReply': 'modules/commentReply',

        /*vendors*/
        'swal': 'vendors/sweetalert2/sweetalert2.min',
        'lazyLoad': 'vendors/lazyload/jquery.lazyload',
        'typeahead': 'vendors/ace/js/elements.typeahead',
        'tags': 'vendors/ace/js/bootstrap-tag.js',
        'ckeditor': 'vendors/ckeditor/ckeditor',
        'fileInputLanguage': 'vendors/bootstrap-fileinput/js/locales/zh',
        'fileInput': 'vendors/bootstrap-fileinput/js/fileinput.min',
        'bootstrapValidator': 'vendors/bootstrapValidator/js/bootstrapValidator.min',
        'bootstrapValidatorLanguage': 'vendors/bootstrapValidator/js/language/zh_CN',
        'datePicker': 'vendors/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min',
        'jcrop': 'vendors/jcrop/js/jquery.Jcrop.min',
        'layer': 'vendors/layer/layer',
        'wow': 'vendors/wow/wow.min',
        'autoHidingNavBar': 'vendors/bootstrap/js/jquery.bootstrap-autohidingnavbar.min',
        'theiaStickySidebar': 'vendors/theia-sticky-sidebar/theia-sticky-sidebar.min',
        'resizeSensor': 'vendors/theia-sticky-sidebar/ResizeSensor.min',
        'highlight': 'vendors/highlight/highlight.pack',
        'share': 'vendors/share/js/social-share.min',
        'prettify': 'vendors/prettify/prettify',
        'flexText': 'vendors/flexText/jquery.flexText',

        'page': 'tmp/page',
        'common': 'tmp/common',
        'blockUI': 'tmpPlugins/blockUI/jquery.blockUI.min',
    },
    //路径配置
    paths: {
        'vendors': base_path + '/dist/vendors',
        'dist': base_path + "/dist",
        'modules': base_path + '/dist/js/modules',
        'tmp': base_path + '/js',
        'tmpPlugins': base_path + '/js/plugins'
    },
    //变量配置
    vars: {
        'locale': 'zh-cn'
    },

    charset: 'utf-8',
    debug: true
});