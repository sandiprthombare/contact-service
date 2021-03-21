package com.evolent.contact;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author Sandip
 *
 */
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactServiceApplication.class, args);
	}
	
	@Bean
	public DozerBeanMapper beanMapper() {
		return new DozerBeanMapper();
	}
	

}
