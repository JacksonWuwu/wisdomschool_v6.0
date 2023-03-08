package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.service.SysRoleService;
import cn.wstom.admin.service.SysUserService;
import cn.wstom.admin.utils.JwtUtils;
import cn.wstom.admin.utils.ServletUtils;

import cn.wstom.common.base.Data;
import cn.wstom.common.constant.HttpConstants;

import cn.wstom.common.constant.JwtConstant;
import cn.wstom.common.utils.KeyUtil;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录验证
 *
 * @author dws
 */
@Controller
@RequestMapping("admin/sysLogin")
@Slf4j
public class SysLoginController extends BaseController {

    //
  //  public static final Map<String, CaptchaVO> captchaMap = new HashMap<>();



    @Value("${jwt.username.format}")
    private String jwtUsername;

    @Value("${jwt.blacklist.format}")
    private String jwtBlacklist;

    @Value("${jwt.token.format}")
    private String jwtToken;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    //@Autowired
    //private TestPaperOneService testPaperOneService;
    @RequestMapping("/remind")
    @ResponseBody
    public String remind() {
        return "remind";
    }


    @PostMapping("/login")
    @ResponseBody
    public Data login(SysUser sysUser){
        //生成加密后密文
        SysUser dbUser = sysUserService.selectOne(sysUser);
        if (!isPwdTure(sysUser,dbUser)) {
            return Data.error("账号或密码错误");
        }else{
            sysUser.setId(dbUser.getId());
            String key = String.format(jwtUsername,sysUser.getId());
            log.error("redis key: {}",key);
            //判断redis中是否存在该用户名
            String name = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(name)){
                String value = (String)redisTemplate.opsForHash().get(jwtToken, sysUser.getId());
                System.out.println(value);
                Date expiredDate = jwtUtils.getHoldTime(value);
                Map<String, Object> map = new HashMap<>();
                map.put("token",value);
                map.put("expiredDate",expiredDate.getTime());
                return Data.success(0,name+"已经登录",map);
            }
            //成功生成token
            String token= jwtUtils.generateToken(sysUser);
            Date expiredDate = jwtUtils.getHoldTime(token);
            //用户名有效时间 - 用户免登录时间
            //得到jwt中的截止时间
            long time=jwtUtils.generateLoginDate().getTime();

            long expired = time-new Date().getTime();

            log.error("原始数据: {} redis {} 截止时间: {}",time,key,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
            //信息放入redis - set key value EX 10
            redisTemplate.opsForValue().set(key,sysUser.getLoginName(),expired,TimeUnit.MILLISECONDS);
            //存当前id对应正在使用的token
            //hset key field value
            redisTemplate.opsForHash().put(jwtToken,sysUser.getId(),token);
            log.error("redis hashKey: {} field: {} token:{}",jwtToken,sysUser.getId(),token);
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("expiredDate",expiredDate.getTime());
            return Data.success("登录成功",map);

        }
    }

