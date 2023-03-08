define(function (require, exports, module) {
    J = jQuery;
    require('prettify');
    require('ckeditor');
    require('layer');
    layer.config({
        path: '/dist/vendors/layer' //layer.js所在的目录，可以是绝对目录，也可以是相对目录
    });

    $(document).ready(function () {
        $("pre").addClass("prettyprint linenums");
        prettyPrint();
    });

    let editor;
    $("#answer-add,.answer-btn").on("click", function () {
        if (window.app.LOGIN_TOKEN != undefined && window.app.LOGIN_TOKEN.id.trim() != '') {
            if ($(".answer-add").length < 1) {
                $(".question-content").before("<div class=\'answer-add\'><div  class=\'user\'><div class=\'author-avatar\'><a href=\'/people/" + window.app.LOGIN_TOKEN.id + "\' target=\'_blank\'><img class=\'avatar-38 mr-10 hidden-xs\' src=\'" + window.app.LOGIN_TOKEN.avatar + "\' alt=\'" + window.app.LOGIN_TOKEN.name + "\'></a></div><div class=\'author-content\'><div class=\'author-name\'>" + window.app.LOGIN_TOKEN.name + "</div><div class=\'author-nick\'>" + window.app.LOGIN_TOKEN.signature + "</div></div></div><div class=\'form-group mt-10\'><div style=\'position: relative;\'><div id=\'content\'></div></div></div><div class=\'row mt-20\'><div class=\'answer-footer\'><button type=\'submit\' class=\'btn btn-primary pull-right btn-answer\'>提交答案</button></div></div></div>");
                CKEDITOR.replace('content', {
                    height: 300
                });
            }
        } else {
            window.location.href = "/login";
        }
    });

    $(document).on('click', '.btn-answer', function () {
        if (window.app.LOGIN_TOKEN != undefined && window.app.LOGIN_TOKEN.id.trim() != '') {
            let questionId = $(".question-id").attr("data-question-id");
            let content = CKEDITOR.instances.content.getData();
            if (content == "") {
                e.preventDefault();
                layer.msg("内容不能为空", {icon: 5, time: 1000});
            }
            $.ajax({
                type: "post",
                dataType: "json",
                url: "/question/answer/add",
                data: {"questionId": questionId, "content": content},
                success: function (result) {
                    if (result.code === 0) {
                        layer.msg("答案提交成功！", {icon: 1});
                        window.location.reload();
                        return false;
                    } else {
                        layer.msg(result.msg, {icon: 5});
                        return false;
                    }
                }
            })
        } else {
            window.location.href = "/login";
        }
    });

    $(".question-list-item").on("mouseenter", function () {
        let $this = $(this);
        $this.find(".right-close").show();
    }).on("mouseleave", function () {
        let $this = $(this);
        $this.find(".right-close").hide();
    });

    $(".all-content").on("click", function () {
        let display = $(".hidden-content").css('display');
        if (display == 'none') {
            $(".hidden-content").show();
            $(".shrink-content").show();
            $(".show-content").hide();
        }
    });

    $(".shrink-content").on("click", function () {
        let display = $(".show-content").css('display');
        if (display == 'none') {
            $(".show-content").show();
            $(".hidden-content").hide();
            $(".shrink-content").hide();
        }
    });

});