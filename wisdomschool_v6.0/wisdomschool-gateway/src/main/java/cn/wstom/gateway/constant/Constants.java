package cn.wstom.gateway.constant;

/**
 * 通用常量信息
 *
 * @author dws
 */
public interface Constants {
    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * 通用成功状态码
     */
    int SUCCESS = 0;

    /**
     * 通用失败状态码
     */
    int FAILURE = -1;

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    String LOGIN_FAIL = "Error";

    /**
     * 自动去除表前缀
     */
    String AUTO_REOMVE_PRE = "true";

    /**
     * 当前记录起始索引
     */
    String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    String IS_ASC = "isAsc";

    /**
     * 表格--查询参数
     */
    String TABLE_PARAMS = "params";

    /**
     * 菜单--顶级id
     */
    String MENU_PARENT_ID = "0";

    /**
     * 章节--顶级id
     */
    String CHAPTER_PARENT_ID = "0";

    /**
     * 校验是否唯一的返回结果码
     */
    String CHECK_UNIQUE = "0";
    String CHECK_NOT_UNIQUE = "1";

    /**
     * 分割符 -- 逗号
     */
    String SEPARATOR_COMMA = ",";
    /**
     * 分割符 -- 点
     */
    String SEPARATOR_DOT = ".";

    /**
     * 消息状态 -- 未读
     */
    int MSG_STATUS_UNREADED = 0;
    /**
     * 消息状态 -- 已读
     */
    int MSG_STATUS_READED = 1;

    int ARTICLE_CATEGORY_NORMAL = 0;
    int QUESTION_NORMAL = 0;

    /**
    *  教师学生默认头像
    * */
    String STUDENT_DEFAULT_AVATAR = "/img/avatars/Malestudent.png";
    String TEACHER_DEFAULT_AVATAR = "/img/avatars/Masteacher.png";

    /**
    *  课程默认封面
    **/
    String BOOK_DEFAULT_AVATAR = "/img/avatars/book.png";


    /**
     * 题目类型
     */
    String TEST_PAPER_TYPE_CHAPTER = "0";
    String TEST_PAPER_TYPE_WORK = "3";
    String TEST_PAPER_TYPE_EXAM = "2";

    /**
     * 试题状态
     */
    String EXAM_TYPE_CANDLE = "-1";
    String EXAM_TYPE_WAIT = "0";
    String EXAM_TYPE_ING = "1";
    String EXAM_TYPE_DONE = "2";

    //  提交
    String EXAM_SUBMIT_SCORE_WAIT = "0";
    String EXAM_SUBMIT_SCORE_DONE = "1";

    //  改卷
    String EXAM_MADE_SCORE_WAIT = "0";
    String EXAM_MADE_SCORE_DONE = "1";
}
