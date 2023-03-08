package cn.wstom.student.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dws
 * @date 2019/03/31
 */
@Getter
@Setter
@ToString
public class Forum extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 0非仅学生可见
     * 1仅学生可见
     */
    private String tcid;
    private String type;
    private String name;
    private String description;
}
