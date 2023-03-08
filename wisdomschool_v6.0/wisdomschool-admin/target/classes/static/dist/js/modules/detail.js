define(function (require, exports, module) {
    require('plugins');
    require('layer');
    require('page');
    let authc = require('authc');
    let firstPage = 1;

    require.async('highlight', function () {
        $(document).ready(function () {
            $('pre').not($(".comment-editor-text")).each(function (i, block) {
                hljs.highlightBlock(block);
            });
        });
    });

    let tools = {
        //表情
        emoticon: {
            "微笑": "smile",
            "撇嘴": "curl",
            "色": "color",
            "发呆": "trance",
            "得意": "proud",
            "流泪": "tears",
            "害羞": "shy",
            "闭嘴": "shut",
            "睡": "sleep",
            "大哭": "crying",
            "尴尬": "embarrass",
            "发怒": "torment",
            "调皮": "naughty",
            "龇牙": "growl",
            "惊讶": "surprise",
            "难过": "sad",
            "酷": "cool",
            "冷汗": "cold",
            "抓狂": "crazy",
            "吐": "spit",
            "偷笑": "titter",
            "可爱": "lovely",
            "白眼": "whiteeye",
            "傲慢": "arrogant",
            "饥饿": "hunger",
            "困": "sleepy",
            "惊恐": "panic",
            "流汗": "sweating",
            "憨笑": "mirth",
            "大兵": "soldier",
            "奋斗": "fight",
            "咒骂": "curse",
            "疑问": "doubt",
            "嘘…": "hiss",
            "晕": "Halo",
            "折磨": "torture",
            "衰": "wane",
            "骷髅": "skeleton",
            "敲打": "beating",
            "再见": "goodbye",
            "擦汗": "wipe",
            "抠鼻": "pullnose",
            "鼓掌": "applause",
            "糗大了": "humiliating",
            "坏笑": "grin",
            "左哼哼": "lefthum",
            "右哼哼": "righthum",
            "哈欠": "yawn",
            "鄙视": "despise",
            "委屈": "aggrieved",
            "快哭了": "gonnacry",
            "阴险": "sinister",
            "亲亲": "kiss",
            "吓": "scared",
            "可怜": "poor",
            "菜刀": "cookknife",
            "西瓜": "watermelon",
            "啤酒": "beer",
            "篮球": "basketball",
            "兵乓": "pingpang",
            "咖啡": "coffee",
            "饭": "rice",
            "猪头": "pig",
            "玫瑰": "rose",
            "凋谢": "withered",
            "示爱": "affection",
            "爱心": "heart",
            "心碎": "brokenheart",
            "蛋糕": "cake",
            "闪电": "lighting",
            "炸弹": "bomb",
            "刀": "knife",
            "足球": "soccer",
            "瓢虫": "ladybug",
            "便便": "shit",
            "月亮": "moon",
            "太阳": "sun",
            "礼物": "gift",
            "拥抱": "hug",
            "强": "strong",
            "弱": "weak",
            "握手": "hands",
            "胜利": "victory",
            "抱拳": "holdfist",
            "勾引": "seduce",
            "拳头": "fist",
            "差劲": "bad",
            "爱你": "loveu",
            "NO": "NO",
            "OK": "OK"
        },
        url: function (path) {
            return app.base + path;
        },

        template: '<div class="comment-show-con clearfix row comment-{0}">' +
            '           <div class="comment-show-con-img col-md-2">' +
            '	            <img src="images/header-img-comment_03.png" alt="">' +
            '	        </div>' +
            '           <div class="comment-show-con-list col-md-10">' +
            '               <div class="pl-text clearfix">' +
            '                   <a href="#" class="comment-size-name">{1} : </a>' +
            '                   <span class="my-pl-con">&nbsp;{2}</span>' +
            '               </div>' +
            '               <div class="date-dz">' +
            '                   <span class="date-dz-left pull-left comment-time">{3}</span>' +
            '                   <div class="date-dz-right pull-right comment-pl-block">' +
            '                       <a href="javascript:;" onclick="commentTo({5}, {6}, {0}, \'{1}\')" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>' +
            '                       <span class="pull-left date-dz-line">|</span>' +
            '                       <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">0</i>)</a>' +
            '                   </div>' +
            '               </div>' +
            '               <div class="hf-list-con">{4}</div>' +
            '           </div>' +
            '      </div>',
        replyTemplate: '<div class="all-pl-con">' +
            '      <div class="pl-text hfpl-text clearfix">' +
            '            <a href="' + app.base + '/users/{0}" class="comment-size-name">{1} : </a>' +
            '            <span class="my-pl-con">回复 @<a href="#" class="atName">{2} </a> : </span>' +
            '            <span class="comment-content-cc">{3}</span>' +
            '      </div>' +
            '      <div class="date-dz"> ' +
            '            <span class="date-dz-left pull-left comment-time">{4}</span>' +
            '            <div class="date-dz-right pull-right comment-pl-block">' +
            '                  <a href="javascript:;" onclick="commentTo({5}, {6}, {0}, \'{1}\')" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>' +
            '                  <span class="pull-left date-dz-line">|</span>' +
            '                  <a href="javascript:;" class="date-dz-z pull-left">' +
            '                        <i class="date-dz-z-click-red"></i>赞 (<i class="z-num">0</i>)' +
            '                  </a> ' +
            '            </div>' +
            '      </div>' +
            '</div>',
        contentRender: {
            //正则 - 匹配特殊内容
            CONT_EXP: /(\[[…\u00FF-\uFFFF]{1,3}\]|.+?)/g,
            CONT_BR: /\r\n/g,
            CONT_NBSP: /[ ]/g,
            //转换特殊内容
            purify: function () {
                let k;
                let args = arguments[0];
                if (args.length === 1) {
                    switch (args.charAt(0)) {
                        case '<':
                            return '&lt;';
                        case '>':
                            return '&gt;';
                        default:
                            return args;
                    }
                } else {
                    switch (args.charAt(0)) {
                        case '#':
                            return "<a href='/t/" + args.slice(1, -1) + "'>" + args + "</a>";
                        case '/':
                            k = args.slice(3, -1);
                            return "//<a href='/k/u/name/" + k + "'>@" + k + "</a>:";
                        case '[':
                            k = args.slice(1, -1);
                            return "<img  class='_jphiz_icon' src='" + tools.url("/dist/image/emoticon/" + tools.emoticon[k] + ".gif") + "' alt='" + k + "' title='" + k + "'/>";
                        default:
                            return args;
                    }
                }
            }
        },
        wrapContent: function (content) {
            return content.replace(tools.contentRender.CONT_EXP, tools.contentRender.purify)
        },
        formatTemplate: function (template) {
            return $.format(template);
        }
    };

    let comment = {
        name: 'comment',
        contentRender: tools.contentRender,
        init: function (options) {
            this.options = $.extend({}, defaults, options);
            let result = {};
            if (this.options.totalPages != null && this.options.totalPages > 1) {
                result['totalPages'] = this.options.totalPages;
                result['number'] = 0;
                /*$('#pager').frontPage(result, $.proxy(this, 'pager'));*/
            }
            this.bindEvents();
            $('.comment-content-cc').each(function (i, content) {
                let text = tools.wrapContent($(content).text());
                $(this).html(text);
            });

        },
        bindEvents: function () {
            let that = this;
            $('#btn-comment-send').on('click', function () {
                let text = $('#editor-text').val();
                let pid = $('#comment-pid').val();
                let replyId = $('#comment-replyid').val();
                let replyUserId = $('#reply-user-id').val();
                that.post(that.options.toId, pid, replyId, replyUserId, text);
            });
        },
        reload: function () {
            this.page(firstPage);
        },
        //分页请求
        page: function (pageNum) {
            let that = this;
            let opts = this.options;
            let $list = $('#comment-container');
            let html = '';
            $.post(opts.loadUrl, {pageSize: opts.pageSize, pageNum: pageNum}, function (result) {
                let data = result.data;
                $('#comment-count').html(data.count);

                $.each(data.list, function (i, cont) {
                    let item = opts.load.call(this, i, cont);
                    html += item;
                });

                $list.empty().append(html);

                if (result.size < 1) {
                    $list.append('<li><p>还没有留言, 快来占沙发</p></li>')
                }

                if (data.pageCount > 1) {
                    result['totalPages'] = data.pageCount;
                    result['number'] = data.pageNum - 1;
                    /*$('#pager').page(result, $.proxy(that, 'page'));*/
                }
            }, 'json')
        },
        //提交评论
        post: function (toId, pid, replyId, replyUserId, text) {
            console.log(toId);
            let opts = this.options;
            let that = this;

            if (!authc.isAuthced()) {
                layer.msg('您还没登陆！请先登录', {icon: 5});
                return;
            }

            if (text.length <= 0) {
                layer.msg('评论内容不能为空', {icon: 5});
                return;
            }

            if (text.length > 500) {
                layer.msg('评论内容不能超过255个字符', {icon: 5});
                return;
            }

            $.ajax({
                url: opts.postUrl,
                data: {
                    'typeId': toId,
                    'parentId': pid,
                    'replyId': replyId,
                    'replyUserId': replyUserId,
                    'content': text
                },
                dataType: 'json',
                type: 'post',
                cache: false,
                async: true,
                error: function () {
                    layer.msg('评论出错', {icon: 2});
                },
                success: function (result) {
                    if (result.code >= 0) {
                        layer.msg(result.msg, {icon: 6});
                        $('#editor-text').val('');
                        $('#comment-pid').val(0);
                        $('#comment-reply-target').hide();
                        $('.char-number').html(0);
                        that.page(firstPage);
                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            })
        }
    };
    let defaults = {
        loadUrl: null,
        postUrl: null,
        toId: 0,
        pageSize: 8,
        totalPages: null,
        number: null,
        //加载数据
        load: function (i, data) {

            let content = tools.wrapContent(data.content);
            let quote = '';
            if (data.children != null) {
                let children = data.children;
                console.log(children);
                let parentName;
                $.each(children, function (i, c) {
                    parentName = c.parentUser === null || c.parentUser === undefined ? '' : c.parentUser.userName;
                    parentName = parentName === null || parentName === undefined ? '' : parentName;
                    quote += $.format(tools.replyTemplate, c.user.id, c.user.userName, parentName, tools.wrapContent(c.content), c.createTime, c.parentId, c.id);
                });
            }

            return $.format(tools.template, data.user.id, data.user.userName, content, data.createTime, quote, data.id, data.id);
        }
    };

    $('#collection').on('click', function () {
        if (!authc.isAuthced()) {
            layer.msg('您还没登陆！请先登录', {icon: 5});
            return;
        }
        let that = $(this);
        let action = that.attr('data-action');
        let id = that.data('id');
        console.log(action);
        if (action == 0) {
            $.getJSON(app.base + '/user/favors/' + id, function (result) {
                if (result.code >= 0) {
                    layer.msg(result.msg, {icon: 6});
                    that.attr("data-action", "1");
                    that.addClass('active');
                } else {
                    layer.msg(result.msg, {icon: 5});
                }
            });
        } else {
            $.getJSON(app.base + '/user/unfavor/' + id, function (result) {
                if (result.code >= 0) {
                    layer.msg(result.msg, {icon: 6});
                    that.attr("data-action", "0");
                    that.removeClass('active');
                } else {
                    layer.msg(result.msg, {icon: 5});
                }
            });
        }
        return;
    });

    $('#collection').each(function () {
        let that = $(this);
        let id = that.data('id');

        if (parseInt(id) > 0) {
            jQuery.getJSON(app.base + '/user/checkFavor/' + id, function (result) {
                if (result.code >= 0) {
                    that.attr("data-action", "1");
                    that.addClass('active');
                }
            });
        }
    });

    exports.init = function (opts) {
        comment.init(opts)
    }
});