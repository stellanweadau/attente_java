package io.github.oliviercailloux.y2018.apartments.apartment;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import static com.google.common.base.Preconditions.checkArgument;

import io.github.oliviercailloux.y2018.apartments.apartment.json.JsonConvert;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class ApartmentStatitics {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Apartment.class);
	final static HashSet<String> listOfFeatures = new HashSet<>(Arrays.asList("address","description","floorArea","floorAreaTerrace","nbBathrooms","nbBedrooms","nbMinNight","nbSleeping","pricePerNight","tele","terrace","title","wifi"));
	static List<Apartment> listOfApartments = JsonConvert.getDefaultApartments();

	/**
	 * <p>
	 * Create a Map able to store the name of a criteria and its statistics.
	 * </p>
	 * <p>
	 * <em>Note </em>: the first character of the criteria must be in lower case.
	 * </p>
	 * @param featureName : The name of the criteria thats we want to extract statistics. 
	 * @return A Map (size 1) that has the name of the feature as its entry, and its statistics through a DoubleSummaryStatistics as values of the Map.
	 */
	
	public static HashMap<String, DoubleSummaryStatistics> generateNumericStatitics(String featureName) {

		checkArgument(listOfFeatures.contains(featureName));
	    LOGGER.info("{} is part of our criteria",featureName);
	    
	    HashMap<String, DoubleSummaryStatistics> criteriaStats = new HashMap<>(1);
	    
		switch(featureName) {
		
		case "floorArea" : {
			List <Double> floorAreaStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				floorAreaStats.add((double) Math.round(listOfApartments.get(i).getFloorArea()* 100.0) / 100.0);
			}
			criteriaStats.put(featureName, getNumericStatistics(floorAreaStats));
		    LOGGER.info("Floor area statistics are available :\n");
			return criteriaStats;
		}
		
		case "floorAreaTerrace" : {
			List <Double> floorAreaTerraceStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				floorAreaTerraceStats.add((double) Math.round(listOfApartments.get(i).getFloorAreaTerrace()* 100.0) / 100.0);
			}		    
			criteriaStats.put(featureName, getNumericStatistics(floorAreaTerraceStats));
			LOGGER.info("Floor area terrace statistics are available :\n");
			return criteriaStats;
		}
		
		case "nbBathrooms" : {
			List <Double> nbBathroomsStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				nbBathroomsStats.add(Double.valueOf(listOfApartments.get(i).getNbBathrooms()));
			}
			criteriaStats.put(featureName,getNumericStatistics(nbBathroomsStats));
			LOGGER.info("Floor area terrace statistics are available :\n");
			return criteriaStats;
		}
		
		case "nbBedrooms" : {
			List <Double> nbBedroomsStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				nbBedroomsStats.add(Double.valueOf(listOfApartments.get(i).getNbBedrooms()));
			}
			criteriaStats.put(featureName,getNumericStatistics(nbBedroomsStats));
			LOGGER.info("Number of bedrooms statistics are available  :\n");
			return criteriaStats;
		}
		
		case "nbMinNight" : {
			List <Double> nbMinNightStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				nbMinNightStats.add(Double.valueOf(listOfApartments.get(i).getNbMinNight()));
			}
		    LOGGER.info("The requested minimum number of night statistics are available :\n");
			criteriaStats.put(featureName,getNumericStatistics(nbMinNightStats));
			return criteriaStats;
		}
		
		case "nbSleeping" : {
			List <Double> nbSleepingStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				nbSleepingStats.add(Double.valueOf(listOfApartments.get(i).getNbSleeping()));
			}
			criteriaStats.put(featureName,getNumericStatistics(nbSleepingStats));
		    LOGGER.info("The number of sleepings statistics are available :\n");
			return criteriaStats;
		}
		
		case "pricePerNight" : {
			List <Double> pricePerNightStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				pricePerNightStats.add((double) Math.round(listOfApartments.get(i).getPricePerNight()* 100.0) / 100.0);
			}
			criteriaStats.put(featureName,getNumericStatistics(pricePerNightStats));
		    LOGGER.info("The price per night statistics are available :\n");
			return criteriaStats;
		}
		
        default: 
		    LOGGER.info("\n{} isn't a Apartment feature",featureName);
			return null;
		}
		
	}
	

	/**
	 * <p>
	 * Create a DoubleSummaryStatistics able to propose severals statistics about the double data provided in parameter.
	 * </p>
	 * <p>
	 * More information about this library in : <em>https://www.concretepage.com/java/jdk-8/java-8-summary-statistics-example</em>.
	 * </p>
	 * @param listOfDoubles : a list of double  
	 */
	
	private static DoubleSummaryStatistics getNumericStatistics(List<Double> listOfDoubles) {
	
	    DoubleSummaryStatistics doubleStatistics = new DoubleSummaryStatistics();
        Iterator<Double> stat = listOfDoubles.listIterator();
        while (stat.hasNext()) {
            doubleStatistics.accept(stat.next());
        }
        return doubleStatistics;
	}
	
	/**
	 * <p>
	 * Create the Map that counts the occurrence of each boolean 
	 * </p>
	 * @param featureName : The name of the criteria thats we want to extract statistics. 
	 */
	public static ImmutableMap<Boolean, Integer> generateBooleanStatitics(String featureName) {
		
		checkArgument(listOfFeatures.contains(featureName));
	    LOGGER.info("{} is part of our criteria",featureName);

		HashMap <Boolean, Integer> results = new HashMap<>();
		int yes = 0;
		int no = 0;
		
		switch(featureName) {

			case "tele" : {
				for (int i=0; i< listOfApartments.size(); i++) {
					if (listOfApartments.get(i).getTele()) {
						yes++;
					} else {
						no++;
					}		
				}
			    LOGGER.info("The tele presence statistics are available :\n");
			}
			case "terrace" : {
				for (int i=0; i< listOfApartments.size(); i++) {
					if (listOfApartments.get(i).getTerrace()) {
						yes++;
					} else {
						no++;
					}		
				}
			    LOGGER.info("The terrace presence statistics are available :\n");
			}
			case "wifi" : {
				for (int i=0; i< listOfApartments.size(); i++) {
					if (listOfApartments.get(i).getTerrace()) {
						yes++;
					} else {
						no++;
					}		
				}
			    LOGGER.info("The wifi presence statistics are available :\n");
			}
			results.put(true,yes);
			results.put(false,no);	
			}
			
		return ImmutableMap.copyOf(results);
		
	}
	
	
	/**
	 * <p>
	 * Return the following statistics : the number of instances, its minimum, average and maximum.
	 * </p>
	 * @param A Map (size 1) that has the name of the feature as its entry, and its statistics through a DoubleSummaryStatistics as values of the Map.
	 * 
	 * @return a short string describing this instance.
	 */
	public static String displayNumericStatistics(HashMap<String, DoubleSummaryStatistics> dataMap) {
		
		Entry<String, DoubleSummaryStatistics> firstEntry = dataMap.entrySet().iterator().next();
		String feature = firstEntry.getKey();
		DoubleSummaryStatistics dss = firstEntry.getValue();
		LOGGER.info("\n\nSome statistics about {} :",feature);
		 
	    return ("\nNumber of apartments : "+dss.getCount()+
			    "\nMinimum : "+dss.getMin()+
			    "\nMaximum : "+dss.getMax()+
			    "\nAverage : "+dss.getAverage()+
			    "\n");
	}
	
	/**
	 * <p>
	 * Return the total occurrence of the criteria for all apartments.
	 * </p>
	 * @param A Map (size 1) that has the name of the feature as its entry, and its statistics through a DoubleSummaryStatistics as values of the Map.
	 * 
	 * @return a short string describing this instance.
	 */
	public static String displayBooleanStatistics(ImmutableMap<Boolean, Integer> criteriaStats) {
		
		return ("\nTrue : "+criteriaStats.get(true)+
			   "\nFalse : "+criteriaStats.get(false)+
			   "\n");
	}

	public static void main(String[] args) {

		HashMap<String, DoubleSummaryStatistics> nbBedroomsStats = generateNumericStatitics("nbBedrooms");
		ImmutableMap<Boolean, Integer> teleStats = generateBooleanStatitics("tele");
		
		System.out.println(displayBooleanStatistics(teleStats));
		System.out.println(displayNumericStatistics(nbBedroomsStats));
		
		System.out.println(displayNumericStatistics(generateNumericStatitics("nbMinNight")));
		System.out.println(displayNumericStatistics(generateNumericStatitics("floorAreaTerrace")));
		System.out.println(displayBooleanStatistics(generateBooleanStatitics("wifi")));

		// créer fonction pour std et median
		// voir diagramme en barres
		// retirer valeur null
	}

}
