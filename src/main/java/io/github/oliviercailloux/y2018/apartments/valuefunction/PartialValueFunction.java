package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.function.Function;


public interface PartialValueFunction<T> extends Function<T, Double> {
	
	@Override
	public String toString();
	
	public double getSubjectiveValue(T objectiveData);
	 
	

}