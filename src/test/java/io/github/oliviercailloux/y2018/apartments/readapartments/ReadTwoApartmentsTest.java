package io.github.oliviercailloux.y2018.apartments.readapartments;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class corresponds to the Resource2 of the project. It displays two apartments from XML file,
 * one included in the classpath or one not included in the classpath.
 */
public class ReadTwoApartmentsTest {

  /**
   * Retrieve the apartment in XML format inside the resources folder and another apartment at the
   * root of the project using the ReadApartmentsXMLFormat method, then print the apartment objects
   * which contains specifications of these 2 differents apartments.
   *
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static void main(String[] args) throws FileNotFoundException, IOException {

    ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();
    ReadApartmentsXMLFormat r1 = new ReadApartmentsXMLFormat();

    String f1 = new File("start-apartment.xml").getAbsolutePath();

    try (InputStream f =
        ReadTwoApartmentsTest.class.getResourceAsStream("start-apartment-classpath.xml")) {
      Apartment a = r.readApartment(f);
      System.out.println(a);
    }

    try (InputStream input = new FileInputStream(f1)) {
      Apartment b = r1.readApartment(input);
      System.out.println(b);
    }
  }
}
