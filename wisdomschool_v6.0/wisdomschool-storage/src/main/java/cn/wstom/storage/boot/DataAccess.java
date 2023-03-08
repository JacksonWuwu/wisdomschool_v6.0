package cn.wstom.storage.boot;

import cn.wstom.storage.server.util.ConfigureReader;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * <h2>服务器部分数据接入设置</h2>
 * <p>该配置类定义了服务器组件使用的MyBatis将如何链接数据库。如需更换其他数据库，请在此配置自己的数据源并替换原有数据源。</p>
 *
 * @author dws
 * @version 1.0
 */
@Configurable
public class DataAccess {
    private static Resource[] mapperFiles;
    private static Resource mybatisConfg;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(ConfigureReader.instance().getFileNodePathDriver());
        ds.setUrl(ConfigureReader.instance().getFileNodePathURL());
        ds.setUsername(ConfigureReader.instance().getFileNodePathUserName());
        ds.setPassword(ConfigureReader.instance().getFileNodePathPassWord());
        return ds;
    }

    @Bean(name = {"storageSqlSessionFactory"})
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource ds) {
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(ds);
        ssf.setConfigLocation(DataAccess.mybatisConfg);
        ssf.setMapperLocations(DataAccess.mapperFiles);
        return ssf;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer msf = new MapperScannerConfigurer();
        msf.setBasePackage("cn.wstom.storage.server.mapper");
        msf.setSqlSessionFactoryBeanName("storageSqlSessionFactory");
        return msf;
    }

    static {
        DataAccess.mapperFiles = new Resource[]{new ClassPathResource("mapper/storage/FolderMapper.xml"), new ClassPathResource("mapper/storage/NodeMapper.xml")};
        DataAccess.mybatisConfg = new ClassPathResource("mapper/mybatis.xml");
    }
}
