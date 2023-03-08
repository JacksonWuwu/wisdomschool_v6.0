<#include "/common/style.ftl"/>
<body class="white-bg">
<div class="container-div clearfix">
    <div class="wrapper wrapper-content animated fadeInRight ibox-content">
        <form class="form-horizontal m" id="form-testPaperOne-edit">
<#--            <input id="id" name="id"-->
<#--                   value="${id}" type="hidden">-->
<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label">标题：</label>-->
<#--                <div class="col-sm-8">-->
<#--                    <input id="headline" name="headline"-->
<#--                           value="${testPaper.headline}" class="form-control"-->
<#--                           type="text">-->
<#--                </div>-->
<#--            </div>-->

<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label">试卷名：</label>-->
<#--                <div class="col-sm-8">-->
<#--                    <input id="testName" name="testName"-->
<#--                           value="${testPaper.testName}" class="form-control"-->
<#--                           type="text">-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label">备注：</label>-->
<#--                <div class="col-sm-8">-->
<#--                    <input id="beiZhu" name="beiZhu"-->
<#--                           value="${testPaper.beiZhu}" class="form-control"-->
<#--                           type="text">-->
<#--                </div>-->
<#--            </div>-->
<#--        </form>-->
            <div class="form-group">
                <label class="col-sm-3 control-label">试卷名：</label>
                <div class="col-sm-8">
                    <input id="testName" name="testName" value="${testPaper.testName}" class="form-control" type="text">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">考试时长/分钟：</label>
                <div class="col-sm-8">
                    <input id="time" name="time" value="${testPaper.time}" class="form-control" type="text" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                           onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">有效时段：</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="start_Time" name="startTime" value="${testPaper.startTime}"  readonly />到
                    <input type="text" class="form-control" id="endTime" name="endTime" value="${testPaper.endTime}"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">人脸识别：</label>
                <div class="col-sm-8">
                    <select style="width:220px;" id="face" name="face">
                        <option value="0"  <#if testPaper.face == "0">selected</#if>>否</option>
                        <option value="1" <#if testPaper.face == "1">selected</#if> >是</option>
                    </select>
                </div>
            </div>
            <#--<div class="form-group">-->
            <#--    <label class="col-sm-3 control-label">图像检测：</label>-->
            <#--    <div class="col-sm-8">-->
            <#--        <select style="width:220px;" id="imageCheck" name="imageCheck">-->
            <#--            <option value="0"  <#if testPaper.imageCheck == "0">selected</#if>>否</option>-->
            <#--            <option value="1" <#if testPaper.imageCheck == "1">selected</#if> >是</option>-->
            <#--        </select>-->
            <#--    </div>-->
            <#--</div>-->
            <div class="form-group">
                <label class="col-sm-3 control-label">切屏检测：</label>
                <div class="col-sm-8">
                    <select style="width:220px;" id="screenCheck" name="screenCheck">
                        <option value="0"  <#if testPaper.screenCheck == "0">selected</#if>>否</option>
                        <option value="1" <#if testPaper.screenCheck == "1">selected</#if> >是</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">视频监控：</label>
                <div class="col-sm-8">
                    <select style="width:220px;" id="monitor" name="monitor">
                        <option value="0"  <#if testPaper.monitor == "0">selected</#if>>否</option>
                        <option value="1" <#if testPaper.monitor == "1">selected</#if> >是</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 control-label">备注：</label>
                <div class="col-sm-8">
                    <input id="beiZhu" name="beiZhu" class="form-control" type="text">
                    <input id="coursrId" name="coursrId" type="hidden" value="${testPaper.courseId}">
                    <input id="id" name="id" type="hidden" value="${testPaper.id}">
                </div>
            </div>
            </div>
        </form>
    </div>
</div><!--/.container-div-->
<#include "/common/stretch.ftl"/>
<script type="text/javascript">
    $(function () {
        $.common.initBind();
    });
    let prefix = "/school/onlineExam/testPaperOne"
    $("#form-testPaperOne-edit").validate({
        rules: {
            // headline: {
            //     required: true
            // },
            testName: {
                required: true
            }

        }
    });

    function submitHandler() {
        if ($.validate.form()) {
            $.operate.saveModal(prefix + "/edit", $('#form-testPaperOne-edit').serialize());
        }
    }
</script>
</body>
