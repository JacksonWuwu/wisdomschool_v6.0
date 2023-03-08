<#include "/front/account/component/header.ftl"/>

<div class="u-container">

            <div class="container">
                <div class="row feature">
                    <div style="width: 890px; margin-bottom: 30px;">
                        <div style="margin-left: 30px; border-bottom: solid 1px #bebebe">
                            <h4 style="margin: 20px 30px;">
                                <span style="color: #1d9cba;" class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;试卷
                            </h4>
                        </div>
                        <div>
                        <#--     内容  -->
                            <#list exams as paper>
                             <div class="courseitem">
                                 <div class="img-box">
                                 </div>
                                 <div class="info-box">
                                     <div class="title">
                                         <span>试卷名:</span>
                                         <a class="hd">${paper.testName}</a>
                                     </div>
                                     <div class="study-info">
                                         <p class="follow-desc">${paper.beiZhu}</p>
                                     </div>
                                     <h5>时间: ${paper.time}</h5>
                                     <div class="op">
                                         <button class="learn-btn" onclick="openExamWindow(${userId}, ${paper.testPaperId}, ${paper.id})">开始</button>
                                     </div>
                                 </div>
                             </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
        </div>

<script src="/js/plugins/layui/layui.js"></script>
<script>
    function openExamWindow(userId,testPaperId,tutId){
        var token=localStorage.getItem("token")
        window.open('/account/courseExam?testPaperId=' + testPaperId + '&&studentId=' + userId +"&&tutId="+tutId+"&&token="+token, '_blank');
    }
</script>
<#include "/front/account/component/footer.ftl"/>
