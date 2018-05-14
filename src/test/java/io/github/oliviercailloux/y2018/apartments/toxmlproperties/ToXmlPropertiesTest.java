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
		Apartment a = new Apartment(80.5, "6 rue des paquerette 74000 Annecy", "Petit Manoir de campagne");
		File f = new File("src/test/resources/xmlfile.xml");
		j.toXML(a, f);
		
		// TO DO test que ça s'est bien écris dans le fichier xml
	}
}
