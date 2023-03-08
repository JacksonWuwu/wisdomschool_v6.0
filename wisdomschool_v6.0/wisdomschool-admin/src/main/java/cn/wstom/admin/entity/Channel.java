/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模块/内容分组
 *
 * @author dws
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Channel extends BaseEntity {
    private static final long serialVersionUID = 2436696690653745208L;

    /**
     * 名称
     */
    private String name;

    /**
     * 唯一关键字
     */
    private String key;

    /**
     * 状态
     * 0：正常
     * 1：禁用
     */
    private Integer status;
}
