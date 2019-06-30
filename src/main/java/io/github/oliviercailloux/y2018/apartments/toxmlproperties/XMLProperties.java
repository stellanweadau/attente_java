package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties {

	private Properties properties;

	private final static Logger LOGGER = LoggerFactory.getLogger(XMLProperties.class);

	public XMLProperties() {
		this.properties = new Properties();
	}

	/**
	 * toXml transform an Apartment into an xml File. The user specify the file in
	 * parameter
	 * 
	 * @param a       the apartment to put into an xml file
	 * @param xmlFile an file object where the apartment will be store. Warning : if
	 *                the file already exists, it will be erased.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void toXML(Apartment a, OutputStream xmlFile) throws IOException {

		properties.setProperty("nbSleeping", String.valueOf(a.getNbSleeping()));
		properties.setProperty("nbMinNight", String.valueOf(a.getNbMinNight()));
		properties.setProperty("terrace", String.valueOf(a.getTerrace()));
		properties.setProperty("nbBedrooms", String.valueOf(a.getNbBedrooms()));
		properties.setProperty("floorArea", String.valueOf(a.getFloorArea()));
		properties.setProperty("pricePerNight", String.valueOf(a.getPricePerNight()));
		properties.setProperty("tele", String.valueOf(a.getTele()));
		properties.setProperty("wifi", String.valueOf(a.getWifi()));
		properties.setProperty("title", a.getTitle());
		properties.setProperty("nbBathrooms", String.valueOf(a.getNbBathrooms()));
		properties.setProperty("address", a.getAddress());
		properties.setProperty("floorAreaTerrace", String.valueOf(a.getFloorAreaTerrace()));
		properties.setProperty("description", a.getDescription());
		properties.storeToXML(xmlFile, "Generated file for the apartment " + a.getTitle());

		LOGGER.info("Stream has been closed");

	}
}