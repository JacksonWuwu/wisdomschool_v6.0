package cn.wstom.exam.service;







import cn.wstom.exam.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础service
 * @author dws
 * @date 2018/11/23
 */

public interface BaseService<T extends BaseEntity> {

    /**
     * <p>
     * 插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return 插入结果
     */
    boolean save(T entity) throws Exception;

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象集合
     * @return 插入结果
     */
    boolean saveBatch(List<T> entityList) throws Exception;

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList 实体对象集合
     * @return 修改插入结果
     */
    boolean saveOrUpdateBatch(List<T> entityList) throws Exception;

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return 删除结果
     */
    boolean removeById(Serializable id) throws Exception;

    /**
     * <p>
     * 根据条件，删除记录
     * </p>
     *
     * @param params 表字段 map 对象
     * @return 删除结果
     */
    boolean removeByMap(Map<String, ?> params) throws Exception;

    /**
     * <p>
     *  删除（根据ID 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return 删除结果
     * @throws Exception
     */
    boolean removeByIds(List<? extends Serializable> idList) throws Exception;

    /**
     * <p>
     * 更新记录
     * </p>
     *
     * @param entity        实体对象
     * @return 更新结果
     */
    boolean update(T entity) throws Exception;

    /**
     * <p>
     * 批量更新
     * </p>
     *
     * @param entityList 实体对象集合
     * @return 更新结果
     */
    boolean updateBatch(List<T> entityList) throws Exception;

    /**
     * <p>
     * 存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return 插入更新结果
     */
    boolean saveOrUpdate(T entity) throws Exception;

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return 查询对象
     */
    T getById(Serializable id);

    /**
     * <p>
     * 查询（根据实体参数查询，字段为空则不参与条件构造）
     * </p>
     *
     * @param t 实体参数
     * @return 查询对象列表
     */
    List<T> list(T t);

    /**
     * <p>
     * 查询（根据实体参数查询，字段为空则不参与条件构造）
     * </p>
     *
     * @param t 实体参数
     * @return 查询对象列表
     */
    Map<String, T> map(T t);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return 查询对象列表
     */
    List<T> listByIds(List<? extends Serializable> idList);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return 查询对象map
     */
    Map<String, T> mapByIds(List<? extends Serializable> idList);

    /**
     * <p>
     * 查询（根据 条件参数）
     * </p>
     *
     * @param params 条件参数
     * @return 查询对象列表
     */
    List<T> listByMap(Map<String, ?> params);

    /**
     * <p>
     * 根据 条件参数，查询一条记录
     * </p>
     *
     * @param params 条件参数
     * @return 查询对象
     */
    T getOne(Map<String, ?> params);

    /**
     * <p>
     * 根据 Wrapper 条件，查询总记录数
     * </p>
     *
     * @param params 条件参数
     * @return 查询对象数量
     */
    int count(Map<String, ?> params);

    /**
     * <p>
     * 查询列表
     * </p>
     *
     * @param params 条件参数
     * @return 查询对象列表List<Map>
     */
    List<Map<String, ?>> listMaps(Map<String, ?> params);
}
