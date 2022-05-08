package com.stockmarket.company.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "stock_exchange")
public class StockExchange {
	@Id
	@Column(name = "se_code")
	private String code;
	@NotBlank
	@Column(name = "se_name")
	private String name;

	@OneToMany(mappedBy = "stockExchange")
	public List<StockPrice> stockPrice;

	public String getSe_code() {
		return code;
	}

	public void setSe_code(String code) {
		this.code = code;
	}

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

	public List<StockPrice> getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(List<StockPrice> stockPrice) {
		this.stockPrice = stockPrice;
	}

}
