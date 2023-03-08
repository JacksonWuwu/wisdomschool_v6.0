<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>学习讨论</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon"
          href="/img/front/favicon.ico"
          type="image/x-icon"/>

    <!--地址栏和标签上显示图标-->
    <!--bootstrap引用-->
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="/css/indexstyle.css" type="text/css" rel="stylesheet">
    <script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>

    <link rel="stylesheet" href="/css/style-home.css"/>
    <link rel="stylesheet" href="/js/plugins/layer/theme/default/layer.css"/>

    <link rel="stylesheet" type="text/css"
          href="/js/plugins/pagination/style/pagination.css"
          media="screen">
    <link rel="stylesheet" type="text/css"
          href="/js/plugins/pagination/style/normalize.css"
          media="screen">

    <meta name="renderer" content="webkit">
    <style>

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
            font: 12px/1.5 arial, STHeiti, 'Microsoft YaHei', 宋体;
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


</head>
<body style="background: #eee;">
<!--主体-->
<div class="container">

    <div class="row">
        <div style="background: white;padding-top: 20px;padding-left: 30px;padding-right: 30px;">

            <input id="topicId" value="${topic.id}" type="hidden">

            <h1 class="discuss-title">${topic.title}</h1>

            <div style="margin-bottom: 22px;margin-top: 9px;">
                <div class="pull-left">
                    <p style="color:#999">
							<span style="display:block;"> <a style="color:#fd8321">${topic.createName}
                            </a>
							</span> <span class="post-time"> 编辑于 ${topic.createTime?string('yyyy-MM-dd hh:mm')}</span>
                    </p>
                </div>
                <div class="pull-right">
                    <p style="color:#999">
							<span> "回复" <span>${topic.replyCount}</span>
							</span> <span>|</span> <span> "赞" <span>${topic.thumbsUp}</span>
							</span> <span>|</span> <span> "浏览" <span>${topic.browse}</span>
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
								<a href="#" id="topic_like" onclick="topic_like()">赞</a>
							</span>
                        <!-- 							<span><span class="glyphicon glyphicon-edit"></span>
                            回帖
                        </span> -->
                    </p>
                </div>
                <div style="margin-bottom: 8px;padding: 17px"></div>
            </div>
            <!-- 列结尾 -->
        </div>

        <!-- 智慧阿细 -->

        <#if !(pageModel.total > 0 || replyAdopt??)>
            <div style="background: white;padding-left:15px;padding-right:15px;">
                <div style="text-align:center;padding:50px">
                    <div class="center-block" style="width:200px">
                        <img src="/img/front/10673188_093000910000_2.png" width="200px">
                    </div>
                    帖子还没回复，快来抢沙发！
                </div>
            </div>
        </#if>


        <!-- 最佳回复 -->
        <#if replyAdopt??>
            <div class="answerAN-tit" style="background: white;height:30px;padding: 30px;">
                <img src="/img/front/answer.png"/>
                <h3 style="color: #10AE58;margin: -10px;padding-top:2px;margin-left: 5px;">最佳回复</h3>
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
                            <span class="replye_div_infor_time">发表于 ${replyAdopt.createTime?string('yyyy-MM-dd hh:mm')}</span>
                            <a class="replye_div_infor_reply"
                               onclick="showDeckReply('${replyAdopt.id}','${replyAdopt.createBy}','${replyAdopt.createName}','${replyAdopt.userType}','0')">回复(5)</a>
                            <a class="replye_div_infor_reply"
                               onclick="replyLike('${replyAdopt.id}')">赞(8)</a>
                            <div class="deck_box" id="deck_box_0">
                                <div class="deck-list">
                                    <#list replyAdopt.decks as deck>
                                        <div class="deck_floor" id="deck_box_0_${deck_index+1}">
                                            <div class="deck_information">
                                                <div class="deck_person">
                                                    <a class="deck_person_a">${deck.createName}</a>
                                                    <#if deck.toUserId != replyAdopt.createBy>
                                                        &nbsp;回复&nbsp;<a class="deck_person_a">${deck.toUserName}</a>
                                                    </#if>
                                                    ：
                                                </div>
                                                <div class="deck_content">${deck.content}</div>
                                            </div>
                                            <div class="deck_bottm">
                                                <span class="deck_time">${deck.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                <a class="deck_a"
                                                   onclick="showDeckReply('${replyAdopt.id}','${deck.createBy}','${deck.createName}','${deck.userType}','0_${deck_index+1}')">回复</a>
                                                <a class="deck_a" onclick="deckLike('${deck.id}')">赞(${deck.thumbsUp}
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


        <!-- 其他回答 -->
        <#if pageModel.rows??>
            <div class="answerAN-tit" style="background: white;height:30px;padding:30px;">
                <h3 style="color: #10AE58;margin: -10px;padding-top:2px;margin-left: 5px;">回复</h3>
            </div>
        </#if>

        <#list pageModel.rows as reply>
            <#if reply.adopt==0>
                <div class="replye_boday" style="background: white;padding-left: 30px;">

                    <div class="replye_div">
                        <a class="head_a">
                            <#if sessionScope.user.headImage != null>
                                <img src="${sessionScope.user.headImage}" style="height: 55px;width: 55px"
                                     class="img-circle" alt="User Image"/>
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
                                    <@shiro.principal property='id'/>
                                </#assign>
                                <#if topic.createBy==userId&&!replyAdopt??>
                                    <a class="replye_div_infor_reply" style="margin-right:20px;"
                                        onclick="Adopt('${reply.id}')">+采纳</a>
                                    </span>
                                </#if>
                            </p>
                            <div class="replye_div_connent">${reply.content}</div>
                            <div class="replye_div_infor">
                                <span class="replye_div_infor_time">发表于 ${reply.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                <a
                                        class="replye_div_infor_reply"
                                        onclick="showDeckReply('${reply.id}','${reply.createBy}','${reply.createName}','${reply.userType}','${reply_index + 1}')">
                                    回复(<#if reply.decks??>${reply.decks?size!0}<#else >0</#if>)</a>
                                <a class="replye_div_infor_reply"
                                   onclick="replyLike('${reply.id}')">赞(${reply.thumbsUp})</a>
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
                                                        &nbsp;回复&nbsp;<a class="deck_person_a">${deck.toUserName}</a>
                                                    </#if>
                                                    ：
                                                </div>
                                                <div class="deck_content">${deck.content}</div>
                                            </div>
                                            <div class="deck_bottm">
                                                <span class="deck_time">${deck.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                <a class="deck_a"
                                                   onclick="showDeckReply('${reply.id}','${deck.createBy}','${deck.createName}','${deck.userType}','${reply_index}_${deck_index+1}')">回复</a>
                                                <a class="deck_a" onclick="deckLike('${deck.id}')">赞(${deck.thumbsUp}
                                                    )</a>
                                            </div>
                                        </div>
                                    </#list>
                                </div>
                                <!-- js生成回复区域 -->
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>


        <div>


        </div>
        <div class="col-md-12" style="background: white;margin-top: 0px">

            <textarea class="layui-textarea layui-hide" id="LAY_demo1" style="display: none;">

                </textarea>

        </div>

        <div style=" float: right; margin-top: 10px; margin-right: 10px;">
            <button type="button" class="btn btn-default" id="cancel"  style="width: 50px;padding: 9px 0"
                    onclick="quxiao()"
            >取消</button>

            <button type="button" class="btn btn-primary" id="saveReply"
               style="background-color: #00BC9B;width: 50px;padding: 9px 0"
               onclick = "huitie()">回帖</button>
        </div>

        <!-- row 结尾 -->
    </div>


    <!-- container 结尾 -->
</div>
<script type="text/javascript" src="/dist/vendors/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
    ckeditor1 = CKEDITOR.replace('LAY_demo1', {
        height: 400,
        toolbar: [

            {name: 'tools', items: ['ShowBlocks']}]
    });

</script>


<script type="text/javascript" src="/js/plugins/pagination/js/jquery.pagination.js"></script>
<script type="text/javascript">
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
</script>

<script type="text/javascript" src="/js/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="/js/plugins/layer/layer.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        //页面加载完自动执行
    });
    ckeditor1 = CKEDITOR.replace('LAY_demo1', {
        height: 400,
        toolbar: [

            {name: 'tools', items: ['ShowBlocks']}]
    });

    //------------------------------------------------------


    var onetalkId = "${onetalkId}";
    //点赞---------------------------------------------------
    function topic_like() {
        var topicId = "${topic.id}";
        $.ajax({
            type: "post",
            url: "/wx/forum/indexlike",
            dataType: 'json',
            data: {
                "topicId": topicId,
                "openId": "${openId}"
            },
            async: false, //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data == "+1success") {
                    alert("点赞成功");
                }
                if (data == "-1success") {
                    alert("取消点赞");
                }
                if (data == "error") {
                    alert("操作失败");
                }
                window.location.relaod();
            }
        });
    }

    function huitie() {

        //var replycontent = UE.getEditor('editor').getContent();
        var replycontent = CKEDITOR.instances.LAY_demo1.document.getBody().getText();
        console.log(replycontent);
        if (replycontent.trim() == "") {
            alert("内容不能为空");
        } else {
            var topicId = $("#topicId").val();
            $.ajax({
                type: "post",
                url: "/wx/forum/replyadd",
                dataType: 'json',
                data: {
                    "replycontent": CKEDITOR.instances.LAY_demo1.getData(),
                    "topicId": topicId,
                    "openId": "${openId}"
                },
                success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                    if (data.code == 0) {
                        layer.open({
                            title: '网站提示'
                            , content: '提交成功',
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
    }


    //采纳回复
    function Adopt(replyId) {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/user/reply/adopt",
            data: {
                "replyId": replyId
            },
            async: false, //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data.msg == "success") {
                    alert("采纳成功");
                    window.location.reload();
                } else {
                    alert("采纳失败");
                    window.location.reload();
                }
            }
        });
    }

    //显示楼中楼回复
    function showDeckReply(replyId, toUserId, toUserName, toUserType, floor) {
        $("#deck_reply").parent().attr("style", "display:none;");
        $("#deck_reply").remove();
        //显示楼中楼
        console.log("deck_box_" + floor);
        document.getElementById("deck_box_" + floor).setAttribute("style", "display:block;");
        $("#deck_box_" + floor).append(
                "	<div class=\"deck_reply\" id=\"deck_reply\">"
                + "		<div><textarea class=\"deck_reply_textarea\" id=\"deck_textarea_" + replyId + "\" placeholder=\"to " + toUserName + " : \"></textarea></div>"
                + "		<a class=\"deck_reply_button\" onclick=\"deckReply('" + replyId + "','" + toUserId + "','" + toUserName + "','" + toUserType + "')\">回复</a>"
                + "	</div>"
        );
    }

    //楼中楼回复
    function deckReply(replyId, toUserId, toUserName, toUserType) {
        var content = $("#deck_textarea_" + replyId).val();
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/wx/forum/deckadd",
            data: {
                "content": content,//内容
                "replyId": replyId,//回复的楼层id
                "toUserId": toUserId,//被回复人的id
                "toUserName": toUserName,//被回复人的名字
                "toUserType": toUserType,//被回复人的类型
                "openId": "${openId}"
            },
            async: false, //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data.code == 0) {
                    alert("回复成功");
                    window.location.reload();
                } else {
                    alert("回复失败");
                    window.location.reload();
                }
            }
        });
    }

    //回复点赞
    function replyLike(replyId) {
        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/wx/forum/replylike",
            data: {
                "replyId": replyId,
                "openId": "${openId}"
            },
            async: false, //同步处理
            success: function (data, status) { //这里的status是ajax自己的参数，请求成功就success
                if (data.msg == "+1success") {
                    alert("点赞成功");
                }
                if (data.msg == "-1success") {
                    alert("取消点赞");
                }
                if (data.msg == "error") {
                    alert("操作失败");
                }
                window.location.reload();
            }
        });
    }
    function prompt() {

        //弹出一个对话框

        alert("提示信息！");

    }
    function quxiao() {
        window.open('/wx/forum/indexlist?forumid=0&sort='+"${sort}"+"&"+"openId="+"${openId}");
    }

</script>
</body>
</html>
