package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.function.Function;

public interface PartialValueFunction<T> extends Function<T, Double> {
	
	@Override
	public String toString();
	
	/**
	 * This function enables to get the subjective value of an objective data
	 * @param objectiveData is the parameter of type T from which we want to get the subjective value 
	 * @return A double (between 0 and 1) that represent the subjective value of the parameter
	 * @throws IllegalArgumentException if the parameter type is not the one we expect 
	 */
	public double getSubjectiveValue(T objectiveData) throws IllegalArgumentException;
	 
	

}