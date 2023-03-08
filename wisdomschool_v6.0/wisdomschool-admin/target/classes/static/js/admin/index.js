$(function () {
    $(".main-nav-list a").each(function (index, elem) {
        $(elem).on('click', function (e) {
            e.preventDefault();
            let url = $.common.trim($(this).attr('href'));
            if (url !== '#' && url !== '' && url !== undefined) {
                $.common.loadPage('main-page-container', url);
            }
            $(".breadcrumb .active").text($(this).text())
        });
    })
});