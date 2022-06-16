package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
	@Bean 
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource); 
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:com/douzone/mysite/config/app/mybatis/configuration.xml"));  

		return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory){ // 파라미터로 받는걸 많이씀 함수로 하는것보다 
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}