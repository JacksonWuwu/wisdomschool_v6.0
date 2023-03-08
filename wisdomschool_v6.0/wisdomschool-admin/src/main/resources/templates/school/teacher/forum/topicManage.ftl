<link rel="stylesheet" href="/js/plugins/layui/css/layui.css">
<link rel="stylesheet" href="/js/plugins/layui/css/global.css">
<div class="">
    <div class="layui-row">
        <div class="fly-panel" style="margin-bottom: 0;">

            <div class="fly-panel-title fly-filter">
                <#assign page_nav="${sort!}">

                <a href="/forum/course" class="<#if page_nav="">layui-this</#if>">综合</a>
                <span class="fly-mid"></span>
                <a href="" class="<#if page_nav="unfinsh">layui-this</#if>">未结</a>
                <span class="fly-mid"></span>
                <a href="" class="<#if page_nav="finshed">layui-this</#if>">已结</a>
                <span class="fly-mid"></span>
                <a href="" class="<#if page_nav="essence">layui-this</#if>">精华</a>
                <span class="fly-filter-right layui-hide-xs">
                    <a href="" class="<#if page_nav="">layui-this</#if>">按最新</a>
                    <span class="fly-mid"></span>
                    <a href="" class="<#if page_nav="hot">layui-this</#if>">按热议</a>
                </span>
            </div>

            <@forumPage orderby="${sort!}" p="${p!}">
                <#if forumpage?? && forumpage.list?size gt 0>
                    <ul class="fly-list">
                        <#list forumpage.list as list>
                            <#if list.infoType=0>
                                <#include "/front/forum/type_question1.ftl">
                            </#if>
                        </#list>
                    </ul>
                <#else>
                    <div class="init">
                        <p class="text-center">
                            (ﾟ∀ﾟ　)<br> 暂时没有任何数据
                        </p>
                    </div>
                </#if>
                <div class="text-center">
                    <ul class="pagination">
                        <#if data??>
                            ${data.pageNumHtml}
                        </#if>
                    </ul>
                </div>
            </@forumPage>

            <!-- <div class="fly-none">没有相关数据</div> -->

            <div style="text-align: center;margin-top: 35px;">
                <div class="laypage-main">
                    <span class="laypage-curr">1</span><a href="/jie/page/2/">2</a><a href="/jie/page/3/">3</a><a
                            href="/jie/page/4/">4</a><a href="/jie/page/5/">5</a><span>…</span><a href="/jie/page/148/"
                                                                                                  class="laypage-last"
                                                                                                  title="尾页">尾页</a><a
                            href="/jie/page/2/" class="laypage-next">下一页</a></div>
            </div>
        </div>
    </div>
    <div class="layui-row">
        <dl class="fly-panel fly-list-one">
            <dt class="fly-panel-title">本周热议</dt>
            <dd>
                <a href="">在线讨论</a>
                <span><i class="iconfont icon-pinglun1"></i> 16</span>
            </dd>
            <dd>
                <a href="">在线讨论</a>
                <span><i class="iconfont icon-pinglun1"></i> 16</span>
            </dd>

            <!-- 无数据时 -->
            <!--
            <div class="fly-none">没有相关数据</div>
            -->
        </dl>
    </div>
</div>
<script src="/js/plugins/layui/layui.js"></script>
<script>
    layui.cache.page = 'jie';
    layui.cache.user = {
        username: '游客'
        , uid: -1
        , avatar: '../../res/images/avatar/00.jpg'
        , experience: 83
        , sex: '男'
    };
    layui.config({
        version: "3.0.0"
        , base: '/js/plugins/layui/mods/'
    }).extend({
        fly: 'index'
    }).use('fly');
</script>