package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.errors.ApiException;

import io.github.oliviercailloux.y2018.apartments.distance.DistanceSubway;
import io.github.oliviercailloux.y2018.apartments.localize.Location;

/**
 * This class enables the user to calculate the utility of a location by linear interpolation,
 * to have the maximum duration between the interest places.
 */
public class ValueDistFunction implements PartialValueFunction<Location> {
	
	private Map<Location, Double> interestlocation;
	private Location appartlocation;
	private double maxDuration;
	private final static Logger LOGGER = LoggerFactory.getLogger(ValueDistFunction.class);
	
	
	/**
	 * Initializes the different variables of the ValueDistFunction class.
	 * @param appartlocation Object Location which represents the apartment location.
	 */
	public ValueDistFunction(Location appartlocation){
		interestlocation = new HashMap<>();
		this.appartlocation = appartlocation;
		maxDuration = 0;
	}
	
	/**
	 * Add the apartment location and its utility to the HashMap and update the variable maxDuration.
	 * @param interest Object Location of an interest place of the user.
	 * @throws Exception 
	 */
	public void addInterestLocation(Location interest) throws Exception {
		double currentdistance = calculateDistanceLocation(interest);
		if (currentdistance > maxDuration)
			maxDuration = currentdistance;
		double utility = 1-setUtility(currentdistance);
		interestlocation.put(interest, utility);
		LOGGER.info("The interest location ("+interest.getCoordinate()+") with the utility "+utility+" has been had with success in the Map.");
	}
	
	/**
	 * 
	 * @return a double which corresponds to the maximum of the duration between an interest place and the apartment.
	 */
	public double getMaxDuration() {		
		return maxDuration;
	}
	
	/**
	 * 
	 * @param interest
	 * @return double number  which corresponds to the distance (seconds) between the Location appartocation and the Location interest in parameter.
	 * @throws Exception 
	 */
	public double calculateDistanceLocation(Location interest) throws Exception {
		DistanceSubway dist = new DistanceSubway(interest.getCoordinate(),appartlocation.getCoordinate());
		double currentdistance = dist.calculateDistanceAddress(DistanceMode.COORDINATE);
		LOGGER.info("The distance between "+interest.getCoordinate()+" and "+appartlocation.getCoordinate()+" has been calculated and is equal to "+ currentdistance);
		return currentdistance;

	}
	
	/**
	 * 
	 * @param currentdistance double distance in seconds.
	 * @return a double corresponding to the utility of the distance.
	 */
	public double setUtility(double currentdistance) {
		LinearValueFunction f = new LinearValueFunction(0,36000);
		return f.getSubjectiveValue(currentdistance);
	}

	@Override
	public double getSubjectiveValue(Location objectiveData) {
		return interestlocation.get(objectiveData);
	}
	
	@Override
	public Double apply(Location objectiveData) {
		return getSubjectiveValue(objectiveData);
	}

}
