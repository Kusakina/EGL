package egl.client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class PersistenceAutoConfiguration {

    private final Environment env;
    private final String location;

    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    public LocalContainerEntityManagerFactoryBean entityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(Stream.of(location, "core").map(location -> "egl.client.model." + location).toArray(String[]::new));

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<>();

        String dataSourcePrefix = String.format("spring.%s-datasource", location);
        properties.put("hibernate.hbm2ddl.auto", env.getProperty(dataSourcePrefix + ".hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty(dataSourcePrefix + ".dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

}
