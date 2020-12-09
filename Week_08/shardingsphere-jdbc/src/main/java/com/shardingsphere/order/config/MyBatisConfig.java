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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Configuration
@MapperScan(basePackages = "com.shardingsphere.order.core.dao")
public class MyBatisConfig {

//    @javax.annotation.Resource(name = "shardingDataSource")
//    private DataSource shardingDataSource;

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource() throws IOException, SQLException, URISyntaxException {
        URL resource = MyBatisConfig.class.getResource("/sharding-jdbc.yaml");
        File yamlFile = new File(resource.getFile());
        System.out.println("自增长1");
        System.out.println(yamlFile);
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(yamlFile);
        System.out.println("自增长");
        System.out.println(dataSource.getConnection());
        return dataSource;
    }


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
        System.out.println(resources);
        return resources;
    }

    public static void main(String[] args) throws IOException, SQLException {
        URL resource = MyBatisConfig.class.getResource("/sharding-jdbc.yaml");
        File yamlFile = new File(resource.getFile());
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(yamlFile);
        Connection connection = dataSource.getConnection();

        Statement statement = connection.createStatement();
//        boolean execute = statement.execute("insert into mall_order ( id, order_code, user_id, shop_id, user_address_id, spu_snapshot_id ,pay_type, order_refund_price, pay_amount, real_pay) values ( 456732135122472960, '456732135122472961', 456732135122472962, 57, 27, 77 ,0,0, 1, 0);");
//        System.out.println(execute);
        ResultSet resultSet = statement.executeQuery("select * from mall_order where id = 12");

        while (resultSet.next()) {
            Object object = resultSet.getObject(1);
            System.out.println(object);
        }

    }
}
