package com.sha.microservicecousemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//B15 ta có @SpringbootApplication là thể hiện đây là project Springboot
//@EnableDiscoveryClien. là the hiện nó là một clien sẵn sàng kết nối với máy chủ eureka.
//@EnableFeignCliens . là thể hiện nó có liên lac nội bộ. ở đây là de liên lạc nội bộ với MicroserviceUserManagement
//B16 quay lại Course Controller để viết các request reponse khác.
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MicroserviceCouseManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCouseManagementApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer configurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
