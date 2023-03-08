package cn.wstom.admin.cache;

import java.io.Serializable;

/**
 * 缓存数据项
 *
 * @author dws
 * @date 2019/01/30
 */
public class CacheItem<T> implements Serializable {

    private static final long serialVersionUID = -7025399304508482930L;
    /**
     * 缓存码
     */
    private String code;

    /**
     * 子缓存码
     */
    private String subCode;

    /**
     * 缓存项
     */
    private T item;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
