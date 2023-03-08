package cn.wstom.admin.service;

import java.util.Map;

/**
 * @author : dws
 */
public interface MailService {
    void config();

    void sendTemplateEmail(String to, String title, String template, Map<String, Object> content);
}
