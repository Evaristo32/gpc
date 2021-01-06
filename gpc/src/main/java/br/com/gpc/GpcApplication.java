package br.com.gpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GpcApplication.class, args);
	}

}
