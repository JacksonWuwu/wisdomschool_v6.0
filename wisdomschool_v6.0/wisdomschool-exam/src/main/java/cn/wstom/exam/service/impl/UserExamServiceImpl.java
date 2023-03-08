package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.*;
import cn.wstom.exam.service.UserExamService;
import cn.wstom.exam.utils.StringUtil;
import com.github.pagehelper.PageHelper;
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
public class UserExamServiceImpl extends BaseServiceImpl<UserExamMapper, UserExam>
        implements UserExamService {

    @Autowired
    private UserExamMapper userExamMapper;
    @Autowired
    private TestPaperOneMapper testPaperOneMapper;
    @Autowired
    private StuOptionanswerMapper optionanswerMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TestpaperOneTestquestionsMapper testpaperOneTestquestionsMapper;
    @Autowired
    private TestpaperQuestionsMapper testpaperQuestionsMapper;
    @Autowired
    private MyTitleTypeMapper myTitleTypeMapper;


    @Override
    public UserExam selectByExamId(String id) {
        return userExamMapper.selectByExamId(id);
    }

    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserExam> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userExamMapper.getStudentInfo(createId, tcoId);
    }

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserExam> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userExamMapper.getTcoName(createId, tcoId);
    }

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserExam> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userExamMapper.getTcoStu(createId, tcoId);
    }

    @Override
    public List<UserExam> getQStudentInfo(String tcoId) {
        return userExamMapper.getQStudentInfo(tcoId);
    }

    @Override
    public List<UserExam> getQTcoName(String tcoId) {
        return userExamMapper.getQTcoName(tcoId);
    }

    @Override
    public List<UserExam> getQTcoStu(String tcoId) {
        return userExamMapper.getQTcoStu(tcoId);
    }

    /**
     * 查找学生试卷  期末
     *
     * @param userTest
     * @return
     */
    @Override
    public List<UserExam> findStuExamPaper(UserExam userTest) {
        return userExamMapper.findStuExamPaper(userTest);
    }

    @Override
    public List<UserExam> findStuExamPaperOne(UserExam userTest) {
        return userExamMapper.findStuExamPaperOne(userTest);
    }

    @Override
    public List<UserExam> findStuExamPaperTwo(UserExam userTest) {
        return userExamMapper.findStuExamPaperTwo(userTest);
    }

    /**
     * 获取期末试卷
     *

     * @return
     */
    @Override
    public  List<UserExam>  getTcoExamPaper(UserExam userTest) {
        return userExamMapper.getTcoExamPaper(userTest);
    }

    @Override
    public List<UserExam> selectListBase(UserExam userTest) {
        return userExamMapper.selectListBase(userTest);
    }

    @Override
    public UserExam selectUserExam(String testPaperId, String userId) {
        return userExamMapper.selectUserExam(testPaperId,userId);
    }

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public  List<UserExam> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId){
        return  userExamMapper.getTcoName2(createId,tcoId,tgId);
    }

    @Override
    public List<Map<String, Object>> selectList(UserExam userTest) throws Exception {
        if (StringUtils.isEmpty(userTest.getTestPaperOneId()) || StringUtils.isBlank(userTest.getTestPaperOneId())) {
            throw new Exception();
        }
        List<Map<String, Object>> result = new ArrayList<>();
        String mark = "true";
        List<UserExam> userTests = userExamMapper.selectListBase(userTest);//   分页，必须在首个查询
        TestPaperOne testPaper = testPaperOneMapper.selectById(userTest.getTestPaperOneId());
        TestpaperOneTestquestions testpaperOneTestquestions = new TestpaperOneTestquestions();
        testpaperOneTestquestions.setCreateId(testPaper.getCreateId());
        testpaperOneTestquestions.setTestPaperOneListId(userTest.getTestPaperOneId());
        List<TestpaperOneTestquestions> list = testpaperOneTestquestionsMapper.selectList(testpaperOneTestquestions);
        for (int i = 0; i < list.size(); i++) {
            TestpaperQuestions testpaperQuestions = testpaperQuestionsMapper.selectById(list.get(i).getTestQuestionsId());
            MyTitleType myTitleType = myTitleTypeMapper.selectById(Integer.parseInt(testpaperQuestions.getTitleTypeId()));
            if (myTitleType.getTitleTypeName().equals("简答题")||myTitleType.getTitleTypeName().equals("填空题")||myTitleType.getTitleTypeName().equals("程序设计题")){
                mark = "false";
                break;
            }
        }
        String finalMark = mark;
        userTests.forEach(u -> {
            SysUser user = adminService.getUserById(u.getUserId());
            Map<String, Object> object = new HashMap<>();
            object.put("madeScore", u.getMadeScore());
            object.put("id", u.getId());
            object.put("pageoneId", testPaper.getId());
            object.put("paperScore", testPaper.getScore());
            object.put("stuName", user.getUserName());
            object.put("stuNum", user.getLoginName());
            object.put("sumbitState", u.getSumbitState());
            object.put("userId", user.getId());
            object.put("stuScore", u.getStuScore());
            object.put("stuEndTime", u.getStuEndTime());
            object.put("mark", finalMark);
            result.add(object);
        });

        return result;
    }


    @Override
    public int updateSubmitState(String sumbit_state, UserExam userExam){
        try{
            userExamMapper.updateSubmitState(sumbit_state,userExam);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    @Override
    public List<UserExam> selectUserExamByCid(String cid){

        return userExamMapper.selectUserExamByCid(cid);
    }


}
