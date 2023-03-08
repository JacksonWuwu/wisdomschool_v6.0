<div class="commentAll">
    <div id="comment-box" class="comment-box common-shadow">
        <@commentPage typeId="${article.id}" parentId="0" type="4">
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
                                    <a href="#" class="comment-size-name">${row.user.userName}: </a>
                                    <span class="my-pl-con comment-content-cc">${row.content}</span>
                                </div>
                                <div class="date-dz">
                                    <span class="date-dz-left pull-left comment-time">${row.createTime?string('yyyy-MM-dd HH:mm:ss')}</span>
                                    <div class="date-dz-right pull-right comment-pl-block">
                                        <a href="javascript:void(0);"
                                           class="date-dz-pl pl-hf hf-con-block pull-left"
                                           onclick="commentTo('${row.id}', '${row.id}', '${userinfo.user.userName}')">回复</a>
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
                                                       onclick="commentTo('${row.id}', '${ch.id}', '${ch.user.userName}')">回复</a>
                                                    <span class="pull-left date-dz-line">|</span>
                                                    <a href="javascript:;" class="date-dz-z pull-left">
                                                        <i class="date-dz-z-click-red"></i>赞 (<i class="z-num">0</i>)
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
                              class="content comment-input comment-editor-text" placeholder="请输入内容"></textarea>
                    <input type="hidden" value="0" name="comment-pid" id="comment-pid"/>
                    <input type="hidden" value="0" name="comment-replyid" id="comment-replyid"/>
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