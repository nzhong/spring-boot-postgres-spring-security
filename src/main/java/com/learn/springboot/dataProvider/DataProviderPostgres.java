package com.learn.springboot.dataProvider;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class DataProviderPostgres {

  @Value("${db.url}")
  private String dbUrl;

  @Value("${db.username}")
  private String dbUsername;

  @Value("${db.password}")
  private String dbPassword;

  @Bean
  public String databaseType() {
    return "POSTGRES";
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds0 = new DriverManagerDataSource();
    ds0.setDriverClassName("org.postgresql.Driver");
    ds0.setUrl(dbUrl);
    ds0.setUsername(dbUsername);
    ds0.setPassword(dbPassword);
    return ds0;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
    DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setDataSource(dataSource);
    lef.setJpaVendorAdapter(jpaVendorAdapter);
    lef.setPackagesToScan("com.learn.springboot.dataProvider");
    return lef;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(false);
    hibernateJpaVendorAdapter.setGenerateDdl(true);
    hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
    return hibernateJpaVendorAdapter;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager();
  }
}