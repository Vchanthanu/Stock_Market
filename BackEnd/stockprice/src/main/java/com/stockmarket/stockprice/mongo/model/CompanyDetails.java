package com.stockmarket.stockprice.mongo.model;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Company")
public class CompanyDetails {
	@JsonIgnore
	@Id
	private String id;
	public String code;
	public String name;
	public String ceo;
	public int turnover;
	public List<StockPriceDetails> stockPrice;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCeo() {
		return ceo;
	}

	public void setCeo(String ceo) {
		this.ceo = ceo;
	}

	public int getTurnover() {
		return turnover;
	}

	public void setTurnover(int turnover) {
		this.turnover = turnover;
	}

	public List<StockPriceDetails> getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(List<StockPriceDetails> stockPrice) {
		this.stockPrice = stockPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
