<link rel="stylesheet" href="/css/course-main.css">
<link href="/js/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/js/plugins/jquery.poshytip/tip-yellowsimple/tip-yellowsimple.css"/>
<script type="text/javascript" src="/js/plugins/jquery.poshytip/jquery.poshytip.min.js"></script>
<#include '/front/inc/commonJs.ftl'/>
<style>
    .learn-btn {
        display: inline-block;
        border-radius: 10px;
        text-align: center;
        background-color: #0089D2;
        color: #FFF;
        width: 71px;
        height: 32px;
        line-height: 32px;
        font-size: 16px;
        position: relative;
        top: 3px;
        border: 0;
    }

    .common-table_box{
        padding: 26px 44px;
        background-color: #fff;
        border-radius: 6px;
    }
    .el-table {
        position: relative;
        overflow: hidden;
        box-sizing: border-box;
        -webkit-box-flex: 1;
        flex: 1;
        max-width: 100%;
        color: #606266;
    }
    .el-table, .el-table__expanded-cell {
        background-color: #fff;
    }

    .common-table-box .el-table {
        font-size: 14px;
    }

    .el-table thead {
        color: #909399;
        font-weight: 500;
    }

    thead {
        display: table-header-group;
        vertical-align: middle;
        border-color: inherit;
    }

    .el-table__body, .el-table__footer, .el-table__header {
        table-layout: fixed;
        border-collapse: separate;
    }

    .table_header{
        table-layout: fixed;
        border-collapse: separate;
    }
    .el-table td.is-center, .el-table th.is-center {
        text-align: center;
        width: 303px;
    }

    .common-table-box .el-table {
        font-size: 14px;
    }

    table {
        border-collapse: separate;
        text-indent: initial;
        border-spacing: 2px;
    }

    .common-table-box .el-table .el-table__header th.is-leaf {
        background: #f5f7fa;
        color: #909399;
        font-size: 14px;
        font-weight: 600;
        height: 58px;
        width: 303px;
    }
    .el-table td, .el-table th.is-leaf {
        border-bottom: 1px solid #ebeef5;
    }

    .el-slider__button-wrapper, .el-table th, .el-time-panel {
        -webkit-user-select: none;
    }
    th {
        display: table-cell;
    }

    .el-table th {
        white-space: nowrap;
        overflow: hidden;
    }
    .el-table--small td, .el-table--small th {
        padding: 8px 0;
    }

    .el-table td, .el-table th {
        min-width: 0;
        box-sizing: border-box;
        text-overflow: ellipsis;
        vertical-align: middle;
        position: relative;
    }

    .el-table__body, .el-table__footer, .el-table__header {
        table-layout: fixed;
        border-collapse: separate;
    }

    table {
        text-indent: initial;
        border-spacing: 0;
    }
    .g-cimn2c {
        margin-right: 0px;
    }

    div.cell{
        width: 303px;
    }

    .cz-button {
        font-size: 12px;
        line-height: 16px;
        padding: 7px 8px;
        border-radius: 3px;
        color: #1058fa;
        background-color: #fff;
        -webkit-transition: all .2s;
        transition: all .2s;
        cursor: pointer;
        display: inline-block;
        border: none;
    }

