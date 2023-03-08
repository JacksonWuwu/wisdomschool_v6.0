package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<AttendanceMapper, Attendance>
        implements AttendanceService {


    @Autowired
    private AttendanceMapper attendanceMapper;

    @Override
    public List<String> findAllnumbercode() {
        return attendanceMapper.findAllnumbercode();
    }

    @Override
    public List<String> findAllqrcode() {
        return attendanceMapper.findAllqrcode();
    }

    @Override
    public Integer getByTcidTostate(Integer tcid) {
        return attendanceMapper.getByTcidTostate(tcid);
    }

    @Override
    public Attendance selectByTcid(Integer tcid) {
        return attendanceMapper.selectByTcid(tcid);
    }

    @Override
    public int addreturn(Attendance attendance) {
        attendanceMapper.addreturn(attendance);
        int i = Integer.valueOf(attendance.getId());
        return i;
    }
}
