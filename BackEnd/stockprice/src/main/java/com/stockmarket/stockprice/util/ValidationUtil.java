package com.stockmarket.stockprice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.stockmarket.stockprice.exception.InvalidInputDataException;

@Component
public class ValidationUtil {
	public void validateGetStockPriceRequest(String companyCode, String stockExchangeCode, String fromDate,
			String toDate) {
		if (StringUtils.isBlank(companyCode)) {
			throw new InvalidInputDataException("companyCode cannot be blank");
		} else if (StringUtils.isBlank(stockExchangeCode)) {
			throw new InvalidInputDataException("stockExchangeCode cannot be blank");
		} else if (StringUtils.isBlank(fromDate)) {
			throw new InvalidInputDataException("fromDate cannot be blank");
		} else if (StringUtils.isBlank(toDate)) {
			throw new InvalidInputDataException("toDate cannot be blank");
		} else if (StringUtils.isNotBlank(fromDate) && !isValidDate(fromDate)) {
			throw new InvalidInputDataException("fromDate should be in yyyy-MM-dd format");
		} else if (StringUtils.isNotBlank(toDate) && !isValidDate(toDate)) {
			throw new InvalidInputDataException("toDate should be in yyyy-MM-dd format");
		}
	}

	private boolean isValidDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		try {
			if (formatter.parse(date) != null)
				return true;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;

	}
}
