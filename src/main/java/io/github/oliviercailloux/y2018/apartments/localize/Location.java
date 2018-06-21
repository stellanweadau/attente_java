package io.github.oliviercailloux.y2018.apartments.localize;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;

import io.github.oliviercailloux.y2018.apartments.utils.KeyManager;

public class Location extends Object{
	
	private LatLng coordinate;
	private String geocodeApiKey;
	
	public Location() throws FileNotFoundException, IOException {
		coordinate = new LatLng();
		geocodeApiKey = KeyManager.getGeocodeApiKey();
	}
	
	public Location(String address) throws ApiException, InterruptedException, IOException {
		coordinate = Localizer.getGeometryLocation(address,geocodeApiKey);
	}
	
	public Location(LatLng coordinate) {
		this.coordinate = coordinate;
	}
	
	public LatLng getCoordinate() {
		return coordinate;
	}
}
