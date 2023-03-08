package cn.wstom.student.task;



import cn.wstom.student.entity.Recourse;
import cn.wstom.student.entity.Topic;
import cn.wstom.student.feign.AdminService;
import cn.wstom.student.service.TopicService;
import cn.wstom.student.utils.AtomicIntegerUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Service
@Slf4j
@Component
public class QuartzTask {



    @Autowired
    private TopicService topicService;

    @Autowired
    private AdminService adminService;

    /**
     *  - topic对象定时持久化到数据库
     *  - resource对象定时持久化到数据库
     */
    public void topicPersistence() {
        AtomicIntegerUtil.getIntegerMap().forEach((key, util) -> {
            if (key.contains(Topic.class.getSimpleName())) {
                String[] str = AtomicIntegerUtil.splitKey(key);
                Topic topic = (Topic) AtomicIntegerUtil.transformToEntity(str[0]);
                topic.setId(str[1]);
                topic.setBrowse((long) util.get());
                try {
                    topicService.update(topic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (key.contains(Recourse.class.getSimpleName())) {
                String[] str = AtomicIntegerUtil.splitKey(key);
                Recourse recourse = (Recourse) AtomicIntegerUtil.transformToEntity(str[0]);
                recourse.setId(str[1]);
                recourse.setCount(util.get());
                try {
                    adminService.recourseUpdate(recourse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        log.info("【QuartzTask定时任务】 task=[{}, {}], date=[{}]", topicService.getClass(), adminService.getClass(), new Date());
    }
}
