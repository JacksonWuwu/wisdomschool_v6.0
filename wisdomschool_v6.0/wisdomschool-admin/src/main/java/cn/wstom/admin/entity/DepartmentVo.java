package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;
import lombok.Data;

/**
 * 教师Vo
 *
 * @author xyl
 * @date 2019/02/15
 */
@Data
public class DepartmentVo {
    private static final long serialVersionUID = -4765542647341463846L;

    /**
     * 系部信息
     */
    @Excel(name = "院系名称", comboField = "name", targetAttr = "name")
    private Department department;

}
