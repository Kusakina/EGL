package egl.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

    @Primary
    @Bean(name = "localDataSource")
    @ConfigurationProperties(prefix="spring.local-datasource")
    public DataSource dataSource() {
        return super.dataSource();
    }

    @Primary
    @Bean(name = "localEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        return super.entityManager();
    }

    @Primary
    @Bean(name = "localTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return super.transactionManager();
    }

}