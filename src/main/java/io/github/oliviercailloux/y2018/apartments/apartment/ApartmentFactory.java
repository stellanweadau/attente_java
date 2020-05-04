package io.github.oliviercailloux.y2018.apartments.apartment;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import io.github.oliviercailloux.y2018.apartments.utils.JsonConvert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for creating Apartment objects.
 */
public abstract class ApartmentFactory {

	private static final int NB_MAX_RETRY = 5;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentFactory.class);

	private static Random rand = new Random();

	/** The URL of the API address. */
	private static final String URL_API_ADDRESS = "https://8n8iajahab.execute-api.us-east-1.amazonaws.com/default/RealRandomAdress";

	/**
	 * This function aims to generate a new apartment with random characteristics
	 * and a real address.
	 * 
	 * @return An Apartment with random characteristics and a real address
	 * @throws IOException if the Address API doesn't answer
	 */
	public static Apartment generateRandomRealApartment() throws IOException {
		return generateRandomApartment(true);
	}

	/**
	 * This function aims to generate a new apartment with random characteristics
	 * and a real address if the Address Api answer.
	 * 
	 * @return An Apartment with random characteristics and a real address if the
	 *         Address Api answer
	 */
	public static Apartment generateRandomApartment() {
		try {
			return generateRandomApartment(false);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * This function aims to generate a new apartment with random characteristics.
	 *
	 * @return the apartment built
	 * @throws IOException if the Address Api doesn't answer
	 */
	private static Apartment generateRandomApartment(boolean realAddress) throws IOException {
		double floorArea = simulateRandomDraw(65d, 21d);
		String address = realAddress ? getRandomAddressOnlyReal() : getRandomAddress();
		int averageRoomArea = 10 + rand.nextInt(20);
		int nbBedrooms = Math.max(((int) (floorArea / averageRoomArea)) - 1, 1);
		int nbSleeping = (1 + rand.nextInt(4)) * nbBedrooms;
		int nbBathrooms = 1 + rand.nextInt(nbBedrooms);
		boolean hasTerrace = Math.random() >= 0.5;
		double floorAreaTerrace = (hasTerrace) ? simulateRandomDraw(15d, 2d) : 0;
		String title = "Location Apartement " + rand.nextInt(10000);
		String description = "This apartment has " + nbBedrooms + " bedrooms and a size of " + floorArea
				+ "square meters";
		boolean wifi = Math.random() >= 0.5;
		double pricePerNight = floorArea * simulateRandomDraw(11d, 3d);
		boolean tele = Math.random() >= 0.5;
		int nbMinNight = rand.nextInt(700) + 1;
		Builder apartBuilder = new Builder();
		return apartBuilder.setFloorArea(floorArea)
				.setAddress(address).setNbBedrooms(nbBedrooms)
				.setNbSleeping(nbSleeping)
				.setNbBathrooms(nbBathrooms)
				.setTerrace(hasTerrace)
				.setFloorAreaTerrace(floorAreaTerrace)
				.setDescription(description)
				.setTitle(title).setWifi(wifi)
				.setPricePerNight(pricePerNight)
				.setNbMinNight(nbMinNight)
				.setTele(tele)
				.build();
	}

	/**
	 * This function aims to generate a list of random apartments with real address.
	 * 
	 * @param nbApartment the number of apartments the list should contains
	 * @return a list of random apartments of size nbApartment with real Address
	 */
	public static List<Apartment> generateRandomApartments(int nbApartment) {
		try {
			return generateRandomApartmentList(nbApartment, false);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * This function aims to generate a list of random apartments with real address.
	 * 
	 * @param nbApartment the number of apartments the list should contains
	 * @return a list of random apartments of size nbApartment with real Address
	 * @throws IOException if the Address API doesn't answer
	 */
	public static List<Apartment> generateRandomRealApartments(int nbApartment) throws IOException {
		return generateRandomApartmentList(nbApartment, true);
	}

	/**
	 * This function aims to generate a list of random apartments.
	 *
	 * @param nbApartment the number of apartments the list should contains
	 * @return a list of random apartments of size nbApartment
	 * @throws IOException if the Address API doesn't answer
	 */
	private static List<Apartment> generateRandomApartmentList(int nbApartment, boolean realAddress)
			throws IOException {
		if (nbApartment <= 0) {
			throw new IllegalArgumentException("You must indicate a number of apartments > 0");
		}
		List<Apartment> listApartment = new ArrayList<>();
		for (int i = 0; i < nbApartment; i++) {
			listApartment.add(realAddress ? generateRandomRealApartment() : generateRandomApartment());
		}
		return listApartment;
	}

	/**
	 * This function simulates a random draw of a variable, being given its
	 * expectation and its deviation.
	 * 
	 * @param mean gives the mathematical expectation of the variable
	 * @param deviation is the standard deviation of the variable
	 * @return an outcome of the randomized experiment
	 */
	private static double simulateRandomDraw(double mean, double deviation) {
		double draw = Math.abs(rand.nextGaussian());
		return deviation * draw + mean;
	}

	/**
	 * Call an API which generates a random address. This function aims at getting
	 * the random address generated.
	 *
	 * @param retry indicates how many time the function has been called (Retry
	 *              System). It will allow us to try escaping problems while
	 *              contacting API (if it fails once, we try to call it again until
	 *              the number specified in the class attribute NB_MAX_RETRY).
	 * @return the address generated.
	 * @throws IOException if we cannot contact the API generator.
	 */
	private static String getRandomAddress(int retry) throws IOException {

		String address = "";
		// Code from
		// https://www.developpez.net/forums/d1354479/java/general-java/recuperer-reponse-d-adresse-http/
		try (InputStream is = new URL(URL_API_ADDRESS).openConnection().getInputStream()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				builder.append(line + "\n");
			}
			String bodyContent = builder.toString();
			// End of code picking

			return JsonConvert.getAddressFromJson(bodyContent);

		} catch (MalformedURLException e) {
			LOGGER.error("Problem while contacting address generator API", e);
		} catch (IOException e) {
			if (retry < NB_MAX_RETRY) {
				return getRandomAddress(retry + 1);
			}
			throw e;
		}
		return address;
	}

	/**
	 * Call an API which generates an existing random address. This function aims at
	 * getting the random address generated. if the Address API cannot answer, it
	 * will generate a random fake address.
	 * 
	 * @return the random address
	 */
	private static String getRandomAddress() {
		try {
			return getRandomAddress(0);
		} catch (IOException e) {
			LOGGER.error("Problem while getting random address", e);
			StringBuilder sb = new StringBuilder();
			sb.append(rand.nextInt(3000)).append(" rue de l'appel échoué ").append(rand.nextInt(19) + 75001)
			.append(" Paris ");
			return sb.toString();
		}
	}

	/**
	 * Call an API which generates an existing random address. This function aims at
	 * getting the random address generated.
	 * 
	 * @return the random address
	 * @throws IOException if the Address API cannot answer
	 */
	private static String getRandomAddressOnlyReal() throws IOException {
		return getRandomAddress(0);
	}

	/**
	 * Generate a list of apartments from a json file.
	 *
	 * @param jsonFileAddress the address where the json with all the apartments are
	 * @return the list of apartments found in the json file.
	 * @throws IOException if we cannot have access to the json file.
	 */
	public static List<Apartment> generateApartmentFromJsonPath(Path jsonFilePath) throws IOException {
		return JsonConvert.jsonToApartments(jsonFilePath);
	}

	/**
	 * Generate apartment from a json file by default.
	 *
	 * @return the list of apartments found in the json file by default.
	 * @throws IOException if we cannot have access to the json file.
	 */
	public static List<Apartment> getDefaultApartments() throws IOException {
		return JsonConvert.getDefaultApartments();
	}

}