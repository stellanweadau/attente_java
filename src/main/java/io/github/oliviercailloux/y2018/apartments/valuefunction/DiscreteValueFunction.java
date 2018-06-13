package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

/**
 * Class which enables the user to get the subjective value of a String given in argument.
 *
 */
public class DiscreteValueFunction<T> implements PartialValueFunction<T> {

	private final ImmutableMap<T, Double> subjective;
	private final static Logger LOGGER = LoggerFactory.getLogger(DiscreteValueFunction.class);
	
	/**
	 * Create a map with 3 discrete values of the same type which are associated to their respective subjective values.
	 * @param s1 first key of type <b>T</b> which corresponds to the subjective value 0
	 * @param s2 second key of type <b>T</b> which corresponds to the subjective value 0.5
	 * @param s3 third key of type <b>T</b> which corresponds to the subjective value 1
	 */
	public DiscreteValueFunction(T s1, T s2, T s3) {
		if (s1 == s2 || s1 == s3 || s2 == s3) {
			LOGGER.error("The strings in input has to be different in the Discrete3ValueFunction class. The Map has not been set with success.");
			throw new IllegalArgumentException("The strings has to be different.");
		}
		subjective = ImmutableMap.of(s1, 0.0, s2, 0.5, s3, 1.0);
		LOGGER.info("The Map with the three strings has been set with success in the Discrete3ValueFunction class.");
	}

	/**
	 * Create a map with 2 discrete values of the same type which are associated to their respective subjective values.
	 * @param s1 first key of type <b>T</b> which corresponds to the subjective value 0
	 * @param s2 second key of type <b>T</b> which corresponds to the subjective value 1
	 */
	public DiscreteValueFunction(T s1, T s2) {
		if (s1 == s2) {
			LOGGER.error("The strings in input has to be different in the DiscreteValueFunction class. The Map has not been set with success.");
			throw new IllegalArgumentException("The strings has to be different.");
		}
		subjective = ImmutableMap.of(s1, 0.0, s2, 1.0);
		LOGGER.info("The Map with the two strings has been set with success in the DiscreteValueFunction class.");
	}
	
	/**
	 * Create a map that match the keys and the subjective values associated
	 * @param subjective a Map<T, Double> where the key is of type <b>T</b> and the associated value, a double between 0 and 1, defines its subjective value. 
	 */
	public DiscreteValueFunction(Map<T, Double> subjective) {
		this.subjective = ImmutableMap.copyOf(subjective);
		Stream<Entry<T, Double>> erreur  = this.subjective.entrySet().stream().filter((entry)-> entry.getValue() < 0 || entry.getValue() > 1);
		Map<T,Double> mapError =  erreur.collect(Collectors.toMap((entry)->entry.getKey(), (entry) -> entry.getValue()));
		if (! (mapError.isEmpty() ) ) {
			throw new IllegalArgumentException("The subjective values must be between 0 and 1. The wrong values are : " + mapError );
			
		}
	}
	
	@Override
	public Double apply(T objectiveData) {
		return subjective.get(objectiveData);
	}

	@Override
	public double getSubjectiveValue(T objectiveData) throws IllegalArgumentException {
		return subjective.get(objectiveData);
	}


}

