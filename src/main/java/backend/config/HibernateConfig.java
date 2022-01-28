package backend.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages={"backend.model"})
@EnableTransactionManagement
public class HibernateConfig implements WebMvcConfigurer {

        private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/mallTable?createDatabaseIfNotExist=true&useSSL=false";
        private final static String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
        private final static String DATABASE_DIALECT = "org.hibernate.dialect.MySQL5Dialect";
        private final static String DATABASE_USERNAME = "root";
        private final static String DATABASE_PASSWORD = "password";
        private final static String DATABASE_INIT = "true";


/*        private final static String DATABASE_URL = "jdbc:h2:file:D:/Users/olatomi/Documents/JAVA/DB/mall_DB";
        private final static String DATABASE_DRIVER = "org.h2.Driver";
        private final static String DATABASE_DIALECT = "org.hibernate.dialect.H2Dialect";
        private final static String DATABASE_USERNAME = "sa";
        private final static String DATABASE_PASSWORD = "";*/

    @Bean("dataSource")
    public DataSource getDataSource(){
        //provide db connection info
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DATABASE_DRIVER);
        dataSource.setUrl(DATABASE_URL);
        dataSource.setUsername(DATABASE_USERNAME);
        dataSource.setPassword(DATABASE_PASSWORD);
        return dataSource;
    }
/*
    @Bean("dataSource")
    public DataSource getDataSource(){
        //provide db connection info
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }*/


    @Bean
    public SessionFactory getSessionFactory(DataSource dataSource){
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);
        builder.addProperties(getHibernateProperties());
        builder.scanPackages("backend.model");
        return builder.buildSessionFactory();
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", DATABASE_DIALECT);
//        properties.put("hibernate.dialect", environment.getProperty("hibernate-dialect"));
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");
        return  properties;
    }




    @Bean
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }



}
