package io.github.oliviercailloux.y2018.apartments.apartment;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

public abstract class ApartmentFactory {
	
	public static Apartment generateApartment(double floorArea, String address, int nbBedrooms,
											  int nbSleeping, int nbBathrooms, boolean terrace, 
											  double floorAreaTerrace, String description, String title, 
											  boolean wifi, double pricePerNight, boolean tele) {
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
				.setTele(tele)
				.build();
	}

}
