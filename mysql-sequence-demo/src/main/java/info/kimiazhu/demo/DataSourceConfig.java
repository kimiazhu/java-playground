package info.kimiazhu.demo;

import java.beans.PropertyVetoException;

import javax.annotation.PreDestroy;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages="info.kimiazhu.demo.mapper")
public class DataSourceConfig {

    private static final String MYBATIS_CONFIG_PATH = "classpath:mybatis-config.xml";
    
    private DataSource dataSource;
    
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.initial-size}")
    private int initSize;
    @Value("${spring.datasource.max-active}")
    private int maxActive;
    @Value("${spring.datasource.min-idle}")
    private int minIdle;
    @Value("${spring.datasource.max-idle}")
    private int maxIdle;
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.test-on-borrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.test-on-return}")
    private boolean testOnReturn;
    @Value("${spring.datasource.test-while-idle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.test-on-connect}")
    private boolean testOnConnect;
    @Value("${spring.datasource.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;




    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        dataSource = new DataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initSize);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxIdle(maxIdle);
        dataSource.setMinIdle(minIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setTestOnConnect(testOnConnect);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setLogValidationErrors(true);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setTypeAliasesPackage("com.xgsdk.portal.model");
        sessionFactory.setConfigLocation(resolver.getResource(MYBATIS_CONFIG_PATH));
        return sessionFactory.getObject();
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
        return new JdbcTemplate(dataSource());
    }
    
    @PreDestroy
    public void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }
}
