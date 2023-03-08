package cn.wstom.student.config;

import cn.wstom.student.task.QuartzTask;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

//@Configuration
public class QuartzConfiguration {

    /**
     * 配置任务
     * @param quartzTask
     * @return
     */
    @Bean(name = "reptilianJob")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(QuartzTask quartzTask) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        //  并发执行
        jobDetail.setConcurrent(false);
        // 任务名字
        jobDetail.setName("reptilianJob");

        //  执行对象
        jobDetail.setTargetObject(quartzTask);

        //  执行的方法
        jobDetail.setTargetMethod("topicPersistence");

        return jobDetail;
    }

    /**
     * 定时触发器
     *
     */
    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean cronJobTrigger(JobDetail reptilianJob) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();

        trigger.setJobDetail(reptilianJob);

        //  cron表达式,设置执行的间隔时间
        trigger.setCronExpression("0 0 0 * * ?");
        trigger.setName("reptilianTrigger");
        return trigger;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(Trigger jobTrigger) {
        SchedulerFactoryBean  factoryBean = new SchedulerFactoryBean();

        factoryBean.setOverwriteExistingJobs(true);

        factoryBean.setStartupDelay(1);
        //注册触发器
        factoryBean.setTriggers(jobTrigger);
        return factoryBean;
    }

}
