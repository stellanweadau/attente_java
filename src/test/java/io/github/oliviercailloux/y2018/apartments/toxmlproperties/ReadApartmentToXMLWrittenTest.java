package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

/**
 *
 * Reuse of Resources2
 */
public class ReadApartmentToXMLWrittenTest {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		try (InputStream f = ReadApartmentToXMLWrittenTest.class.getResourceAsStream("xmlfileTest.xml")){
			Apartment a = r.readApartment(f);
			System.out.println(a);
		}

	}

}
