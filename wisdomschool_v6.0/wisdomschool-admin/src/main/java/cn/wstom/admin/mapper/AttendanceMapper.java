package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.Attendance;

import java.util.List;

public interface AttendanceMapper extends BaseMapper<Attendance> {

    List<String> findAllnumbercode();

    List<String> findAllqrcode();

    Integer getByTcidTostate(Integer tcid);

    Attendance selectByTcid(Integer tcid);

    int addreturn(Attendance attendance);
}
