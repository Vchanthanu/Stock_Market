package com.stockmarket.stockprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "StockPrice-API", version = "2.0", description = "StockPrice Details"))
@SpringBootApplication
public class StockpriceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockpriceApplication.class, args);
	}

}
