package com.mysql.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.mysql.demo.common.DbTypeEm;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.mysql.demo.core.dao")
public class MybatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);



    @Bean(name = "getMasterSource")
    @ConfigurationProperties(prefix = "datasource.master")
    public DataSource getMasterSource() {
        return new DruidDataSource();
    }


    @Bean(name = "getSalveSource")
    @ConfigurationProperties(prefix = "datasource.salve")
    public DataSource getSalveSource() {
        return new DruidDataSource();
    }


    @Bean(name = "dbRouting")
    public DataSource dbRouting(@Qualifier("getMasterSource") DataSource masterSource,
                                @Qualifier("getSalveSource") DataSource salveSource) throws IOException {

        //创建每个库的datasource
        Map<Object, Object> targetDataSources = new HashMap<>(DbTypeEm.values().length);

        targetDataSources.put(DbTypeEm.db0, masterSource);
        targetDataSources.put(DbTypeEm.db1, salveSource);
        //设置多数据源
        DbRouting dbRouting = new DbRouting();
        dbRouting.setTargetDataSources(targetDataSources);
        return dbRouting;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dbRouting") DataSource dbRouting) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dbRouting);
        factoryBean.setMapperLocations(getResource("mapper", "**/*.xml"));

        //日志打印控制台
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setLogImpl(StdOutImpl.class);
        factoryBean.setConfiguration(configuration);
        //防注入插件
//        factoryBean.setPlugins(paginationInterceptor());
        return factoryBean.getObject();
    }



    private org.springframework.core.io.Resource[] getResource(String basePackage, String pattern) throws IOException {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(new StandardEnvironment()
                .resolveRequiredPlaceholders(basePackage)) + "/" + pattern;
        org.springframework.core.io.Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
        return resources;
    }
//
//    private PaginationInterceptor paginationInterceptor() {
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        // 开启 count 的 join 优化,只针对 left join !!!
//        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
//        paginationInterceptor.setDialectType("mysql");
//        List<ISqlParser> sqlParserList = new ArrayList<>();
//        // 攻击 SQL 阻断解析器、加入解析链
//        sqlParserList.add(new BlockAttackSqlParser() {
//            // 防止delete全表操作
//            @Override
//            public void processDelete(Delete delete) {
//                super.processDelete(delete);
//            }
//
//            // 防止update全表操作
//            @Override
//            public void processUpdate(Update update) {
//                super.processUpdate(update);
//            }
//        });
//        paginationInterceptor.setSqlParserList(sqlParserList);
//        return paginationInterceptor;
//    }
}
