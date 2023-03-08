package cn.wstom.exam.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author dws
 */
public class TableDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private Long total;

    private Integer pageNum;

    private Integer pageSize;

    private Integer pages;

    /**
     * 列表数据
     */
    private List<?> rows;
    /**
     * 消息状态码
     */
    private Integer code;

    /**
     * 表格数据对象
     */
    public TableDataInfo() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, Long total) {
        this.rows = list;
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
