package io.github.oliviercailloux.y2018.apartments.localize;

import java.io.IOException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

import io.github.oliviercailloux.y2018.apartments.utils.KeyManager;

public class Localizer {
	
	private LatLng startCoordinate;
	private LatLng endCoordinate;
	
	public Localizer(String startAddress, String endAddress) throws ApiException, InterruptedException, IOException {
		this.startCoordinate = getGeometryLocation(startAddress);
		this.endCoordinate = getGeometryLocation(endAddress);
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
	private LatLng getGeometryLocation(String address) throws ApiException, InterruptedException, IOException{
		
		String geocodeApiKey = KeyManager.getGeocodeApiKey();
		
		GeoApiContext context = new GeoApiContext.Builder()
				.apiKey(geocodeApiKey)
				.build();
		
		GeocodingResult[] res = GeocodingApi.newRequest(context).address(address).await();
		
		return res[0].geometry.location;
	
	}
	
	public LatLng getStartCoordinate() {
		return startCoordinate;
	}
	
	public LatLng getEndCoordinate() {
		return endCoordinate;
	}
	

}
