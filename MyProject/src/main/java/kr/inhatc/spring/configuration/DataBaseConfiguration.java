package kr.inhatc.spring.configuration;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
// 설정관련 부분
//1)
@Configuration
// classpath = src/main/resources
@PropertySource("classpath:/application.properties")
public class DataBaseConfiguration {
	
	//4)
	@Autowired //객체를 메모리에 올려줌
	private ApplicationContext applicationContext;
	
	//2)
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig(); // 객체생성
		
	}
	//3)
	@Bean //데이터 소스 연결
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println("=======>" + dataSource.toString());
		return dataSource;
	}
	//4) session관리
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));

		// Mybatis 설정 추가
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		return sqlSessionFactoryBean.getObject();
	}
	//6)
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig() {
		return new org.apache.ibatis.session.Configuration();
	}
	//5)
	@Bean
	public SqlSessionTemplate sqlSessiongTemplate(SqlSessionFactory sqlSessionFactory) {
		
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	

}
