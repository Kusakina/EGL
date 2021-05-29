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
        basePackages = { "egl.client.repository.global" },
        entityManagerFactoryRef = "globalEntityManager",
        transactionManagerRef = "globalTransactionManager")
public class PersistenceGlobalAutoConfiguration extends PersistenceAutoConfiguration {

    public PersistenceGlobalAutoConfiguration(Environment env) {
        super(env, "global");
    }

    @Bean
    @ConfigurationProperties(prefix="spring.global-datasource")
    public DataSource globalDataSource() {
        return dataSource();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean globalEntityManager() {
        return entityManager();
    }

    @Bean
    public PlatformTransactionManager globalTransactionManager() {
        return transactionManager();
    }


}