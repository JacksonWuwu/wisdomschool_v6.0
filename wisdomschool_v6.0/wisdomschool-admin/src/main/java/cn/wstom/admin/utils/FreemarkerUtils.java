package cn.wstom.admin.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author dws
 * @date 2019/04/12
 */
@Component
public class FreemarkerUtils {

    private final Configuration configuration;

    @Autowired
    public FreemarkerUtils(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * 获取模板数据
     *
     * @param templateName 模板名称
     * @return 模板数据
     * @throws IOException 模板不存在
     */
    public Template getTemplate(String templateName) throws IOException {
        return configuration.getTemplate(templateName);
    }

    /**
     * 获取模板数据
     *
     * @param templateName 模板名称
     * @return 模板数据
     * @throws IOException 模板不存在
     */
    public Template getTemplate(String templateName, String encoding) throws IOException {
        return configuration.getTemplate(templateName, encoding);
    }
}
