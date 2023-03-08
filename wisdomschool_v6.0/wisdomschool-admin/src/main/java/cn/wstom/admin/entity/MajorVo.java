package cn.wstom.admin.entity;

import cn.wstom.admin.entity.Department;
import cn.wstom.admin.entity.Major;
import cn.wstom.common.annotation.Excel;

import lombok.Data;

/**
 * 专业Vo
 *
 * @author hyb
 * @date 2019/02/21
 */
@Data
public class MajorVo extends Major {
    private static final long serialVersionUID = -4765542647341463846L;

    /**
     * 系部信息
     */
    @Excel(name = "系部", comboField = "name", targetAttr = "name")
    private Department department;
}
