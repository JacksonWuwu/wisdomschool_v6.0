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
                         <span style="color: #1d9cba;" class="glyphicon glyphicon-comment"></span>&nbsp;&nbsp;回答
                     </h4>
                 </div>
                 <div>
                 <#--     内容  -->
                     <#list comments as comment>
                        <div class="col-md-4 col-xs-12">
                            <div style="margin-bottom: 5px;">
                                <a href="javaScript:void(0)" id="${comment.id }"
                                   name="comment">${comment.title }</a>
                            </div>
                            <div style="color: #a2a2a2; margin-bottom: 8px;">
                                ${comment.content }
                            </div>
                            <div style="color: #5cb85c;">发表于 [${comment.title}] }</div>
                        </div>
                     </#list>

                 <#--     内容  -->
                     <div class="row">
                         <div class="col-md-8 col-md-offset-2">
                             <ul class="list-group">

                            <#list pageModel.rows as comment>
                                <li class="list-group-item">
                                    <div class="box">
                                        <div style="margin-bottom: 22px; margin-top: 9px">
                                            <div class="pull-left">
                                                <p style="color: #999">

                                                    <a href="javaScript:void(0)" style="color: #24e5bf"
                                                       id="${comment.id}" name="username">${comment.createName}</a>
                                                    <span class="post-time"> 编辑于 ${comment.createTime?string('yyyy-MM-dd hh:mm')}</span>
                                                </p>
                                            </div>
                                            <div class="pull-right">
                                                <#if comment.createBy == userId>
                                                    <a href="javaScript:void(0)" onclick="commentRemove('${comment.id}', '${comment.type}')">删除</a>
                                                </#if>
                                                <span>|</span> <span> "赞" <span>${comment.thumbsUp}</span></span>
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
            window.location.href = "/account/comment/index?sort=${sort}&pageNum="
                    + currentPage;
        }
    }, function (api) {
        currentPage = api.getCurrent();
        $('.now').text(api.getCurrent());
    });
</script>

<#include "/front/account/component/footer.ftl"/>
