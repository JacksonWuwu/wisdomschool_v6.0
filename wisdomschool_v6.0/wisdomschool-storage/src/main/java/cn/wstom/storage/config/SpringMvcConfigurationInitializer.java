package cn.wstom.storage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringMvcConfigurationInitializer extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //上传的图片在c盘下的/opt/plate目录下，访问路径如
//        下:http://localhost:8088/opt/plate/icon_yxgl.jpg
//         //其中plate表示访问的前缀。"file:/opt/plate/"是文件真实的存储路径
//        E:\course\wstom\file
        registry.addResourceHandler("/file/**").addResourceLocations("file:E:/course/wstom/file/");
        //file:/opt/plate/指向本地图片路径地址
        super.addResourceHandlers(registry);
    }
}