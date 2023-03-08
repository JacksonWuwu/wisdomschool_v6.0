<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE ${r'mapper'}
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.mapper.${className}Mapper">

    <resultMap type="${className}" id="${className}Result">
        <#list fullColumns as column>
            <result property="${column.attrname}" column="${column.columnName}"/>
        </#list>
    </resultMap>

    <sql id="select${className}Vo">
        <#assign arrLength = fullColumns?size/>
        select <#list fullColumns as column> ${column.columnName}<#if (column_index != (arrLength - 1))>,</#if></#list>
        from ${tableName}
    </sql>

    <select id="selectList" parameterType="${className}" resultMap="${className}Result">
        <include refid="select${className}Vo"/>
        <where>
            <#list fullColumns as column>
                <if test="${column.attrname} != null <#if (column.attrType == 'String' )> and ${column.attrname}.trim() != '' </#if>">
                    and ${column.columnName} = ${r'#{'}${column.attrname}${r'}'}</if>
            </#list>
        </where>
    </select>

    <select id="selectById" parameterType="${primaryKey.attrType}" resultMap="${className}Result">
        <include refid="select${className}Vo"/>
        where id = ${r'#{'}id${r'}'}
    </select>

    <insert id="insert"
            parameterType="${className}"<#if (primaryKey.extra == 'auto_increment')> useGeneratedKeys="true" keyProperty="${primaryKey.attrname}"</#if>>
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list fullColumns as column>
                <#if ((column.columnName != primaryKey.columnName) || (primaryKey.extra != 'auto_increment'))>
                    <if test="${column.attrname} != null <#if (column.attrType == 'String' )> and ${column.attrname} != '' </#if> ">${column.columnName}
                        ,
                    </if>
                </#if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list fullColumns as column>
                <#if ((column.columnName != primaryKey.columnName) || (primaryKey.extra != 'auto_increment'))>
                    <if test="${column.attrname} != null <#if (column.attrType == 'String' )> and ${column.attrname} != '' </#if> ">${r'#{'}${column.attrname}${r'}'}
                        ,
                    </if>
                </#if>
            </#list>
        </trim>
    </insert>

    <update id="update" parameterType="${className}">
        update ${tableName}
        <trim prefix="SET" suffixOverrides=",">
            <#list fullColumns as column>
                <#if (column.columnName != primaryKey.columnName)>
                    <if test="${column.attrname} != null <#if (column.attrType == 'String' )> and ${column.attrname} != '' </#if> ">${column.columnName}
                        = ${r'#{'}${column.attrname}${r'}'},
                    </if>
                </#if>
            </#list>
        </trim>
        where id = ${r'#{'}id${r'}'}
    </update>

    <delete id="deleteById" parameterType="String">
        delete from ${tableName} where id = ${r'#{'}id${r'}'}
    </delete>

    <delete id="deleteBatchIds" parameterType="String">
        delete from ${tableName} where ${primaryKey.columnName} in
        <foreach item="id" collection="list" open="(" separator="," close=")">
            ${r'#{'}id${r'}'}
        </foreach>
    </delete>

</mapper>