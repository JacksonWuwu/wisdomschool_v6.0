package cn.wstom.student.service.impl;


import cn.wstom.student.entity.BaseEntity;
import cn.wstom.student.mapper.BaseMapper;
import cn.wstom.student.service.BaseService;
import cn.wstom.student.utils.StringUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础Service实现，（ M ：mapper 对象，T ：实体 ）
 *
 * @author dws
 * @date 2018/11/23
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity>
        implements BaseService<T> {

    @Autowired
    protected M baseMapper;

    /**
     * <p>
     * 判断数据库操作是否成功
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

    /**
     * <p>
     * 删除不存在的逻辑上属于成功
     * </p>
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean delBool(Integer result) {
        return null != result && result >= 0;
    }

    /**
     * <p>
     * 返回SelectCount执行结果
     * </p>
     *
     * @param result
     * @return int
     */
    protected static int retCount(Integer result) {
        return (null == result) ? 0 : result;
    }

    @Override
    public boolean save(T entity) throws Exception {
        return retBool(baseMapper.insert(entity));
    }

    /**
     * 批量插入
     *
     * @param entityList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatch(List<T> entityList) throws Exception {
        if (StringUtil.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try {
            baseMapper.insertBatch(entityList);
        } catch (Throwable e) {
            throw new Exception("Error: Cannot execute saveBatch Method. Cause", e);
        }
        return true;
    }

    /**
     * <p>
     * 存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Override
    public boolean saveOrUpdate(T entity) throws Exception {
        if (null != entity) {
            if (null != entity.getId() && null != getById(entity.getId())) {
                return update(entity);
            }
            return save(entity);

        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateBatch(List<T> entityList) throws Exception {
        if (StringUtil.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try {
            for (T anEntityList : entityList) {
                saveOrUpdate(anEntityList);
            }
        } catch (Throwable e) {
            throw new Exception("Error: Cannot execute saveOrUpdateBatch Method. Cause", e);
        }
        return true;
    }

    @Override
    public boolean removeById(Serializable id) throws Exception {
        try {
            return delBool(baseMapper.deleteById(id));
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public boolean removeByMap(Map<String, ?> params) throws Exception {
        if (StringUtil.isEmpty(params)) {
            throw new Exception("removeByMap columnMap is empty.");
        }
        try {
            return delBool(baseMapper.deleteByMap(params));
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public boolean removeByIds(List<? extends Serializable> idList) throws Exception {
        try {
            return delBool(baseMapper.deleteBatchIds(idList));
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public boolean update(T entity) throws Exception {
        return retBool(baseMapper.update(entity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateBatch(List<T> entityList) throws Exception {
        if (StringUtil.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try {
            for (T entity : entityList) {
                update(entity);
            }
        } catch (Throwable e) {
            throw new Exception("Error: Cannot execute updateBatchById Method. Cause", e);
        }
        return true;
    }

    @Override
    public T getById(Serializable id) {
        return baseMapper.selectById(id);
    }

    @Override
    public List<T> list(T t) {
        return baseMapper.selectList(t);
    }

    @Override
    public Map<String, T> map(T t) {
        List<T> list = this.list(t);
        Map<String, T> map = new HashMap<>(list.size());
        list.forEach(l -> map.put(l.getId(), l));
        return map;
    }

    @Override
    public List<T> listByIds(List<? extends Serializable> idList) {
        if (idList == null || idList.isEmpty()) {
            return Lists.newArrayList();
        }
        return baseMapper.selectBatchIds(idList);
    }

    @Override
    public Map<String, T> mapByIds(List<? extends Serializable> idList) {
        List<T> ts = this.listByIds(idList);
        Map<String, T> map = new HashMap<>(ts.size());
        ts.forEach(t -> map.put(t.getId(), t));
        return map;
    }

    @Override
    public List<T> listByMap(Map<String, ?> columnMap) {
        return baseMapper.selectByMap(columnMap);
    }

    @Override
    public T getOne(Map<String, ?> columnMap) {
        return baseMapper.selectOne(columnMap);
    }

    @Override
    public int count(Map<String, ?> columnMap) {
        return retCount(baseMapper.selectCount(columnMap));
    }

    @Override
    public List<Map<String, ?>> listMaps(Map<String, ?> columnMap) {
        return baseMapper.selectMaps(columnMap);
    }
}
