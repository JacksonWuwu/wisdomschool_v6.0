package cn.wstom.monitor.controller;


import cn.wstom.monitor.constant.Data;
import cn.wstom.monitor.constant.JwtConstant;
import cn.wstom.monitor.entity.ExamUser;
import cn.wstom.monitor.utils.JwtUtils;
import cn.wstom.monitor.utils.ServletUtils;
import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping("/examMonitor")
public class SessionController {


    @Autowired
    JwtUtils jwtUtils;

    public static Map<String, ExamUser> users = new ConcurrentHashMap<>();

    //OpenVidu对象作为SDK的入口点
    private OpenVidu openVidu;

    //集合以配对会话名称和OpenVidu会话对象
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    //集合对会话名称和令牌进行配对（内部映射对令牌和关联的角色）
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    //OpenVidu服务器正在侦听的URL
    private String OPENVIDU_URL;
    //与我们的OpenVidu服务器共享的秘密
    private String SECRET;

    public SessionController (@Value("${openvidu.secret}") String secret,
                              @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    @RequestMapping("/index")
    public String monitorIndex (@RequestParam("paperId") String paperId,
                                ModelMap modelMap) {
        modelMap.put("paperId", paperId);
        return "teacher";
    }

    @RequestMapping("/student/{paperId}")
    @ResponseBody
    public Data monitorStudent (@PathVariable("paperId") String paperId) {
        String token = getToken();
        String id = jwtUtils.getUserIdFromToken(token);
        String userName = jwtUtils.getUserNameFromToken(token);
        users.put(id, new ExamUser(userName, id, OpenViduRole.PUBLISHER));
        Map<String, Object> map = new HashMap<>();

        //与此用户关联的角色
        //OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).getRole();
        OpenViduRole role = users.get(id).getRole();

        //此用户连接到视频通话时要传递给其他用户的可选数据
        //在本例中，一个JSON，其值存储在登录时的HttpSession对象中
        String serverData = "{\"serverData\": \"" + id + "\"}";

        //使用serverData和角色构建tokenOptions对象
        TokenOptions tokenOptions = new TokenOptions.Builder().data(serverData).role(role).build();
        if (this.mapSessions.get(paperId) != null) {
            //会话已存在
            System.out.println("会话已存在" + paperId);
            try {
                //使用最近创建的令牌选项生成新令牌
                String openviduToken = this.mapSessions.get(paperId).generateToken(tokenOptions);
                //更新存储新令牌的集合
                this.mapSessionNamesTokens.get(paperId).put(openviduToken, role);
                //将所有需要的属性添加到模板中
                map.put("sessionName", paperId);
                map.put("token", openviduToken);
                map.put("nickName", id);
                map.put("userName", userName);
                return Data.success(map);
            } catch (Exception e) {
                // 如果错误就返回 dashboard.html
                map.put("username", userName);
                return Data.error(1, "错误", map);
            }
        } else {
            // 新会话
            try {
                // 创建新的OpenVidu 会话
                Session session = this.openVidu.createSession();
                //使用最近创建的令牌选项生成新令牌
                String openviduToken = session.generateToken(tokenOptions);
                //将会话和令牌存储在我们的集合中
                this.mapSessions.put(paperId, session);
                this.mapSessionNamesTokens.put(paperId, new ConcurrentHashMap<>());
                this.mapSessionNamesTokens.get(paperId).put(openviduToken, role);
                //将所有需要的属性添加到模板中
                map.put("sessionName", paperId);
                map.put("token", openviduToken);
                map.put("nickName", id);
                map.put("userName", userName);
                return Data.success(map);
            } catch (Exception e) {
                // 如果错误就返回 dashboard.html
                map.put("username", userName);
                return Data.error(1, "错误", map);
            }
        }

    }

    @RequestMapping("/teacher/{paperId}")
    @ResponseBody
    public Data monitorTeacher (@PathVariable("paperId") String paperId) throws OpenViduJavaClientException, OpenViduHttpException {
        String token = getToken();
        //String paperId = "399";
        String id = jwtUtils.getUserIdFromToken(token);
        String userName = jwtUtils.getUserNameFromToken(token);
        users.put(id, new ExamUser(userName, id, OpenViduRole.SUBSCRIBER));
        Map<String, Object> map = new HashMap<>();

        //与此用户关联的角色
        //OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).getRole();
        OpenViduRole role = users.get(id).getRole();

        //此用户连接到视频通话时要传递给其他用户的可选数据
        //在本例中，一个JSON，其值存储在登录时的HttpSession对象中
        String serverData = "{\"serverData\": \"" + id + "\"}";

        //使用serverData和角色构建tokenOptions对象
        TokenOptions tokenOptions = new TokenOptions.Builder().data(serverData).role(role).build();
        if (this.mapSessions.get(paperId) != null) {
            //会话已存在
            System.out.println("会话已存在" + paperId);
            try {
                //使用最近创建的令牌选项生成新令牌
                String openviduToken = this.mapSessions.get(paperId).generateToken(tokenOptions);
                //更新存储新令牌的集合
                this.mapSessionNamesTokens.get(paperId).put(openviduToken, role);
                //将所有需要的属性添加到模板中
                map.put("sessionName", paperId);
                map.put("token", openviduToken);
                map.put("nickName", id);
                map.put("userName", userName);
                return Data.success(map);
            } catch (Exception e) {
                // 如果错误就返回 dashboard.html
                map.put("username", userName);
                return Data.error(1, "错误", map);
            }
        } else {
            // 新会话
            try {
                // 创建新的OpenVidu 会话
                Session session = this.openVidu.createSession();
                //使用最近创建的令牌选项生成新令牌
                String openviduToken = session.generateToken(tokenOptions);
                //将会话和令牌存储在我们的集合中
                this.mapSessions.put(paperId, session);
                this.mapSessionNamesTokens.put(paperId, new ConcurrentHashMap<>());
                this.mapSessionNamesTokens.get(paperId).put(openviduToken, role);
                //将所有需要的属性添加到模板中
                map.put("sessionName", paperId);
                map.put("token", openviduToken);
                map.put("nickName", id);
                map.put("userName", userName);
                return Data.success(map);
            } catch (Exception e) {
                // 如果错误就返回 dashboard.html
                map.put("username", userName);
                return Data.error(1, "错误", map);
            }
        }

    }

    //离开会议
    @RequestMapping(value = "/leavesession")
    @ResponseBody
    public String leavesession (@RequestParam(name = "sessionName") String sessionName, @RequestParam(name = "token") String token) throws Exception {
        System.out.println("会话=" + mapSessions);
        System.out.println("会话令牌=" + mapSessionNamesTokens);
        //如果会话存在（本例中为“教程”）
        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {
            //如果令牌存在
            if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
                //用户离开了会话
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    //最后一个用户离开：必须删除会话
                    this.mapSessions.remove(sessionName);
                }
                return "已经离开";

            } else {
                //令牌无效
                this.mapSessions.remove(sessionName);
                System.out.println("令牌无效");
                return "令牌无效";
            }

        } else {
            this.mapSessions.remove(sessionName);
            //会话不存在
            System.out.println("会话不存在");
            return "会话不存在";
        }
    }

    //离开会议
    @RequestMapping(value = "/closesession")
    @ResponseBody
    public String closesession (@RequestParam(name = "sessionName") String sessionName) throws Exception {
        this.mapSessions.remove(sessionName);
        return "已退出会话";
    }

    public String getToken () {
        String token = ServletUtils.getRequest().getParameter("token");
        if (!"".equals(token) && token != null) {
            return ServletUtils.getRequest().getParameter("token");
        }
        ;

        return ServletUtils.getRequest().getHeader(JwtConstant.tokenHeader);
    }

}
