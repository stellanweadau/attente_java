package io.github.oliviercailloux.y2018.apartments.apartment;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import io.github.oliviercailloux.y2018.apartments.utils.JsonConvert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A factory for creating Apartment objects.
 */
public abstract class ApartmentFactory {

	/**  This constant allow us to know the maximum number of times we should try to  reach the API address. */
	private static final int NB_MAX_RETRY = 5;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentFactory.class);

	private static Random rand = new Random();

	/** The URL of the API address. */
	private static final String URL_API_ADDRESS = "https://8n8iajahab.execute-api.us-east-1.amazonaws.com/default/RealRandomAdress";

	/**
	 * The function aims to build a new apartment when all the characteristics are known.
	 *
	 * @param floorArea <i>double</i> the floor area of the apartment built
	 * @param address <i>String</i> the address of the apartment built
	 * @param nbBedrooms <i>int</i> the number of bedrooms of the apartment built
	 * @param nbSleeping <i>int</i> the maximum number of people who can sleep in this apartment
	 * @param nbBathrooms <i>int</i> the number of bathrooms of the apartment built
	 * @param terrace <i>boolean</i> says whether the apartment has a terrace or not
	 * @param floorAreaTerrace <i>double</i> gives the size of the terrace
	 * @param description <i>String</i> a description of the apartment
	 * @param title <i>String</i> gives the title of the announce
	 * @param wifi <i>boolean</i> says whether the apartment has the wifi or not
	 * @param pricePerNight <i>double</i> gives the price per night spent in the apartment
	 * @param nbMinNight <i>int</i> indicate the number minimum of night to locate an apartment
	 * @param tele <i>boolean</i> says whether the apartment has a TV or not
	 * 
	 * @return <i>Apartment</i> the apartment built with the previous characteristics
	 */
	public static Apartment generateApartment(double floorArea, String address, int nbBedrooms, int nbSleeping, 
			int nbBathrooms, boolean terrace, double floorAreaTerrace, 
			String description, String title, boolean wifi, 
			double pricePerNight, int nbMinNight, boolean tele) {
		Builder apartBuilder = new Builder();
		return apartBuilder.setFloorArea(floorArea)
				.setAddress(address)
				.setNbBedrooms(nbBedrooms)
				.setNbSleeping(nbSleeping)
				.setNbBathrooms(nbBathrooms)
				.setTerrace(terrace)
				.setFloorAreaTerrace(floorAreaTerrace)
				.setDescription(description)
				.setTitle(title)
				.setWifi(wifi)
				.setPricePerNight(pricePerNight)
				.setNbMinNight(nbMinNight)
				.setTele(tele)
				.build();
	}

	/**
	 * This function aims to generate a new apartment with random characteristics.
	 *
	 * @return <i>Apartment</i> the apartment built
	 */
	public static Apartment generateRandomApartment() {

		double floorArea = simulateRandomDraw(65d, 21d);
		String address = getRandomAddress();
		int averageRoomArea = 10 + rand.nextInt(20);
		int nbBedrooms = Math.max(((int) (floorArea / averageRoomArea)) - 1, 1);
		int nbSleeping = (1 + rand.nextInt(4)) * nbBedrooms;
		int nbBathrooms = 1 + rand.nextInt(nbBedrooms);
		boolean terrace = Math.random() >= 0.5;
		double floorAreaTerrace = (terrace) ? simulateRandomDraw(15d, 2d) : 0;
		String title = "Location Apartement "+ rand.nextInt(10000);
		String description = "This apartment has " + nbBedrooms + " bedrooms and a size of " + floorArea + "square meters";
		boolean wifi = Math.random() >= 0.5;
		double pricePerNight = floorArea * simulateRandomDraw(11d, 3d);
		boolean tele = Math.random() >= 0.5;
		int nbMinNight = rand.nextInt(700) + 1;
		return generateApartment(floorArea, address, nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, title, 
				wifi, pricePerNight, nbMinNight, tele);
	}

	/**
	 * This function aims to generate a list of random apartments.
	 *
	 * @param nbApartment <i>int</i> the number of apartments the list should contains
	 * @return <i>ArrayList</i> a list of random apartments of size nbApartment
	 */
	public static ArrayList<Apartment> generateRandomApartmentList(int nbApartment) {
		if(nbApartment <=0) {
			throw new IllegalArgumentException("You must indicate a number of apartments > 0");
		}
		ArrayList<Apartment> listApartment = new ArrayList<>();
		for(int i = 0; i < nbApartment ; i++) {
			listApartment.add(generateRandomApartment());
		}
		return listApartment;
	}

	/**
	 * This function simulates a random draw of a variable, being given its
	 * expectation and its deviation.
	 * 
	 * @param mean <i>double</i> gives the mathematical expectation of the variable
	 * @param deviation <i>double</i> is the standard deviation of the variable
	 * @return <i>double</i> an outcome of the randomized experiment
	 */
	private static double simulateRandomDraw(double mean, double deviation) {
		double draw = Math.abs(rand.nextGaussian());
		return deviation * draw + mean;
	}

	/**
	 * Call an API which generates a random address.
	 * This function aims at getting the random address generated.
	 *
	 * @param retry <i> int </i> indicates how many time the function has been called (Retry System).
	 * It will allow us to try escaping problems while contacting API (if it fails once, we try to call it
	 * again until the number specified in the class attribute NB_MAX_RETRY).
	 * @return <i>String</i> the address generated.
	 * @throws IOException if we cannot contact the API generator.
	 */
	private static String getRandomAddress(int retry) throws IOException {

		String address = "";
		//Code from https://www.developpez.net/forums/d1354479/java/general-java/recuperer-reponse-d-adresse-http/ 
		try(InputStream is = new URL(URL_API_ADDRESS).openConnection().getInputStream()) { 
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));   
			StringBuilder builder = new StringBuilder(); 
			for(String line = reader.readLine(); line != null; line = reader.readLine()) { 
				builder.append(line + "\n"); 
			} 
			String bodyContent = builder.toString();
			//End of code picking

			return JsonConvert.getAddressFromJson(bodyContent); 

		} catch (MalformedURLException e) {
			LOGGER.error("Problem while contacting address generator API",e);
		} catch (IOException e) {
			if(retry < NB_MAX_RETRY) {
				return getRandomAddress(retry+1);
			}
			else {
				throw e;
			}
		}
		return address;
	}

	/**
	 * Call an API which generates an existing random address.
	 * This function aims at getting the random address generated.
	 * if the Address API cannot answer, it will generate a random fake address.
	 * @return the random address
	 */
	private static String getRandomAddress() {
        try {
            return getRandomAddress(0);
        } catch (IOException e) {
        	LOGGER.error("Problem while getting random address",e);
            StringBuilder sb = new StringBuilder();
            sb.append(rand.nextInt(3000))
            .append(" rue de l'appel échoué ")
            .append(rand.nextInt(19)+75001)
            .append(" Paris ");
            return sb.toString();
        }
    }
	
	/**
	 * Generate a list of apartments from a json file.
	 *
	 * @param jsonFileAddress <i> String </i> the address where the json with all the apartments are
	 * @return the list <i> List </i> of apartments found in the json file.
	 * @throws IOException if we cannot have access to the json file.
	 */
	public static List<Apartment> generateApartmentFromJson(String jsonFileAddress) throws IOException {
		return JsonConvert.jsonToApartments(jsonFileAddress);
	}
	
	/**
	 * Generate apartment from a json file by default.
	 *
	 * @return the list <i> List </i> of apartments found in the json file by default.
	 * @throws IOException if we cannot have access to the json file.
	 */
	public static List<Apartment> generateApartmentFromJson() throws IOException {
		return JsonConvert.jsonToApartments();		
	}

}