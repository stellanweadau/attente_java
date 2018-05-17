package io.github.oliviercailloux.y2018.apartments.distance;

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

import io.github.oliviercailloux.y2018.apartments.valuefunction.DistanceMode;

/**
 * This class enables the user to calculate the distance in hours between two points using the metro transport.
 * This class uses Google Maps API.
 * Inspiration from https://www.programcreek.com/java-api-examples/index.php?api=com.google.maps.model.DistanceMatrix
 *
 */
public class DistanceSubway {
	
	//private String url;

	private String api_key;
	private String startPoint;
	private String endPoint;
	private LatLng startCoordinate;
	private LatLng endCoordinate;
	private String api_key_geocode;
	final static Logger LOGGER = LoggerFactory.getLogger(DistanceSubway.class);
	
	/**
	 * Create an Object DistanceSubway in order to calculate a distance between two points using the metro transport.
	 * @param api_key the API Key to use Google Maps Services
	 * @param startPoint the start point of the path
	 * @param endPoint the end point of the path
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ApiException 
	 */
	public DistanceSubway(String api_key, String api_geocode_key, String startPoint, String endPoint) throws ApiException, InterruptedException, IOException{
	
		this.api_key = api_key;
		this.endPoint = endPoint;
		this.startPoint = startPoint;
		this.api_key_geocode = api_geocode_key;
		this.startCoordinate = this.getGeometryLocation(startPoint);		
		this.endCoordinate = this.getGeometryLocation(endPoint);
		
		
		
		LOGGER.info("DistanceSubway Object created with success. API Key= "+api_key+" ; Departure= "+startPoint+" ; Arrival= "+ endPoint);
	}
	
	
	/**
	 * This method enables the user to calculate a distance between two points using Google Maps API.
	 * The method uses DistanceMatrix of Google Maps library.
	 * @param distanceMode is a enum type, allow the user to choose between address mode (by the name) or by coordinate mode.
	 * @return distance in hours between the two points given in the constructor.
	 */
	public double calculateDistanceAddress(DistanceMode distancemode) throws ApiException, InterruptedException, IOException {
		
		GeoApiContext dist = new GeoApiContext.Builder()
				.apiKey(api_key)
				.build();
		
		LOGGER.info("GeoApiContext build with success.");
		
		DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(dist);
		
		LOGGER.info("DistanceMatrixApiRequest build with success.");
		DistanceMatrix result = null;
		
		if(distancemode == DistanceMode.ADDRESS)
		{
		 result = request.origins(startPoint)
				.destinations(endPoint)
				.mode(TravelMode.TRANSIT)
				.transitModes(TransitMode.SUBWAY)
				.language("fr-FR")
				.await();
		}
		else
		{
			 result = request.origins(startCoordinate)
					.destinations(endCoordinate)
					.mode(TravelMode.TRANSIT)
					.transitModes(TransitMode.SUBWAY)
					.language("fr-FR")
					.await();
		}
		
		LOGGER.info("DistanceMatrix build with success.");
		return (double)(result.rows[0].elements[0].duration.inSeconds)/3600;
	}
	
	/**	getGeometryLocation return, base on the full address of the location, the geocode of it.
	 * 
	 * @param location is the full address of the location
	 * @return
	 * 	a LatLng Object which contains the latitude and longitude of the location
	 * @throws ApiException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private LatLng getGeometryLocation(String location) throws ApiException, InterruptedException, IOException
	{
		GeoApiContext context = new GeoApiContext.Builder()
				.apiKey(this.api_key_geocode)
				.build();
		
		GeocodingResult[] res = GeocodingApi.newRequest(context).address(location).await();
		
		return res[0].geometry.location;
	
	}
	
}
