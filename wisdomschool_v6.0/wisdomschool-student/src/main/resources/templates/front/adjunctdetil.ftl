<div class="container">

    <div class="login-box col-md-12">
<#--        <div id="start" style="  color: black;" />-->
        <form class="form-horizontal" id="adjunctform">
            <h1>上机作业-上传</h1>
            <div class="form-group ">
                <div>
                    当前时间为：<span id="times"></span>
                </div>
                <div>
                    结束时间为：<span id="times">${adjunct.deadline}</span>
                </div>
            </div>
            <div class="form-group ">
                <div class="input-group  col-sm-6">
                    <#--<label class="col-sm-2 control-label" id="ajid">${adjunct.adjunctname}</label>-->
                </div>
            </div>
            <div class="form-group ">
                <#if adjunctStudent.states==0>
                    <label class="col-sm-2 control-label">上机文档</label>
                    <#else>
                    <label class="col-sm-2 control-label">上传记录</label>
                </#if>
                <div class="input-group  col-sm-6">
                    <#if adjunctStudent.states==0>
                        <#list list.rows as row>
                            <div class="res-body-title">
                            <div class="res-title-img">
                                <img src="/img/front/res.jpg"/>
                            </div>
                            <div class="res-title">
                            <a href="#" yuname="${row.name }" url="${row.id }"
                        onclick="readFile(this)"> ${row.name }</a><br/>
                                <!--href="/modal/ziyuan/documentView.jsp?filePath=${row.id }"-->
                                <!--small>上传者：${row.createBy} </small-->
                            <small>上传时间：${row.createTime?string('yyyy-MM-dd HH:mm:ss')}</small>
                            <small>格式：${row.ext }</small>
                            <small>下载次数：${row.count }</small>
                            </div>
                            <div class="res-title-download">
                            <#if row.ext == 'pdf'>
                                <a href="#" url="${fylist[4] }" name="${fylist[0] }" onclick="pdfView('${row.attrId}')"
                            yunid="${fylist[5] }">在线浏览</a>
                            </#if>
                        <a href="#" url="${fylist[4] }" name="${fylist[0] }" onclick="downLoad('${row.attrId}')"
                        yunid="${fylist[5] }">点击下载</a>
                            </div>
                            </div>
                        </#list>

                    <#else>
                        <div class="res-body-title">
                        <div class="res-title-img">
                            <img src="/img/front/res.jpg"/>
                        </div>
                        <div class="res-title">
                        <a href="#"> ${adjunctStudent.filename}</a><br/>
                        <small>上传时间：${adjunctStudent.submitline}</small>
                        </div>
                        <div class="res-title-download">
                            <a href="#" onclick="deleteadjunct('${adjunctid}','${sid}','${adjunctStudent.filename}')">点击删除</a>
                        </div>
                        </div>
                    </#if>
                </div>
            </div>

            <#--<div class="form-group ">-->
                <#--<#if adjunctStudent.states==0>-->
                    <#--<label class="col-sm-2 control-label">上机备注</label>-->
                    <#--<div class="input-group  col-sm-8">-->
                <#--&lt;#&ndash;                    <label class="col-sm-2 control-label" id="ajid">${adjunct.jobcontent}</label>&ndash;&gt;-->
                    <#--<textarea id="textarea1"  rows="4" readonly="true">作业暂无备注</textarea>-->
                    <#--</div>-->
                <#--<#else>-->
                <#--</#if>-->

            <#--</div>-->

            <#if adjunctStudent.states==0>
                <div class="form-group">
                    <label class="col-sm-2 control-label">上传作业：</label>
                    <div class="input-group col-sm-8">
                        <div >
                            <input type="file" id="input-file-now" class="dropify" />
                        </div>
                    </div>
                </div>
                <#else>
                <div class="form-group">
                    <label class="col-sm-2 control-label">重新上传：</label>
                    <div class="input-group col-sm-8">
                        <div >
                            <input type="file" id="input-file-now" class="dropify" />
                        </div>
                    </div>
                </div>
            </#if>

            <div class="form-group ">
                <#if adjunctStudent.states==0>
                    <label class="col-sm-2 control-label">作业心得</label>
                    <div class="input-group  col-sm-10">
                    <textarea id="textarea" name="jobcontent" cols="150" rows="8" >
                    </textarea>
                    </div>
                <#else>
                </#if>

            </div>

            <div class="alert alert-info" style="width: 100%;">
                <div align="center">
                    <button type="button" class="btn btn-outline-primary" onclick="release()">
                        <font style="vertical-align: inherit;font-size: 22;">提交</font>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="http://cdn.bootcss.com/tinymce/4.4.3/tinymce.min.js"></script>
<#include "/common/stretch.ftl"/>
<script src="/js/dropify/dropify.min.js" type="text/javascript"></script>
<script type="text/javascript">
        //得到时间并写入span标签
        function getDate(){
            //获取当前时间
            var date = new Date();
            //格式化为本地时间格式
            var date1 = date.toLocaleString();
            //获取span标签ID
            var div1 = document.getElementById("times");
            //将时间写入span标签
            div1.innerHTML = date1;
        }
    //使用定时器每秒向div写入当前时间
    setInterval("getDate()",1000);

    $('.dropify').dropify();
    $(function () {
        $.common.initBind();
    });


    tinymce.init({ selector:'textarea' });
    document.getElementById("textarea1").value="${adjunct.jobcontent}";
    $().ready(function () { //$().ready页面加载好就执行

    });


    let prefix = "/user/${adjunct.id}";
    // $("#adjunctform").validate({
    //     rules: {
    //         "numbercode": {
    //             required: true
    //         },
    //     },
    //     messages: {
    //         "numbercode": {
    //             required: "请输入签到码"
    //         },
    //     }
    // });
    function release() {
        let prefix = "/user/${adjunct.id}";
        var formData=new FormData();
        var filePath = $("#input-file-now")[0].value;    //js中无法获取文件的真是路径
        var fileName = filePath.substring(filePath.lastIndexOf('\\')+1); //文件名
        var extName = fileName.substring(fileName.lastIndexOf('.')+1);    //后缀名
        console.log("文件后缀名"+extName);
        formData.append("f",$("#input-file-now")[0].files[0]);
        formData.append("fileName",fileName);
        // formData.append("asid",ASID);
        formData.append("type",extName);
        $.ajax({
            type: "POST",
            url: prefix + "/updataAdjunct",
            data:formData,
            contentType: false,
            processData: false,
            dataType:"json",
            success:function(data){
                alert(data.msg);
                window.location.href="/user/course/adjunct/${tcid}"+"&token="+localStorage.getItem("token");
            },
            error:function (msg) {
                alert("服务器故障，请联系管理员");
            }
        })
    }


    function downLoad(fileId) {
        window.location.href = "/resource/download/" + fileId+"&token="+localStorage.getItem("token");
    }

    function downadjunct(fileId){
        window.location.href = "/resource/downloadadjunct/" +fileId+"/${sid}"+"/${adjunctStudent.filename}"+"&token="+localStorage.getItem("token");
    }


    function deleteadjunct(fileId,sid,fileName){
        var url="/resource/deleteadjunct";
        $.post(url,{
            "fileId":fileId,
            "uid":sid,
            "fileName":fileName,
        },function (data) {
            alert("删除成功，请重新上传")
            window.location.href="/user/toadjunct?id=${adjunctid}"+"&token="+localStorage.getItem("token");
        },"json");
    }


</script>
