package cn.wstom.student.entity;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 开篇导学
 *
 * @author dws
 * @date 2019/02/22
 */
@Getter
@ToString
@Setter
public class Lead extends BaseEntity {
    private static final long serialVersionUID = 22824849907902375L;

    /**
     * 教师课程id
     */
    private String tcId;

    /**
     * 内容
     */
    private String content;
}
