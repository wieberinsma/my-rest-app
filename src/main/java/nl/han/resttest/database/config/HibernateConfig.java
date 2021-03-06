package nl.han.resttest.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * _EnableTransactionMangement_
 * For @Transactional functionality on @Entity
 *
 * _JpaTransactionManager_
 * Only available implementation for JPA (javax.persistence) under Spring, supports JPA and direct JDBC datasource access.
 * Datasource must be same as entityManagerFactory (which is default), instead declared for visibility
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig
{
    @Inject
    private DataSource dataSource;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("nl.han.resttest.database.model");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
