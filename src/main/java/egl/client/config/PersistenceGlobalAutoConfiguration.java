package egl.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = { "egl.client.repository.global" },
        entityManagerFactoryRef = "globalEntityManager",
        transactionManagerRef = "globalTransactionManager")
public class PersistenceGlobalAutoConfiguration extends PersistenceAutoConfiguration {

    public PersistenceGlobalAutoConfiguration(Environment env) {
        super(env, "global");
    }

    @Bean(name = "globalDataSource")
    @ConfigurationProperties(prefix="spring.global-datasource")
    public DataSource dataSource() {
        return super.dataSource();
    }

    @Bean(name = "globalEntityManager")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        return super.entityManager();
    }

    @Bean(name = "globalTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return super.transactionManager();
    }
}