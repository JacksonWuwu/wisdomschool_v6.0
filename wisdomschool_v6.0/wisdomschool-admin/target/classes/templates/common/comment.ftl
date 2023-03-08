<style>
    /*清除浮动*/
    .commentAll .clearfix:before,
    .commentAll .clearfix:after {
        content: " ";
        display: table;
    }

    .commentAll .clearfix:after {
        clear: both;
    }

    .commentAll .clearfix {
        zoom: 1;
    }

    /*浮动*/
    .commentAll .pull-right {
        float: right !important;
    }

    .commentAll .pull-left {
        float: left !important;
    }

    /*----------------------------------------------------------------------------------------------------------*/
    .commentAll .content {
        float: left;
    }

    .commentAll .flex-text-wrap, .commentAll pre {
        margin: 0 !important;
    }

    .commentAll .commentAll {
        width: 500px;
        padding: 20px;
        border: 1px solid #ededed;
        margin: 20px auto;
    }

    /*----------评论区域 begin----------*/
    .commentAll .comment-show {
        margin-top: 20px;
    }

    .commentAll .comment-show-con {
        width: 100%;
        border-top: 1px solid #EDEDED;
        padding: 10px 0;
    }

    .commentAll .comment-show-con-img {
        width: 48px;
        height: 48px;
        overflow: hidden;
        margin-top: 5px;
        margin-left: 3%;
    }

    .commentAll .comment-show-con-list {
        width: 85%;
    }

    .commentAll .pl-text {
        width: 100%;
        margin-top: 7px;
        word-wrap: break-word;
        overflow: hidden;
    }

    .commentAll .date-dz {
        width: 100%;
        float: left;
    }

    .commentAll .hf-list-con {
        float: left;
        width: 100%;
        background-color: #eaeaec;
        margin-top: 7px;
    }

    .commentAll .comment-size-name {
        font-size: 14px;
        color: #339b53;
    }

    .commentAll .my-pl-con {
        font-size: 15px;
        color: #8b8b8b;
        width: 100%;
    }

    .commentAll .date-dz-left {
        font-size: 13px;
        color: #8b8b8b;
        display: block;
        padding-top: 18px;
    }

    .commentAll .comment-time, .comment-pl-block {
        padding-top: 7px;
    }

    .commentAll .comment-pl-block {
        margin-top: 0;
    }

    .commentAll .date-dz-right {
        display: block;
        padding-top: 6px;
        padding-right: 18px;
        position: relative;
        overflow: hidden;
    }

    .commentAll .removeBlock {
        float: left;
        font-size: 13px;
        color: #8b8b8b;
        margin-right: 24px;
        display: block;
        opacity: 0;
    }

    .commentAll .hf-con-block {
        display: block;
    }

    .commentAll .date-dz-pl, .commentAll .date-dz-line, .commentAll .date-dz-z {
        font-size: 13px;
        color: #8b8b8b;
    }

    .commentAll .date-dz-line {
        display: block;
        padding: 0 0 0 20px;
    }

    .commentAll .date-dz-z-click-red {
        width: 17px;
        height: 17px;
        display: block;
        float: left;
        background-image: url(../images/icon-all_01.png);
        background-repeat: no-repeat;
        background-position: -6px -198px;
        margin-right: 5px;
    }

    .commentAll .z-num {
        font-style: normal;
    }

    .commentAll .date-dz-z-click {
        color: #b83b44;
    }

    .commentAll .red {
        background-position: -6px -119px !important;
    }

    .commentAll .hf-pl {
        width: 70px;
        height: 30px;
        line-height: 30px;
        background-color: #339b53;
        text-align: center;
        display: block;
        float: right;
        color: #FFFFFF;
        font-size: 12px;
        border-radius: 6px;
        margin-right: 2px;
        margin-top: 20px;
    }

    .commentAll .hf-con {
        width: 100%;
        margin-top: 24px;
    }

    .commentAll .hf-input {
        font-size: 12px;
    }

    .commentAll .all-pl-con {
        width: 96%;
        padding: 2% 0;
        float: left;
        margin: 0 2%;
    }

    .commentAll .atName {
        font-size: 14px;
        color: #339b53;
    }

    .commentAll .hfpl-text {
        margin-top: 0;
    }

    .commentAll .date-dz:hover .removeBlock {
        opacity: 1;
    }

    .commentAll .hf-list-con .all-pl-con {
        border-top: 1px solid #d9d9d9;
        padding-bottom: 12px;
    }

    .commentAll .hf-list-con .all-pl-con:first-child {
        border-top: 0;
    }

    .commentAll pre {
        white-space: pre;
        white-space: pre-wrap;
        word-wrap: break-word;
    }

    .commentAll .flex-text-wrap {
        width: 100%;
        position: relative;
        *zoom: 1;
    }

    .commentAll textarea,
    .commentAll .flex-text-wrap {
        outline: 0;
        margin: 0;
        border: none;
        padding: 0;
        *padding-bottom: 0 !important;
    }

    .commentAll .flex-text-wrap textarea,
    .commentAll .flex-text-wrap pre {
        *white-space: pre;
        *word-wrap: break-word;
        white-space: pre-wrap;
        width: 100%;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        -ms-box-sizing: border-box;
        box-sizing: border-box;
    }

    .commentAll .flex-text-wrap textarea {
        overflow: hidden;
        position: absolute;
        top: 0;
        left: 0;
        height: 100%;
        width: 100%;
        resize: none;
        /* IE7 box-sizing fudge factor */
        *height: 94%;
        *width: 94%;
    }

    .commentAll .flex-text-wrap pre {
        display: block;
        visibility: hidden;
    }

    .commentAll .flex-text-wrap,
    .commentAll textarea {
        margin-bottom: 0px
    }

    .commentAll textarea,
    .commentAll .flex-text-wrap pre {
        line-height: 1.7;
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        font-size: 100%;
        padding: 10px 15px;
        border: 1px solid #c6c8ce;
        width: 100%;
        -webkit-appearance: none;
        background: #fff;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        -moz-background-clip: padding;
        -webkit-background-clip: padding-box;
        background-clip: padding-box;
        -webkit-box-shadow: 0 0 8px rgba(182, 195, 214, .6) inset, 0 1px 1px #fff;
        -moz-box-shadow: 0 0 8px rgba(182, 195, 214, .6) inset, 0 1px 1px #fff;
        box-shadow: 0 0 8px rgba(182, 195, 214, .6) inset, 0 1px 1px #fff;
        -webkit-transition-duration: 300ms;
        -moz-transition-duration: 300ms;
        -o-transition-duration: 300ms;
        -ms-transition-duration: 300ms;
        transition-duration: 300ms;
        -webkit-transition-easing: ease-in-out;
        -moz-transition-easing: ease-in-out;
        -o-transition-easing: ease-in-out;
        -ms-transition-easing: ease-in-out;
        transition-easing: ease-in-out;
        -webkit-transition-property: border-color, -webkit-box-shadow;
        -webkit-transition-property: border-color, box-shadow;
        -moz-transition-property: border-color, -moz-box-shadow;
        -moz-transition-property: border-color, box-shadow;
        -o-transition-property: border-color, box-shadow;
        -ms-transition-property: border-color, box-shadow;
        transition-property: border-color, box-shadow;
    }

    .commentAll .fork-link {
        display: block;
        position: absolute;
        top: 0;
        right: 0;
        width: 140px;
    }

    /*----------评论区域 end----------*/
