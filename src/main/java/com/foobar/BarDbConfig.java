package com.foobar;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "barEntityManagerFactory",
    transactionManagerRef = "barTransactionManager", basePackages = {"com.foobar.bar.repo"})
public class BarDbConfig {

	
	  @Bean(name = "barDataSource")
	  
	  @ConfigurationProperties(prefix = "bar.datasource") public DataSource
	  dataSource() { return DataSourceBuilder.create().build(); }
	  
	  @Bean(name = "barEntityManagerFactory") public
	  LocalContainerEntityManagerFactoryBean barEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("barDataSource") DataSource dataSource) 
	  { 
		    Map<String, String> jpaProperties = new HashMap<String, String>();
		    jpaProperties.put("hibernate.hbm2ddl.auto", "update");
		  
		    return builder.dataSource(dataSource).packages("com.foobar.bar.domain").
				  persistenceUnit("bar").properties(jpaProperties).build(); 
		  }
	  
	  @Bean(name = "barTransactionManager") public PlatformTransactionManager
	  barTransactionManager(@Qualifier("barEntityManagerFactory") EntityManagerFactory barEntityManagerFactory)
	  {
		  return new JpaTransactionManager(barEntityManagerFactory);
	}
}
