package cn.wstom.exam.service.impl;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.ClbumCourseVo;
import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TeacherCourse;
import cn.wstom.exam.entity.TestPaper;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.TestPaperMapper;
import cn.wstom.exam.service.TestPaperService;
import cn.wstom.exam.utils.StringUtil;
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
public class TestPaperServiceImpl extends BaseServiceImpl<TestPaperMapper, TestPaper>
        implements TestPaperService {

    @Resource
    private TestPaperMapper testPaperMapper;
    @Resource
    private AdminService adminService;



    @Override
    public boolean update(TestPaper entity) throws Exception {
        String type = testPaperMapper.selectById(entity.getId()).getSetExam();
        if (type.equals(Constants.EXAM_TYPE_ING)
                || type.equals(Constants.EXAM_TYPE_DONE)) {
            return false;
        } else {
            return super.update(entity);
        }
    }


    @Override
    public boolean updateWithoutCheckType(TestPaper entity) throws Exception{
        return super.update(entity);
    }

    /**
     * 校验标题名唯一
     *
     * @param paper
     * @return
     */
    @Override
    public String checkTestNameUnique(TestPaper paper) {
        if (StringUtil.isNotNull(paper)) {
            TestPaper testPaper = testPaperMapper.checkTestNameUnique(paper.getTestName());
            if (StringUtil.isNotNull(testPaper) && StringUtil.isNotNull(testPaper.getId())
                    && testPaper.getId().equals(paper.getId())) {
                return Constants.CHECK_NOT_UNIQUE;
            }
        }
        return Constants.CHECK_UNIQUE;
    }

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaper> getStudentInfo(@Param("teacherId") String teacherId) {
        return testPaperMapper.getStudentInfo(teacherId);
    }

    /**
     * 获取科目
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaper> getTcoName(@Param("teacherId") String teacherId) {
        return testPaperMapper.getTcoName(teacherId);
    }

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<TestPaper> getTcoStu(@Param("teacherId") String teacherId) {
        return testPaperMapper.getTcoStu(teacherId);
    }

    /**
     * 查找学生试卷
     *
     * @param testPaper
     * @return
     */
    @Override
    public List<TestPaper> findStuPaper(TestPaper testPaper) {
        return testPaperMapper.findStuPaper(testPaper);
    }

    /**
     * 获取作业资源和相关班级的目录
     * @return
     */
    @Override
    public List<Map<String, Object>> getTestPagerAndClbumTree(SysUser user, String cId, String pagerType) {
        List<Map<String, Object>> tree = new ArrayList<>();

        TestPaper testPaper = new TestPaper();
        testPaper.setCreateId(user.getUserAttrId());
        testPaper.setCoursrId(cId);
        testPaper.setType(pagerType);
        List<TestPaper> list = testPaperMapper.selectList(testPaper);

        TeacherCourse teacherCourse = new TeacherCourse();
        teacherCourse.setCid(cId);
        teacherCourse.setTid(user.getUserAttrId());
        TeacherCourse rtc = adminService.teacherCourseList(teacherCourse).get(0);

        ClbumCourseVo clbumCourse = new ClbumCourseVo();
        clbumCourse.setTcid(rtc.getId());
        List<ClbumCourseVo> clbumCourseVos = adminService.clbumCourseVos(clbumCourse);

        for (int i=0; i<list.size(); i++) {
            Map<String, Object> pager = new HashMap<>();
            TestPaper tp = list.get(i);
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

}
