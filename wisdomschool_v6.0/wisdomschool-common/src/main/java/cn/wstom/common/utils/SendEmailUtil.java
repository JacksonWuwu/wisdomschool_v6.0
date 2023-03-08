package cn.wstom.common.utils;


import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class SendEmailUtil {

    /**
     * 发送邮件
     * Created by YLing
     *
     * @param receiveEmail  收件人邮箱地址
     * @param emailTitle    邮件标题
     * @param emailBody     邮件正文
     * @return
     */
    public static boolean sendEmail(String receiveEmail, String emailTitle, String emailBody){

        System.out.println(">>>开发发送邮件：\n"+receiveEmail+"\n"+emailTitle+"\n"+emailBody+"\n");

        try {
            Email email = new SimpleEmail();
            email.setSSLOnConnect(true);
            email.setHostName("smtp.qq.com");
            email.setSmtpPort(465);
            email.setAuthentication("281271223@qq.com", "pqalblghxixjcbcg");
            email.setFrom("281271223@qq.com");
            email.addTo(receiveEmail);
            email.setSubject(emailTitle);
            email.setMsg(emailBody);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送邮件出错了！！");
            return false;
        }

        return true;
    }

    /**
     * 发送找回密码验证码的邮件
     * Created by YLing
     * @param receiveEmail
     * @param validateCode
     * @return
     */
    public static boolean sendPasswordCaptchaEmail(String receiveEmail, String validateCode){
        return sendEmail(receiveEmail, "找回密码-验证码", "您正在进行找回密码操作，当前验证码为：" + validateCode);
    }


}