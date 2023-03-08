package cn.wstom.exam.task;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.TestPaper;
import cn.wstom.exam.entity.UserTest;
import cn.wstom.exam.service.TestPaperService;
import cn.wstom.exam.service.UserTestService;
import cn.wstom.exam.socket.exam.CourseExamSocket;
import cn.wstom.exam.socket.work.CourseWorkSocket;
import cn.wstom.exam.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liniec
 * @date 2020/01/18 23:49
 *
 */
//@Service
@Slf4j
@Component
public class ExamQuartzTask {

    @Autowired
    private TestPaperService testPaperService;
    @Autowired
    private UserTestService userTestService;

    /**
     * 监听时间段
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void listenExamPeriod() {
        Map<String, TestPaper> totalMap = new HashMap<>();//    添加考试、作业试题
        totalMap.putAll(CourseExamSocket.getTestPaperMap());
        totalMap.putAll(CourseWorkSocket.getWorkPaperMap());

        totalMap.forEach((tpId, paper) -> {
            TestPaper index = new TestPaper();
            //  若没有设置时间则抛错
            String startTime = paper.getStartTime();
            String endTime = paper.getEndTime();
            String longTime = paper.getTime();
            String type = paper.getType();
            if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
                if (type.equals(Constants.TEST_PAPER_TYPE_EXAM)) {
                    log.info("【考试任务定时】未设置时间：testPaper={}", paper.toString());//
                }
                if (type.equals(Constants.TEST_PAPER_TYPE_WORK)) {
                    log.info("【作业任务定时】未设置时间：testPaper={}", paper.toString());//
                }
            } else {
                //  时间到达设置时间以及该时间状态在wait中则修改考试状态
                if (DateUtils.belongCalendar(DateUtils.getNowDate(), DateUtils.parseDate(startTime), DateUtils.parseDate(endTime))
                        && paper.getSetExam().equals(Constants.EXAM_TYPE_WAIT)) {
                    index.setId(paper.getId());
                    index.setSetExam(Constants.EXAM_TYPE_ING);
                    try {
                        testPaperService.updateWithoutCheckType(index);
                        paper.setSetExam(Constants.EXAM_TYPE_ING);
                        if (type.equals(Constants.TEST_PAPER_TYPE_EXAM)) {
                            log.info("【到达考试时间】更新试卷状态：testPaper={}", paper.toString());//
                        }
                        if (type.equals(Constants.TEST_PAPER_TYPE_WORK)) {
                            log.info("【到达作业时间】更新作业状态：testPaper={}", paper.toString());//
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } //  时间到达设置的结束时间，修改状态并通知用户提交试卷
                else if (!DateUtils.belongDate(DateUtils.parseDate(endTime), DateUtils.getNowDate())
                        && paper.getSetExam().equals(Constants.EXAM_TYPE_ING)) {

                    index.setId(paper.getId());
                    index.setSetExam(Constants.EXAM_TYPE_DONE);
                    try {
                        paper.setSetExam(Constants.EXAM_TYPE_DONE);
                        testPaperService.updateWithoutCheckType(index);
                        if (type.equals(Constants.TEST_PAPER_TYPE_EXAM)) {
                            log.info("【超出考试时间】更新试卷状态：testPaper={}", paper.toString());
//                            CourseExamSocket.notifyUserWorkPaper(paper.getId(), "submit");
                        }
                        if (type.equals(Constants.TEST_PAPER_TYPE_WORK)) {
                            log.info("【超出作业时间】更新试卷状态：testPaper={}", paper.toString());
                            CourseWorkSocket.notifyUserWorkPaper(paper.getId(), CourseWorkSocket.SocketConstants.SOCKET_TYPE_SUBMIT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (type.equals(Constants.TEST_PAPER_TYPE_EXAM)) {
                        log.info("【考试任务定时】考试：testPaper={}", paper.toString());
                    }
                    if (type.equals(Constants.TEST_PAPER_TYPE_WORK)) {
                        log.info("【作业任务定时】作业：testPaper={}", paper.toString());
                    }
                }
            }
        });
    }

    /**
     * 监听学生试卷，通知
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void listenExamToNotifyStu() {
        Map<String, WebSocketSession> webSocketSessionMap = CourseExamSocket.getUserIdSessionMap();
        Map<String, TestPaper> testPaperMap = CourseExamSocket.getTestPaperMap();
        Map<String, List<UserTest>> stuExamMap = CourseExamSocket.getUserTestMap();

        testPaperMap.forEach((tpId, paper) -> {
            log.info("【考试通知任务定时】作业：testPaper={}", paper.toString());
            List<UserTest> list = stuExamMap.get(tpId);
            list.forEach(userTest -> {
                WebSocketSession session = webSocketSessionMap.get(userTest.getUserId());
                if (session != null) {
                    try {
                        session.sendMessage(new TextMessage(TEST));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private final String TEST = "Hello world!";
}
