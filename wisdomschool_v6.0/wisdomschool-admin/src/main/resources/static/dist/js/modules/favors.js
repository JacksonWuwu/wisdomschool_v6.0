define(function (require, exports, module) {
    require("layer");

    $('button[data-opt]').on('click', function () {
        var id = $(this).data('id');
        layer.confirm('确定取消收藏吗？', {
            btn: ['确定', '取消']
        }, function () {
            $.getJSON('/user/unfavor/' + id, function (result) {
                if (result.code >= 0) {
                    layer.msg(result.message, {icon: 6});
                    $('#content-' + id).fadeOut();
                    $('#content-' + id).remove();
                } else {
                    layer.msg(result.message, {icon: 5});
                }
            })
        })
    });
});