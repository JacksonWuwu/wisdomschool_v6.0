<!--
    下拉列表
    存在nextId则为级联下拉列表
 -->
<#macro selectPage id name values attrName url init selected nextId>
    <#if values??>
        <select id="${id}" class="form-control m-b"
                name="${name}">
            <#list values as value>
                <option value="${value.id}">${value.name}</option>
            </#list>
        </select>
    <#else >
        <select id="${id}" class="form-control m-b select2Cascade"
                <#if attrName??>data-attrname="${attrName}"</#if>
                name="${name}" data-url="${url}" data-init="${init}"
                <#if selected??>data-selected="${selected}"</#if>
                <#if nextId?? > data-next="${nextId}"</#if>>
        </select>
    </#if>
</#macro>