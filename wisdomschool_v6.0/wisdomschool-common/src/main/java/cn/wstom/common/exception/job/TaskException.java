package cn.wstom.common.exception.job;

/**
 * 计划策略异常
 *
 * @author dws
 */
public class TaskException extends Exception {
    private static final long serialVersionUID = 1L;

    private Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    public TaskException(String msg, Code code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public enum Code {
        /**
         * 存在任务
         */
        TASK_EXISTS,
        /**
         * 不存在任务
         */
        NO_TASK_EXISTS,
        /**
         * 任务已经开始
         */
        TASK_ALREADY_STARTED,
        /**
         * 未知
         */
        UNKNOWN,
        /**
         * 配置错误
         */
        CONFIG_ERROR,
        /**
         * 任务不可用
         */
        TASK_NODE_NOT_AVAILABLE
    }
}