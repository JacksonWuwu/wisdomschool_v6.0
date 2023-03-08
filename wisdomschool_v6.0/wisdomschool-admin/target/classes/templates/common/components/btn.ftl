<#macro dictRadio dictName >
    <@dictionary dictType=dictName>
        <#list dictData as row>
            <div class="radio-box" <#if (row.isDefault == "y")>checked</#if>>
                <input type="radio" id="${row.dictType}${row.dictValue}" name="visible" value="${row.dictValue}"
                       <#if (row.isDefault == "y")>checked</#if>/>
                <label for="${row.dictType}${row.dictValue}">${row.dictLabel}</label>
            </div>
        </#list>
    </@dictionary>
</#macro>