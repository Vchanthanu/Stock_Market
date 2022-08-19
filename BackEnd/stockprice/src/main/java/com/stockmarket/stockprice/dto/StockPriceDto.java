package com.stockmarket.stockprice.dto;

import java.util.List;

import com.stockmarket.mongo.model.StockPriceDetails;

public class StockPriceDto {
	List<StockPriceDetails> priceList;
	float min;
	float max;
	float average;

	public List<StockPriceDetails> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<StockPriceDetails> priceList) {
		this.priceList = priceList;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}

}
