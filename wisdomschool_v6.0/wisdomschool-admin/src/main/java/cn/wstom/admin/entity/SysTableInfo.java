package cn.wstom.admin.entity;




import java.util.List;

/**
 * 数据库表
 *
 * @author dws
 */
public class SysTableInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 表的主键列信息
     */
    private SysColumnInfo primaryKey;

    /**
     * 表的列名(不包含主键)
     */
    private List<SysColumnInfo> columns;

    /**
     * 类名(第一个字母大写)
     */
    private String className;

    /**
     * 类名(第一个字母小写)
     */
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<SysColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<SysColumnInfo> columns) {
        this.columns = columns;
    }

    public SysColumnInfo getColumnsLast() {
       SysColumnInfo sysColumnInfo = null;
        if (columns != null && !columns.isEmpty()) {
            sysColumnInfo = columns.get(0);
        }
        return sysColumnInfo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public Object getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(SysColumnInfo primaryKey) {
        this.primaryKey = primaryKey;
    }
}
