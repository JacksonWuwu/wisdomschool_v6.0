package cn.wstom.admin.entity;


import lombok.Data;

import java.sql.Date;

@Data
public class AttendanceVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Integer tcid;

    private Date createTime;

    private Integer type;

    private Integer state;

    private Integer password;

    private Clbum clbum;

    private Integer cid;
    private Integer aid;

    private Integer sid;

    private Integer results;

    private String loginName;

    private String userName;
}
