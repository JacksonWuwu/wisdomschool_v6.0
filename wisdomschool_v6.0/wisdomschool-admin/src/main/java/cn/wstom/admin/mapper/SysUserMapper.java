package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.StatisSysUser;
import cn.wstom.admin.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author dws
 */

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByLoginName(String userName);

    /**
     * 通过手机号码查询用户
     *
     * @param phoneNumber 手机号码
     * @return 用户对象信息
     */
    SysUser selectUserByPhoneNumber(String phoneNumber);

    /**
     * 通过邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象信息
     */
    SysUser selectUserByEmail(@Param("email") String email);

    /**
     * 校验手机号码是否唯一
     *
     * @param phoneNumber 手机号码
     * @return 结果
     */
    SysUser checkPhoneUnique(String phoneNumber);

    /**
     * 校验email是否唯一
     *
     * @param email 用户邮箱
     * @return 结果
     */
    SysUser checkEmailUnique(String email);

    /**
     * 查询是否已关注或者是该用户粉丝
     *
     * @param userFollow 关注者id
     * @param userFansId 粉丝id
     * @return
     */
    int checkUserFans(@Param("userFollow") String userFollow, @Param("userFansId") String userFansId);

    /**
     * 查询是否是互相关注用户，等于2则为互相关注
     *
     * @param userFollow 关注者id
     * @param userFansId 粉丝id
     * @return
     */
    int checkUserMutualFans(@Param("userFollow") String userFollow, @Param("userFansId") String userFansId);

    /**
     * 根据用户属性id和用户属性类型查询用户名
     *
     *
     * @param sysUser 实体
     * @return 用户名称
     */
    SysUser selectOneUserName(SysUser sysUser);

    /**
     * 根据用户属性id集合和用户属性类型集合查询用户名
     *
     *
     * @param sysUserList 实体集合
     * @return 用户名称列表
     */
    List<SysUser> selectUserName(List<SysUser> sysUserList);

    List<SysUser> selectBatchTids(List<String> tcId);

    List<SysUser> selectUserSchoolId(int id);

    List<SysUser> selectStudentByCourseIdAndTeacherId(@Param("courseId") String courseId,
                                                      @Param("teacherId") String teacherId);

    List<SysUser> selectStudentByCourseIdAndTeacherId(@Param("courseId") String courseId,
                                                      @Param("teacherId") String teacherId,
                                                      @Param("loginName") String loginName,
                                                      @Param("clbumId") String clbumId);

    List<StatisSysUser> selectStudentByCourseIdsAndTeacherIds(StatisSysUser statisSysUser);


    List<SysUser> selectStudentByclbumId(@Param("loginName") String loginName,
                                         @Param("clbumId") String clbumId);

    SysUser updateUserEmail(String email);

    int updateUserPassword(SysUser sysUser);

    /**
     * 通过属性ID,attid查询用户
     *
     * @param userAttrId 用户属性ID
     * @return 用户对象信息
     */
    SysUser selectUserByUserAttrId(String userAttrId);


    List<SysUser> selectByAttrids(List<String> userAttrIds);

    /*
    * 查询拥有角色sdsadmin的老师
    * */
    List<SysUser> selectByrid();

    /*
     * 查询拥有班主任角色的老师
     * */
    List<SysUser> selectByridTwo();

    List<SysUser> selectUserByRoleKey(String roleKey);

    SysUser selectOne(SysUser sysUser);
}
