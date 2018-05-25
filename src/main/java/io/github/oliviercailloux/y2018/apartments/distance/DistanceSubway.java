package io.github.oliviercailloux.y2018.apartments.distance;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TravelMode;

import io.github.oliviercailloux.y2018.apartments.localize.Localizer;
import io.github.oliviercailloux.y2018.apartments.utils.KeyManager;
import io.github.oliviercailloux.y2018.apartments.valuefunction.DistanceMode;

/**
 * This class enables the user to calculate the distance in hours between two
 * points using the metro transport. This class uses Google Maps API.
 * Inspiration from
 * https://www.programcreek.com/java-api-examples/index.php?api=com.google.maps.model.DistanceMatrix
 *
 */
public class DistanceSubway {

	private String startPoint;
	private String endPoint;
	private LatLng startCoordinate;
	private LatLng endCoordinate;

	final static Logger LOGGER = LoggerFactory.getLogger(DistanceSubway.class);

	/**
	 * Create an Object DistanceSubway in order to calculate a distance between two
	 * points using the metro transport.
	 * 
	 * @param api_key
	 *            the API Key to use Google Maps Services
	 * @param startPoint
	 *            the start point of the path
	 * @param endPoint
	 *            the end point of the path
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ApiException
	 */
	public DistanceSubway(String startPoint, String endPoint) throws ApiException, InterruptedException, IOException {
		if (startPoint == null || endPoint == null)
			throw new IllegalArgumentException("Address is not a valid object");
		if (startPoint.length() == 0 || endPoint.length() == 0)
			throw new IllegalArgumentException("Address is empty");

		this.endPoint = endPoint;
		this.startPoint = startPoint;

		setCoordinate();

		LOGGER.info("DistanceSubway Object created with success. Departure= " + startPoint + " ; Arrival= " + endPoint);
	}

	public DistanceSubway(LatLng startCoordinate, LatLng endCoordinate) {
		this.startCoordinate = startCoordinate;
		this.endCoordinate = endCoordinate;
	}

	/**
	 * This method allows the user to set the polar coordinate of startCoordinate
	 * and endCoordinate. It uses the Localizer in order to calculate the
	 * coordinate.
	 * 
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void setCoordinate() throws ApiException, InterruptedException, IOException {

		startCoordinate = Localizer.getGeometryLocation(startPoint);
		endCoordinate = Localizer.getGeometryLocation(endPoint);
	}

	/**
	 * This method enables the user to calculate a distance between two points using
	 * Google Maps API. The method uses DistanceMatrix of Google Maps library.
	 * The API key can be found here : <a href="https://developers.google.com/maps/documentation/geocoding/start?hl=fr#get-a-key">Get your api key </a>
	 * The request return an Object DistanceMatrix with three properties ; desitnation_addresses, origin_addresses and rows. 
	 * Rows is an Array of elements. In our case there is only one element. In this element, there is the
	 * properties distance and duration, and both have the properties Text (human reading) and value (double).
	 * @param distanceMode
	 *            is a enum type, allow the user to choose between address mode (by
	 *            the name) or by coordinate mode.
	 * @return distance in hours between the two points given in the constructor.
	 */
	public double calculateDistanceAddress(DistanceMode distancemode)
			throws ApiException, InterruptedException, IOException {
		String apiKey;
		try {
			 apiKey = KeyManager.getApiKey();
		} catch (FileNotFoundException e) {
			LOGGER.info(
					"The file GEOCODE_API_KEY.txt can't be found at the project root. Please be sure that the file is correctly named and present at the project root");
			throw new FileNotFoundException(
					"ERROR : The file GEOCODE_API_KEY.txt can't be found at the project root. Please be sure that the file is correctly named and present at the project root");

		}
		try {
			GeoApiContext dist = new GeoApiContext.Builder().apiKey(apiKey).build();
			LOGGER.info("GeoApiContext build with success.");

			DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(dist);

			LOGGER.info("DistanceMatrixApiRequest build with success.");

			DistanceMatrix result = null;

			switch (distancemode) {
			case ADDRESS:
				result = request.origins(startPoint).destinations(endPoint).mode(TravelMode.TRANSIT)
						.transitModes(TransitMode.SUBWAY).language("fr-FR").await();
				break;
			case COORDINATE:
				result = request.origins(startCoordinate).destinations(endCoordinate).mode(TravelMode.TRANSIT)
						.transitModes(TransitMode.SUBWAY).language("fr-FR").await();
				break;
			default:
				result = request.origins(startPoint).destinations(endPoint).mode(TravelMode.TRANSIT)
						.transitModes(TransitMode.SUBWAY).language("fr-FR").await();
				break;
			}

			LOGGER.info("DistanceMatrix build with success.");
			return result.rows[0].elements[0].duration.inSeconds;
		} catch (Exception e) {

			if (e.getClass() == IllegalStateException.class) {
				LOGGER.info("ERROR : The api key is not valid, please be sure you have a valid key");
				throw new IllegalStateException(
						"ERROR : The api key is not valid, please be sure you have a valid key");
			} else {
				throw e;
			}
		}

	}

}
