package cn.wstom.exam.mapper;



import cn.wstom.exam.entity.UserExam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 测试关联 数据层
 *
 * @author lzj
 * @date 20190223
 */
public interface UserExamMapper extends BaseMapper<UserExam> {
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
     * 试卷 期末
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
     * @return
     */
    List<UserExam>  getTcoExamPaper(UserExam userTest);
    UserExam selectUserExam(@Param("testPaperId")String testPaperId,@Param("userId")String userId);
    List<UserExam> selectListBase(UserExam userTest);


    void updateSubmitState(@Param("sumbit_state") String sumbit_state,@Param("userExam") UserExam userExam) throws Exception;


    public List<UserExam> selectUserExamByCid(String cid);

}
