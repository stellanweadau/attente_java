package io.github.oliviercailloux.y2018.apartments.valuefunction;

import io.github.oliviercailloux.y2018.apartments.valuefunction.PartialValueFunction;
public class ValueFunction {
	
	private double value;
	
	public ValueFunction(PartialValueFunction<String> partialValueString, PartialValueFunction<Double> partialValueDouble, String objDataString, double objDataDouble) {
		double subjDataForString = partialValueString.getValue(objDataString); 
		double subjDataForDouble = partialValueDouble.getValue(objDataDouble);
		value = subjDataForDouble + subjDataForString;
	}
	public double getValue() {
		return value;
	}

}
