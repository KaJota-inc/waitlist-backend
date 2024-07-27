package com.kajota.kajota_webpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication
public class KajotaWebpageApplication {

	public static void main(String[] args) {
		SpringApplication.run(KajotaWebpageApplication.class, args);
	}

}