</style>
<div class="commentAll">
    <div id="comment-box" class="comment-box common-shadow">
        <@commentPage typeId="${typeId}" parentId="0" type="${type}">
            <div class="comment-title">
                <h4>全部评论: <i id="comment-count">${commentpage.count}</i> 条</h4>
            </div>
            <ul id="comment-container" class="comment-container">
                <input type="hidden" value="${commentpage.count}" id="pcount">
                <#if commentpage?? && (commentpage.list?size > 0) >
                    <#list commentpage.list as row>

                        <div class="comment-show-con clearfix row" id="comment-${row.id}">
                            <div class="comment-show-con-img col-md-2"><img
                                        src="images/header-img-comment_03.png" alt=""></div>
                            <div class="comment-show-con-list col-md-10">
                                <div class="pl-text clearfix">
                                    <a href="#"
                                       class="comment-size-name">${row.user.userName}: </a>
                                    <span class="my-pl-con comment-content-cc">${row.content}</span>
                                </div>
                                <div class="date-dz">
                                    <span class="date-dz-left pull-left comment-time">${row.createTime?string('yyyy-MM-dd HH:mm:ss')}</span>
                                    <div class="date-dz-right pull-right comment-pl-block">
                                        <a href="javascript:void(0);"
                                           class="date-dz-pl pl-hf hf-con-block pull-left"
                                           onclick="commentTo('${row.id}', '${row.id}', '${row.user.id}', '${row.user.userName}')">回复</a>
                                        <span class="pull-left date-dz-line">|</span>
                                        <a href="javascript:void(0);"
                                           class="date-dz-z pull-left"><i
                                                    class="date-dz-z-click-red"></i>赞 (<i
                                                    class="z-num">0</i>)</a>
                                    </div>
                                </div>
                                <div class="hf-list-con">
                                    <#list row.children as ch>
                                        <div class="all-pl-con">
                                            <div class="pl-text hfpl-text clearfix">
                                                <a href="${base}/users/${ch.user.id}"
                                                   class="comment-size-name">${ch.user.userName}</a>
                                                <span class="my-pl-con">回复 @<a href="#"
                                                                               class="atName">${ch.parentUser.userName} </a> : </span>
                                                <span class="comment-content-cc">${ch.content}</span>
                                            </div>
                                            <div class="date-dz">
                                                <span class="date-dz-left pull-left comment-time">${ch.createTime?string('yyyy-MM-dd HH:mm:ss')}</span>
                                                <div class="date-dz-right pull-right comment-pl-block">
                                                    <a href="javascript:;" class="removeBlock">删除</a>
                                                    <a href="javascript:;"
                                                       class="date-dz-pl pl-hf hf-con-block pull-left"
                                                       onclick="commentTo('${row.id}', '${ch.id}', '${ch.user.id}', '${ch.user.userName}')">回复</a>
                                                    <span class="pull-left date-dz-line">|</span>
                                                    <a href="javascript:;"
                                                       class="date-dz-z pull-left">
                                                        <i class="date-dz-z-click-red"></i>赞 (<i
                                                                class="z-num">0</i>)
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                            </div>
                            <#--</@userInfo>-->
                        </div>
                    </#list>
                <#else >
                    <li><p>还没有留言, 快来占沙发</p></li>
                </#if>
            </ul>
        </@commentPage>
        <div id="pager" class="text-center">
        </div>
        <a name="comment-editor"></a>
        <div class="editor-wrap">
            <div class="editor-header">我来说一句: <span id="comment-reply-target"
                                                    style="display:none;"> @<i
                            id="reply-target"></i></span><span class="pull-right "
                                                               style="margin-top: 5px; font-size: 13px;"><em
                            class="char-number">0</em>/500</span>
            </div>
            <div class="editor-container">
                <div class="editor-content">
                                            <textarea id="editor-text" rows="9" onkeyup="keyUP(this)"
                                                      class="content comment-input comment-editor-text"
                                                      placeholder="请输入内容"></textarea>
                    <input type="hidden" value="0" name="comment-pid" id="comment-pid"/>
                    <input type="hidden" value="0" name="comment-replyid" id="comment-replyid"/>
                    <input type="hidden" name="comment-reply-user-id" id="reply-user-id"/>
                </div>
                <div class="editor-attr clearfix">
                    <div class="attr-func">
                        <ul class="func-list">
                            <li class="list-b">
                                <a href="javascript:void(0);" class="join" id="c-btn"><i
                                            class="fa fa-smile-o fa-2"></i></a>
                            </li>
                            <li class="list-b">
                                <div class="comment-send">
                                    <button id="btn-comment-send"
                                            class="btn btn-success btn-sm bt">
                                        发送
                                    </button>
                                </div>
                            </li>
                        </ul>
                    </div>

                </div>
            </div>
            <div class="phiz-box emoticon-box" id="c-phiz" style="display:none">
                <div class="phiz-list emoticon-list" view="c-phizs"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    function commentTo(pid, replyid, replyUserId, user) {
        document.getElementById('editor-text').scrollIntoView();
        $('#editor-text').focus();
        $('#editor-text').val('');
        $('#reply-target').text(user);
        $('#comment-pid').val(pid);
        $('#comment-replyid').val(replyid);
        $('#reply-user-id').val(replyUserId);
        $('#comment-reply-target').show();
    };

    seajs.use('detail', function (comment) {
        let pcount = $("#pcount").val();
        comment.init({
            loadUrl: '${base}/<#if type==1>article<#else >user/course</#if>/comments/${typeId}',
            postUrl: '${base}/<#if type==1>article<#else >user/course</#if>/comment/submit',
            toId: '${typeId}',
            totalPages: pcount
        });
    });

    seajs.use('jphiz', function () {
        $("#c-btn").jphiz({
            base: '${base}/dist',
            textId: 'editor-text',
            lnkBoxId: 'c-lnk',
            phizBoxId: 'c-phiz'
        });
    });

    seajs.use('commentReply');

    /*<!--textarea限制字数-->*/
    function keyUP(t) {
        let len = $(t).val().length;
        if (len > 500) {
            $(t).val($(t).val().substring(0, 500));
        }
        $(".char-number").html($(t).val().length);
    }
</script>