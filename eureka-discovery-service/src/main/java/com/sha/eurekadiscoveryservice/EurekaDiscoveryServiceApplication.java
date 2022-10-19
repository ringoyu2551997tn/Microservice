package com.sha.eurekadiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// để chạy được máy chủ eruka cần vào file application cau hinh.
// sau đó ở đây cần thêm 2 chú thích: @SpringBootApplication và @EnableEurekaServer sau đó chạy main là dk
@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryServiceApplication.class, args);
	}

}
