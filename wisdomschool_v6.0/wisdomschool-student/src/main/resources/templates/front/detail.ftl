<link rel="stylesheet" href="/css/course-main.css">
<link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
<script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
<link rel="stylesheet" href="/css/forum.css">
<link rel="stylesheet" href="/css/editor.css">
<link rel="stylesheet" href="/js/plugins/layer/theme/default/layer.css"/>

<style type="text/css">
    blockquote {
        border-left: none;
    }

    .discuss-title {
        background: url(/img/front/ico-discuss.png) no-repeat;
        font-size: 16px;
        line-height: 1.5;
        font-weight: 400;
        padding-left: 30px;
        margin-bottom: 20px;
    }

    .post-time {
        color: #999;
        font-size: 12px;
        float: left;
    }

    .discuss-topic-head {
        border-bottom: 1px solid #ddd;
        line-height: 1.5;
        padding: 27px;
    }

    .media-footer a {
        margin-right: 10px;
    }

    .M-box a:HOVER {
        background: #00BC9B;
    }

    .M-box .active {
        float: left;
        margin: 0 5px;
        width: 38px;
        height: 38px;
        line-height: 38px;
        background: #00BC9B;
        color: #fff;
        font-size: 14px;
        border: 1px solid #00BC9B;
    }

    .replye_boday {
        border-bottom: 1px solid #E0E0E0;
        padding-bottom: 15px;
        position: relative;
        padding-top: 15px;
        overflow: hidden;
        zoom: 1;
    }

    .replye_div {
        float: none;
        width: auto;
    }

    .head_a {
        text-decoration: none;
        display: block;
        float: left;
        width: 50px;
        height: 50px;
        border: 1px solid #EDEDED;
        overflow: hidden;
        margin-right: 15px;
        -webkit-border-radius: 25px;
        -moz-border-radius: 25px;
        border-radius: 25px;
        margin-left: 15px;
    }

    .head_a_img {
        width: 50px;
        display: block;
        -ms-interpolation-mode: bicubic;
        border: 0;
    }

    .replye_div_div {
        width: 88%;
        float: left;
        line-height: 1em;
        padding-left: 15px;
    }

    .replye_div_div_p {
        line-height: 1.4em;
        margin: 0;
        padding: 0;
        font-weight: 400;
    }

    .replye_div_div_p_author {
        margin-bottom: 5px;
        display: inline-block;
        *zoom: 1;
        *display: inline;
        text-decoration: none;
        color: #fd8321;
        line-height: 1.4em;
        font-weight: 400;
    }

    .replye_div_div_p_floor {
        font-weight: 400;
        line-height: 1.4em;
        padding: 1px 12px;
        border-radius: 3px;
        color: #FFF;
        font-size: 14px;
        background: #DCDADC;
        float: right;
    }

    .replye_div_connent {
        color: #666;
        font-size: 14px;
        line-height: 2;
    }

    .replye_div_infor {
        margin-top: 10px;
        color: #999;
        font-size: 12px;
        line-height: 20px;
    }

    .replye_div_infor_time {
        margin-top: 10px;
        color: #999;
        font-size: 12px;
        line-height: 20px;
    }

    .replye_div_infor_reply {
        float: right;
        color: #21a88b;
        margin-left: 20px;
        text-decoration: none;
        font-size: 12px;
        line-height: 20px;
    }

    .deck_box {
        display: none;
        padding: 15px;
        margin: 15px 0 5px;
        color: #666666;
        background: #eff0f2;
        line-height: 1em;
        font: 12px/1.5 arial, STHeiti, 'Microsoft YaHei', ??????;
        word-wrap: break-word;
        line-height: 1.5;
    }

    .deck-list {
        margin-top: 15px;
    }

    .deck_floor {
        padding-bottom: 10px;
        margin-bottom: 10px;
        border-bottom: 1px solid #E0E0E0;
    }

    .deck_information {
        line-height: 1.5;
        display: block;
        margin-bottom: 10px;
    }

    .deck_person {
        line-height: 1.5;
        display: inline;
        white-space: nowrap;
        margin-right: 5px;
    }

    .deck_person_a {
        white-space: nowrap;
        color: #fd8321;
        text-decoration: none;
        max-width: 13em;
        overflow: hidden;
        text-overflow: ellipsis;
        display: inline-block;
        vertical-align: middle;
        margin-top: -3px;
    }

    .deck_content {
        color: #666;
        display: inline;
        line-height: 2;
        vertical-align: middle;
    }

    .deck_bottm {
        margin-top: 10px;
        font-size: 12px;
        line-height: 20px;
        color: #999;
    }

    .deck_time {
        font-size: 12px;
        line-height: 20px;
        color: #999;
    }

    .deck_a {
        font-size: 12px;
        line-height: 20px;
        text-decoration: none;
        float: right;
        color: #21a88b;
        margin-left: 20px;
    }

    .deck_reply {
        overflow: hidden;
        display: block;
        position: relative;
        padding-top: 15px;
        margin-bottom: 15px;
        margin-top: 10px;
    }

    .deck_reply_textarea {
        border: 1px solid #d4d4d4;
        color: #34495e;
        font-family: Lato, sans-serif;
        border-radius: 3px;
        font-style: inherit;
        font-weight: inherit;
        outline: 0;
        font-size: 12px;
        height: 50px;
        line-height: 18px;
        padding: 5px;
        margin: 0;
        resize: none;
        overflow: hidden;
        resize: none;
        width: 100%;
    }

    .deck_reply_button {
        display: inline-block;
        text-align: center;
        font-size: 14px;
        border-radius: 3px;
        border: none 0;
        cursor: pointer;
        outline: 0;
        white-space: nowrap;
        text-decoration: none;
        color: #FFF;
        background: #00B091;
        width: 75px;
        height: 30px;
        line-height: 30px;
        padding: 0;
        float: right;
        margin-top: 10px;
    }
