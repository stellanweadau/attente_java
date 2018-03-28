package io.github.oliviercailloux.y2018.apartments.apartment;


public class Apartment {

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
	 * @param priceN is a real number superior or equal to zero, how much it cost (before any fees) to stay per night in euros
	 */
	private double priceN; 

	/**
	 * @param nbMinNight is an integer superior or equal to zero, indicates how long in nights the customer will stays
	 */
	private int nbMinNight; 

	/**
	 * @param tele is a boolean (true/false) which indicates which indicates if there's a television or not
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
		this.priceN = 0;
		this.nbMinNight = 0 ;
		this.tele = false ;
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
	 * get the number of bedrooms
	 * @return an integer positive or equal to zero
	 */
	public int getNbBedrooms() {
		return nbBedrooms;
	}

	/**
	 * get the number of sleeping
	 * @return an integer positive or equal to zero
	 */
	public int getNbSleeping() {
		return nbSleeping;
	}

	/**
	 * get the number of bathrooms
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
	 * get the description of the apartment
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
	 * get the price for one night 
	 * @return a double positive or equal to zero
	 */
	public double getPriceN() {
		return priceN;
	}

	/**
	 * get the minimum number of nights 
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
		if (floorArea < 0)
			throw new IllegalArgumentException ("The floor area can not be negative");
		this.floorArea = floorArea;
	}

	/**
	 * @param address is a string of characters 
	 */
	public void setaddress(String address) {
		this.address = address;
	}
	
	/**
	 * @param nbBedrooms is an integer superior or equal to zero
	 */
	public void setNbBedrooms(int nbBedrooms) {
		if (nbBedrooms < 0)
			throw new IllegalArgumentException ("The number of Bedrooms can not be negative");
		this.nbBedrooms = nbBedrooms;
	}

	/**
	 * @param nbSleepings is an integer superior or equal to zero
	 */
	public void setNbSleeping(int nbSleeping) {
		if (nbSleeping < 0)
			throw new IllegalArgumentException ("The accomodation capcity can not be negative");
		this.nbSleeping = nbSleeping ;
	}
	
	/**
	 * @param nbBathrooms is an integer superior or equal to zero 
	 */
	public void setNbBathrooms(int nbBathrooms) {
		if (nbBathrooms < 0)
			throw new IllegalArgumentException ("The number of bathroom can not be negative");
		this.nbBathrooms = nbBathrooms ;
	}
	
	/**
	 * @param terrace is a boolean (true/false)
	 */
	public void setTerrace(boolean terrace) {
		this.terrace = terrace ;
	}

	/**
	 * @param floorAreaTerrace is a real number superior or equal to zero, it only works if terrace = true (use setTerrace)
	 */
	public void setFloorAreaTerrace(double floorAreaTerrace) {
		if (this.terrace == false && floorAreaTerrace > 0)
			throw new IllegalArgumentException ("The terrace can not have a floor area if it doesn't exists");
		if (floorAreaTerrace < 0)
			throw new IllegalArgumentException ("The floor area of the terrace can not be negative");
		this.floorAreaTerrace = floorAreaTerrace ;
	}
	
	/**
	 * @param description is a string of characters
	 */
	public void setDescription(String description) {
		this.description = description ;
	}
	
	/**
	 * @param title is a string of characters 
	 */
	public void setTitle(String title) {
		this.title = title ;
	}
	
	/**
	 * @param wifi is a boolean (true/false)
	 */
	public void setWifi(boolean wifi) {
		this.wifi = wifi ;
	}
	
	/**
	 * @param priceN is a real number superior or equal to zero
	 */
	public void setPriceN(double priceN) {
		if (priceN < 0)
			throw new IllegalArgumentException ("The price per night can not be negative");
		this.priceN = priceN ;
	}
	
	/**
	 * @param nbMinNight is an integer superior or equal to zero
	 */
	public void setNbMinNight(int nbMinNight) {
		if (nbMinNight < 0)
			throw new IllegalArgumentException ("The minimum number of nights can not be negative");
		this.nbMinNight = nbMinNight ;
	}
	
	/**
	 * @param tele is a boolean (true/false)
	 */
	public void setTele(boolean tele) {
		this.tele = tele ;
	}
	
}
