package com.lithium.esb.compliance.util;

/**
 * Utility to add 0 padder in front of the month.
 * The representation of the HDFS folder format is using 01,02 etc instead of 1,2 for the month.
 * Java month of year returns int values with no zero padding and hence this util.
 */
public class DateMonthZeroPadder {

	/**
	 * Padd with zeros for month.
	 *
	 * @param monthOfTheYear the month of the year
	 * @return the string
	 */
	public static String paddWithZerosForMonth(int monthOfTheYear) {
		if (monthOfTheYear < 10)
			return "0" + monthOfTheYear;
		else
			return "" + monthOfTheYear;
	}
}
