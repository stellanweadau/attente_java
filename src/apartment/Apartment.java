package apartment;


public class Apartment {

	/**
	 * @floorArea a real number with two decimals it represents the floor area of the Apartment in square meters
	 */
	protected double floorArea; 

	/**
	 * @address a String of characters that gives the full location of the Apartment (number, street name, ZIP code, city, country)
	 */
	protected String address; 

	/**
	 * @nbChambers is an integer superior or equal to zero, it is the number of chamber available of use in the Apartment
	 */
	protected int nbChambers;

	/**
	 * @nbSleepings is an integer superior or equal to zero corresponding of the accommodation capacity of the Apartment (nb of people that can sleep in the Apartment)
	 */
	protected int nbSleeping; 

	/**
	 * @nbBR an integer superior or equal to zero which corresponds to the number of bathrooms
	 */
	protected int nbBR;

	/**
	 * @terrace a boolean (true/false) which indicates if there's a terrace or not 
	 */
	protected boolean terrace;

	/**
	 * @floorAreaTerrace is a real number superior or equal to zero with two decimals it represents the floor area of the terrace of the Apartment if there's any
	 */
	protected double floorAreaTerrace; 

	/**
	 * @description is a string of characters that describe the Apartment and the offer (its accommodations)
	 */
	protected String description; 
	/**
	 * @title is a string of characters that represents the title of the announcement
	 */
	protected String title; 
	/**
	 * @wifi is a boolean (true/false) which indicates if there's wireless connection to internet or not
	 */
	protected boolean wifi; 

	/**
	 * @priceN is a real number superior or equal to zero with two decimals, how much it cost (before any fees) to stay per night in euros
	 */
	protected double priceN; 

	/**
	 * @nbMinNight is an integer superior or equal to zero, indicates how long in nights the customer will stays
	 */
	protected int nbMinNight; 

	/**
	 * @tele is a boolean (true/false) which indicates which indicates if there's a television or not
	 */
	protected boolean tele; 

	
	/**
	 * This constructor without parameters gives default values to each mandatory characteristics of the class Apartment
	 */
	public Apartment() {
		floorArea = 0;
		address = "";
		title = ""; 	
	}
	/**
	 * @param floorArea is a real number superior or equal to zero with two decimals, it represents the floor area of the Apartment in square meters
	 * @param address is a string of characters that gives the full location of the Apartment
	 * @param title a string of characters that represents the title of the announcement
	 */
	
	
	public Apartment (double floorArea, String adress, String title) {	
		this.floorArea = floorArea;
		this.address = adress;
		this.nbSleeping = 0 ;
		this.nbBR = 0;
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
	public String getAdress() {
		return address;
	}
	
	/**
	 * get the number of chambers
	 * @return an integer positive or equal to zero
	 */
	public int getNbChambers() {
		return nbChambers;
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
	public int getNbBR() {
		return nbBR;
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
	 * @param floorArea is a real number superior or equal to zero with two decimals
	 */
	public void setFloorArea(double floorArea) {
		if (floorArea < 0)
			throw new IllegalArgumentException ("The floor area can not be negative");
		this.floorArea = floorArea;
	}

	/**
	 * @param address is a string of characters 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * @param nbChambers is an integer superior or equal to zero
	 */
	public void setNbChambers(int nbChambers) {
		if (nbChambers < 0)
			throw new IllegalArgumentException ("The number of chambers can not be negative");
		this.nbChambers = nbChambers;
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
	 * @param nbBR is an integer superior or equal to zero 
	 */
	public void setNbBR(int nbBR) {
		if (nbBR < 0)
			throw new IllegalArgumentException ("The number of bathroom can not be negative");
		this.nbBR = nbBR ;
	}
	
	/**
	 * @param terrace is a boolean (true/false)
	 */
	public void setTerrace(boolean terrace) {
		this.terrace = terrace ;
	}

	/**
	 * @param floorAreaTerrace is a real number superior or equal to zero with two decimals, it only works if terrace = true (use setTerrace)
	 */
	public void setFloorAreaTerrace(double floorAreaTerrace) {
		if (this.terrace = false & floorAreaTerrace > 0)
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
	 * @param priceN is a real number superior or equal to zero with two decimals
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
