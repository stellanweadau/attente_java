package io.github.oliviercailloux.y2018.apartments.localize;

import java.io.IOException;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;

public class Location extends Object{
	
	private LatLng coordinate;
	
	public Location() {
		coordinate = new LatLng();
	}
	
	public Location(String address) throws ApiException, InterruptedException, IOException {
		Localizer localizer = new Localizer(address, address);
		coordinate = localizer.getStartCoordinate();
	}
	
	public Location(LatLng coordinate) {
		this.coordinate = coordinate;
	}
	
	public LatLng getCoordinate() {
		return coordinate;
	}
}
