package cn.wstom.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SdsadminRole extends BaseEntity {
    private Integer sid;
    private int rid;
    private int grades;
    private int department;
    private Grades gradess;
    private Department departments;
}
