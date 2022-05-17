package com.stockmarket.stockprice.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "company")
public class Company {

	@Id
	@NotBlank(message = "code cannot be blank")
	@Column(name = "co_code")
	public String code;
	@NotBlank(message = "name cannot be blank")
	@Column(name = "co_name")
	public String name;
	@NotBlank(message = "ceo cannot be blank")
	@Column(name = "co_ceo")
	public String ceo;
	@NotNull(message = "turnover cannot be empty")
	@Column(name = "co_turnover")
	public int turnover;
	@NotBlank(message = "website cannot be blank")
	@Column(name = "co_website")
	public String website;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	public List<StockPrice> stockPrice;

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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public List<StockPrice> getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(List<StockPrice> stockPrice) {
		this.stockPrice = stockPrice;
	}

}
