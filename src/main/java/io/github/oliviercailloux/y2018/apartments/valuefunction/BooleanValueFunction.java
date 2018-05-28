package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.Map;
import static com.google.common.base.Preconditions.checkArgument;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class which enables the user to get the subjective value of a Boolean given in argument.
 * 
 */
public class BooleanValueFunction implements PartialValueFunction<Boolean>{
	private Map<Boolean, Double> subjective;
	private final static Logger LOGGER = LoggerFactory.getLogger(BooleanValueFunction.class);


	/**
	 * Create a map which associate a subjective value to true and a subjective value to false
	 * @param valueForTrue a double between 0 and 1 that correspond to the importance accorded to the value true
	 * @param valueForFalse a double between 0 and 1 that correspond to the importance accorded to the value false
	 */
	public BooleanValueFunction (double valueForTrue, double valueForFalse) {


		checkArgument(valueForTrue <= 1 || valueForTrue >= 0 , "The subjective value associated to true has to be between 0 and 1");
		checkArgument(valueForFalse <= 1 || valueForFalse >= 0 , "The subjective value associated to false has to be between 0 and 1");

		subjective = new HashMap<>();
		subjective.put(true, valueForTrue);
		subjective.put(false, valueForFalse);
		LOGGER.info("The Map with the subjective values for true and false has been set with success in the BooleanValueFunction class.");
	}



	@Override
	public Double apply(Boolean objectiveData) {
		return subjective.get(objectiveData);
	}

	@Override
	public double getSubjectiveValue(Boolean objectiveData) throws IllegalArgumentException {
		return subjective.get(objectiveData);
	}

	public double getMax() {
		double valueForTrue = subjective.get(true);
		double valueForFalse = subjective.get(false);
		if (valueForTrue > valueForFalse) {
			return valueForTrue;
		}
		else if(valueForFalse>= valueForTrue) {
			return valueForFalse;	
		}
		else {
			return 0 ;
		}


	}




}
