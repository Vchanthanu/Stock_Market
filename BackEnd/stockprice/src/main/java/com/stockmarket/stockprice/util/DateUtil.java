package com.stockmarket.stockprice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	public Date getDate(String dateString, int day) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = formatter.parse(dateString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, day);
			return formatter.parse(formatter.format(calendar.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

}
