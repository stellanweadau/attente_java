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
		// TO DO test que ça s'est bien écris dans le fichier xml
	}
}
