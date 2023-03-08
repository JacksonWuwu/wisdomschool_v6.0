package cn.wstom.student.service.impl;

import cn.wstom.student.entity.AttendanceDetail;
import cn.wstom.student.mapper.AttendanceDetailMapper;
import cn.wstom.student.service.AttendanceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceDetailServiceImpl extends BaseServiceImpl<AttendanceDetailMapper, AttendanceDetail>
        implements AttendanceDetailService {

    @Autowired
    private AttendanceDetailMapper attendanceDetailMapper;

    @Override
    public List<AttendanceDetail> selectListBysids(List<String> sids) {
        return attendanceDetailMapper.selectListBysids(sids);
    }

    @Override
    public List<Integer> findAllstudentid(Integer attendanceid) {
        return attendanceDetailMapper.findAllstudentid(attendanceid);
    }

    @Override
    public Boolean updateByAidAndSid(AttendanceDetail attendanceDetail) {
        return attendanceDetailMapper.updateByAidAndSid(attendanceDetail);
    }

    @Override
    public AttendanceDetail getByattendanceDetail(AttendanceDetail attendanceDetail) {
        return attendanceDetailMapper.getByattendanceDetail(attendanceDetail);
    }

    @Override
    public List<AttendanceDetail> getByAid(String aid) {
        return attendanceDetailMapper.getByAid(aid);
    }

    @Override
    public void updateBySidAndTcid(AttendanceDetail attendanceDetail) {

        attendanceDetailMapper.updateBySidAndTcid(attendanceDetail);
    }

    @Override
    public List<AttendanceDetail> selectOkListByTcidAndResults(Integer aid) {
        return attendanceDetailMapper.selectOkListByTcidAndResults(aid);
    }


}