    //"重新登录"
    //@PostMapping("/relogin")
    //@ResponseBody
    public Data relogin(SysUser sysUser){
        SysUser dbUser = sysUserService.selectOne(sysUser);
        System.out.println("++++++++++"+dbUser);
        if (!isPwdTure(sysUser,dbUser)) {

            return Data.error("账号或密码错误");

        }else{
            sysUser.setId(dbUser.getId());
            String token =getToken();
            //删除用户名
            String userKey = String.format(jwtUsername,sysUser.getId());
            redisTemplate.delete(userKey);
            //删除用户token
            redisTemplate.opsForHash().delete(jwtToken,sysUser.getId());
            //token放入黑名单
            String group = jwtUtils.getGroupFromToken(token);
            long time= jwtUtils.generateLoginDate().getTime();
            long expired = time - new Date().getTime();
            log.error("黑名单 - 原始数据: {} redis {} 截止时间: {}",time,userKey,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));

            String blackKey = String.format(jwtBlacklist,group);
            //可能token已过期
            if(expired>0) {
                redisTemplate.opsForValue().set(blackKey, token, expired, TimeUnit.MILLISECONDS);
            }

            //重新生成用户名有效时间 - 用户免登录时间
            String newToken = jwtUtils.generateToken(sysUser);
            //得到jwt中的截止时间
            time=jwtUtils.generateLoginDate().getTime();
            expired = time-new Date().getTime();

            log.error("重新登录 原始数据-: {} redis {} 截止时间: {}",time,userKey,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
            //信息放入redis - set key value EX 10
            redisTemplate.opsForValue().set(userKey,sysUser.getLoginName(),expired,TimeUnit.MILLISECONDS);
            //存当前id对应正在使用的token
            //hset key field value
            redisTemplate.opsForHash().put(jwtToken,sysUser.getId(),newToken);
            log.error("redis hashKey: {} field: {} token:{}",jwtToken,sysUser.getId(),token);
            Date expiredDate = jwtUtils.getHoldTime(newToken);
            Map<String, Object> map = new HashMap<>();
            map.put("token",newToken);
            map.put("expiredDate",expiredDate.getTime());
            return Data.success(map);
        }
    }

