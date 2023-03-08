package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.SysUser;
import cn.wstom.exam.entity.TestPaperWork;
import cn.wstom.exam.entity.UserPaperWork;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.StuOptionanswerMapper;
import cn.wstom.exam.mapper.TestPaperWorkMapper;
import cn.wstom.exam.mapper.UserPaperWorkMapper;
import cn.wstom.exam.service.UserPaperWorkService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * 测试关联 服务层实现
 *
 * @author hzh
 * @date 20190223
 */
@Service
public class UserPaperWorkServiceImpl extends BaseServiceImpl<UserPaperWorkMapper, UserPaperWork>
        implements UserPaperWorkService {

    @Autowired
    private UserPaperWorkMapper userPaperWorkMapper;
    @Autowired
    private TestPaperWorkMapper testPaperWorkMapper;
    @Autowired
    private StuOptionanswerMapper optionanswerMapper;
    @Autowired
    private AdminService adminService;


    @Override
    public UserPaperWork selectByExamId(String id) {
        return userPaperWorkMapper.selectByExamId(id);
    }

    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserPaperWork> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userPaperWorkMapper.getStudentInfo(createId, tcoId);
    }

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserPaperWork> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userPaperWorkMapper.getTcoName(createId, tcoId);
    }

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public List<UserPaperWork> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId) {
        return userPaperWorkMapper.getTcoStu(createId, tcoId);
    }

    @Override
    public List<UserPaperWork> getQStudentInfo(String tcoId) {
        return userPaperWorkMapper.getQStudentInfo(tcoId);
    }

    @Override
    public List<UserPaperWork> getQTcoName(String tcoId) {
        return userPaperWorkMapper.getQTcoName(tcoId);
    }

    @Override
    public List<UserPaperWork> getQTcoStu(String tcoId) {
        return userPaperWorkMapper.getQTcoStu(tcoId);
    }

    /**
     * 查找学生试卷  期末
     *
     * @param userTest
     * @return
     */
    @Override
    public List<UserPaperWork> findStuExamPaper(UserPaperWork userTest) {
        return userPaperWorkMapper.findStuExamPaper(userTest);
    }

    @Override
    public List<UserPaperWork> findStuExamPaperOne(UserPaperWork userTest) {
        return userPaperWorkMapper.findStuExamPaperOne(userTest);
    }

    @Override
    public List<UserPaperWork> findStuExamPaperTwo(UserPaperWork userTest) {
        return userPaperWorkMapper.findStuExamPaperTwo(userTest);
    }

    /**
     * 获取期末试卷
     *

     * @return
     */
    @Override
    public  List<UserPaperWork>  getTcoExamPaper(UserPaperWork userTest) {
        return userPaperWorkMapper.getTcoExamPaper(userTest);
    }

    @Override
    public List<UserPaperWork> selectListBase(UserPaperWork userTest) {
        return userPaperWorkMapper.selectListBase(userTest);
    }

    @Override
    public UserPaperWork selectUserExam(String testPaperId, String userId) {
        return userPaperWorkMapper.selectUserExam(testPaperId,userId);
    }

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    @Override
    public  List<UserPaperWork> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId){
        return  userPaperWorkMapper.getTcoName2(createId,tcoId,tgId);
    }

    @Override
    public List<Map<String, Object>> selectList(UserPaperWork userTest) throws Exception {
        if (StringUtils.isEmpty(userTest.getTestPaperWorkId()) || StringUtils.isBlank(userTest.getTestPaperWorkId())) {
            throw new Exception();
        }
        List<Map<String, Object>> result = new ArrayList<>();


        List<UserPaperWork> userTests = userPaperWorkMapper.selectListBase(userTest);//   分页，必须在首个查询
        TestPaperWork testPaper = testPaperWorkMapper.selectById(userTest.getTestPaperWorkId());
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

            result.add(object);
        });

        return result;
    }

    @Override
    public void deleteByPaperWorkId(String id) {
        userPaperWorkMapper.deleteByPaperWorkId(id);
    }

    @Override
    public boolean update(UserPaperWork userPaperWork){
        int i = userPaperWorkMapper.update(userPaperWork);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

   @Override
   public int updatePaperWorkScore(UserPaperWork userPaperWork){
        return userPaperWorkMapper.updatePaperWorkScore(userPaperWork);
   };

    /**
     * <p>
     * 存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Override
    public boolean saveOrUpdate(UserPaperWork entity) throws Exception {
        return super.saveOrUpdate(entity);
    }



}
