package io.github.oliviercailloux.y2018.apartments.apartment;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Preconditions.checkArgument;

public class Apartment extends Object {

	private static final Logger LOGGER = LoggerFactory.getLogger(Apartment.class);

	/**
	 * @param a real number it represents the floor area of the apartment in square
	 *          meters. Has to be initialized, if not, the apartment can't be
	 *          created without a floor area.
	 */
	private double floorArea;

	/**
	 * @param a String of characters that gives the full location of the apartment
	 *          (number, street name, ZIP code, city, country), has to initialized
	 *          Has to be initialized, if not, the apartment can't be created
	 *          without an address.
	 */
	private String address;

	/**
	 * @param an integer superior or equal to zero, it is the number of bedrooms
	 *           available of use in the apartment. if not initialized, the
	 *           parameter will have a default value of <code>0</code> which mean
	 *           the apartment doesn't have any bedroom.
	 */
	private int nbBedrooms;

	/**
	 * @param an integer superior or equal to zero corresponding of the
	 *           accommodation capacity of the apartment (nb of people that can
	 *           sleep in the apartment). If not initialized, the parameter will
	 *           have a default value of <code>0</code> which mean the apartment
	 *           doesn't have any place to sleep.
	 */
	private int nbSleeping;

	/**
	 * @param an integer superior or equal to zero which corresponds to the number
	 *           of bathrooms. If not initialized, the parameter will have a default
	 *           value set of <code>0</code> which mean the apartment doesn't have
	 *           any bathroom.
	 */
	private int nbBathrooms;

	/**
	 * @param a boolean (true/false) which indicates if there's a terrace or not can
	 *          be interpreted as hasTerrace. If not initialized, the parameter will
	 *          have a default value set of <code>false</code> which mean the
	 *          apartment doesn't have a terrace.
	 */
	private boolean hasTerrace;

	/**
	 * @param a real number superior or equal to zero it represents the floor area
	 *          of the terrace of the apartment if there's any. If not initialized,
	 *          the parameter will have a default value set of <code>0</code> which
	 *          mean the apartment have a floor area of terrace of zero, whether it
	 *          has or not a terrace.
	 */
	private double floorAreaTerrace;

	/**
	 * @param a string of characters that describe the apartment and the offer (its
	 *          accommodations). If not initialized, the parameter will have a
	 *          default value set of <code>""</code> which mean the announcement
	 *          doesn't have a description.
	 */
	private String description;

	/**
	 * @param a string of characters that represents the title of the announcement.
	 *          It has to be initialized, if not, the apartment can't be created
	 *          without a <code>title</code>.
	 */
	private String title;

	/**
	 * @param a boolean (true/false) which indicates if there is wireless connection
	 *          to Internet or not can be interpreted as hasWifi. If not
	 *          initialized, the parameter will have a default value of
	 *          <code>false</code> which mean the apartment doesn't have wifi.
	 */
	private boolean wifi;

	/**
	 * @param a real number superior or equal to zero, how much it cost (before any
	 *          fees) to stay per night in euros. If not initialized, the parameter
	 *          will have a default value of <code>0</code> which mean the apartment
	 *          doesn't have a price per night.
	 */
	private double pricePerNight;

	/**
	 * @param an integer superior or equal to zero, indicates how long in nights the
	 *           customer have to stay. If not initialized, the parameter will have
	 *           a default value of <code>0</code> which mean the apartment doesn't
	 *           have a minimum of night to stay-in.
	 */
	private int nbMinNight;

	/**
	 * @param a boolean (true/false) which indicates if there's a television or not
	 *          can be interpreted as hasTelevision. If not initialized, the
	 *          parameter will have a default set value of <code>false</code> which
	 *          mean the apartment doesn't have a tele.
	 */
	private boolean tele;

