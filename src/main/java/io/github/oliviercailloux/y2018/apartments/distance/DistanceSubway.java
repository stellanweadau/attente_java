package io.github.oliviercailloux.y2018.apartments.distance;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

/**
 * This class enables the user to calculate between two points using the metro transport.
 * This class uses Google Maps API.
 * Inspiration from https://www.programcreek.com/java-api-examples/index.php?api=com.google.maps.model.DistanceMatrix
 *
 */
public class DistanceSubway {
	
	//private String url;
	private String api_key;
	private String startPoint;
	private String endPoint;
	static Logger distanceSubway = LoggerFactory.getLogger(DistanceSubway.class);
	
	/**
	 * Create an Object DistanceSubway in order to calculate a distance between two points using the metro transport.
	 * @param api_key the API Key to use Google Maps Services
	 * @param startPoint the start point of the path
	 * @param endPoint the end point of the path
	 */
	public DistanceSubway(String api_key, String startPoint, String endPoint){
		//url = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + startPoint + "&destinations=" + endPoint + "&mode=" + mode + "&language=fr-FR&key=" + api_key + "\\r\\n" ;
		this.api_key = api_key;
		this.endPoint = endPoint;
		this.startPoint = startPoint;
		distanceSubway.info("DistanceSubway Object created with success. API Key= "+api_key+" ; Departure= "+startPoint+" ; Arrival= "+ endPoint);
	}
	
	/**
	 * This method enables the user to calculate a distance between two points using Google Maps API.
	 * The method uses DistanceMatrix of Google Maps library.
	 * @return distance between the two points given in the constructor.
	 */
	public long calculateDistance() throws ApiException, InterruptedException, IOException {
		
		GeoApiContext dist = new GeoApiContext.Builder()
				.apiKey(api_key)
				.build();
		
		distanceSubway.info("GeoApiContext build with success.");
		
		DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(dist);
		
		distanceSubway.info("DistanceMatrixApiRequest build with success.");
		
		DistanceMatrix result = request.origins(startPoint)
				.destinations(endPoint)
				.mode(TravelMode.TRANSIT)
				.language("fr-FR")
				.await();
		
		distanceSubway.info("DistanceMatrix build with success.");
		
		return result.rows[0].elements[0].distance.inMeters;
	}
	
}