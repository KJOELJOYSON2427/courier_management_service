package com.example.Scheduling.DBConfig;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
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
        basePackages = "com.example.Scheduling.gmailRepo",   // your GmailTokenRepository package
        entityManagerFactoryRef = "gmailEntityManagerFactory",
        transactionManagerRef = "gmailTransactionManager"
)
public class GmailDbConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSource gmailDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean gmailEntityManagerFactory(
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(gmailDataSource())
                .packages("com.example.Scheduling.gmailToken") // your GmailToken entity package
                .persistenceUnit("gmail")
                .build();
    }

    @Bean
    public PlatformTransactionManager gmailTransactionManager(
            @Qualifier("gmailEntityManagerFactory")
            EntityManagerFactory gmailEntityManagerFactory
    ) {
        return new JpaTransactionManager(gmailEntityManagerFactory);
    }
}
