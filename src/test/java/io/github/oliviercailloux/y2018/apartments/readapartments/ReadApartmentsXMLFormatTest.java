package io.github.oliviercailloux.y2018.apartments.readapartments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/** Test class for ReadApartmentsXMLFormat class. */
class ReadApartmentsXMLFormatTest {

  @Test
  void readApartmentTest() throws IOException {

    ReadApartmentsXMLFormat r = initializeReadApartmentsXMLFormat();

    try (InputStream f =
        ReadApartmentsXMLFormatTest.class.getResourceAsStream("start-apartment-classpath.xml")) {

      Apartment a = r.readApartment(f);

      assertEquals("5 avenue Roger Salengro 92370 Chaville France", a.getAddress());
      assertEquals("Villa à louer", a.getTitle());
      assertEquals("", a.getDescription());
      assertEquals(5, a.getNbSleeping());
      assertEquals(0, a.getNbBathrooms());
      assertEquals(0, a.getNbBedrooms());
      assertEquals(150.0, a.getFloorArea());
      assertEquals(160.0, a.getFloorAreaTerrace());
      assertEquals(3, a.getPricePerNight());
      assertEquals(0, a.getNbMinNight());
      assertTrue(a.getTele());
      assertTrue(a.getTerrace());
      assertTrue(a.getWifi());
    }
  }

  private ReadApartmentsXMLFormat initializeReadApartmentsXMLFormat() {

    ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

    return r;
  }
}
