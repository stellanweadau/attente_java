package io.github.oliviercailloux.y2018.apartments.apartment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Apartment extends Object {
	
	static Logger apartment = LoggerFactory.getLogger(Apartment.class);

	/**
	 * @param floorArea a real number it represents the floor area of the Apartment in square meters
	 */
	private double floorArea; 

	/**
	 * @param address a String of characters that gives the full location of the Apartment (number, street name, ZIP code, city, country)
	 */
	private String address; 

	/**
	 * @param nbBedrooms is an integer superior or equal to zero, it is the number of bedrooms available of use in the Apartment
	 */
	private int nbBedrooms;

	/**
	 * @param nbSleepings is an integer superior or equal to zero corresponding of the accommodation capacity of the Apartment (nb of people that can sleep in the Apartment)
	 */
	private int nbSleeping; 

	/**
	 * @param nbBathrooms an integer superior or equal to zero which corresponds to the number of bathrooms
	 */
	private int nbBathrooms;

	/**
	 * @param terrace a boolean (true/false) which indicates if there's a terrace or not 
	 */
	private boolean terrace;

	/**
	 * @param floorAreaTerrace is a real number superior or equal to zero it represents the floor area of the terrace of the Apartment if there's any
	 */
	private double floorAreaTerrace; 

	/**
	 * @param description is a string of characters that describe the Apartment and the offer (its accommodations)
	 */
	private String description; 

	/**
	 * @param title is a string of characters that represents the title of the announcement
	 */
	private String title;

	/**
	 * @param wifi is a boolean (true/false) which indicates if there is wireless connection to internet or not
	 */
	private boolean wifi; 

	/**
	 * @param pricePerNight is a real number superior or equal to zero, how much it cost (before any fees) to stay per night in euros
	 */
	private double pricePerNight; 

	/**
	 * @param nbMinNight is an integer superior or equal to zero, indicates how long in nights the customer will stays
	 */
	private int nbMinNight; 

	/**
	 * @param tele is a boolean (true/false) which indicates if there's a television or not
	 */
	private boolean tele; 


	/**
	 * @param floorArea is a real number superior or equal to zero, it represents the floor area of the Apartment in square meters
	 * @param address is a string of characters that gives the full location of the Apartment
	 * @param title a string of characters that represents the title of the announcement
	 */
	public Apartment (double floorArea, String address, String title) {	
		this.floorArea = floorArea;
		this.address = address;
		this.nbBedrooms = 0 ;
		this.nbSleeping = 0 ;
		this.nbBathrooms = 0;
		this.terrace = false ;
		this.floorAreaTerrace = 0 ;
		this.description = "";
		this.title = title;
		this.wifi = false ;
		this.pricePerNight = 0;
		this.nbMinNight = 0 ;
		this.tele = false ;
		apartment.info("The Apartment has been created with success");
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Apartment))
			return false;
		Apartment apart = (Apartment) obj;
		return apart.floorArea == floorArea && apart.address.equals(address) && apart.nbBedrooms == nbBedrooms && apart.nbSleeping == nbSleeping && apart.nbBathrooms == nbBathrooms && apart.terrace == terrace && apart.floorAreaTerrace == floorAreaTerrace && apart.description.equals(description) && apart.title.equals(title) && apart.wifi == wifi && apart.pricePerNight == pricePerNight && apart.nbMinNight == nbMinNight &&  apart.tele == tele ;
			
		}
	
	@Override
	public String toString() {
		String dispTitle ;
		String dispAddress ;
		String dispFloorArea ;
		String dispNbBedrooms ;
		String dispNbSleeping ;
		String dispNbBathrooms ;
		String dispTerrace ;
		String dispFloorAreaTerrace ;
		String dispDescription ;
		String dispWifi ;
		String dispPricePerNight ;
		String dispNbMinNight ;
		String dispTele ;

		dispTitle = "\nTitle : " + title ;
		dispAddress = "\nAddress : " + address ;
		dispFloorArea = "\nFloor area : " + Double.toString(floorArea) + " square meters";
		dispNbBedrooms = "\nNumber of bedrooms : " + ((nbBedrooms == 0) ? "N/A" : Integer.toString(nbBedrooms)+" bedroom(s)") ;
		dispNbSleeping = "\nNumber of sleeping (capacity) : " + ((nbSleeping == 0) ? "N/A" : Integer.toString(nbSleeping)+" person(s)") ;
		dispNbBathrooms = "\nNumber of bathrooms : " + ((nbBathrooms == 0) ? "N/A" : Integer.toString(nbBathrooms)+" bathroom(s)") ;
		dispTerrace = "\nTerrace : " + ((terrace == true) ? "Yes" : "No") ;
		dispFloorAreaTerrace = ((terrace == false) ? "" : "\nTerrace floor area : " + ((floorAreaTerrace == 0) ? "N/A" : Double.toString(floorAreaTerrace)+" square meters")) ;
		dispDescription = "\nDescription : " + ((description == "") ? "N/A" : description) ;
		dispWifi = "\nWifi : " + ((wifi == true) ? "Yes" : "No") ;
		dispTele = "\nTelevision : " + ((tele == true) ? "Yes" : "No") ;
		dispPricePerNight = "\nPrice per night : " + ((pricePerNight == 0) ? "N/A" : Double.toString(pricePerNight)+"$") ;
		dispNbMinNight = "\nNumber of night minimum to rent this apartment : " + ((nbMinNight == 0) ? "N/A" : Integer.toString(nbMinNight)+" night(s)") ;
		
		return dispTitle + dispAddress + dispFloorArea  +dispNbBedrooms + dispNbSleeping + dispNbBathrooms + dispTerrace + dispFloorAreaTerrace + dispDescription  +dispWifi + dispPricePerNight + dispNbMinNight + dispTele ; 
	}

	/**
	 * get the value of the floor area
	 * @return a double positive or equal to zero
	 */
	public double getFloorArea() {
		return floorArea;
	}

	/**
	 * get the value of the address
	 * @return a String of characters
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * get the number of bedrooms, 0 if missing
	 * @return an integer positive or equal to zero
	 */
	public int getNbBedrooms() {
		return nbBedrooms;
	}

	/**
	 * get the number of sleeping, 0 if missing
	 * @return an integer positive or equal to zero
	 */
	public int getNbSleeping() {
		return nbSleeping;
	}

	/**
	 * get the number of bathrooms, 0 if missing
	 * @return an integer positive or equal to zero
	 */
	public int getNbBathrooms() {
		return nbBathrooms;
	}

	/**
	 * get the presence (or absence) of a terrace
	 * @return a boolean
	 */
	public boolean getTerrace() {
		return terrace;
	}

	/**
	 * get the value of the floor area of the terrace (0 if there is no terrace)
	 * @return a double positive or equal to zero
	 */
	public double getFloorAreaTerrace() {
		return floorAreaTerrace;
	}

	/**
	 * get the description of the apartment, "" if missing
	 * @return a string of characters
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * get the title of the announcement 
	 * @return a string of characters
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * get the presence (or absence) of WiFi 
	 * @return a boolean
	 */
	public boolean getWifi() {
		return wifi;
	}

	/**
	 * get the price for one night, 0 if missing
	 * @return a double positive or equal to zero
	 */
	public double getPricePerNight() {
		return pricePerNight;
	}

	/**
	 * get the minimum number of nights, 0 if missing
	 * @return an integer positive or equal to zero
	 */
	public int getNbMinNight() {
		return nbMinNight;
	}

	/**
	 * get the presence (or absence) of a TV
	 * @return a boolean
	 */
	public boolean getTele() {
		return tele;
	}


	/**
	 * @param floorArea is a real number superior or equal to zero
	 */
	public void setFloorArea(double floorArea) {
		if (floorArea < 0) {
			apartment.debug("The floor area should not be negative");
			throw new IllegalArgumentException ("The floor area can not be negative");
		}
		this.floorArea = floorArea;
		apartment.info("The floor area has been set to "+ floorArea);
	}

	/**
	 * @param address is a string of characters 
	 */
	public void setaddress(String address) {
		this.address = address;
		apartment.info("The address has been set to "+ address);
	}

	/**
	 * @param nbBedrooms is an integer superior or equal to zero
	 */
	public void setNbBedrooms(int nbBedrooms) {
		if (nbBedrooms < 0) {
			apartment.debug("The number of bedrooms should not be negative");
			throw new IllegalArgumentException ("The number of Bedrooms can not be negative");
		}
		this.nbBedrooms = nbBedrooms;
		apartment.info("The number of bathrooms has been set to "+ nbBedrooms);
	}

	/**
	 * @param nbSleepings is an integer superior or equal to zero
	 */
	public void setNbSleeping(int nbSleeping) {
		if (nbSleeping < 0) {
			apartment.debug("The accomodation capacity should not be negative");
			throw new IllegalArgumentException ("The accomodation capacity can not be negative");
		}
		this.nbSleeping = nbSleeping ;
		apartment.info("The number of sleepings has been set to "+ nbSleeping);
	}

	/**
	 * @param nbBathrooms is an integer superior or equal to zero 
	 */
	public void setNbBathrooms(int nbBathrooms) {
		if (nbBathrooms < 0) {
			apartment.debug("The number of bathrooms should not be negative");
			throw new IllegalArgumentException ("The number of bathrooms can not be negative");
		}
		this.nbBathrooms = nbBathrooms ;
		apartment.info("The number of bathrooms has been set to "+ nbBathrooms);
	}

	/**
	 * @param terrace is a boolean (true/false)
	 */
	public void setTerrace(boolean terrace) {
		this.terrace = terrace ;
		apartment.info("terrace has been set to "+ terrace);
	}

	/**
	 * @param floorAreaTerrace is a real number superior or equal to zero, it only works if terrace = true (use setTerrace)
	 */
	public void setFloorAreaTerrace(double floorAreaTerrace) {
		if (this.terrace == false && floorAreaTerrace > 0) {
			apartment.debug("The floor area terrace can't be set if the the terace is set to 'false'");
			throw new IllegalArgumentException ("The terrace can not have a floor area if it doesn't exists");
		}
		if (floorAreaTerrace < 0) {
			apartment.debug("The floor area of the terrace should not be negative");
			throw new IllegalArgumentException ("The floor area of the terrace can not be negative");
		}
		this.floorAreaTerrace = floorAreaTerrace ;
		apartment.info("The floor area of the terrace has been set to "+ floorAreaTerrace);
	}

	/**
	 * @param description is a string of characters
	 */
	public void setDescription(String description) {
		this.description = description ;
		apartment.info("The description has been set to " + description);
	}

	/**
	 * @param title is a string of characters 
	 */
	public void setTitle(String title) {
		this.title = title ;
		apartment.info("The title has been set to "+ floorArea);
	}

	/**
	 * @param wifi is a boolean (true/false)
	 */
	public void setWifi(boolean wifi) {
		this.wifi = wifi ;
		apartment.info("The wifi has been set to "+ wifi);
	}

	/**
	 * @param pricePerNight is a real number superior or equal to zero
	 */
	public void setPricePerNight(double pricePerNight) {
		if (pricePerNight < 0)
			throw new IllegalArgumentException ("The price per night can not be negative");
		this.pricePerNight = pricePerNight ;
		apartment.info("The price per night has been set to "+ pricePerNight);
	}

	/**
	 * @param nbMinNight is an integer superior or equal to zero
	 */
	public void setNbMinNight(int nbMinNight) {
		if (nbMinNight < 0)
			throw new IllegalArgumentException ("The minimum number of nights can not be negative");
		this.nbMinNight = nbMinNight ;
		apartment.info("The number minimum of night has been set to "+ nbMinNight);
	}

	/**
	 * @param tele is a boolean (true/false)
	 */
	public void setTele(boolean tele) {
		this.tele = tele ;
		apartment.info("The tele has been set to "+ tele);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
