package com.foucsr.crmportal.oracle.database.config;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author FocusR
 * 
 */
@Configuration
@EnableJpaRepositories(
		basePackages = "com.foucsr.crmportal.oracle.database.repository",
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracleTransactionManager"
)
public class OracleSourceConfigLocal
{
	@Autowired
	private Environment env;
	@Profile("dev")	
	@Bean
    @ConfigurationProperties(prefix="datasource.oracle")
    public DataSourceProperties ordersDataSourceProperties() {
        return new DataSourceProperties();
    }
	@Profile("dev")	
    @Bean
    public DataSource ordersDataSource() {
        DataSourceProperties primaryDataSourceProperties = ordersDataSourceProperties();
		return DataSourceBuilder.create()
        			.driverClassName(primaryDataSourceProperties.getDriverClassName())
        			.url(primaryDataSourceProperties.getUrl())
        			.username(primaryDataSourceProperties.getUsername())
        			.password(primaryDataSourceProperties.getPassword())
        			.build();
    }
    
	
	@Profile("dev")
	@Bean
	public EntityManagerFactory oracleEntityManagerFactory() throws SQLException, IllegalArgumentException, NamingException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.ORACLE);
		vendorAdapter.setShowSql(true);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.foucsr.crmportal.oracle.database.model");
		factory.setDataSource(ordersDataSource());
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	@Profile("dev")
	@Bean
	public PlatformTransactionManager oracleTransactionManager()
			throws SQLException, IllegalArgumentException, NamingException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(oracleEntityManagerFactory());
		return txManager;
	}
    
}
