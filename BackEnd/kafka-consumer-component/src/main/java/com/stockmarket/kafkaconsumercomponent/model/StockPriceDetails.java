package com.stockmarket.kafkaconsumercomponent.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "StockPrice")
public class StockPriceDetails {
	@JsonIgnore
	@Id
	private String id;
	public String companyCode;
	private Date priceUpdatedDate;
	private float stockPrice;
	private StockExchange stockExchange;

	public float getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(float stockPrice) {
		this.stockPrice = stockPrice;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Date getPriceUpdatedDate() {
		return priceUpdatedDate;
	}

	public void setPriceUpdatedDate(Date priceUpdatedDate) {
		this.priceUpdatedDate = priceUpdatedDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
