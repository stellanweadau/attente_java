package io.github.oliviercailloux.y2018.apartments.readapartments;

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
		
		
		return apartment;
	}

	public static void main(String[] args) throws IOException {
		
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
		
		InputStream input = new FileInputStream("C:\\Users\\Guillaume\\Documents\\Dauphine\\L3 MIAGE\\Semestre 6\\Java\\test.xml");
		
		Apartment a = r.readApartment(input);
		
		System.out.println(a.getTitle());
		System.out.println(a.getAddress());
		System.out.println(a.getFloorArea());
		System.out.println(a.getDescription());
		System.out.println(a.getNbBathrooms());
		System.out.println(a.getWifi());
		
		
		
		
		
		

	}

}
