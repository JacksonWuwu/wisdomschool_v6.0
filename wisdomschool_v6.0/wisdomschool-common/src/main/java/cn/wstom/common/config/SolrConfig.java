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
@ConfigurationProperties(prefix = "solr")
@Setter
@Getter
public class SolrConfig {
    /**
     * solr服务器连接设置
     **/
    private String serverUrl;
}
