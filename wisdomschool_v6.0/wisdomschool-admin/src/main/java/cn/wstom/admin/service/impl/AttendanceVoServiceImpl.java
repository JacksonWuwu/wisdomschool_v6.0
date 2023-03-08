package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceVoServiceImpl extends BaseServiceImpl<AttendanceVoMapper, AttendanceVo>
        implements AttendanceVoService {

    @Autowired
    private AttendanceVoMapper attendanceVoMapper;
}
