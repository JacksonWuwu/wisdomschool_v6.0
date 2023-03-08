<style>
    input[type="radio"] {
        position: absolute;
        clip: rect(0, 0, 0, 0);
    }

    input[type="radio"] + label::before {
        content: "";
        background: #FFFFFF;
        border: 1px solid #C2C7CB;
        width: 15px;
        height: 15px;
        border-radius: 50%;
        float: left;
        margin-right: 6px;
        margin-top: 2px;
    }

    input[type="radio"]:checked + label::before {
        background-color: #76E025;
        background-clip: content-box;
        padding: 3px;
    }
    input[type="checkbox"] {
        position: absolute;
        clip: rect(0, 0, 0, 0);
    }

    input[type="checkbox"] + label::before {
        content: "";
        background: #FFFFFF;
        border: 1px solid #C2C7CB;
        width: 15px;
        height: 15px;
        border-radius: 50%;
        float: left;
        margin-right: 6px;
        margin-top: 2px;
    }

    input[type="checkbox"]:checked + label::before {
        background-color: #76E025;
        background-clip: content-box;
        padding: 3px;
    }
    .showanswer {
        width: 100px;
        height: 30px;
        background: green;
        border: none;
        border-radius: 6px;
        color: white;
        margin: 6px 10px;
        display: none;
    }

    .consummation {
        width: 100px;
        height: 30px;
        background: #f0ad4e;
        border: none;
        border-radius: 6px;
        color: white;
        margin: 6px 10px;
        display: block;
        margin: 0 auto;
        margin-top: 30px;
        margin-bottom: 40px;
    }

    .answer {
        color: red;
        margin-top: 5px;
    }
    .questionType{
        font-size: 20px;
        font-weight: bolder;
        padding-bottom: 10px;
    }
