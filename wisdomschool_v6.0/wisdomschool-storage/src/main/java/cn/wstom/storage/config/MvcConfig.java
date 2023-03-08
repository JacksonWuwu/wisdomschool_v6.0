package cn.wstom.storage.config;

import cn.wstom.storage.boot.DataAccess;
import cn.wstom.storage.server.util.ConfigureReader;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import java.util.List;

/**
 * <h2>Web功能-MVC相关配置类</h2>
 * <p>
 * 该Spring配置类主要负责配置处理行为。
 * </p>
 *
 * @author dws
 * @version 1.0
 */
@Configurable
@ComponentScan(basePackages = "cn.wstom.storage")
@ServletComponentScan({"cn.wstom.storage.listener", "cn.wstom.storage.filter"})
@Import({DataAccess.class})
public class MvcConfig implements WebMvcConfigurer {

    private FastJsonHttpMessageConverter httpMessageConvertersConfig;

    private StringHttpMessageConverter stringHttpMessageConverter;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Autowired
    public MvcConfig(FastJsonHttpMessageConverter httpMessageConvertersConfig, StringHttpMessageConverter stringHttpMessageConverter) {
        this.httpMessageConvertersConfig = httpMessageConvertersConfig;
        this.stringHttpMessageConverter = stringHttpMessageConverter;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/**").addResourceLocations("file:" + ConfigureReader.instance().getPath() + File.separator + "webContext" + File.separator);
        registry.addResourceHandler("/fileblocks/**")
                .addResourceLocations("file:" + ConfigureReader.instance().getFileBlockPath());
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(ConfigureReader.instance().getTemporaryfilePath());
        return factory.createMultipartConfig();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter);
        converters.add(httpMessageConvertersConfig);
    }

    /**
     * 路径匹配配置
     *
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }
}
