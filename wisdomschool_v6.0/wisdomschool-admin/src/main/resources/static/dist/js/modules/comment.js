define(function (require, exports, module) {
    require("layer");

    $('button[data-opt]').on('click', function () {
        let id = $(this).data('id');
        layer.confirm('确定删除此评论吗？', {
            btn: ['确定', '取消']
        }, function () {
            $.getJSON(app.base + '/user/comment/delete/' + id, function (result) {
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
