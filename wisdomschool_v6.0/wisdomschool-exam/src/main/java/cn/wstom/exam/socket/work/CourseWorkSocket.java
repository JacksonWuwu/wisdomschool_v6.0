package cn.wstom.exam.socket.work;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.*;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liniec
 * @date 2020/01/20
 *  根据功能划分构建的socket
 *   - 业务：监听用户在线情况
 *      - exam_online   <userId> <testPaperId> <ip> <status: close,lighting> <connectionTime> <submitTime>...
 *      - socket_online_log
 */
@Slf4j
//@Component
//@ServerEndpoint(value = "/socket/work/{userId}")
public class CourseWorkSocket {

    private static TestPaperService testPaperService = SpringUtils.getBean(TestPaperService.class);

    private static UserTestService userTestService = SpringUtils.getBean(UserTestService.class);

    /**
     * 用户Session
     */
    private static Map<String, Session> userIdSessionMap = new ConcurrentHashMap<>();

    //  testpaperId, testpaper
    private static Map<String, TestPaper> workPaperMap = new ConcurrentHashMap<>();

    private static Map<String, List<UserTest>> userTestMap = new ConcurrentHashMap<>();


    @PostConstruct
    public void init() {
        /**
         * 初始化：获取作业列表
         */
        notifyNowTimeWorkPaperMap();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String  userId) {
        userIdSessionMap.put(userId, session);
        System.out.println("用户：userId{}【开始连接】" + userId);

        //  shuchu

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

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userId")String  userId) {
        userIdSessionMap.remove(userId);
        System.out.println("用户：userId{}【断开连接】" + userId);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //
        System.out.println(message);
//        session.getBasicRemote().sendText("submit");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 通知更新作业列表
     */
    public static void notifyNowTimeWorkPaperMap() {
        log.info("【作业Socket】更新试题");
        TestPaper paper = new TestPaper();
        paper.setType(Constants.TEST_PAPER_TYPE_WORK);
        paper.setSetExam(Constants.EXAM_TYPE_ING);
        List<TestPaper> nowPaper = testPaperService.list(paper);

        nowPaper.forEach(p -> {
            UserTest index = new UserTest();
            workPaperMap.put(p.getId(), p);
            index.setTestPaperId(p.getId());
            userTestMap.put(p.getId(), userTestService.findStuExamPaper(index));
        });
    }

    /**
     * 通知用户
     * @param paperId
     * @param message
     */
    public static void notifyUserWorkPaper(String paperId, String message) {
        List<UserTest> userTests = userTestMap.get(paperId);
        if (!userTests.isEmpty()) {
            userTests.forEach(userTest -> {
                try {
                    Session userSession = userIdSessionMap.get(userTest.getUserId());
                    if (userSession != null/* && userSession.isOpen()*/) {
                        userSession.getBasicRemote().sendText(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static Map<String, TestPaper> getWorkPaperMap() {
        return workPaperMap;
    }

    public static Map<String, List<UserTest>> getUserTestMap() {
        return userTestMap;
    }

    public static Map<String, Session> getUserIdSessionMap() {
        return userIdSessionMap;
    }

    public interface SocketConstants {
        String SOCKET_TYPE_SUBMIT = "submit";
    }
}
