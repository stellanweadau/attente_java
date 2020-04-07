package io.github.oliviercailloux.y2018.apartments.apartment;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

import java.util.ArrayList;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Apartment objects.
 */
public abstract class ApartmentFactory {
	
	/** The r. */
	private static Random r = new Random();  
	
	/**
	 * The function aims to build a new apartment when all the characteristics are known.
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
	 * @param nbMinNight indicate the number minimum of night to locate an apartment
	 * @param tele says whether the apartment has a TV or not
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
		int nbBedrooms = Math.max(((int) (floorArea / (10 + r.nextInt(20))) - 1), 0);
		int nbSleeping = (1 + r.nextInt(4)) * nbBedrooms;
		int nbBathrooms = 1 + r.nextInt(nbBedrooms);
		boolean terrace = Math.random() >= 0.5;
		double floorAreaTerrace = (terrace) ? simulateRandomDraw(15d, 2d) : 0;
		String title = "Location Apartement "+ r.nextInt(1000000);
		String description = "This apartment has " + nbBedrooms + " bedrooms and a size of " + floorArea + "square meters";
		boolean wifi = Math.random() >= 0.5;
		double pricePerNight = floorArea * simulateRandomDraw(8d,3d);
		boolean tele = Math.random() >= 0.5;
		int nbMinNight = r.nextInt(700) + 60;
		return generateApartment(floorArea,address, nbBedrooms, nbSleeping, nbBathrooms,
								 terrace, floorAreaTerrace, description,title, 
								 wifi, pricePerNight,nbMinNight, tele);

	}
	
	/**
	 * This function aims to generate a list of random apartments
	 *
	 * @param nbApartment the number of apartments the list should contains
	 * @return a list of random apartments of size nbApartment
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
	 * @param mean gives the mathematical expectation of the variable
	 * @param deviation is the standard deviation of the variable
	 * @return an outcome of the randomized experiment
	 */
	private static double simulateRandomDraw(double mean, double deviation) {
		double draw = r.nextGaussian();
		return deviation * draw + mean;
	}

}
