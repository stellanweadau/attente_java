package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readApartments.ReadTwoApartmentsTest;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

class ToXmlPropertiesTest {
	@Test

	void readApartmentTest() throws IllegalArgumentException, IllegalAccessException, IOException
	{
		XMLProperties j = new XMLProperties();
		Apartment a = new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo");
		a.setNbSleeping(10);
		a.setNbMinNight(1);
		a.setTerrace(true);
		a.setNbBedrooms(5);
		a.setPricePerNight(404);
		a.setTele(false);
		a.setWifi(true);
		a.setNbBathrooms(1);
		a.setDescription("Un igloo tout mignon en compagnie du père noël et de la mère noël");
		a.setFloorAreaTerrace(8.6);
		File f = new File("src/test/resources/io/github/oliviercailloux/y2018/apartments/readApartments/xmlfileTest.xml");
		j.toXML(a, f);
<<<<<<< HEAD
		
=======
>>>>>>> 98e6a31c1b458822bde7fa273ff6f48c51a5011d
		
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		

		try (InputStream f1 = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml")){

			Apartment a1 = r.readApartment(f1);

			Assert.assertEquals("Address doesn't match with the address in the XML file","118 rue du père noel 77480",a1.getAddress());
			Assert.assertEquals("Title doesn't match with the title in the XML file","Grand Igloo",a1.getTitle());
			Assert.assertEquals("Description doesn't match with the description in the XML file","Un igloo tout mignon en compagnie du père noël et de la mère noël",a1.getDescription());
			Assert.assertEquals("Number of sleepings doesn't match with the number of sleepings in the XML file",10,a1.getNbSleeping());
			Assert.assertEquals("Number of bathrooms doesn't match with the number of bathrooms in the XML file",1,a1.getNbBathrooms());
			Assert.assertEquals("Number of bedrooms doesn't match with the number of bedrooms in the XML file",5,a1.getNbBedrooms());
			Assert.assertEquals("Floor area doesn't match with the floor area in the XML file",1182118.48,a1.getFloorArea(),0);
			Assert.assertEquals("Floor area terrace doesn't match with the floor area terrace in the XML file",8.6,a1.getFloorAreaTerrace(),0);
			Assert.assertEquals("Price per night doesn't match with the price per night in the XML file",404.0,a1.getPricePerNight(),0);
			Assert.assertEquals("Minimum number of nights doesn't match with the minimum number of nights in the XML file",1,a1.getNbMinNight());
			Assert.assertFalse("The value of boolean tele doesn't match with the value of tele in the XML File",a1.getTele());
			Assert.assertTrue("The value of boolean terrace doesn't match with the value of terrace in the XML File",a1.getTerrace());
			Assert.assertTrue("The value of boolean wifi doesn't match with the value of wifi in the XML File",a1.getWifi());
			
		}
		
		ReadApartmentsXMLFormat r1 = new ReadApartmentsXMLFormat();
		try (InputStream f1 = ReadTwoApartmentsTest.class.getResourceAsStream("xmlfileTest.xml")){
			Apartment a1 = r1.readApartment(f1);
			System.out.println(a1);
			
		}
	}
}
