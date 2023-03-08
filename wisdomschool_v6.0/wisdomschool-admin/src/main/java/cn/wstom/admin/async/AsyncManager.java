package cn.wstom.admin.async;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务管理器
 *
 * @author dws
 */
public class AsyncManager {
    /**
     * 单例模式
     */
    private static AsyncManager async = new AsyncManager();
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;
    /**
     * 异步操作任务调度线程池
     */
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder().namingPattern("task-schedule-pool-%d").daemon(false).build());

    public static AsyncManager async() {
        return async;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }
}