	/**
	 * Constructor by default to be used by Apartment.Builder
	 */
	private Apartment() {
		this.floorArea = 0;
		this.address = null;
		this.title = null;
		setNbBedrooms(0);
		setNbSleeping(0);
		setNbBathrooms(0);
		setTerrace(false);
		setFloorAreaTerrace(0);
		setDescription("");
		setWifi(false);
		setPricePerNight(0);
		setNbMinNight(0);
		setTele(false);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Apartment))
			return false;
		Apartment apart = (Apartment) obj;
		return apart.floorArea == this.floorArea && apart.address.equals(this.address) && apart.nbBedrooms == this.nbBedrooms
				&& apart.nbSleeping == this.nbSleeping && apart.nbBathrooms == this.nbBathrooms && apart.hasTerrace == this.hasTerrace
				&& apart.floorAreaTerrace == this.floorAreaTerrace && apart.description.equals(this.description)
				&& apart.title.equals(this.title) && apart.wifi == this.wifi && apart.pricePerNight == this.pricePerNight
				&& apart.nbMinNight == this.nbMinNight && apart.tele == this.tele;
	}

	@Override
	/**
	 * A toString meant to be used while in development for the testing
	 * 
	 * @return only the essential information of an apartment, which are the floor
	 *         area, its address and his title
	 */
	public String toString() {
		String floorAreaTS = "\nFloor area : " + Double.toString(this.floorArea) + " square meters";
		String addressTS = "\nAddress : " + this.address;
		String titleTS = "\nTitle : " + this.title;
		return floorAreaTS + addressTS + titleTS;
	}

	/**
	 * A toString based method meant to be used after the development for regular
	 * use
	 * 
	 * @return all information of an apartment
	 */
	public String toFullString() {
		String dispTitle;
		String dispAddress;
		String dispFloorArea;
		String dispNbBedrooms;
		String dispNbSleeping;
		String dispNbBathrooms;
		String dispTerrace;
		String dispFloorAreaTerrace;
		String dispDescription;
		String dispWifi;
		String dispPricePerNight;
		String dispNbMinNight;
		String dispTele;

		dispTitle = "\nTitle : " + this.title;
		dispAddress = "\nAddress : " + this.address;
		dispFloorArea = "\nFloor area : " + Double.toString(this.floorArea) + " square meters";
		dispNbBedrooms = "\nNumber of bedrooms : "
				+ ((this.nbBedrooms == 0) ? "N/A" : Integer.toString(this.nbBedrooms) + " bedroom(s)");
		dispNbSleeping = "\nNumber of sleeping (capacity) : "
				+ ((this.nbSleeping == 0) ? "N/A" : Integer.toString(this.nbSleeping) + " person(s)");
		dispNbBathrooms = "\nNumber of bathrooms : "
				+ ((this.nbBathrooms == 0) ? "N/A" : Integer.toString(this.nbBathrooms) + " bathroom(s)");
		dispTerrace = "\nTerrace : " + ((this.hasTerrace) ? "Yes" : "No");
		dispFloorAreaTerrace = new StringBuilder()
				.append(!(this.hasTerrace) ? "": "\nTerrace floor area : ")
				.append((floorAreaTerrace == 0) ? "N/A" : Double.toString(this.floorAreaTerrace) + " square meters")
				.toString();
		dispDescription = "\nDescription : " + ((this.description == "") ? "N/A" : this.description);
		dispWifi = "\nWifi : " + ((this.wifi) ? "Yes" : "No");
		dispTele = "\nTelevision : " + ((this.tele) ? "Yes" : "No");
		dispPricePerNight = "\nPrice per night : "
				+ ((this.pricePerNight == 0) ? "N/A" : Double.toString(this.pricePerNight) + "€");
		dispNbMinNight = "\nNumber of night minimum to rent this apartment : "
				+ ((this.nbMinNight == 0) ? "N/A" : Integer.toString(this.nbMinNight) + " night(s)");

		return dispTitle + dispAddress + dispFloorArea + dispNbBedrooms + dispNbSleeping + dispNbBathrooms + dispTerrace
				+ dispFloorAreaTerrace + dispDescription + dispWifi + dispPricePerNight + dispNbMinNight + dispTele;
	}

	/**
	 * get the value of the floor area
	 * 
	 * @return a double positive or equal to zero
	 */
	public double getFloorArea() {
		return this.floorArea;
	}

	/**
	 * get the value of the address
	 * 
	 * @return a String of characters
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * get the number of bedrooms, 0 if missing
	 * 
	 * @return an integer positive or equal to zero
	 */
	public int getNbBedrooms() {
		return this.nbBedrooms;
	}

	/**
	 * get the number of sleeping, 0 if missing
	 * 
	 * @return an integer positive or equal to zero
	 */
	public int getNbSleeping() {
		return this.nbSleeping;
	}

	/**
	 * get the number of bathrooms, 0 if missing
	 * 
	 * @return an integer positive or equal to zero
	 */
	public int getNbBathrooms() {
		return this.nbBathrooms;
	}

	/**
	 * get the presence (or absence) of a terrace
	 * 
	 * @return a boolean
	 */
	public boolean getTerrace() {
		return this.hasTerrace;
	}

	/**
	 * get the value of the floor area of the terrace (0 if there is no terrace)
	 * 
	 * @return a double positive or equal to zero
	 */
	public double getFloorAreaTerrace() {
		return this.floorAreaTerrace;
	}

	/**
	 * get the description of the apartment, "" if missing
	 * 
	 * @return a string of characters
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * get the title of the announcement
	 * 
	 * @return a string of characters
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * get the presence (or absence) of WiFi
	 * 
	 * @return a boolean
	 */
	public boolean getWifi() {
		return this.wifi;
	}

	/**
	 * get the price for one night, 0 if missing
	 * 
	 * @return a double positive or equal to zero
	 */
	public double getPricePerNight() {
		return this.pricePerNight;
	}

	/**
	 * get the minimum number of nights, 0 if missing
	 * 
	 * @return an integer positive or equal to zero
	 */
	public int getNbMinNight() {
		return this.nbMinNight;
	}

	/**
	 * get the presence (or absence) of a TV
	 * 
	 * @return a boolean
	 */
	public boolean getTele() {
		return this.tele;
	}

	/**
	 * @param floorArea is a real number superior or equal to zero
	 */
	private void setFloorArea(double floorArea) {
		checkArgument(floorArea >= 0, "The floor area should not be negative");
		this.floorArea = floorArea;
		LOGGER.info("The floor area has been set to " + floorArea);
	}

	/**
	 * @param address is a string of characters
	 */
	private void setAddress(String address) {
		checkArgument(!address.isEmpty() && address != null , "The address should not be empty");
		this.address = address;
		LOGGER.info("The address has been set to " + address);
	}

	/**
	 * @param nbBedrooms is an integer superior or equal to zero
	 */
	private void setNbBedrooms(int nbBedrooms) {
		checkArgument(nbBedrooms >= 0, "The number of Bedrooms can not be negative");
		this.nbBedrooms = nbBedrooms;
		LOGGER.info("The number of bathrooms has been set to " + nbBedrooms);
	}

	/**
	 * @param nbSleepings is an integer superior or equal to zero
	 */
	private void setNbSleeping(int nbSleeping) {
		checkArgument(nbSleeping >= 0, "The accomodation capacity can not be negative");
		this.nbSleeping = nbSleeping;
		LOGGER.info("The number of sleepings has been set to " + nbSleeping);
	}

	/**
	 * @param nbBathrooms is an integer superior or equal to zero
	 */
	private void setNbBathrooms(int nbBathrooms) {
		checkArgument(nbBathrooms >= 0, "The number of bathrooms can not be negative");
		this.nbBathrooms = nbBathrooms;
		LOGGER.info("The number of bathrooms has been set to " + nbBathrooms);
	}

	/**
	 * @param terrace is a boolean (true/false)
	 */
	private void setTerrace(boolean terrace) {
		this.hasTerrace = terrace;
		LOGGER.info("terrace has been set to " + terrace);
	}

	/**
	 * @param floorAreaTerrace is a real number superior or equal to zero, it only
	 *                         works if terrace = true (use setTerrace)
	 */
	private void setFloorAreaTerrace(double floorAreaTerrace) {
		checkArgument((!this.hasTerrace && floorAreaTerrace == 0) || (this.hasTerrace),
				"The terrace can not have a floor area if it doesn't exists");
		checkArgument(floorAreaTerrace >= 0, "The floor area of the terrace can not be negative");
		this.floorAreaTerrace = floorAreaTerrace;
		LOGGER.info("The floor area of the terrace has been set to " + floorAreaTerrace);
	}

	/**
	 * @param description is a string of characters
	 */
	private void setDescription(String description) {
		checkArgument(!description.isEmpty(), "The description should not be empty");
		this.description = description;
		LOGGER.info("The description has been set to " + description);
	}

	/**
	 * @param title is a string of characters
	 */
	private void setTitle(String title) {
		checkArgument((!title.isEmpty()) && (title != null), "The title should not be empty");
		this.title = title;
		LOGGER.info("The title has been set to " + floorArea);
	}

	/**
	 * @param wifi is a boolean (true/false)
	 */
	private void setWifi(boolean wifi) {
		this.wifi = wifi;
		LOGGER.info("The wifi has been set to " + wifi);
	}

	/**
	 * @param pricePerNight is a real number superior or equal to zero
	 */
	private void setPricePerNight(double pricePerNight) {
		checkArgument(pricePerNight >= 0, "The price per night can not be negative");
		this.pricePerNight = pricePerNight;
		LOGGER.info("The price per night has been set to " + pricePerNight);
	}

	/**
	 * @param nbMinNight is an integer superior or equal to zero
	 */
	private void setNbMinNight(int nbMinNight) {
		checkArgument(nbMinNight >= 0, "The minimum number of nights can not be negative");
		this.nbMinNight = nbMinNight;
		LOGGER.info("The number minimum of night has been set to " + nbMinNight);
	}

	/**
	 * @param tele is a boolean (true/false)
	 */
	private void setTele(boolean tele) {
		this.tele = tele;
		LOGGER.info("The tele has been set to " + tele);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, floorArea, nbBedrooms, nbSleeping, nbBathrooms, hasTerrace, floorAreaTerrace,
				description, title, wifi, pricePerNight, nbMinNight, tele);
	}

	/**
	 * Code inspired from :
	 * https://codereview.stackexchange.com/questions/127391/simple-builder-pattern-implementation-for-building-immutable-objects/127509#127509
	 */
	public static class Builder {

		private Apartment apartmentToBuild;

		public Builder() {
			apartmentToBuild = new Apartment();
		}

		public Apartment build() {
			if(apartmentToBuild.getFloorArea() < 0) {
				throw new IllegalStateException("The floor area of the apartment cannot be negative");
			}else if(apartmentToBuild.getAddress() == null) {
				throw new IllegalStateException("The address of the apartment must be specified");
			}else if(apartmentToBuild.getTitle() == null) {
				throw new IllegalStateException("The title of the apartment must be specified");
			}else {
				Apartment buildApartment = apartmentToBuild;
				apartmentToBuild = new Apartment();
				return buildApartment;
			}
		}

		public Builder setFloorArea(double floorArea) {
			this.apartmentToBuild.setFloorArea(floorArea);
			return this;
		}

		public Builder setAddress(String address) {
			this.apartmentToBuild.setAddress(address);
			return this;
		}

		public Builder setTitle(String title) {
			this.apartmentToBuild.setTitle(title);
			return this;
		}

		public Builder setNbBedrooms(int nbBedrooms) {
			this.apartmentToBuild.setNbBedrooms(nbBedrooms);
			return this;
		}

		public Builder setNbSleeping(int nbSleeping) {
			this.apartmentToBuild.setNbSleeping(nbSleeping);
			return this;
		}

		public Builder setNbBathrooms(int nbBathrooms) {
			this.apartmentToBuild.setNbBathrooms(nbBathrooms);
			return this;
		}

		public Builder setFloorAreaTerrace(double floorAreaTerrace) {
			this.apartmentToBuild.setFloorAreaTerrace(floorAreaTerrace);
			return this;
		}

		public Builder setPricePerNight(double pricePerNight) {
			this.apartmentToBuild.setPricePerNight(pricePerNight);
			return this;
		}

		public Builder setNbMinNight(int nbMinNight) {
			this.apartmentToBuild.setNbMinNight(nbMinNight);
			return this;
		}

		public Builder setTerrace(boolean terrace) {
			this.apartmentToBuild.setTerrace(terrace);
			return this;
		}
		
		public Builder setDescription(String description) {
			this.apartmentToBuild.setDescription(description);
			return this;
		}
		
		public Builder setWifi(boolean wifi) {
			this.apartmentToBuild.setWifi(wifi);
			return this;
		}
		
		public Builder setTele(boolean tele) {
			this.apartmentToBuild.setTele(tele);
			return this;
		}
	}

}
