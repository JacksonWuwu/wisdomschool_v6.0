package cn.wstom.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author dws
 * @date 2019/03/07
 */
@Component
@ConfigurationProperties(prefix = "wstom.site")
@Setter
@Getter
public class SiteConfig {
    /**
     * 网站名称
     */
    private String siteName;
    /**
     * 网页主页链接
     */
    private String siteUrl;

    /**
     * logo
     */
    private String siteLogo;

    /**
     * 首页标题
     */
    private String seoTitle;

    /**
     * 网站关键字
     */
    private String seoKeywords;

    /**
     * 网站描述
     */
    private String seoDescription;

    /**
     * 站点底部信息
     */
    private String siteFooterCode;

    /**
     * 备案信息
     */
    private String siteBean;
}
