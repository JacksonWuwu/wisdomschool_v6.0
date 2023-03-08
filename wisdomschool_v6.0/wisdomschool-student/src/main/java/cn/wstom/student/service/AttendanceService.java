package cn.wstom.student.service;



import cn.wstom.student.entity.Attendance;

import java.util.List;

public interface AttendanceService extends BaseService<Attendance> {


    List<String> findAllnumbercode();

    List<String> findAllqrcode();

    Integer getByTcidTostate(Integer tcid);

    Attendance selectByTcid(Integer tcid);


    int addreturn(Attendance attendance);
}
