package cn.wstom.student.entity;



import lombok.Data;

@Data
public class AttendanceDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer aid;

    private Integer sid;

    private Integer results;

    private String loginName;

    private String userName;

    private Integer tcid;

    public AttendanceDetail() {
    }
}
