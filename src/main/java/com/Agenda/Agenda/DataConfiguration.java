package com.Agenda.Agenda;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/agendapessoal");  //modificar de acordo com a porta hospedada e o nome da database criada
		dataSource.setUsername("root"); //nome usuario
		dataSource.setPassword("1234"); //senha do usuario
		return dataSource;	
	}
	
	@Bean
	public JpaVendorAdapter jpaVendoAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect"); //pode dar erro de acordo com a verdao do mysql
		adapter.setPrepareConnection(true);
		return adapter;
	}
	

}
