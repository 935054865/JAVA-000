package com.shardingsphere.order.config;


import com.baomidou.mybatisplus.core.MybatisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import org.springframework.core.io.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.shardingsphere.order.core.dao")
public class MyBatisConfig {

//    @javax.annotation.Resource(name = "shardingDataSource")
//    private DataSource shardingDataSource;

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource() throws IOException, SQLException, URISyntaxException {
        URL resourceAsStream = MyBatisConfig.class.getResource("/sharding-jdbc.yaml");
        System.out.println("种类");
        System.out.println(resourceAsStream.toURI());
        File yamlFile = new File(resourceAsStream.toURI());
        return YamlShardingSphereDataSourceFactory.createDataSource(yamlFile);
    } //配置主从数据源路由


    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        log.info("> sqlSessionFactory");
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(shardingDataSource());
        sessionFactory.setMapperLocations(getResource("mapper", "**/*.xml"));
        //日志打印控制台
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(StdOutImpl.class);
        sessionFactory.setConfiguration(configuration);
        return sessionFactory.getObject();
    }


    private org.springframework.core.io.Resource[] getResource(final String basePackage, final String pattern) throws IOException {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(new StandardEnvironment()
                .resolveRequiredPlaceholders(basePackage)) + "/" + pattern;
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        return resources;
    }
}