</style>
<div id="shuatishow"
     style="color: #000000;margin-left: 15px;margin-right:10px;margin-top: 8px;font-size: 15px;text-align: left;height: auto;line-height: normal">
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="09">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="09">
            <div style="font-size: 18px;margin-bottom: 10px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <#list s.myOptionAnswerList as m>
                    <li style="list-style: none;margin-left: 15px;margin-bottom: 5px">
                    <span>
                        <input type="radio" class="single" name="oneselect${s.myQuestions.id}"
                         <#if m_index ==0>
                                    id="${s.myQuestions.id}A" value="${m.stoption}">
                        <label for="${s.myQuestions.id}A" style="font-weight: normal;"
                        <#if m.stanswer!=0>
                               class="correct"
                            </#if>
                            >&nbsp;A.&nbsp;&nbsp;${m.stoption}
                        </label>
                        </#if>
                        <#if m_index ==1>
                            id="${s.myQuestions.id}B" value="${m.stoption}">
                            <label for="${s.myQuestions.id}B" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                   class="correct"
                            </#if>
                                >&nbsp;B.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==2>
                            id="${s.myQuestions.id}C" value="${m.stoption}">
                            <label for="${s.myQuestions.id}C" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                   class="correct"
                            </#if>
                                >&nbsp;C.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==3>
                            id="${s.myQuestions.id}D" value="${m.stoption}">
                            <label for="${s.myQuestions.id}D" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                   class="correct"
                            </#if>
                                >&nbsp;D.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==4>
                            id="${s.myQuestions.id}E" value="${m.stoption}">
                            <label for="${s.myQuestions.id}E" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;E.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==5>
                            id="${s.myQuestions.id}F" value="${m.stoption}">
                            <label for="${s.myQuestions.id}F" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;F.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                    </span>
                    </li>
                </#list>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="03">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="03">
            <div style=" font-size: 18px;margin-bottom: 10px;width:880px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <#list s.myOptionAnswerList as m>
                    <li style="list-style: none;margin-bottom: 5px">
                    <span>
                        <input class="checkbox" type="checkbox"  name="manyselect${s.myQuestions.id}"
                         <#if m_index ==0>
                                    id="${s.myQuestions.id}A" value="${m.stoption}">
                        <label for="${s.myQuestions.id}A" style="font-weight: normal;"
                        <#if m.stanswer!=0>
                            class="correct"
                        </#if>
                            >&nbsp;A.&nbsp;&nbsp;${m.stoption}
                        </label>
                        </#if>
                        <#if m_index ==1>
                            id="${s.myQuestions.id}B" value="${m.stoption}">
                            <label for="${s.myQuestions.id}B" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;B.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==2>
                            id="${s.myQuestions.id}C" value="${m.stoption}">
                            <label for="${s.myQuestions.id}C" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;C.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==3>
                            id="${s.myQuestions.id}D" value="${m.stoption}">
                            <label for="${s.myQuestions.id}D" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;D.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==4>
                            id="${s.myQuestions.id}E" value="${m.stoption}">
                            <label for="${s.myQuestions.id}E" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;E.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                        <#if m_index ==5>
                            id="${s.myQuestions.id}F" value="${m.stoption}">
                            <label for="${s.myQuestions.id}F" style="font-weight: normal;"
                            <#if m.stanswer!=0>
                                class="correct"
                            </#if>
                                >&nbsp;F.&nbsp;&nbsp;${m.stoption}
                            </label>
                        </#if>
                    </span>
                    </li>
                </#list>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="05">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="05">
            <div style=" font-size: 18px;margin-bottom: 10px;word-wrap:break-word;">${s.myQuestions.title}
                <li style="padding-left:10px;list-style: none;display: inline;float: right">
                       <span>
                           <input type="radio" class="judge"  name="judge${s.myQuestions.id}" id="${s.myQuestions.id}true" value="对">
                           <label for="${s.myQuestions.id}true" style="font-weight: normal;"><span style="color: #0E9A00" class="glyphicon glyphicon-ok"></span></label>
                       </span>
                </li>
                <li style="padding-left:10px;list-style: none;display: inline;float: right">
                       <span>
                           <input type="radio" class="judge" name="judge${s.myQuestions.id}" id="${s.myQuestions.id}false" value="错">
                           <label for="${s.myQuestions.id}false" style="font-weight: normal;"><span style="color: red" class="glyphicon glyphicon-remove"></span></label>
                       </span>
                </li>
            </div>
            <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
            <div id="${s.myQuestions.id}answer" class="answer"
                 style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="04">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="04">
            <div style="font-size: 18px;margin-bottom: 10px;width:880px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <div style="display:inline-block;vertical-align:top;">答：</div>
                <textarea id="${s.myQuestions.id}" class="shortAnswer" name="shortAnswerQquestion" rows="5"
                          style="width: 800px;"></textarea>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="06">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="06">
            <div style="font-size: 18px;margin-bottom: 10px;width:880px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <div style="display:inline-block;vertical-align:top;">答：</div>
                <textarea id="${s.myQuestions.id}" class="shortAnswer" name="shortAnswerQquestion" rows="5"
                          style="width: 800px;"></textarea>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="10">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="10">
            <div style="font-size: 18px;margin-bottom: 10px;width:880px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <div style="display:inline-block;vertical-align:top;">答：</div>
                <textarea id="${s.myQuestions.id}" class="programminguestion" name="shortAnswerQquestion" rows="5"
                          style="width: 800px;"></textarea>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="11">
            <p class="questionType">${s.myQuestions.titleTypeName}</p>
            <#break>
        </#if>
    </#list>
    <#list shuatis as s>
        <#if s.myQuestions.titleTypeNum=="11">
            <div style="font-size: 18px;margin-bottom: 10px;width:880px;word-wrap:break-word;">
                ${s.myQuestions.title}
            </div>
            <div style="margin-bottom: 20px">
                <div style="display:inline-block;vertical-align:top;">答：</div>
                <textarea id="${s.myQuestions.id}" class="programminguestion" name="shortAnswerQquestion" rows="5"
                          style="width: 800px;"></textarea>
                <button onclick="showanswer(${s.myQuestions.id})" class="showanswer">答案解析</button>
                <div id="${s.myQuestions.id}answer" class="answer"
                     style="display: none;width:880px;word-wrap:break-word;">答案：${s.myQuestions.parsing}</div>
            </div>
        </#if>
    </#list>
    <span id="chapterAlert" style="font-size:18px;width: 880px;color: red;display:none;text-align:center">&nbsp;&nbsp;题目没有刷完，请继续刷题</span>
    <button class="consummation" id="consummation" onclick="chapterConsummation(${listsize},'${myQuestionsIds}')">完成刷题</button>
</div>
