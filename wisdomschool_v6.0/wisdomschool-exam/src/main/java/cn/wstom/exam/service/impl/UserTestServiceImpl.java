package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TestPaper;
import cn.wstom.exam.entity.UserTest;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.StuOptionanswerMapper;
import cn.wstom.exam.mapper.TestPaperMapper;
import cn.wstom.exam.mapper.UserTestMapper;
import cn.wstom.exam.service.UserTestService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试关联 服务层实现
 *
 * @author hzh
 * @date 20190223
 */
@Service
public class UserTestServiceImpl extends BaseServiceImpl<UserTestMapper, UserTest>
        implements UserTestService {

    @Autowired
    private UserTestMapper userTestMapper;
    @Autowired
    private TestPaperMapper testPaperMapper;
    @Autowired
    private StuOptionanswerMapper optionanswerMapper;
    @Autowired
    private AdminService adminService;


    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserTest> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userTestMapper.getStudentInfo(createId, tcoId);
    }

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserTest> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userTestMapper.getTcoName(createId, tcoId);
    }

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserTest> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userTestMapper.getTcoStu(createId, tcoId);
    }

    @Override
    public List<UserTest> getQStudentInfo(String tcoId) {
        return userTestMapper.getQStudentInfo(tcoId);
    }

    @Override
    public List<UserTest> getQTcoName(String tcoId) {
        return userTestMapper.getQTcoName(tcoId);
    }

    @Override
    public List<UserTest> getQTcoStu(String tcoId) {
        return userTestMapper.getQTcoStu(tcoId);
    }

    /**
     * 查找学生试卷  期末
     *
     * @param userTest
     * @return
     */
    @Override
    public List<UserTest> findStuExamPaper(UserTest userTest) {
        return userTestMapper.findStuExamPaper(userTest);
    }

    /**
     * 获取期末试卷
     *

     * @return
     */
    @Override
    public  List<UserTest>  getTcoExamPaper(UserTest userTest) {
        return userTestMapper.getTcoExamPaper(userTest);
    }

    @Override
    public List<UserTest> selectListBase(UserTest userTest) {
        return userTestMapper.selectListBase(userTest);
    }
    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
   @Override
   public  List<UserTest> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId){
       return  userTestMapper.getTcoName2(createId,tcoId,tgId);
   }

    @Override
    public List<Map<String, Object>> selectList(UserTest userTest) throws Exception {
        if (StringUtils.isEmpty(userTest.getTestPaperId()) || StringUtils.isBlank(userTest.getTestPaperId())) {
            throw new Exception();
        }
        List<Map<String, Object>> result = new ArrayList<>();


        List<UserTest> userTests = userTestMapper.selectListBase(userTest);//   分页，必须在首个查询
        TestPaper testPaper = testPaperMapper.selectById(userTest.getTestPaperId());
        userTests.forEach(u -> {
            SysUser user = adminService.getUserById(u.getUserId());

            Map<String, Object> object = new HashMap<>();
            object.put("madeScore", u.getMadeScore());
            object.put("id", u.getId());
            object.put("pageId", testPaper.getId());
            object.put("paperScore", testPaper.getScore());
            object.put("stuName", user.getUserName());
            object.put("stuNum", user.getLoginName());
            object.put("sumbitState", u.getSumbitState());
            object.put("userId", user.getId());
            object.put("stuScore", u.getStuScore());

            result.add(object);
        });

        return result;
    }

    @Override
    public List<UserTest> selectUserTest(String testPaperId, String userId) {
        return userTestMapper.selectUserTest(testPaperId,userId);
    }

}
