package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "org.example" })
@EnableJpaRepositories(basePackages = { "org.example" })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@PropertySource("classpath:application.properties")
@Slf4j
public class CommonJpaConfig {


	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}



	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryLogging(DataSource dataSource) throws SQLException {
		LocalContainerEntityManagerFactoryBean em = defaultEntityManagerFactory(dataSource, additionalProperties());

		return em;
	}

	private LocalContainerEntityManagerFactoryBean defaultEntityManagerFactory(DataSource dataSource, Properties properties) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[] { "org.example" });
		em.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
		em.setPersistenceProviderClass(PersistenceProvider.class);
		em.setSharedCacheMode(SharedCacheMode.NONE);
		em.setJtaDataSource(dataSource);
		em.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		em.setJpaProperties(properties);
		em.afterPropertiesSet();

		return em;
	}


	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}

	private Properties additionalProperties() {
		final Properties eclipseLinkProperties = new Properties();
		eclipseLinkProperties.put(PersistenceUnitProperties.WEAVING, "static");
		eclipseLinkProperties.put(PersistenceUnitProperties.QUERY_CACHE, "false");
		eclipseLinkProperties.put(PersistenceUnitProperties.DDL_GENERATION, "drop-and-create-tables");

		eclipseLinkProperties.put(PersistenceUnitProperties.LOGGING_LEVEL, "SEVERE");

		return eclipseLinkProperties;
	}
}
