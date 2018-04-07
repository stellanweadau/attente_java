package toxmlpropertiesTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

class ToXmlPropertiesTest {

	@Test
	void ToXmlPropertiesTesttest() throws FileNotFoundException, IOException {
		ReadApartmentsXMLFormat r1 = new ReadApartmentsXMLFormat();
		
		String f = new File("xml\\GeneratedApartment.xml").getAbsolutePath();
		
		try (InputStream input = new FileInputStream(f)){
			Apartment b = r1.readApartment(input);
			Assert.assertEquals("Poubelle", b.getTitle());
			Assert.assertEquals("888 rue du jackpot", b.getAddress());
			Assert.assertEquals(77.0, b.getFloorArea(), 1);
		}
		
		

	}

}
