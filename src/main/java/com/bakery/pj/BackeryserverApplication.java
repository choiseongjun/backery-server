package com.bakery.pj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackageClasses = BackeryserverApplication.class)
public class BackeryserverApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BackeryserverApplication.class, args);
	}
	@Override

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

    return application.sources(BackeryserverApplication.class);

  }



//	@Bean
//	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//		sessionFactory.setDataSource(dataSource);
//		
//		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*Mapper.xml");
//		sessionFactory.setMapperLocations(res);
//		
//		return sessionFactory.getObject();
//	}
}
