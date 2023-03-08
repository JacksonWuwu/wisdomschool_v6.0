package cn.wstom.student.mapper;





import cn.wstom.student.entity.AttendanceDetail;

import java.util.List;

public interface AttendanceDetailMapper extends BaseMapper<AttendanceDetail> {

    List<AttendanceDetail> selectListBysids(List<String> sids);
    List<Integer> findAllstudentid(Integer attendanceid);
    Boolean updateByAidAndSid(AttendanceDetail attendanceDetail);
    AttendanceDetail getByattendanceDetail(AttendanceDetail attendanceDetail);
    List<AttendanceDetail> getByAid(String aid);

    void updateBySidAndTcid(AttendanceDetail attendanceDetail);
    List<AttendanceDetail> selectOkListByTcidAndResults(Integer aid);
}
