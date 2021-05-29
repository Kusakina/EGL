package egl.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = { "egl.client.repository.local" },
        entityManagerFactoryRef = "localEntityManager",
        transactionManagerRef = "localTransactionManager")
public class PersistenceLocalAutoConfiguration extends PersistenceAutoConfiguration {

    public PersistenceLocalAutoConfiguration(Environment env) {
        super(env, "local");
    }

    @Bean
    @ConfigurationProperties(prefix="spring.local-datasource")
    public DataSource localDataSource() {
        return dataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localEntityManager() {
        return entityManager();
    }

    @Bean
    public PlatformTransactionManager localTransactionManager() {
        return transactionManager();
    }

}