package cn.wstom.exam.socket.exam;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * @author liniec
 * @date 2020/01/17 15:23
 *  根据功能划分构建的socket
 */
@Component
//@ServerEndpoint(value = "/socket/exam/notification")
public class ExamNotifySocket {

    @PostConstruct
    public void init() {
        //  初始化
    }

    @OnOpen
    public void onOpen(Session session) {

    }

    @OnClose
    public void onClose(Session session) {
        //  exam_online Service.update(exam_online)
    }
}
