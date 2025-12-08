package com.example.Scheduling.DBConfig;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.Scheduling.parcelRepo",           // your secondary repos
        entityManagerFactoryRef = "secondaryEntityManager",           // must match bean name
        transactionManagerRef = "secondaryTransactionManager"         // must match bean name
)
public class SecondaryDBConfig {

    // 1. DataSource bean (no @Primary → secondary is not primary)
    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    // 2. EntityManagerFactory – inject DataSource instead of calling method
    @Bean("secondaryEntityManager")  // name must match entityManagerFactoryRef above
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {  // inject!

        return builder
                .dataSource(secondaryDataSource)
                .packages("com.example.Scheduling.parcelModels")
                .persistenceUnit("secondaryPU")
                .build();
    }

    // 3. Transaction Manager
    @Bean("secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}