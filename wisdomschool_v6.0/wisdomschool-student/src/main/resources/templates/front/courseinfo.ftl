<#include '/front/inc/commonJs.ftl'/>
    <div class="ov f-pr j-ch f-cb">
    <div class="courseMark djMark f-pa j-djMark"></div>
    <div class="courseMark sfMark f-pa j-sfMark"></div>
    <div class="g-sd1 left j-chimg"><img width="450" height="250"
                                         src="/img/front/couser.jpg">
    </div>
    <div class="g-mn1">
        <div class="f-pr g-mn1c right-wrap">
            <div class="right j-right">
                <div class="f-cb">
                    <div class="ctarea f-fl j-cht">
                        <div class="u-coursetitle f-fl" id="auto-id-1555802291123"><h2
                                    class="f-thide"><span class="u-coursetitle_title"
                                                          title="${course.name}">${course.name}</span>
                            </h2>
                            <div class="f-cb margin-top-15"><span
                                        class="hot f-fs0">1469人学过</span>
                                <div class="teacher">
                                    <div class="f-fl">教师：</div>
                                    <div class="f-fl" style="margin-right:3px;">${teacherUser.userName}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <a title="编辑课程" class="j-cmEdit cmEdit f-fr right-icon"
                       style="display: none;"></a><a title="放弃学习"
                                                     class="j-cmDel cmDel f-fr right-icon"
                                                     id="auto-id-1555802291127"></a>
                    <div class="j-course-share-ui f-fr">
                        <div class="u-shareUI f-fr f-cb" id="auto-id-1555802291139"><a
                                    class="j-info info right-icon" title="查看详情"
                                    id="auto-id-1555802291148" style=""></a> <a
                                    class="j-toStore toStore right-icon" title="加入收藏"
                                    id="auto-id-1555802291150" style=""></a> <a
                                    class="j-share share right-icon" id="auto-id-1555802291145"
                                    style="">
                                <div class="j-shareUI f-win box x-hide">
                                    <div class="ux-share-size24">
                                        <div class="ux-share">
                                            <ul class="ux-share-ways">
                                                <li class="ux-share-wechat">
                                                    <span class="ux-icon-wechat"></span>
                                                    <div class="ux-share-wechat-ercodebox"
                                                         style="display: none;">
                                                        <div class="ux-share-wechat-ercode">
                                                            <img src=""
                                                                 alt="微信分享">
                                                            <div class="arrow-down arrow-down-out">
                                                                <i class="arrow-down arrow-down-in"></i>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                                <li class="ux-share-QQ">
                                                    <span class="ux-icon-QQ"></span>
                                                </li>
                                                <li class="ux-share-weibo">
                                                    <span class="ux-icon-weibo"></span>
                                                </li>
                                            </ul>
                                            <!--Regular if108-->
                                        </div>
                                    </div>
                                </div>
                            </a></div>
                    </div>
                </div>
                <p id="j-price"
                   style="width:300px; line-height:19px; font-size:12px; color:red;"></p>
                <div class="j-coursehead-right-info">
                    <!--课程简介&ndash;&gt;-->
                    <div class="learn-main-info" style="border-bottom: none;">
                        <p style="font-size: 15px;">
                            <span style="font-weight: bold">简介：</span>${teacherCourse.courseBriefIntroduction }</p>
                    </div>
                </div>
            </div>
            <div class="j-coursehead-right-btn"></div>
        </div>
    </div>
</div>