    //"根据jwt得到信息"
    @GetMapping("/getInfo")
    @ResponseBody
    public Data getInfo(HttpServletRequest request){
        SysUser sysUser = getUser();
        sysUser.setPassword("");
        sysUser.setSalt("");
        List<SysRole> sysRoles = sysRoleService.selectAllRolesByUserId(getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("sysUser",sysUser);
        map.put("sysRoles",sysRoles);
        return Data.success(map);
    }

    //"清除token，登入"
    @GetMapping("/logout")
    public Data logout(HttpServletRequest request){

        String token = request.getHeader(JwtConstant.tokenHeader);

        log.info("logout 请求头 {}",token);

        String id = jwtUtils.getUserIdFromToken(token);
        //删除登录的用户名
        String userKey = String.format(jwtUsername,id);
        redisTemplate.delete(userKey);

        //删除id当前使用的token
        redisTemplate.opsForHash().delete(jwtToken,id);
        //token放入黑名单
        String group = jwtUtils.getGroupFromToken(token);
        long time= jwtUtils.getLoginDate(token);
        long expired = time - new Date().getTime();
        log.error("logout 原始数据: {} redis {} 截止时间: {}",time,userKey,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        String blackKey = String.format(jwtBlacklist,group);
        if (expired>0) {
            redisTemplate.opsForValue().set(blackKey, token, expired, TimeUnit.MILLISECONDS);
        }

        return Data.success("注销成功");
    }

    //"刷新token"
    @GetMapping(value = "/tokenRefresh")
    @ResponseBody
    public Data refreshToken(HttpServletRequest request) {
        //1、获取请求头中的Authorization完整值
        String oldToken = getToken();
        String refreshToken = "";

        //2、是否可以进行刷新（未过有效时间/是否在免登录范围）
//        if(!jwtUtils.canRefresh(oldToken)|| jwtUtils.isHoldTime(oldToken)){
//            return R.error().message("jwt还未失效，无需刷新").code(20001);
//        }

        //再次获得免登录机会
        long time = jwtUtils.generateLoginDate().getTime();
        long expired = time - new Date().getTime();

        refreshToken =  jwtUtils.refreshToken(oldToken);

        String id = jwtUtils.getUserIdFromToken(refreshToken);
        //原token放入黑名单
        String group = jwtUtils.getGroupFromToken(oldToken);
        String key = String.format(jwtBlacklist,group);
        if (expired>0) {
            redisTemplate.opsForValue().set(key, oldToken, expired, TimeUnit.MILLISECONDS);
        }
        //当前使用的token进行修改
        redisTemplate.opsForHash().put(jwtToken,id,refreshToken);
        //更新用户有效时间
        String userkey = String.format(jwtUsername,id);
        String username = jwtUtils.getUserNameFromToken(refreshToken);
        if(expired>0){
            redisTemplate.opsForValue().set(userkey,username,expired,TimeUnit.MILLISECONDS);
        }

        Date date = jwtUtils.getHoldTime(refreshToken);

        //将新的token交给前端
        Date expiredDate = jwtUtils.getHoldTime(refreshToken);
        //return R.ok().data("token",refreshToken).data("date",date);
        Map<String, Object> map = new HashMap<>();
        map.put("token",refreshToken);
        map.put("expiredDate",expiredDate.getTime());
        return Data.success("刷新成功",map);
    }


   // //判断token是否已经过期
   //@GetMapping("/expired")
   //@ResponseBody
   //public Data expired(){
   //    long time= jwtUtils.generateLoginDate().getTime();
   //    Date holdTime = jwtUtils.getHoldTime(getToken());
   //    long expired = new Date().getTime()-holdTime.getTime();
   //    System.out.println(holdTime);
   //    System.out.println(new Date().getTime());
   //    System.out.println(expired);
   //    if(expired>0) {
   //      return Data.error(-1,"已经过期");
   //    }else {
   //        return Data.success("还没过期");
   //    }
   //}








    @GetMapping("/teacher/login")
    @ResponseBody
    public String teacherLogin(HttpServletRequest request, HttpServletResponse response) {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"" + HttpConstants.CODE_NOT_LOGIN + "\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
//        return "Login";
    }
//    @GetMapping("/examControl/login")
//    public String examControllogin(String cid, String tid, String paperId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
//        // 如果是Ajax请求，返回Json字符串。
//        if (ServletUtils.isAjaxRequest(request)) {
//            return ServletUtils.renderString(response, "{\"code\":\"" + HttpConstants.CODE_NOT_LOGIN + "\",\"msg\":\"未登录或登录超时。请重新登录\"}");
//        }
//        System.out.println("cid:" + cid);
//        System.out.println("tid:" + tid);
//        System.out.println("paperId:" + paperId);
//        SysUser sysUser = sysUserService.selectUserByUserAttrId(tid);
//        modelMap.put("loginName", sysUser.getLoginName());
//        modelMap.put("cid", cid);
//        modelMap.put("tid", tid);
//        modelMap.put("paperId", paperId);
////        return "login";
//        return "examControlLogin";
//    }





    //@PostMapping("/student/login")
    //@ResponseBody
    //public Data ajaxStudentLogin(String encrypted,String testPaperOneId) {
    //    final String loginInfo = DecryptionUtil.decryption(encrypted, KeyUtil.getPrivateKey());
    //    final LoginInfo info = JSONUtils.readValue(loginInfo, LoginInfo.class);
    //    if (info == null) {
    //        return Data.error("用户名和密码不能为空！");
    //    }
    //    if (System.currentTimeMillis() - Long.parseLong(info.getTime()) > TIME_OUT) {
    //        return Data.error("登陆超时");
    //    }
    //    UsernamePasswordToken token = new UsernamePasswordToken(info.getUsername(), info.getPassword(), info.isRememberMe());
    //    TestPaperOne testPaperOne = testPaperOneService.getById(testPaperOneId);
    //    System.out.println(testPaperOneId);
    //    System.out.println(testPaperOne);
    //    Subject subject = SecurityUtils.getSubject();
    //    if (testPaperOne.getSetExam().equals("1")){
    //        try {
    //            subject.login(token);
    //            AsyncManager.async().execute(
    //                    AsyncIntegral.recordIntegral(ShiroUtils.getUser(), "login",
    //                            "用户【" + ShiroUtils.getUser().getUserName() + "】登陆"));
    //            return success();
    //        } catch (AuthenticationException e) {
    //            String msg = "用户或密码错误";
    //            if (StringUtils.isNotEmpty(e.getMessage())) {
    //                msg = e.getMessage();
    //            }
    //            return error(msg);
    //        }
    //    }else{
    //        String msg = "该考试未开始！请联系教师开始考试~";
    //        return error(msg);
    //    }
    //}

    //@PostMapping("/examControl/login")
    //@ResponseBody
    //public Data ajaxExamControlLogin(String password) {
    //    if (password == null) {
    //        return Data.error("密码不能为空！");
    //    }
    //    System.out.println(password);
    //    try {
    //        if (password.equals("123456")) {
    //            return success();
    //        } else {
    //            String msg = "密码错误";
    //            return error(msg);
    //        }
    //    } catch (AuthenticationException e) {
    //        String msg = "密码错误";
    //        if (StringUtils.isNotEmpty(e.getMessage())) {
    //            msg = e.getMessage();
    //        }
    //        return error(msg);
    //    }
    //}

    @RequestMapping("/publicKey")
    @ResponseBody
    public Data getPublicKey() {
        return Data.success().put("k", KeyUtil.getPublicKey()).put("t", System.currentTimeMillis());
    }

    @GetMapping("/unauth")
    public String unauth() {
        return "/error/unauth";
    }

    //@RequestMapping("/profile")
    //@ResponseBody
    //public SysUser loginInfo() {
    //    return getUser();
    //}


    /**
     * 请求发送邮件，目前来说只有找回密码有发送邮件功能，如果多个功能都有，就要考虑复用性和url名字可识性
     *
     * @param email
     * @return
     */
    //@PostMapping("/sendemail")
    //@ResponseBody
    //public Data sendEmail(String email) {
    //    //根据给定的邮箱发送邮件
    //    SysUser sysUser = sysUserService.selectUserByEmail(email);
    //
    //    if (sysUser == null) {
    //        //用户为空，说明填写的邮箱就不对
    //        return Data.error("邮箱账号不存在");
    //    }
    //
    //    //先生成验证码，先发送邮件，发送成功后保存验证码
    //    String captcha = RandomStringUtils.randomNumeric(4);
    //
    //    //先发送验证码，发送成功了再保存到数据库
    //    boolean b = SendEmailUtil.sendPasswordCaptchaEmail(email, captcha);
    //    if (b) {
    //        CaptchaVO captchaVO = new CaptchaVO();
    //        captchaVO.setCaptcha(captcha);
    //        captchaVO.setIp(ShiroUtils.getIp());
    //        captchaMap.put(email, captchaVO);
    //
    //        return Data.success("验证码已发送到您的邮箱，请注意查收");
    //    } else {
    //        //发送失败，提醒
    //        return Data.error("验证码发送失败，请检查邮箱账号");
    //    }
    //}


    //@PostMapping("/updatePasswordByCaptcha")
    //@ResponseBody
    //public Data updatePasswordByCaptcha(String email, String repassword, String captcha) {
    //    CaptchaVO captchaVO = captchaMap.get(email);
    //
    //    if (captchaVO == null) {
    //        return Data.error("邮箱账号不存在");
    //
    //    } else if (!StringUtils.equals(captchaVO.getIp(), ShiroUtils.getIp())) {
    //        return Data.error("地址异常，请重新发送邮件");
    //
    //    } else if (!StringUtils.equalsIgnoreCase(captchaVO.getCaptcha(), captcha)) {
    //        return Data.error("验证码不正确");
    //
    //    } else {
    //        // 根据给定的邮箱发送邮件
    //        SysUser sysUser = sysUserService.selectUserByEmail(email);
    //
    //        if (sysUser == null) {
    //            //用户为空，说明填写的邮箱就不对
    //            return Data.error("邮箱账号不存在");
    //        }
    //
    //        sysUser.setSalt(ShiroUtils.randomSalt());
    //        sysUser.setPassword(ShiroUtils.encryptPassword(sysUser.getLoginName(), repassword, sysUser.getSalt()));
    //
    //        int result = sysUserService.updateUserPassword(sysUser);
    //        if (result > 0) {
    //
    //            //验证成功了，返回重置密码的页面
    //            return Data.success("密码修改成功");
    //        }
    //
    //        return Data.error("密码修改异常");
    //    }
    //
    //}


}
