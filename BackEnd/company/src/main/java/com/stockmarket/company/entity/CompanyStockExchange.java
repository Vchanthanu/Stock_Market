package com.stockmarket.company.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompanyStockExchange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2615503081236176477L;
	@Column(name = "cse_co_code")
	public String companyCode;
	@Column(name = "cse_se_code")
	private String stockExchangeCode;
	@Column(name = "cse_price_date")
	private LocalDateTime priceUpdatedDate;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getStockExchangeCode() {
		return stockExchangeCode;
	}

	public void setStockExchangeCode(String stockExchangeCode) {
		this.stockExchangeCode = stockExchangeCode;
	}

	public LocalDateTime getPriceUpdatedDate() {
		return priceUpdatedDate;
	}

	public void setPriceUpdatedDate(LocalDateTime priceUpdatedDate) {
		this.priceUpdatedDate = priceUpdatedDate;
	}

}