</style>
<#include '/front/inc/commonJs.ftl'/>
<div id="auto-id-1555802290742" <#--style="padding-top: 60px;"--> class="auto-1555802290734-parent"
     cz-shortcut-listen="true">
    <div class="g-headwrap" id="j-fixed-head" data-log-id="topNav"
         data-log-data="{&quot;pageName&quot;:&quot;????????????&quot;}">
        <div class="m-yktnav " id="j-topnav">
            <div class="m-yktnav_wrap f-pr f-cb">
                <div class="m-breadcrumbBox f-cb" id="j-breadcrumbBox">
                    <div class="g-mn2">
                        <div class="g-flow f-cb">
                            <ul class="g-flow">
                                <li class="navcrumb-item">
                                    <a target="_blank" href="/admin">????????????</a>
                                </li>
                                <li class="navcrumb-item current" id="navcrumbId-2">
                                    <span class="arrow"></span>
                                    <a href="javascript: void(0);">${course.name }</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="g-flow">
                    <div class="b-20"></div>
                    <div>
                        <div class="f-bg headBox" id="courseHead">
                            <div class="u-courseHead" id="auto-id-1555802291070">
                                <#include '/front/courseinfo.ftl'/>
                                <div class="j-courseheadTab">
                                    <ul class="f-cb tabarea">
                                        <li class="f-fl"><a class="j-chtab" hidefocus="true"
                                                            href="/user/course/learnone/${tcid}">??????</a></li>
                                        <!--Regular if107-->
