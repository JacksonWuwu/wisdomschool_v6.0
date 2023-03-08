package cn.wstom.exam.mapper;




import cn.wstom.exam.entity.BaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础Mapper T：实体类型
 * @author dws
 * @date 2018/11/23
 */
@Mapper
public interface BaseMapper<T extends BaseEntity>  {

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
	 * @return 影响行数
     */
    int insert(T entity);

	/**
	 * <p>
	 * 批量插入记录
	 * </p>
	 *
	 * @param entities 实体对象集合
	 * @return 影响行数
	 */
    int insertBatch(List<T> entities);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
	 * @return 影响行数
     */
    int deleteById(@Param("id") Serializable id);

    /**
     * <p>
     * 根据条件，删除记录
     * </p>
     *
	 * @param params 查询参数
	 * @return 影响行数
     */
    int deleteByMap(Map<String, ?> params);

    /**
     * <p>
     * 删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 影响行数
     */
    int deleteBatchIds(List<? extends Serializable> idList);

    /**
     * <p>
     * 更新记录
     * </p>
     *
     * @param entity        实体对象
	 * @return 影响行数
     */
    int update(T entity);

	/**
	 * <p>
	 * 根据 ID 查询
	 * </p>
	 *
	 * @param id 主键ID
	 * @return 查询对象
	 */
	T selectById(@Param("id") Serializable id);

	/**
	 * <p>
	 * 查询（根据实体参数查询，字段为空则不参与条件构造）
	 * </p>
	 *
	 * @param t 实体参数
	 * @return 查询对象列表
	 */
    List<T> selectList(T t);

	/**
	 * <p>
	 * 查询（根据ID 批量查询）
	 * </p>
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 查询对象列表
	 */
	List<T> selectBatchIds(List<? extends Serializable> idList);

    /**
     * <p>
     * 查询（根据条件）
     * </p>
     *
	 * @param params 查询参数
	 * @return 查询对象列表
     */
    List<T> selectByMap(Map<String, ?> params);

    /**
     * <p>
     * 根据条件，查询一条记录
     * </p>
     *
	 * @param params 查询参数
	 * @return 查询对象
     */
    T selectOne(Map<String, ?> params);

    /**
     * <p>
     * 根据条件，查询总记录数
     * </p>
     *
     * @param params 查询参数
	 * @return 结果数量
     */
    Integer selectCount(Map<String, ?> params);

    /**
     * <p>
     * 根据条件，查询全部记录返回List<Map<String, Object>>集合
     * </p>
     *
	 * @param params 查询参数
	 * @return 查询对象列表
     */
    List<Map<String, ?>> selectMaps(Map<String, ?> params);
}
