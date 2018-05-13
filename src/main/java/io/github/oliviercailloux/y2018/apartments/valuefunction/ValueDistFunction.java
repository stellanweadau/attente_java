package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaFileManager.Location;

import com.google.maps.errors.ApiException;

import io.github.oliviercailloux.y2018.apartments.distance.DistanceSubway;

public class ValueDistFunction implements PartialValueFunction<Location> {
	
	private Map<String, Double> interestlocation;
	private String api_key;
	private Location appartlocation;
	private double currentdistance;
	private double maxDuration;
	
	
	/**
	 * 
	 * @param appartlocation Object Location which represents the apartment location.
	 */
	public ValueDistFunction(Location appartlocation){
		interestlocation = new HashMap<>();
		api_key="";
		this.appartlocation = appartlocation;
		maxDuration = 0;
	}
	
	/**
	 * Add the apartment location and its utility to the HashMap.
	 * @param interest Object Location of an interest place of the user.
	 */
	public void addInterestLocation(Location interest) {
		double utility = getSubjectiveValue(interest);
		interestlocation.put(interest.getName(), utility);
	}
	
	public double getMaxDuration() {
		return maxDuration;
	}
	
	/**
	 * Update the current distance between the apartment and an interest place.
	 * @param interest Object Location of an interest place of the user.
	 */
	public void calculateDistanceLocation(Location interest) throws ApiException, InterruptedException, IOException {
		DistanceSubway dist = new DistanceSubway(api_key,interest.getName(),appartlocation.getName());
		currentdistance = dist.calculateDistance();
		if (currentdistance > maxDuration)
			maxDuration = currentdistance;

	}

	@Override
	public double getSubjectiveValue(Location objectiveData) {
		LinearValueFunction f = new LinearValueFunction(0,10);
		return f.getSubjectiveValue(currentdistance);
	}
	
	@Override
	public Double apply(Location objectiveData) {
		return getSubjectiveValue(objectiveData);
	}

}