<#--                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true">?????????</a></li>-->
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="b-15"></div>
                    <div class="m-forumIndex f-cb">
                        <div class="g-cimn2">
                            <div class="g-cimn2c f-bg f-cb" id="forum-box">
                                <div class="u-forumbread">
                                    <a href="/user/courseTopicList/${tcid}">?????????</a>
                                    <span class="sp"></span>
                                    <#--<a href="https://study.163.com/forum/index.htm?cid=1004075068&amp;fid=1002893099&amp;p=1">???????????????</a>
                                    <span class="sp"></span>-->
                                    <span>????????????</span>
                                </div>
                                <div style="background: white;padding-top: 20px;padding-left: 30px;padding-right: 30px;">

                                    <input id="topicId" value="${topic.id}" type="hidden">

                                    <h1 class="discuss-title">${topic.title}</h1>

                                    <div style="margin-bottom: 22px;margin-top: 9px;">
                                        <div class="pull-left">
                                            <p style="color:#999">
							<span style="display:block;"> <a style="color:#fd8321">${topic.createName}
							</a>
							</span> <span class="post-time"> ????????? ${topic.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                            </p>
                                        </div>
                                        <div class="pull-right">
                                            <p style="color:#999">
							<span> "??????" <span>${topic.replyCount}</span>
							</span> <span>|</span> <span> "???" <span>${topic.thumbsUp}</span>
							</span> <span>|</span> <span> "??????" <span>${topic.browse}</span>
							</span>
                                            </p>
                                        </div>
                                        <div class="discuss-topic-head"></div>
                                    </div>

                                    <div>
                                        <p>${topic.content}</p>
                                        <div style="margin-right: 30px" class="pull-right">
                                            <p style="color:#999;font-size: 14px">
							<span> <span class="glyphicon glyphicon-thumbs-up"></span>
								<a href="#" id="topic_like">???</a>
							</span>
                                                <!-- 							<span><span class="glyphicon glyphicon-edit"></span>
                                                    ??????
                                                </span> -->
                                            </p>
                                        </div>
                                        <div style="margin-bottom: 8px;padding: 17px"></div>
                                    </div>
                                    <!-- ????????? -->
                                </div>

                                <!-- ???????????? -->

                                <#if !(pageModel.total > 0 || replyAdopt??)>
                                    <div style="background: white;padding-left:15px;padding-right:15px;">
                                        <div style="text-align:center;padding:50px">
                                            <div class="center-block" style="width:200px">
                                                <img src="/img/front/10673188_093000910000_2.png" width="200px">
                                            </div>
                                            ???????????????????????????????????????
                                        </div>
                                    </div>
                                </#if>


                                <!-- ???????????? -->
                                <#if replyAdopt??>
                                    <div class="answerAN-tit" style="background: white;height:30px;padding: 30px;">
                                        <img src="/img/front/answer.png"/>
                                        <h3 style="color: #10AE58;margin: -10px;padding-top:2px;">????????????</h3>
                                    </div>
                                    <div class="replye_boday"
                                         style="background: white;margin-bottom:8px;padding-left: 30px;">
                                        <div class="replye_div">
                                            <a class="head_a">
                                                <#if replyAdopt.userType == 2>
                                                    <#if replyAdopt.user.headImage??>
                                                        <img src="${replyAdopt.user.headImage }" class="head_a_img"/>
                                                    <#else>
                                                        <img src="/img/front/small.jpg"
                                                             class="head_a_img"/>
                                                    </#if >
                                                <#else >
                                                    <img src="/img/front/timg.png"
                                                         class="head_a_img"/>
                                                </#if>
                                            </a>
                                            <div class="replye_div_div">
                                                <p class="replye_div_div_p">
                                                    <a class="replye_div_div_p_author">${replyAdopt.createName}</a>
                                                </p>
                                                <div class="replye_div_connent">${replyAdopt.content}</div>
                                                <div class="replye_div_infor">
                                                    <span class="replye_div_infor_time">????????? ${replyAdopt.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                    <a class="replye_div_infor_reply"
                                                       onclick="showDeckReply('${replyAdopt.id}','${replyAdopt.createBy}','${replyAdopt.createName}','${replyAdopt.userType}','0')">??????(5)</a>
                                                    <a class="replye_div_infor_reply"
                                                       onclick="replyLike('${replyAdopt.id}')">???(${replyAdopt.thumbsUp!0}
                                                        )</a>
                                                    <div class="deck_box" id="deck_box_0">
                                                        <div class="deck-list">
                                                            <#list replyAdopt.decks as deck>
                                                                <div class="deck_floor" id="deck_box_0_${deck_index+1}">
                                                                    <div class="deck_information">
                                                                        <div class="deck_person">
                                                                            <a class="deck_person_a">${deck.createName}</a>
                                                                            <#if deck.toUserId != replyAdopt.createBy>
                                                                                &nbsp;??????&nbsp;<a
                                                                                    class="deck_person_a">${deck.toUserName}</a>
                                                                            </#if>
                                                                            ???
                                                                        </div>
                                                                        <div class="deck_content">${deck.content}</div>
                                                                    </div>
                                                                    <div class="deck_bottm">
                                                                        <span class="deck_time">${deck.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                                        <a class="deck_a"
                                                                           onclick="showDeckReply('${replyAdopt.id}','${deck.createBy}','${deck.createName}','${deck.userType}','0_${deck_index+1}')">??????</a>
                                                                        <a class="deck_a"
                                                                           onclick="deckLike('${deck.id}')">???(${deck.thumbsUp!0}
                                                                            )</a>
                                                                    </div>
                                                                </div>
                                                            </#list>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </#if>


                                <!-- ???????????? -->
                                <#if pageModel.rows?? && (pageModel.rows?size > 0)>
                                    <div class="answerAN-tit" style="background: white;height:30px;padding:30px;">
                                        <h3 style="color: #10AE58;margin: -10px;padding-top:2px;">??????</h3>
                                    </div>
                                </#if>

                                <#list pageModel.rows as reply>
                                    <#if reply.adopt==0>
                                        <div class="replye_boday" style="background: white;padding-left: 30px;">

                                            <div class="replye_div">
                                                <a class="head_a">
                                                    <#if sessionScope.user.headImage != null>
                                                        <img src="${sessionScope.user.headImage}"
                                                             style="height: 55px;width: 55px" class="img-circle"
                                                             alt="User Image"/>
                                                    </#if>
                                                    <#if reply.userType==2>
                                                        <#if reply.user.headImage??>
                                                            <img
                                                                    src="${reply.user.headImage }"
                                                                    class="head_a_img"/>
                                                        <#else >
                                                            <img
                                                                    src="/img/front/small.jpg"
                                                                    class="head_a_img"/>
                                                        </#if>
                                                    <#else>
                                                        <img
                                                                src="/img/front/timg.png"
                                                                class="head_a_img"/>
                                                    </#if>
                                                </a>
                                                <div class="replye_div_div">
                                                    <p class="replye_div_div_p">
                                                        <a class="replye_div_div_p_author">${reply.createName}</a> <span
                                                                class="replye_div_div_p_floor">${reply_index + 1 +(pageModel.pageNum-1)*10}#</span>
                                                        <#assign userId>
<#--                                                            <@shiro.principal property='id'/>-->
                                                            ${sysUser.id}
                                                        </#assign>
                                                        <#if topic.createBy==userId&&!replyAdopt??>
                                                            <a class="replye_div_infor_reply" style="margin-right:20px;"
                                                               href="#" onclick="Adopt('${reply.id}')">+??????</a>
                                                            </span>
                                                        </#if>
                                                    </p>
                                                    <div class="replye_div_connent">${reply.content}</div>
                                                    <div class="replye_div_infor">
                                                        <span class="replye_div_infor_time">????????? ${reply.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                        <#if reply.createBy == userId>
                                                            <a class="replye_div_infor_reply" onclick="replyDelete('${reply.id}')">??????</a>
                                                        </#if>
                                                        <a class="replye_div_infor_reply"
                                                                onclick="showDeckReply('${reply.id}','${reply.createBy}','${reply.createName}','${reply.userType}','${reply_index + 1}')">
                                                            ??????(<#if reply.decks??>${reply.decks?size!0}<#else >0</#if>
                                                            )</a>
                                                        <a class="replye_div_infor_reply"
                                                           onclick="replyLike('${reply.id}')">???(${reply.thumbsUp!0})</a>
                                                    </div>
                                                    <div class="deck_box" id="deck_box_${reply_index + 1}">
                                                        <div class="deck-list">

                                                            <#list reply.decks as deck>
                                                                <div class="deck_floor"
                                                                     id="deck_box_${deck_index}_${deck_index+1}">
                                                                    <div class="deck_information">
                                                                        <div class="deck_person">
                                                                            <a class="deck_person_a">${deck.createName}</a>
                                                                            <#if test="${deck.toUserId != reply.createBy}">
                                                                                &nbsp;??????&nbsp;<a
                                                                                    class="deck_person_a">${deck.toUserName}</a>
                                                                            </#if>
                                                                            ???
                                                                        </div>
                                                                        <div class="deck_content">${deck.content}</div>
                                                                    </div>
                                                                    <div class="deck_bottm">
                                                                        <span class="deck_time">${deck.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                                        <#if deck.createBy == userId>
                                                                            <a class="deck_a" onclick="deckDelete('${deck.id}')">??????</a>
                                                                        </#if>
                                                                        <a class="deck_a"
                                                                           onclick="showDeckReply('${reply.id}','${deck.createBy}','${deck.createName}','${deck.userType}','${reply_index}_${deck_index+1}')">??????</a>
                                                                        <a class="deck_a"
                                                                           onclick="deckLike('${deck.id}')">???(${deck.thumbsUp!0}
                                                                            )</a>
                                                                    </div>
                                                                </div>
                                                            </#list>
                                                        </div>
                                                        <!-- js?????????????????? -->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </#if>
                                </#list>
                                <div class="col-md-12" style="background: white;margin-top: 0px">
                                    <!-- <script id="editor" type="text/plain" name="new_content"></script> -->
                                    <textarea class="layui-textarea layui-hide" id="LAY_demo1" style="display: none;">

                </textarea>
                                    <button style="background: #00BC9B" id="saveReply"
                                            class="pull-right btn btn-success">??????
                                    </button>
                                    <!-- ????????? -->
                                </div>
                            </div>
                        </div>
                        <div class="g-cisd2 f-bg">
                            <div class="m-forumRule">
                                <h3 class="tit">??????????????????</h3>
                                <blockquote>???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????</blockquote>
                                <blockquote>????????????????????????????????????????????????/???????????????</blockquote>
                                <blockquote>
                                    <b>??????????????????</b>
                                </blockquote>
                                <blockquote>
                                    <li><b>??????????????????????????????</b>?????????????????????????????????????????????????????????????????????????????????????????????</li>
                                </blockquote>
                                <blockquote>
                                    <li><b>??????????????????????????????</b>???????????????????????????????????????????????????????????????????????????????????????</li>
                                </blockquote>
                                <blockquote>
                                    <li><b>??????????????????????????????</b>????????????????????????????????????????????????????????????????????????????????????????????????????????????</li>
                                </blockquote>
                                <blockquote>
                                    <b>??????????????????</b>
                                </blockquote>
                                <blockquote>
                                    ???????????????????????????????????????????????????????????????????????????????????????
                                </blockquote>
                                <blockquote>
                                    <li>??????????????????????????????????????????????????????????????????????????????/?????????</li>
                                </blockquote>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/dist/vendors/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/js/plugins/pagination/js/jquery.pagination.js"></script>
<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
<script type="text/javascript">
    let ckeditor1 = CKEDITOR.replace('LAY_demo1', {
        height: 300
    });

    $('.info-tip').poshytip({
        className: 'tip-yellowsimple',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5,
        allowTipHover: false
    });
    $('.M-box')
        .pagination(
            {
                totalData: "${pageModel.total}",
                showData: "${pageModel.pageSize}",
                current: "${pageModel.pageNum}",
                pageCount: "${pageModel.pages}",
                coping: true,
                callback: function (index) {
                    currentPage = index.getCurrent();
                    console.log("currentPage:" + currentPage);
                    window.location.href = "/talk/topicAction!show.action?id=${topic.id}&pageNum="
                        + currentPage;
                }
            }, function (api) {
                currentPage = api.getCurrent();
                console.log("currentPage:" + currentPage);
                $('.now').text(api.getCurrent());
            });

    $(document).ready(function () {
        //???????????????????????????


    });

    //------------------------------------------------------

    var onetalkId = "${onetalkId}";
    //??????---------------------------------------------------
    $("#topic_like").click(function () {
        var topicId = "${topic.id}";
        $.ajax({
            type: "post",
            url: "talk/topicAction!topiclike.action",
            data: {
                "topicId": topicId
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data == "+1success") {
                    alert("????????????");
                }
                if (data == "-1success") {
                    alert("????????????");
                }
                if (data == "error") {
                    alert("????????????");
                }
                window.location.reload();
            }
        });
    });

    $("#saveReply").click(function () {
        //var replycontent = UE.getEditor('editor').getContent();
        var replycontent = CKEDITOR.instances.LAY_demo1.document.getBody().getText();
        console.log(replycontent);
        if (replycontent.trim() == "") {
            alert("??????????????????");
        } else {
            var topicId = $("#topicId").val();
            $.ajax({
                type: "post",
                url: "${basePath }/user/reply/add",
                dataType: 'json',
                data: {
                    "replycontent": CKEDITOR.instances.LAY_demo1.getData(),
                    "topicId": topicId,
                    "tcid": ${tcid}
                },
                success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                    if (data.code == 0) {
                        layer.open({
                            title: '????????????'
                            , content: '????????????',
                            end: function () {
                                location.reload();
                            }
                        });
                        $('#tiwenModal').modal('hide');
                    } else {
                        alert("error");
                        window.location.reload();
                    }
                }
            });
        }
    });


    //????????????
    function Adopt(replyId) {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/reply/adopt",
            data: {
                "replyId": replyId
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data == "success") {
                    alert("????????????");
                    window.location.reload();
                } else {
                    alert("????????????");
                    window.location.reload();
                }
            }
        });
    }

    //?????????????????????
    function showDeckReply(replyId, toUserId, toUserName, toUserType, floor) {
        $("#deck_reply").parent().attr("style", "display:none;");
        $("#deck_reply").remove();
        //???????????????
        console.log("deck_box_" + floor);
        document.getElementById("deck_box_" + floor).setAttribute("style", "display:block;");
        $("#deck_box_" + floor).append(
            "	<div class=\"deck_reply\" id=\"deck_reply\">"
            + "		<div><textarea class=\"deck_reply_textarea\" id=\"deck_textarea_" + replyId + "\" placeholder=\"to " + toUserName + " : \"></textarea></div>"
            + "		<a class=\"deck_reply_button\" onclick=\"deckReply('" + replyId + "','" + toUserId + "','" + toUserName + "','" + toUserType + "')\">??????</a>"
            + "	</div>"
        );
    }

    //???????????????
    function deckReply(replyId, toUserId, toUserName, toUserType) {
        var content = $("#deck_textarea_" + replyId).val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/deck/add",
            data: {
                "content": content,//??????
                "replyId": replyId,//???????????????id
                "toUserId": toUserId,//???????????????id
                "toUserName": toUserName,//?????????????????????
                "toUserType": toUserType,//?????????????????????
                "tcid":${tcid},//?????????????????????
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data.code == 0) {
                    alert("????????????");
                    window.location.reload();
                } else {
                    alert("????????????");
                    window.location.reload();
                }
            }
        });
    }

    //????????????
    function replyLike(replyId) {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/reply/like",
            data: {
                "replyId": replyId
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data.msg == "+1success") {
                    alert("????????????");
                }
                if (data.msg == "-1success") {
                    alert("????????????");
                }
                if (data.msg == "error") {
                    alert("????????????");
                }
                window.location.reload();
            }
        });
    }

    // ????????????
    function replyDelete(replyId) {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/account/comment/remove",
            data: {
                "id": replyId,
                "type": "reply"
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data.msg == "+1success") {
                    alert("????????????");
                }
                if (data.msg == "-1success") {
                    alert("????????????");
                }
                if (data.msg == "error") {
                    alert("????????????");
                }
                window.location.reload();
            }
        });
    }

    //?????????
    function deckDelete(deckId) {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/account/comment/remove",
            data: {
                "id": deckId,
                "type": "deck"
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data.msg == "+1success") {
                    alert("????????????");
                }
                if (data.msg == "-1success") {
                    alert("????????????");
                }
                if (data.msg == "error") {
                    alert("????????????");
                }
                window.location.reload();
            }
        });
    }


    //???????????????
    function deckLike(deckId) {
        $.ajax({
            type: "post",
            url: "talk/deckAction!deckLike.action",
            data: {
                "deckId": deckId
            },
            async: false, //????????????
            success: function (data, status) { //?????????status???ajax?????????????????????????????????success
                if (data == "+1success") {
                    alert("????????????");
                }
                if (data == "-1success") {
                    alert("????????????");
                }
                if (data == "error") {
                    alert("????????????");
                }
                window.location.reload();
            }
        });
    }
</script>
