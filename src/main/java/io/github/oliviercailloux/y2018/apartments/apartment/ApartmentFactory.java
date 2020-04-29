package io.github.oliviercailloux.y2018.apartments.apartment;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import io.github.oliviercailloux.y2018.apartments.utils.JsonConvert;

/**
 * A factory for creating Apartment objects.
 */
public abstract class ApartmentFactory {

	private static final int NB_MAX_RETRY = 5;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentFactory.class);

	private static Random rand = new Random();

	/** The URL of the API address. */
	private static final String URL_API_ADDRESS = "https://api-adresse.data.gouv.fr/reverse/";

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
		String address = realAddress ? getOnlineRandomAddress() : getRandomAddress();
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
		return apartBuilder.setFloorArea(floorArea).setAddress(address).setNbBedrooms(nbBedrooms)
				.setNbSleeping(nbSleeping).setNbBathrooms(nbBathrooms).setTerrace(hasTerrace)
				.setFloorAreaTerrace(floorAreaTerrace).setDescription(description).setTitle(title).setWifi(wifi)
				.setPricePerNight(pricePerNight).setNbMinNight(nbMinNight).setTele(tele).build();
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
	 * @param mean      gives the mathematical expectation of the variable
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
	 * <p>
	 * So, we generate a random latitude and a longitude and try to
	 * {@link io.github.oliviercailloux.y2018.apartments.utils.JsonConvert#getAddressFromJson(String)
	 * retrieve an address}
	 * <p>
	 * 
	 * @return the address generated.
	 * @throws InvalidObjectException in case the API doesn't return a good format
	 *                                after a certain number of attempts (RETRY)
	 */
	private static String getOnlineRandomAddress() throws InvalidObjectException {
		/**
		 * Maximum test value in case the API does not return the
		 * features.properties.labels field In the case of an API error (for example
		 * 500, or others), jax-rs throws an error of type
		 * <code>ClientErrorException</code>
		 */
		final int RETRY = 5;
		// Latitude and longitude for Ile de France
		final double LAT_IDF = 48.8_499_198d;
		final double LONG_IDF = 2.6_370_411d;
		// Open a new client JAX-RS
		final Client client = ClientBuilder.newClient();
		for (int i = 1; i <= RETRY; i++) {
			// Latitude and longitude generation
			double lat = Math.round(LAT_IDF * 10.0d) / 10.0d;
			double lng = Math.round(LONG_IDF * 10.0d) / 10.0d;
			String longitude = String.valueOf(lng) + String.valueOf(rand.nextInt((99_999 - 10000) + 1) + 10_000);
			String latitude = String.valueOf(lat) + String.valueOf(rand.nextInt((99_999 - 10000) + 1) + 10_000);
			// Call API
			WebTarget target = client.target(URL_API_ADDRESS).queryParam("lon", longitude).queryParam("lat", latitude);
			LOGGER.info(target.toString());
			String result = target.request(MediaType.TEXT_PLAIN).get(String.class);
			try {
				return JsonConvert.getAddressFromJson(result);
			} catch (IllegalArgumentException e) {
				// We do nothing because we will try again.
				// If the error persists, we raise an error at the end of the loop
				LOGGER.error(String.format("API returned wrong address -long=%s, -lat=%s (Round %d/%d) \n%s", longitude,
						latitude, Integer.valueOf(i), Integer.valueOf(RETRY), e.toString()));
			}
		}
		client.close();
		// We were unable to retrieve a correct address
		throw new InvalidObjectException("It appears that the API has failed to return a correct address.");
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
			return ApartmentFactory.getOnlineRandomAddress();
		} catch (ClientErrorException | InvalidObjectException e) {
			LOGGER.error(String.format("Problem while getting random address %s", e.toString()));
			StringBuilder sb = new StringBuilder();
			sb.append(ApartmentFactory.rand.nextInt(3000)).append(" rue de l'appel échoué ")
					.append(ApartmentFactory.rand.nextInt(19) + 75001).append(" Paris ");
			return sb.toString();
		}
	}

	/**
	 * Generate a list of apartments from a json file.
	 *
	 * @param jsonFilePath the address where the json with all the apartments are
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
		return JsonConvert.jsonToApartments();
	}

}