package cn.wstom.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SdsadminVo extends SysUser {
    private static final long serialVersionUID = -4765542647341463846L;
    private Sdsadmin sdsadmin;
    private int grades;
    private int department;
    private int rid;
    private Grades gradess;
    private Department departments;
    private String gdid;
    private Integer sid;
}
