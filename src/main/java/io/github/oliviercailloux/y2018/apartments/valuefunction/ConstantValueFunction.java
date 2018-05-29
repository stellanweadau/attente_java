package io.github.oliviercailloux.y2018.apartments.valuefunction;

public class ConstantValueFunction<T> implements PartialValueFunction<T> {

	@Override
	public Double apply(T objectiveData) {
		return getSubjectiveValue(objectiveData);
	}

	@Override
	public double getSubjectiveValue(T objectiveData) throws IllegalArgumentException {
		return 0;
	}
	

}
