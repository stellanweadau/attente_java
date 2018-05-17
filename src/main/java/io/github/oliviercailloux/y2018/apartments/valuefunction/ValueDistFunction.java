package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaFileManager.Location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.errors.ApiException;

import io.github.oliviercailloux.y2018.apartments.distance.DistanceSubway;

/**
 * This class enables the user to calculate the utility of a location by linear interpolation,
 * to have the maximum duration between the interest places.
 */
public class ValueDistFunction implements PartialValueFunction<Location> {
	
	private Map<Location, Double> interestlocation;
	private String api_key;
	//private String geocode_api_key;
	private Location appartlocation;
	private double maxDuration;
	private Logger valueDistFunction = LoggerFactory.getLogger(ValueDistFunction.class);
	
	
	/**
	 * Initializes the different variables of the ValueDistFunction class.
	 * @param appartlocation Object Location which represents the apartment location.
	 */
	public ValueDistFunction(Location appartlocation, String api_key/*, String api_geocode_key*/){
		interestlocation = new HashMap<>();
		this.api_key=api_key;
		//this.geocode_api_key = api_geocode_key;
		this.appartlocation = appartlocation;
		maxDuration = 0;
	}
	
	/**
	 * Add the apartment location and its utility to the HashMap.
	 * @param interest Object Location of an interest place of the user.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ApiException 
	 */
	public void addInterestLocation(Location interest) throws ApiException, InterruptedException, IOException {
		double utility = setUtility(calculateDistanceLocation(interest));
		interestlocation.put(interest, utility);
		valueDistFunction.info("The interest location "+interest.getName()+" has been had with success in the Map.");
	}
	
	/**
	 * 
	 * @return a double which corresponds to the maximum of the duration between an interest place and the apartment.
	 */
	public double getMaxDuration() {		
		return maxDuration;
	}
	
	/**
	 * Update the current distance between the apartment and an interest place.
	 * @param interest Object Location of an interest place of the user.
	 */
	public double calculateDistanceLocation(Location interest) throws ApiException, InterruptedException, IOException {
		DistanceSubway dist = new DistanceSubway(api_key,/*geocode_api_key,*/interest.getName(),appartlocation.getName());
		double currentdistance = dist.calculateDistanceAddress(DistanceMode.ADDRESS);
		valueDistFunction.info("The current distance between the interest place and the apartment has been updated.");
		if (currentdistance > maxDuration)
			maxDuration = currentdistance;
		valueDistFunction.info("The distance between "+interest.getName()+" and "+appartlocation.getName()+" has been calculated and is equal to "+ currentdistance);
		return currentdistance;

	}
	
	/**
	 * 
	 * @param currentdistance double distance in hours.
	 * @return a double corresponding to the utility of the distance.
	 */
	public double setUtility(double currentdistance) {
		LinearValueFunction f = new LinearValueFunction(0,10);
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
