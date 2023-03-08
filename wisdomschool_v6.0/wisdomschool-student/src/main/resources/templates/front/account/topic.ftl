<#include "/front/account/component/header.ftl"/>

<style>
    .M-box .active {
        float: left;
        margin: 0 5px;
        width: 38px;
        height: 38px;
        line-height: 38px;
        background: #89a797;
        color: #fff;
        font-size: 14px;
        border: 1px solid #89a797;
    }
</style>
 <div class="u-container">

     <div class="container">
         <div class="row feature">
             <div style="width: 890px; margin-bottom: 30px;">
                 <div style="margin-left: 30px; border-bottom: solid 1px #bebebe">
                     <h4 style="margin: 20px 30px;">
                         <span style="color: #1d9cba;" class="glyphicon glyphicon-comment"></span>&nbsp;&nbsp;话题
                     </h4>
                 </div>
                 <div>
                    <#--     内容  -->
                     <div class="row">
                         <div class="col-md-8 col-md-offset-2">
                             <ul class="list-group">

                            <#list pageModel.rows as topic>
                                <li class="list-group-item">
                                    <div class="box">
                                        <div style="margin-bottom: 22px; margin-top: 9px">
                                            <div class="pull-left">
                                                <p style="color: #999">

                                                    <a href="javaScript:void(0)" style="color: #24e5bf"
                                                       id="${topic.id}" name="username">${topic.createName}</a>
                                                    &nbsp; &nbsp; 发表在 <a>[${topic.forum.name}]</a> &nbsp; &nbsp;
                                                    <span class="post-time"> 编辑于 ${topic.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                    <#if topic.lastReply??>
                                                        最后回复 &nbsp; &nbsp;
                                                        ${topic.createTime?string('yyyy-MM-dd hh:mm')}
                                                    </#if>
                                                </p>
                                            </div>
                                            <div class="pull-right">
                                                    <#if topic.createBy == userId>
                                                        <a href="javaScript:void(0)" onclick="topicRemove('${topic.id}')">删除</a>
                                                    </#if>
                                                <span> "回复"</span> <span>${topic.replyCount}</span>
                                                <span>|</span> <span> "赞" <span>${topic.thumbsUp}</span>
                                                    </span> <span>|</span> <span> "浏览" <span>${topic.browse}</span>
                                                    </span>
                                            </div>
                                        </div>

                                    </div>
                                </li>
                            </#list>

                             </ul>
                         </div>
                     </div>

                     <#--   分页  -->
                     <script src="${ctx }/js/plugins/pagination/js/jquery.pagination.js"></script>

                     <div class="row">
                         <div class="col-md-5"></div>
                         <div class="col-md-7">
                             <div class="M-box"></div>
                         </div>
                     </div>

                 </div>
             </div>
         </div>
     </div>
 </div>

<script src="/js/plugins/layui/layui.js"></script>

<script type="text/javascript">
    $('.M-box').pagination({
        totalData: "${pageModel.total}",
        showData: "${pageModel.pageSize}",
        current: "${pageModel.pageNum}",
        pageCount: "${pageModel.pages}",
        coping: true,
        callback: function (index) {
            console.log(index);
            currentPage = index.getCurrent();
            window.location.href = "/account/topic/index?sort=${sort}&pageNum="
                    + currentPage;
        }
    }, function (api) {
        currentPage = api.getCurrent();
        console.log("currentPage:" + currentPage);
        $('.now').text(api.getCurrent());
    });
</script>

<#include "/front/account/component/footer.ftl"/>