</style>
<div id="auto-id-1555802290742" <#--style="padding-top: 60px;"--> class="auto-1555802290734-parent"
     cz-shortcut-listen="true">
    <div class="g-headwrap" id="j-fixed-head" data-log-id="topNav"
         data-log-data="{&quot;pageName&quot;:&quot;顶部导航&quot;}">
        <div class="m-yktnav " id="j-topnav">
            <div class="m-yktnav_wrap f-pr f-cb">
                <div class="m-breadcrumbBox f-cb" id="j-breadcrumbBox">
                    <div class="g-mn2">
                        <div class="g-flow f-cb">
                            <ul class="g-flow">
                                <li class="navcrumb-item">
                                    <a href="">我的课程</a>
                                </li>
                                <li class="navcrumb-item current" id="navcrumbId-2">
                                    <span class="arrow"></span>
                                    <a href="javascript: void(0);">${course.name}</a>
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
                                        <li class="f-fl"><a class="j-chtab selected" hidefocus="true">考试</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="f-cb">
                        <#--                        <div class="g-cisd2" id="bdCom">-->
                        <#--                            <div id="j-repo-box" class="f-dn f-bg repoBox f-cb">-->
                        <#--                                <h2 class="u-ctit">相关题库</h2>-->
                        <#--                                <div id="j-repoList" class="repoList f-bdr">-->
                        <#--                                </div>-->
                        <#--                            </div>-->
                        <#--                        </div>-->
                        <div class="g-cimn2" id="bdDir">
                            <div class="g-cimn2c">
                                <div id="j-livecourse-remind">
                                </div>
                                <div class="b-15"></div>
                                <div class="f-cb f-bg f-pr" id="j-chapter-list" style="z-index:10;">
                                    <div class="f-cb">
                                        <h2 class="u-ctit f-thide f-fl" id="j-chapter-title" style="width:320px;">
                                            上机作业</h2>
                                    </div>
                                    <div class="f-cb">
                                        <div class="common-table-box">
                                            <div class="el-table el-table--fit el-table--enable-row-hover el-table--enable-row-transition el-table--small" style="width: 100%;">
                                                <div class="hidden-columns"><div></div><div></div><div></div><div></div><div></div></div>
                                                <div class="el-table__header-wrapper">
                                                    <table cellspacing="0" cellpadding="0" border="0" class="el-table__header" style="width: 1112px;">
                                                        <thead class="has-gutter">
                                                        <tr class="">
                                                            <th colspan="1" rowspan="1" class="el-table_13_column_70  is-center   is-leaf"><div class="cell">作业名称</div></th>
                                                            <th colspan="1" rowspan="1" class="el-table_13_column_72  is-center   is-leaf"><div class="cell">状态</div></th>
                                                            <th colspan="1" rowspan="1" class="el-table_13_column_73  is-center   is-leaf"><div class="cell">链接</div></th>
                                                            <th colspan="1" rowspan="1" class="el-table_13_column_74  is-center   is-leaf"><div class="cell">作业成绩</div></th>
                                                            <th class="gutter" style="width: 0px; display: none;"></th>
                                                        </tr>
                                                        </thead>
                                                    </table>
                                                </div>
                                                <div class="el-table__body-wrapper is-scrolling-none">
                                                    <table cellspacing="0" cellpadding="0" border="0" class="el-table__body" style="width: 1112px;">
                                                        <tbody>
                                                        <#list chapterResourceList as cs>
                                                        <#if cs??>
                                                            <tr class="el-table__row">
                                                            <#if cs.states==1>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip">${cs.adjunct.adjunctname}</div>
                                                                </td>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip">已完成</div>
                                                                </td>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip"><a href="javascript:void(0);"
                                                            title="${cs.adjunct.adjunctname}" class="info-tip"
                                                                                              style="padding-left:6px;padding-right:6px;"
                                                        onclick="toadjunct('${cs.adjid}')">
                                                            <span class="fa fa-paper-plane" style="font-size: 18px;"></span>
                                                            </a></div>
                                                                </td>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip">${cs.results}</div>
                                                                </td>
                                                                </tr>
                                                            <#else>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip">${cs.adjunct.adjunctname}</div>
                                                                </td>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip">正在进行</div>
                                                                </td>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip"><a href="javascript:void(0);"
                                                            title="${cs.adjunct.adjunctname}" class="info-tip"
                                                                                              style="padding-left:6px;padding-right:6px;"
                                                        onclick="toadjunct('${cs.adjid}')">
                                                            <span class="fa fa-paper-plane" style="font-size: 18px;"></span>
                                                            </a></div>
                                                                </td>
                                                                <td rowspan="1" colspan="1" class="el-table_13_column_70 is-center">
                                                                <div class="cell el-tooltip">暂无成绩</div>
                                                                </td>
                                                                </tr>
                                                            </#if>



                                                        <#--                                                </#if>-->
                                                        <#--                                            <#else>-->
                                                        <#--                                                <div class="f-cb">-->
                                                        <#--                                                    <h2 class="u-ctit f-thide f-fl" id="j-chapter-title" style="width:320px;">-->
                                                        <#--                                                        当前无考试任务</h2>-->
                                                        <#--                                                </div>-->
                                                        <#else>
                                                            <div class="f-cb">-->
                                                                <h2 class="u-ctit f-thide f-fl" id="j-chapter-title" style="width:320px;">
                                                                    当前无任务</h2>
                                                            </div>

                                                        </#if>
                                                        </#list>
                                                        </tbody>
                                                    </table><!----><!---->
                                                </div><!----><!----><!----><!---->
                                                <div class="el-table__column-resize-proxy" style="display: none;"></div></div></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    $('.info-tip').poshytip({
        className: 'tip-yellowsimple',
        showTimeout: 1,
        alignTo: 'target',
        alignX: 'center',
        offsetY: 5,
        allowTipHover: false
    });
</script>
