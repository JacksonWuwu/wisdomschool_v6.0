package cn.wstom.student.entity;


import lombok.Data;

@Data
public class Attendance extends BaseEntity {
    private static final long serialVersionUID = 1L;


    private String title;

    private Integer tcid;

    private Integer type;

    private Integer state;

    private Integer password;

    private Clbum clbum;

    private Integer cid;

    private String qrcode;

}
