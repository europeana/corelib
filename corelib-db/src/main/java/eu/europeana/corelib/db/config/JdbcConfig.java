package eu.europeana.corelib.db.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JdbcConfig  {

    @Value("#{europeanaProperties['db.driverClass']}")
    private String jdbcDriverClass;

    @Value("#{europeanaProperties['postgres.jdbcUrl']}")
    private String jdbcUrl;

    /**
     * When deploying on Cloud Foundry this dataSource is ignored because when there is 1 database service defined in
     * the manifest, the Spring Auto-reconfiguration framework will create it's own dataSource for that instead.
     * See https://spring.io/blog/2011/11/04/using-cloud-foundry-services-with-spring-part-2-auto-reconfiguration
     * <p>
     * Unfortunately Spring Auto-reconfiguration then also automatically sets maxIdle and maxActive to 4. See also
     * https://community.pivotal.io/s/article/Connection-pool-warning-message-maxIdle-is-larger-than-maxActive-setting-maxIdle-to-4-seen-in-PCF-deployed-Spring-app
     */
    @Bean(name = "corelib_db_dataSource", destroyMethod = "close")
    public DataSource dbDataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName(jdbcDriverClass);
        ds.setUrl("jdbc:" + jdbcUrl);
        ds.setMaxIdle(2);
        ds.setMaxActive(10);
        return ds;
    }


    @Bean(name = "corelib_db_entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dbDataSource());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(false);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQL82Dialect");
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);

        return entityManagerFactoryBean;
    }

    @Bean(name="corelib_db_transactionManager")
    @Primary
    public JpaTransactionManager txManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setDataSource(dbDataSource());
        return transactionManager;
    }
}
