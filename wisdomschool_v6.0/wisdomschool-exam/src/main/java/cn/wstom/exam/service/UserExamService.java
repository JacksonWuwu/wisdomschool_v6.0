package cn.wstom.exam.service;



import cn.wstom.exam.entity.UserExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 测试关联 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface UserExamService extends BaseService<UserExam> {
    UserExam selectByExamId(String id);

    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserExam> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId);

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserExam> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId);



    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserExam> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId);

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserExam> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId);


    /**
     * 查找年级 期末
     *
     * @param tcoId
     * @return
     */
    List<UserExam> getQStudentInfo(@Param("tcoId") String tcoId);

    /**
     * 获取科目 期末
     *
     * @param tcoId
     * @return
     */
    List<UserExam> getQTcoName(@Param("tcoId") String tcoId);

    /**
     * 获取学生 期末
     *
     * @param tcoId
     * @return
     */
    List<UserExam> getQTcoStu(@Param("tcoId") String tcoId);


    /**
     * 查找学生试卷  期末
     *
     * @param userTest
     * @return
     */
    public List<UserExam> findStuExamPaper(UserExam userTest);
    public List<UserExam> findStuExamPaperOne(UserExam userTest);
    public List<UserExam> findStuExamPaperTwo(UserExam userTest);


    /**
     * 获取期末试卷
     *
     * @param userTest
     * @return
     */
    List<UserExam>  getTcoExamPaper(UserExam userTest);


    List<Map<String, Object>> selectList(UserExam userTest) throws Exception ;

    List<UserExam> selectListBase(UserExam userTest);
    UserExam selectUserExam(@Param("testPaperId")String testPaperId,@Param("userId")String userId);


    public int updateSubmitState(String sumbit_state,UserExam userExam);

    public List<UserExam> selectUserExamByCid(String cid);


}
