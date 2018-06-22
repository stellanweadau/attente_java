package io.github.oliviercailloux.y2018.apartments.valuefunction;

/**
 * Class that associates the subjective value of a given parameter to 0.
 *
 * @param <T> the type of the parameter
 */

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
