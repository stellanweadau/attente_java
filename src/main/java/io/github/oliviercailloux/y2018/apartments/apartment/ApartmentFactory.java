package io.github.oliviercailloux.y2018.apartments.apartment;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

import java.util.Random;

/**
 * A factory for creating Apartment objects.
 */
public abstract class ApartmentFactory {
	
	/**
	 * The function aims to build a new apartment when all the characteristics are known
	 *
	 * @param floorArea the floor area of the apartment built
	 * @param address the address of the apartment built
	 * @param nbBedrooms the number of bedrooms of the apartment built
	 * @param nbSleeping the maximum number of people who can sleep in this apartment
	 * @param nbBathrooms the number of bathrooms of the apartment built
	 * @param terrace says whether the apartment has a terrace or not
	 * @param floorAreaTerrace gives the size of the terrace
	 * @param description a description of the apartment
	 * @param title gives the title of the announce
	 * @param wifi says whether the apartment has the wifi or not
	 * @param pricePerNight gives the price per night spent in the apartment
	 * @param tele says whether the apartment has a TV or not
	 * 
	 * @return the apartment built with the previous characteristics
	 */
	public static Apartment generateApartment(double floorArea, String address, int nbBedrooms,int nbSleeping, 
											  int nbBathrooms, boolean terrace, double floorAreaTerrace, 
											  String description, String title, boolean wifi, 
											  double pricePerNight,int nbMinNight, boolean tele) {
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
	 * @return the apartment built
	 */
	public static Apartment generateRandomApartment() {
		
		double floorArea = simulateRandomDraw(65d, 21d);
		String address = "2 avenue Pasteur 94160 Saint-mandé";
		int nbBedrooms = Math.max(((int) (floorArea / ((int) (10 + Math.random() * 20))) - 1), 0);
		int nbSleeping = ((int) (1 + Math.random() * 4)) * nbBedrooms;
		int nbBathrooms = (int) (Math.random() * nbBedrooms);
		boolean terrace = (Math.random() * 2 >= 1) ? true : false;
		double floorAreaTerrace = (terrace) ? simulateRandomDraw(15d, 2d) : 0;
		String title = "Location Apartement "+(int)(Math.random()*1000000);
		String description = "This apartment has " + nbBedrooms + " bedrooms and a size of " + floorArea + "square meters";
		boolean wifi = (Math.random() * 2 >= 1) ? true : false;
		double pricePerNight = floorArea * simulateRandomDraw(8d,3d);
		boolean tele = (Math.random() * 2 >= 1) ? true : false;
		int nbMinNight = (int)(Math.random()*700);
		return generateApartment(floorArea,address, nbBedrooms, nbSleeping, nbBathrooms,
								 terrace, floorAreaTerrace, description,title, 
								 wifi, pricePerNight,nbMinNight, tele);

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
		Random random = new Random();
		double draw = random.nextGaussian();
		return deviation * draw + mean;
	}

}
