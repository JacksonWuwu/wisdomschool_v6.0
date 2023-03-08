package cn.wstom.admin.service;

import cn.wstom.admin.entity.Attendance;

import java.util.List;

public interface AttendanceService extends BaseService<Attendance> {


    List<String> findAllnumbercode();

    List<String> findAllqrcode();

    Integer getByTcidTostate(Integer tcid);

    Attendance selectByTcid(Integer tcid);


    int addreturn(Attendance attendance);
}
