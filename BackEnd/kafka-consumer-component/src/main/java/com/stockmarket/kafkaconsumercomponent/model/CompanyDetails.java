package com.stockmarket.kafkaconsumercomponent.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "Company")
public class CompanyDetails {
	@JsonIgnore
	@Id
	private String id;
	private String code;
	private String name;
	private String ceo;
	private int turnover;
	@Transient
	private List<StockPriceDetails> stockPrice;
	@JsonIgnore
	private String requestType;

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

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

}
