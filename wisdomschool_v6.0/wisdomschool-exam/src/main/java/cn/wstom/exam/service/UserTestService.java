package cn.wstom.exam.service;



import cn.wstom.exam.entity.UserTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 测试关联 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface UserTestService extends BaseService<UserTest> {

    /**
     * 查找年级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserTest> getStudentInfo(@Param("createId") String createId, @Param("tcoId") String tcoId);

    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserTest> getTcoName(@Param("createId") String createId, @Param("tcoId") String tcoId);



    /**
     * 获取班级
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserTest> getTcoName2(@Param("createId") String createId, @Param("tcoId") String tcoId, @Param("tgId") String tgId);

    /**
     * 获取学生
     *
     * @param createId
     * @param tcoId
     * @return
     */
    List<UserTest> getTcoStu(@Param("createId") String createId, @Param("tcoId") String tcoId);


    /**
     * 查找年级 期末
     *
     * @param tcoId
     * @return
     */
    List<UserTest> getQStudentInfo(@Param("tcoId") String tcoId);

    /**
     * 获取科目 期末
     *
     * @param tcoId
     * @return
     */
    List<UserTest> getQTcoName(@Param("tcoId") String tcoId);

    /**
     * 获取学生 期末
     *
     * @param tcoId
     * @return
     */
    List<UserTest> getQTcoStu(@Param("tcoId") String tcoId);


    /**
     * 查找学生试卷  期末
     *
     * @param userTest
     * @return
     */
    public List<UserTest> findStuExamPaper(UserTest userTest);


    /**
     * 获取期末试卷
     *
     * @param cId
     * @param userId
     * @return
     */
    List<UserTest>  getTcoExamPaper(UserTest userTest);


    List<Map<String, Object>> selectList(UserTest userTest) throws Exception ;
    List<UserTest> selectUserTest(@Param("testPaperId")String testPaperId,@Param("userId")String userId);
    List<UserTest> selectListBase(UserTest userTest);
}
