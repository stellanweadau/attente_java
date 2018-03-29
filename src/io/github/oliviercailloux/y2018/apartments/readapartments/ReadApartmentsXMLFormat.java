package io.github.oliviercailloux.y2018.apartments.readapartments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class ReadApartmentsXMLFormat {
	
	private Properties prop;

	public ReadApartmentsXMLFormat() {
		
		prop = new Properties();
	}
	
	/**
	 * This method enables to read a XML file and store the informations into an apartment object. The XML file must contain at list a value for floorArea address and title. The key of each parameters in the XML file is respectively the name of the parameter. 
	 * @param input is the path of XML file
	 * @return an apartment object with values for each parameters found in the XML files and default values for the other parameters.
	 * @throws IOException
	 */
	public Apartment readApartment(InputStream input) throws IOException{
		
		prop.loadFromXML(input);
		
		Apartment apartment = new Apartment(Double.parseDouble(prop.getProperty("floorArea")),prop.getProperty("address"),prop.getProperty("title"));
		
		if (prop.containsKey("description"))
			apartment.setDescription(prop.getProperty("description"));
		if (prop.containsKey("nbBathrooms"))
			apartment.setNbBathrooms(Integer.parseInt(prop.getProperty("nbBathrooms")));
		if (prop.containsKey("floorAreaTerrace"))
			apartment.setFloorAreaTerrace(Double.parseDouble(prop.getProperty("floorAreaTerrace")));
		if (prop.containsKey("wifi"))
			apartment.setWifi(Boolean.valueOf(prop.getProperty("floorAreaTerrace")));
		if (prop.containsKey("tele"))
			apartment.setTele(Boolean.valueOf(prop.getProperty("tele")));
		if (prop.containsKey("terrace"))
			apartment.setTerrace(Boolean.valueOf(prop.getProperty("terrace")));
		if (prop.containsKey("nbSleeping"))
			apartment.setNbSleeping(Integer.parseInt(prop.getProperty("nbSleeping")));
		if (prop.containsKey("nbBedrooms"))
			apartment.setNbBedrooms(Integer.parseInt(prop.getProperty("nbBedrooms")));
		if (prop.containsKey("pricePerNight"))
			apartment.setPricePerNight(Double.parseDouble(prop.getProperty("pricePerNight")));
		if (prop.containsKey("nbMinNight"))
			apartment.setNbMinNight(Integer.parseInt(prop.getProperty("nbMinNight")));
		
		return apartment;
	}

	public static void main(String[] args) throws IOException {
		
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
		
		File f = new File("resources\\testXMLApartment.xml");
		
		try (InputStream input = new FileInputStream(f)){
			
			Apartment a = r.readApartment(input);
			
			System.out.println(a.getTitle());
			System.out.println(a.getAddress());
			System.out.println(a.getFloorArea());
			System.out.println(a.getDescription());
			System.out.println(a.getNbBathrooms());
			System.out.println(a.getWifi());
			
		}
		
		
		
		
		

	}

}
