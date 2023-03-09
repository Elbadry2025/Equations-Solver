package org.example;

import java.math.BigDecimal;
import java.math.MathContext;

public class precisionFinder {
	public double precision(double num, int digits) {
		double result;
		BigDecimal resultBigDecimal = new BigDecimal(0);
		try {
			resultBigDecimal  = new BigDecimal(num);
		} catch (Exception e) {
			return resultBigDecimal.doubleValue();
		}
		resultBigDecimal = resultBigDecimal.round(new MathContext(digits));
		result = resultBigDecimal.doubleValue();
		return result;
	}
}
