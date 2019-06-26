package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties {

	private Properties properties;

	private final static Logger LOGGER = LoggerFactory.getLogger(XMLProperties.class);

	public XMLProperties() {
		this.properties = new Properties();
	}

	/**
	 * toXml transform an Apartment into an xml File. The user specify the file in
	 * parameter
	 * 
	 * @param a       the apartment to put into an xml file
	 * @param xmlFile an file object where the apartment will be store. Warning : if
	 *                the file already exists, it will be erased.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void toXML(Apartment a, OutputStream xmlFile)
			throws IOException, IllegalArgumentException, IllegalAccessException {

		for (Field f : a.getClass().getDeclaredFields()) {

			String[] fullName = f.toString().split(" ")[2].split("\\.");

			f.setAccessible(true);
			properties.setProperty(fullName[fullName.length - 1], f.get(a).toString());

			LOGGER.info("Adding entry : " + fullName[fullName.length - 1] + " : " + f.get(a));

		}
		properties.remove("apartment");
		properties.remove("LOGGER");
		properties.remove("Logger");
		properties.storeToXML(xmlFile, "Generated file for the apartment " + a.getTitle());

		LOGGER.info("Stream has been closed");

	}

	/**
	 * Generates an object representing random apartments.
	 * Means and standard deviations were found in the following sources:
	 * https://www.airbnb.fr
	 * https://www.logisneuf.com/statistique-immobiliere.html
	 * https://www.seloger.com/prix-de-l-immo/location/pays/france.htm
	 */
	public static Apartment generateRandom() {

		LOGGER.info("Begining of random generation of an Apartment");

		final ImmutableList<String> titles = ImmutableList.of("Location Apartement 1223", "Location Apartement 2434",
				"Location Apartement 4353", "Location Apartement 3423", "Location Apartement 4234",
				"Location Apartement 3424", "Location Apartement 3477", "Location Apartement 376",
				"Location Apartement 678", "Location Apartement 757");
		final ImmutableList<String> address = ImmutableList.of("2 avenue Pasteur 94160 Saint-mandé",
				"8 avenue de Paris 94160 Saint-mandé", "5 avenue des Champs-Elysées 75016", "13 rue des Arts 75001",
				"10 rue de Dauphine 75016", "33 rue de Tolbiac 75013", "33 rue de Tolbiac 75013", " ", " ", " ");

		int n = (int) (Math.random() * titles.size()-1);
		int m = (int) (Math.random() * address.size()-1);

		double floorArea = simulateRandomDraw(65d, 21d);
		
		boolean terrace = (Math.random() * 2 >= 1) ? true : false;
		double floorAreaTerrace = (terrace) ? simulateRandomDraw(15d, 2d) : 0;
		
		int nbMinNight = (int) (Math.random() * 8);
		
		int averageRoomArea = (int) (10 + (Math.random() * 20));
		int nbBedrooms = Math.max(((int) (floorArea / averageRoomArea)) - 1, 0);
		
		double pricePerMeterSquared = simulateRandomDraw(8d, 3d);
		double pricePerNight = floorArea * pricePerMeterSquared;
		
		int nbSleeping = ((int) (1 + Math.random() * 4)) * nbBedrooms;
		
		int nbBathrooms = (int) (Math.random() * nbBedrooms);

		Apartment.Builder builder = new Apartment.Builder();

		builder.setFloorArea(floorArea);
		builder.setAddress(address.get(m));
		builder.setTitle(titles.get(n));
		builder.setNbBedrooms(nbBedrooms);
		builder.setNbSleeping(nbSleeping);
		builder.setNbBathrooms(nbBathrooms);
		builder.setTerrace(terrace);
		builder.setPricePerNight(pricePerNight);
		builder.setNbMinNight(nbMinNight);
		builder.setFloorAreaTerrace(floorAreaTerrace);

		Apartment a = builder.build();

		LOGGER.info("Generation done successfully");

		return a;
	}
	
	/**
	 * This function simulates a random draw of a variable, being given its expectation and its deviation.
	 * @param mean the mathematical expectation of the variable
	 * @param deviation the standard deviation of the variable
	 * @return an outcome of the randomized experiment 
	 */
	private static double simulateRandomDraw(double mean, double deviation) {
		double draw = new Random().nextGaussian();
		return deviation * draw + mean;
	}

	/**
	 * This is the main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		generateRandom();
	}
}
