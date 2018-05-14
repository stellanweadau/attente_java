package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;



import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

/**
 * Test class for ReadApartmentsXMLFormat class.
 *
 */
class ReadApartmentsToXMLWrittenTest {

	
	@Test
	void readApartmentTest() throws IOException{

		ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

		

		try (InputStream f = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml")){

			Apartment a = r.readApartment(f);

			Assert.assertEquals("Address doesn't match with the address in the XML file","118 rue du père noel 77480",a.getAddress());
			Assert.assertEquals("Title doesn't match with the title in the XML file","Grand Igloo",a.getTitle());
			Assert.assertEquals("Description doesn't match with the description in the XML file","Un igloo tout mignon en compagnie du père noël et de la mère noël",a.getDescription());
			Assert.assertEquals("Number of sleepings doesn't match with the number of sleepings in the XML file",10,a.getNbSleeping());
			Assert.assertEquals("Number of bathrooms doesn't match with the number of bathrooms in the XML file",1,a.getNbBathrooms());
			Assert.assertEquals("Number of bedrooms doesn't match with the number of bedrooms in the XML file",5,a.getNbBedrooms());
			Assert.assertEquals("Floor area doesn't match with the floor area in the XML file",1282118.48,a.getFloorArea(),0);
			Assert.assertEquals("Floor area terrace doesn't match with the floor area terrace in the XML file",8.6,a.getFloorAreaTerrace(),0);
			Assert.assertEquals("Price per night doesn't match with the price per night in the XML file",404.0,a.getPricePerNight(),0);
			Assert.assertEquals("Minimum number of nights doesn't match with the minimum number of nights in the XML file",1,a.getNbMinNight());
			Assert.assertTrue("The value of boolean tele doesn't match with the value of tele in the XML File",a.getTele());
			Assert.assertTrue("The value of boolean terrace doesn't match with the value of terrace in the XML File",a.getTerrace());
			Assert.assertTrue("The value of boolean wifi doesn't match with the value of wifi in the XML File",a.getWifi());
			

		}


	}
	
	private ReadApartmentsXMLFormat initializeReadApartmentsXMLFormat() {
		
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
		
		return r;
	}

}
