package io.github.oliviercailloux.y2018.apartments.apartment;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

import java.util.ArrayList;
import java.util.Random;

/**
 * A factory for creating Apartment objects.
 */
public abstract class ApartmentFactory {
	
	private static Random rand = new Random();
	//private static String urlAddress;
	
	/**
	 * The function aims to build a new apartment when all the characteristics are known.
	 *
	 * @param floorArea <i>double</i> the floor area of the apartment built
	 * @param address <i>String</i> the address of the apartment built
	 * @param nbBedrooms <i>int</i> the number of bedrooms of the apartment built
	 * @param nbSleeping <i>int</i> the maximum number of people who can sleep in this apartment
	 * @param nbBathrooms <i>int</i> the number of bathrooms of the apartment built
	 * @param terrace <i>boolean</i> says whether the apartment has a terrace or not
	 * @param floorAreaTerrace <i>double</i> gives the size of the terrace
	 * @param description <i>String</i> a description of the apartment
	 * @param title <i>String</i> gives the title of the announce
	 * @param wifi <i>boolean</i> says whether the apartment has the wifi or not
	 * @param pricePerNight <i>double</i> gives the price per night spent in the apartment
	 * @param nbMinNight <i>int</i> indicate the number minimum of night to locate an apartment
	 * @param tele <i>boolean</i> says whether the apartment has a TV or not
	 * 
	 * @return <i>Apartment</i> the apartment built with the previous characteristics
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
	 * @return <i>Apartment</i> the apartment built
	 */
	public static Apartment generateRandomApartment() {
		
		double floorArea = simulateRandomDraw(65d, 21d);
		String address = "2 avenue Pasteur 94160 Saint-mandé";
		int nbBedrooms = Math.max(((int) (floorArea / (10 + rand.nextInt(20))) - 1), 0);
		int nbSleeping = (1 + rand.nextInt(4)) * nbBedrooms;
		int nbBathrooms = 1 + rand.nextInt(nbBedrooms);
		boolean terrace = Math.random() >= 0.5;
		double floorAreaTerrace = (terrace) ? simulateRandomDraw(15d, 2d) : 0;
		String title = "Location Apartement "+ rand.nextInt(1000000);
		String description = "This apartment has " + nbBedrooms + " bedrooms and a size of " + floorArea + "square meters";
		boolean wifi = Math.random() >= 0.5;
		double pricePerNight = floorArea * simulateRandomDraw(8d,3d);
		boolean tele = Math.random() >= 0.5;
		int nbMinNight = rand.nextInt(700) + 60;
		return generateApartment(floorArea,address, nbBedrooms, nbSleeping, nbBathrooms,
								 terrace, floorAreaTerrace, description,title, 
								 wifi, pricePerNight,nbMinNight, tele);

	}
	
	/**
	 * This function aims to generate a list of random apartments
	 *
	 * @param nbApartment <i>int</i> the number of apartments the list should contains
	 * @return <i>ArrayList</i> a list of random apartments of size nbApartment 
	 */
	public static ArrayList<Apartment> generateRandomApartmentList(int nbApartment) {
		
		ArrayList<Apartment> listApartment = new ArrayList<>();
		for(int i = 0; i < nbApartment ; i++) {
			listApartment.add(generateRandomApartment());
		}
		return listApartment;
	}
	
	/**
	 * This function simulates a random draw of a variable, being given its
	 * expectation and its deviation.
	 * 
	 * @param mean <i>double</i> gives the mathematical expectation of the variable
	 * @param deviation <i>double</i> is the standard deviation of the variable
	 * @return <i>double</i> an outcome of the randomized experiment
	 */
	private static double simulateRandomDraw(double mean, double deviation) {
		double draw = rand.nextGaussian();
		return deviation * draw + mean;
	}
	
	//private static String generateRandomAddress() {
		
	//}

}
