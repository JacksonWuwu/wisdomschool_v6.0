package cn.wstom.admin.entity;

import cn.wstom.admin.entity.Clbum;
import cn.wstom.admin.entity.Department;
import cn.wstom.admin.entity.Major;
import cn.wstom.common.annotation.Excel;
import lombok.Data;

/**
 * 班级Vo
 *
 * @author hyb
 * @date 2019/02/21
 */
@Data
public class ClbumVo extends Clbum {
    private static final long serialVersionUID = -4765542647341463846L;

    private String tid;

    /**
     * 系部信息
     */
    @Excel(name = "系部", comboField = "name", targetAttr = "name")
    private Department department;

    /**
     * 专业信息
     */
    @Excel(name = "专业", comboField = "name", targetAttr = "name")
    private Major major;
}
