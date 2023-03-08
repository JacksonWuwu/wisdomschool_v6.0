package cn.wstom.exam.service;



import cn.wstom.exam.entity.UserPaperWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 测试关联 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface UserPaperWorkService extends BaseService<UserPaperWork> {
    UserPaperWork selectByExamId(String id);

    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId);

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId);



    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId);

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId);


    /**
     * 查找年级 期末
     *
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getQStudentInfo(@Param("tcoId") String tcoId);

    /**
     * 获取科目 期末
     *
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getQTcoName(@Param("tcoId") String tcoId);

    /**
     * 获取学生 期末
     *
     * @param tcoId
     * @return
     */
    List<UserPaperWork> getQTcoStu(@Param("tcoId") String tcoId);


    /**
     * 查找学生试卷  期末
     *
     * @param userTest
     * @return
     */
    public List<UserPaperWork> findStuExamPaper(UserPaperWork userTest);
    public List<UserPaperWork> findStuExamPaperOne(UserPaperWork userTest);
    public List<UserPaperWork> findStuExamPaperTwo(UserPaperWork userTest);


    /**
     * 获取期末试卷
     *
     */
    List<UserPaperWork>  getTcoExamPaper(UserPaperWork userTest);


    List<Map<String, Object>> selectList(UserPaperWork userTest) throws Exception ;

    List<UserPaperWork> selectListBase(UserPaperWork userTest);
    UserPaperWork selectUserExam(@Param("testPaperId") String testPaperId, @Param("userId") String userId);


    void deleteByPaperWorkId(String id);

     @Override
     boolean update(UserPaperWork userPaperWork);

    /**
     * 更新学生作业的分数
     * @return
     */
     int updatePaperWorkScore(UserPaperWork userPaperWork);
}
