package io.github.oliviercailloux.y2018.apartments.valuefunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.valuefunction.PartialValueFunction;

/**
 * The public class ValueFunction enables the creation of an object with two instances of PartialValueFunction, one of String type and one of Double type
 * and two objective value of the respective types. This object computes those objective data through the PartialValueFunction associated and make the sum of
 * the subjective values obtained. 
 * This class enables to know the subjective value of two criteria, one whose the type is String and an other which is a double. 
 * 
 * 
 *
 */
public class ValueFunction {
	
	private double value;
	static Logger valueFunction = LoggerFactory.getLogger(ValueFunction.class);

	
	/**
	 * Constructor of the object {@link ValueFunction}
	 * @param partialValueString A PartialValue object which enables to know the subjective value of a String objective data
	 * @param partialValueDouble A PartialValue object which calculates the subjective value of a objective data whose type is double
	 * @param objDataString A String that represent the objective data from which we want to know the subjective value associated 
	 * @param objDataDouble A Double that represent the objective value from which we want to know the subjective value associated
	 */
	public ValueFunction(PartialValueFunction<String> partialValueString, PartialValueFunction<Double> partialValueDouble, String objDataString, double objDataDouble) throws IllegalArgumentException {
		if (partialValueString.getSubjectiveValue(objDataString)<0 || partialValueString.getSubjectiveValue(objDataString)>1) {
			valueFunction.debug("The subjective value of "+ objDataString+" should be between 0 and 1");
			throw new IllegalArgumentException("The subjective value of "+ objDataString+" should be between 0 and 1");
		}
		double subjDataForString = partialValueString.getSubjectiveValue(objDataString); 
		valueFunction.info("Subjective value of " + objDataString + " computed");
		
		if (partialValueDouble.getSubjectiveValue(objDataDouble)<0 || partialValueDouble.getSubjectiveValue(objDataDouble)>1) {
			valueFunction.debug("The subjective value of "+ objDataDouble+" should be between 0 and 1");
			throw new IllegalArgumentException("The subjective value of "+ objDataDouble+" should be between 0 and 1");
		}
		valueFunction.info("Subjective value of " + objDataDouble + " computed");
		double subjDataForDouble = partialValueDouble.getSubjectiveValue(objDataDouble);
		value = subjDataForDouble + subjDataForString;
	}
	
	/**
	 * This function enables to send the value of a ValueFunction object
	 * @return a double which is the subjective value associated to the two criteria that the ValueFunction object has taken in parameters
	 */
	public double getValue() {
		return value;
	}

}
