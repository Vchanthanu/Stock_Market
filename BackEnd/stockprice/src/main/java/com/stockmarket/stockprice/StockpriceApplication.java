package com.stockmarket.stockprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "StockPrice-API", version = "1.0", description = "StockPrice Details"))
@SpringBootApplication
@EnableEurekaClient
public class StockpriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockpriceApplication.class, args);
	}

}
