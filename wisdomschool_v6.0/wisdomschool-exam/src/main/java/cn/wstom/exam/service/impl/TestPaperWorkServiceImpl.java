package cn.wstom.exam.service.impl;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.ClbumCourseVo;
import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TeacherCourse;
import cn.wstom.exam.entity.TestPaperWork;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.TestPaperWorkMapper;
import cn.wstom.exam.service.TestPaperWorkService;
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
public class TestPaperWorkServiceImpl extends BaseServiceImpl<TestPaperWorkMapper, TestPaperWork> implements TestPaperWorkService {

    @Resource
    private TestPaperWorkMapper testPaperWorkMapper;
    @Resource
    private AdminService adminService;


    @Override
    public boolean update(TestPaperWork entity) throws Exception {
        String type = testPaperWorkMapper.selectById(entity.getId()).getSetExam();
        if (type.equals(Constants.EXAM_TYPE_ING)
                || type.equals(Constants.EXAM_TYPE_DONE)) {
            return false;
        } else {
            return super.update(entity);
        }
    }


    @Override
    public List<TestPaperWork> selectPaper(TestPaperWork testPaperOne) {
        return testPaperWorkMapper.selectPaper(testPaperOne);
    }

    @Override
    public boolean updateWithoutCheckType(TestPaperWork entity) throws Exception {
        return false;
    }

    @Override
    public String checkTestNameUnique(TestPaperWork testPaper) {
        return null;
    }

    @Override
    public boolean updateSetExam(TestPaperWork testPaper) {
        return testPaperWorkMapper.updateSetExam(testPaper);
    }

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaperWork> getStudentInfo(@Param("teacherId") String teacherId) {
        return testPaperWorkMapper.getStudentInfo(teacherId);
    }

    /**
     * 获取科目
     *
     * @param courseId
     * @return
     */
    @Override
    public List<TestPaperWork> getTcoName(@Param("courseId") String courseId) {
        return testPaperWorkMapper.getTcoName(courseId);
    }

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaperWork> getTcoStu(@Param("teacherId") String teacherId) {
        return testPaperWorkMapper.getTcoStu(teacherId);
    }


    /**
     * 查找学生试卷
     *
     * @param testPaper
     * @return
     */
    @Override
    public List<TestPaperWork> findStuPaper(TestPaperWork testPaper) {
        return testPaperWorkMapper.findStuPaper(testPaper);
    }

    /**
     * 获取作业资源和相关班级的目录
     * @return
     */
    @Override
    public List<Map<String, Object>> getTestPagerAndClbumTree(SysUser user, String cId, String pagerType) {
        List<Map<String, Object>> tree = new ArrayList<>();

        TestPaperWork testPaper = new TestPaperWork();
        testPaper.setCreateId(user.getUserAttrId());
        testPaper.setCoursrId(cId);
        testPaper.setType(pagerType);
        List<TestPaperWork> list = testPaperWorkMapper.selectList(testPaper);

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCid(cId);
        teacherCourse.setTid(user.getUserAttrId());
        TeacherCourse rtc = adminService.teacherCourseList(teacherCourse).get(0);

        ClbumCourseVo clbumCourse = new ClbumCourseVo();
        clbumCourse.setTcid(rtc.getId());
        List<ClbumCourseVo> clbumCourseVos = adminService.clbumCourseVos(clbumCourse);

        for (int i=0; i<list.size(); i++) {
            Map<String, Object> pager = new HashMap<>();
            TestPaperWork tp = list.get(i);
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
    public TestPaperWork selectOne(TestPaperWork testPaperWork) {
        return testPaperWorkMapper.selectOne(testPaperWork);
    }

}
