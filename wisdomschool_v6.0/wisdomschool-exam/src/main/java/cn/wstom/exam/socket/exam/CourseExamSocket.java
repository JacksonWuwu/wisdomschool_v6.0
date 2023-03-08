package cn.wstom.exam.socket.exam;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.TestPaper;
import cn.wstom.exam.entity.UserTest;
import cn.wstom.exam.service.TestPaperService;
import cn.wstom.exam.service.UserTestService;
import cn.wstom.exam.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liniec
 * @date 2020/01/17 15:23
 *  根据功能划分构建的socket
 *   - 业务：监听用户在线情况
 *      - exam_online   <userId> <testPaperId> <ip> <status: close,lighting> <connectionTime> <submitTime>...
 *      - socket_online_log
 */
@Slf4j
public class CourseExamSocket {

    private static TestPaperService testPaperService = SpringUtils.getBean(TestPaperService.class);

    private static UserTestService userTestService = SpringUtils.getBean(UserTestService.class);

    /**
     *  QuartzConfiguration 配置注入定时任务
     *  定时任务：  通知考试（testPaper），强制关闭（exam_online, testPaper)
     */

    /**
     * 用户Session
     */
    private static Map<String, WebSocketSession> userIdSessionMap = new ConcurrentHashMap<>();

    //  testpaperId, testpaper
    private static Map<String, TestPaper> testPaperMap = new ConcurrentHashMap<>();

    private static Map<String, List<UserTest>> userTestMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        /**
         * 初始化：获取作业列表
         */
        notifyNowTimeExamPaperMap();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String  userId) {
        userIdSessionMap.put(userId, (WebSocketSession) session);

        /**
         * exam_online = Service.findById(userId, testPaperId)
         * if (exam_online != null)
         *      - remainTime = testPaper.time - format(exam_online.connectionTime, newTime) // 经过时间
         *      if (remainTime <= 0)
         *          return send('exam end')
         *       else return send(elapsedTime, 'reconnect success')
         *      - exam_online Service.update(exam_online)
         * else {
         *      exam_online Service.add(exam_online)
         *      return send("connect success')
         *  }
         */
        //  socket_online_log.add(log)
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId")String  userId) {
        //  exam_online Service.update(exam_online)
    }

    public static void notifyNowTimeExamPaperMap() {
        log.info("【考试Socket】更新试题");
        TestPaper paper = new TestPaper();
        paper.setType(Constants.TEST_PAPER_TYPE_EXAM);
        paper.setSetExam(Constants.EXAM_TYPE_ING);
        List<TestPaper> nowPaper = testPaperService.list(paper);
        UserTest index = new UserTest();

        nowPaper.forEach(p -> {
            testPaperMap.put(p.getId(), p);
            index.setTestPaperId(p.getId());
            userTestMap.put(p.getId(), userTestService.findStuExamPaper(index));
        });
    }

    public static Map<String, TestPaper> getTestPaperMap() {
        return testPaperMap;
    }

    public static Map<String, List<UserTest>> getUserTestMap() {
        return userTestMap;
    }

    public static Map<String, WebSocketSession> getUserIdSessionMap() {
        return userIdSessionMap;
    }

}
