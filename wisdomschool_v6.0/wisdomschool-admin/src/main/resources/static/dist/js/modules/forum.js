define(function (require, exports, module) {
    let plugins = require('plugins');
    J = jQuery;
    require('layer');
    require('prettify');
    layer.config({
        path: '/dist/vendors/layer' //layer.js所在的目录，可以是绝对目录，也可以是相对目录
    });

    $(".load-content").on("click", function () {
        let id = $(this).parent().parent().parent().attr("data-info-id");
        let type = $(this).parent().parent().parent().attr("data-info-type");
        let _this = this;
        if (type == 0) {
            $.get("/findQuestionById/" + id,
                function (data) {
                    if (data.code >= 0) {
                        $(_this).parent().parent().prepend("<div class='all-content'>" + data.data.content + "</div>");
                        $("pre").addClass("prettyprint linenums");
                        prettyPrint();
                    } else {
                        layer.msg('该信息已删除或参数错误', {icon: 2});
                        return false;
                    }
                });
        } else if (type == 1) {
            $.get("/findArticleById/" + id,
                function (data) {
                    if (data.code >= 0) {
                        $(_this).parent().parent().prepend("<div class='all-content'>" + data.data.content + "</div>");
                        $("pre").addClass("prettyprint linenums");
                        prettyPrint();
                    } else {
                        layer.msg('该信息已删除或参数错误', {icon: 2});
                        return false;
                    }
                });
        } else if (type == 2) {
            $.get("/findShareById/" + id,
                function (data) {
                    if (data.code >= 0) {
                        $(_this).parent().parent().prepend("<div class='all-content'>" + data.data.content + "</div>");
                        $("pre").addClass("prettyprint linenums");
                        prettyPrint();
                    } else {
                        layer.msg('该信息已删除或参数错误', {icon: 2});
                        return false;
                    }
                });
        }
        let display = $(this).parent().css('display');
        if (display != 'none') {
            $(this).parent().parent().next().children(".shrink-content").show();
            $(this).parent().hide();
        }
    });

    $(".shrink-content").on("click", function () {
        let display = $(this).css('display');
        if (display != 'none') {
            $(this).hide();
            $(this).parent().prev().children(".all-content").remove();
            $(this).parent().prev().children(".excerpt").show();
        }
    });


    $(".userFollow-button").hover(function () {
        let follow = $(this).text();
        if (follow == "已关注") {
            $(this).html("取消关注");
        }
    }, function () {
        let follow = $(this).text();
        if (follow == "取消关注") {
            $(this).html("已关注");
        }
    });

    //关注用户
    $(".userFollow-button").on("click", function () {
        if (app.LOGIN_TOKEN.trim() !== '') {
            let id = $(this).attr("data-user-id");
            $.ajax({
                url: '/ucenter/user/follow',
                data: {"id": id},
                dataType: "json",
                type: "post",
                cache: false,
                async: false,
                error: function (i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function (data) {
                    if (data.code == 0) {
                        //弹出提示2秒后刷新页面
                        layer.msg(data.message, {icon: 1, time: 2000}, function () {
                            window.location.reload();
                        });
                        return false;
                    } else if (data.code == 2) {
                        layer.msg(data.message, {icon: 5, time: 2000}, function () {
                            window.location.reload();
                        });
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            $("#loginModal").modal();
        }
    });


    $(".article-digg").on("click", function () {
        if (app.LOGIN_TOKEN.trim() !== '') {
            let id = $(".article-content").attr("data-info-id");
            $.ajax({
                url: '/article/digg',
                data: {"id": id},
                dataType: "json",
                type: "post",
                cache: false,
                async: false,
                error: function (i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function (data) {
                    if (data.code == 0) {
                        //弹出提示2秒后刷新页面
                        layer.msg(data.message, {icon: 1, time: 2000}, function () {
                            window.location.reload();
                        });
                        return false;
                    } else if (data.code == 2) {
                        layer.msg(data.message, {icon: 5, time: 2000}, function () {
                            window.location.reload();
                        });
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            $("#loginModal").modal();
        }
    });

    $(".follow-button").on("click", function () {
        if (app.LOGIN_TOKEN.trim() !== '') {
            let questionId = $(".question-id").attr("data-question-id");
            $.ajax({
                url: '/question/follow',
                data: {"questionId": questionId},
                dataType: "json",
                type: "post",
                cache: false,
                async: false,
                error: function (i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function (data) {
                    if (data.code == 0) {
                        //弹出提示2秒后刷新页面
                        layer.msg(data.message, {icon: 1, time: 2000}, function () {
                            window.location.reload();
                        });
                        return false;
                    } else if (data.code == 2) {
                        layer.msg(data.message, {icon: 5, time: 2000}, function () {
                            window.location.reload();
                        });
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            $("#loginModal").modal();
        }
    });

    $(".ask-button").on("click", function () {
        window.location.href = "/question/add";
    });


    $(".follow-topic").on("click", function () {
        if (app.LOGIN_TOKEN.trim() !== '') {
            let id = $(".topic-card").attr("data-topic-id");
            $.ajax({
                url: '/topics/follow',
                data: {"id": id},
                dataType: "json",
                type: "post",
                cache: false,
                async: false,
                error: function (i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function (data) {
                    if (data.code == 0) {
                        layer.msg(data.message, {icon: 1, time: 2000}, function () {
                            window.location.reload();
                        });
                        return false;
                    } else if (data.code == 2) {
                        layer.msg(data.message, {icon: 5, time: 2000}, function () {
                            window.location.reload();
                        });
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            $("#loginModal").modal();
        }
    });

    $(".buy-share").on("click", function () {
        if (app.LOGIN_TOKEN.trim() !== '') {
            let id = $(this).attr("data-share-id");
            $.ajax({
                url: '/share/buy',
                data: {"id": id},
                dataType: "json",
                type: "post",
                cache: false,
                async: false,
                error: function (i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function (data) {
                    if (data.code == 0) {
                        layer.msg(data.message, {icon: 1});
                        return false;
                    } else if (data.code == 2) {
                        layer.msg(data.message, {icon: 5});
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            $("#loginModal").modal();
        }
    });

    $(".add-favorite").on("click", function () {
        if (app.LOGIN_TOKEN.trim() !== '') {
            let id = $(this).attr("data-info-id");
            let type = $(this).attr("data-type-id");
            $.ajax({
                url: '/ucenter/favorite/add',
                data: {"id": id, "type": type},
                dataType: "json",
                type: "post",
                cache: false,
                async: false,
                error: function (i, g, h) {
                    layer.msg('发送错误', {icon: 2});
                },
                success: function (data) {
                    if (data.code == 0) {
                        layer.msg(data.message, {icon: 1});
                        return false;
                    } else if (data.code == 2) {
                        layer.msg(data.message, {icon: 5});
                    } else {
                        layer.msg(data.message, {icon: 2});
                    }
                }
            });
        } else {
            $("#loginModal").modal();
        }
    });

    if ($(".question-id").length > 0) {
        $.get("/question/viewcount", {id: $(".question-id").attr("data-question-id")});
    }


    if ($(".article-content").length > 0) {
        $.get("/article/viewcount", {id: $(".article-content").attr("data-info-id")});
    }

    if ($(".share-content").length > 0) {
        $.get("/share/viewcount", {id: $(".share-content").attr("data-info-id")});
    }
});