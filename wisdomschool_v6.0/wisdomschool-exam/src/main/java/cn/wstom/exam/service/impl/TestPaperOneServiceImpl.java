package cn.wstom.exam.service.impl;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.ClbumCourseVo;
import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TeacherCourse;
import cn.wstom.exam.entity.TestPaperOne;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.TestPaperOneMapper;
import cn.wstom.exam.service.TestPaperOneService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试卷 服务层实现
 *
 * @author hzh
 * @date 20190223
 */
@Service
public class TestPaperOneServiceImpl extends BaseServiceImpl<TestPaperOneMapper, TestPaperOne> implements TestPaperOneService {

    @Resource
    private TestPaperOneMapper testPaperOneMapper;
    @Resource
    private AdminService adminService;


    @Override
    public boolean update(TestPaperOne entity) throws Exception {
        String type = testPaperOneMapper.selectById(entity.getId()).getSetExam();
        if (type.equals(Constants.EXAM_TYPE_ING)
                || type.equals(Constants.EXAM_TYPE_DONE)) {
            return false;
        } else {
            return super.update(entity);
        }
    }


    @Override
    public List<TestPaperOne> selectPaper(TestPaperOne testPaperOne) {
        return testPaperOneMapper.selectPaper(testPaperOne);
    }

    @Override
    public boolean updateWithoutCheckType(TestPaperOne entity) throws Exception {
        return false;
    }

    @Override
    public String checkTestNameUnique(TestPaperOne testPaper) {
        return null;
    }

    @Override
    public boolean updateSetExam(TestPaperOne testPaper) {
        return testPaperOneMapper.updateSetExam(testPaper);
    }

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaperOne> getStudentInfo(@Param("teacherId") String teacherId) {
        return testPaperOneMapper.getStudentInfo(teacherId);
    }

    /**
     * 获取科目
     *
     * @param courseId
     * @return
     */
    @Override
    public List<TestPaperOne> getTcoName(@Param("courseId") String courseId) {
        return testPaperOneMapper.getTcoName(courseId);
    }

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaperOne> getTcoStu(@Param("teacherId") String teacherId) {
        return testPaperOneMapper.getTcoStu(teacherId);
    }


    /**
     * 查找学生试卷
     *
     * @param testPaper
     * @return
     */
    @Override
    public List<TestPaperOne> findStuPaper(TestPaperOne testPaper) {
        return testPaperOneMapper.findStuPaper(testPaper);
    }

    /**
     * 获取作业资源和相关班级的目录
     * @return
     */
    @Override
    public List<Map<String, Object>> getTestPagerAndClbumTree(SysUser user, String cId, String pagerType) {
        List<Map<String, Object>> tree = new ArrayList<>();

        TestPaperOne testPaper = new TestPaperOne();
        testPaper.setCreateId(user.getUserAttrId());
        testPaper.setCoursrId(cId);
        testPaper.setType(pagerType);
        List<TestPaperOne> list = testPaperOneMapper.selectList(testPaper);

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCid(cId);
        teacherCourse.setTid(user.getUserAttrId());
        TeacherCourse rtc = adminService.teacherCourseList(teacherCourse).get(0);

        ClbumCourseVo clbumCourse = new ClbumCourseVo();
        clbumCourse.setTcid(rtc.getId());
        List<ClbumCourseVo> clbumCourseVos = adminService.clbumCourseVos(clbumCourse);

        for (int i=0; i<list.size(); i++) {
            Map<String, Object> pager = new HashMap<>();
            TestPaperOne tp = list.get(i);
            pager.put("id", tp.getId());
            pager.put("name", tp.getTestName());
            pager.put("title", tp.getTestName());
            pager.put("pId", -1);
            tree.add(pager);
            int finalI = i;
            clbumCourseVos.forEach(c -> {
                Map<String, Object> clbumCourseMap = new HashMap<>();
                clbumCourseMap.put("id", c.getCid());
                if (StringUtils.isNotEmpty(c.getClbum().getName())) {
                    clbumCourseMap.put("name", c.getClbum().getName());
                    clbumCourseMap.put("title", c.getClbum().getName());
                }
//                clbumCourseMap.put("")    TODO:   添加其他数据
                clbumCourseMap.put("pId", tp.getId());
                tree.add(clbumCourseMap);
            });
        }

        return tree;
    }

    @Override
    public int updateTimeById(TestPaperOne testPaperOne){
        try{
            testPaperOneMapper.updateTimeById(testPaperOne);
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    @Override
    public TestPaperOne getById(String id){

        return testPaperOneMapper.getById(id);
    }

    @Override
    public TestPaperOne selectOne(TestPaperOne testPaperOne){

        return testPaperOneMapper.selectOne(testPaperOne);
    }


}
