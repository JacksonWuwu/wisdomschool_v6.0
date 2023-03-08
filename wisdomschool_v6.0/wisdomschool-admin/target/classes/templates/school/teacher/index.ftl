<#include "/common/ui.ftl"/>

    <@adminLayout  "智慧教育云平台-以科技助力教育" "wstom-首页" "wstom-社区" "分享让世界更美好">
        <div class="page-content">
            <div class="row">
                <div class="col-xs-12" id="main-page-container">
                    <div class="container-div layout-container-div clearfix">
                        <div class="row">
                            <div class="pagerContent pic-list">
                                <div class="pagerMainContent" style="display: none;">
                                    <a key="course.id" class="pic-list-constant-item" onclick="TclickLoad(event)">
                                        <div class="">
                                            <img src="/showCondensedPicture?fileId=ff90c3a6-f516-4b1c-a33a-a3932b005152" key="thumbnailPath" class="pic-list-constant-img">
                                            <div class="cover-info">
                                                <i class="fa fa-list-ul"></i> &nbsp;<h4 key="course.name"></h4>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <div id="m-content">
                                </div>
                            </div>
                        </div>
                    </div><!--/.container-div-->
                </div>
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    </@adminLayout>





