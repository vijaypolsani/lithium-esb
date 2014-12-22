package com.lithium.esb.compliance.util;

public class DateMonthZeroPadder {

	public static String paddWithZerosForMonth(int monthOfTheYear) {
		if (monthOfTheYear < 10)
			return "0" + monthOfTheYear;
		else
			return "" + monthOfTheYear;
	}
}
