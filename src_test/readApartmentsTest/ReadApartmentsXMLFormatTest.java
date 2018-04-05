package readApartmentsTest;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;



import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;


class ReadApartmentsXMLFormatTest {

	@Test
	void readApartmentAddressTest() throws IOException{

		ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

		File f = new File("resources\\start-apartment-classpath.xml");

		try (InputStream input = new FileInputStream(f)){

			Apartment a = r.readApartment(input);

			String s = "5 avenue Roger Salengro 92370 Chaville France";

			Assert.assertEquals(s,a.getAddress(),"Address doesn't match with the address in the XML file");
		}
	}
		
	@Test
	void readApartmentFloorAreaTest() throws IOException{

		ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

		File f = new File("resources\\start-apartment-classpath.xml");

		try (InputStream input = new FileInputStream(f)){

			Apartment a = r.readApartment(input);

			Assert.assertEquals(a.getFloorArea(),150.0,0);

		}


	}
	
	@Test
	void readApartmentNbSleepingTest() throws IOException{

		ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

		File f = new File("resources\\start-apartment-classpath.xml");

		try (InputStream input = new FileInputStream(f)){

			Apartment a = r.readApartment(input);

			Assert.assertEquals(a.getNbSleeping(),5,0);

		}


	}
	
	@Test
	void readApartmentFloorAreaTerraceTest() throws IOException{

		ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

		File f = new File("resources\\start-apartment-classpath.xml");

		try (InputStream input = new FileInputStream(f)){

			Apartment a = r.readApartment(input);

			Assert.assertEquals(a.getFloorAreaTerrace(),160,0);

		}


	}
	
	@Test
	void readApartmentPricePerNightTest() throws IOException{

		ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

		File f = new File("resources\\start-apartment-classpath.xml");

		try (InputStream input = new FileInputStream(f)){

			Apartment a = r.readApartment(input);

			Assert.assertEquals(a.getPricePerNight(),3,0);

		}


	}
	
	private ReadApartmentsXMLFormat initializeReadApartmentsXMLFormat() {
		
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
		
		return r;
	}

}
