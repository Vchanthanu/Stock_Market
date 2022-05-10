package com.stockmarket.company.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "company_stock_exchange")
public class StockPrice {

	@EmbeddedId
	CompanyStockExchangePk id;
	@Column(name = "cse_stock_price")
	private float stockPrice;

	@ManyToOne
	@JsonBackReference(value = "stockPriceCompany")
	@MapsId("companyCode")
	@JoinColumn(name = "cse_co_code")
	public Company company;

	@ManyToOne
	@JsonBackReference(value = "stockPricestockExchange")
	@MapsId("stockExchangeCode")
	@JoinColumn(name = "cse_se_code")
	private StockExchange stockExchange;

	public CompanyStockExchangePk getId() {
		return id;
	}

	public void setId(CompanyStockExchangePk id) {
		this.id = id;
	}

	public float getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(float stockPrice) {
		this.stockPrice = stockPrice;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

}
