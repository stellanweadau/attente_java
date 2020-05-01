package io.github.oliviercailloux.y2018.apartments.apartment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

/**
 * Test class for ApartmentFactory.
 *
 */
class ApartmentFactoryTest {

	/**
	 * Test GenerateApartment function tests the correspondence between the results
	 * sent and those recorded.
	 */
	@Test
	void testGenerateApartmentFromParameters() {
		double floorArea = 456.56;
		String address = "A Random Address";
		int nbBedrooms = 5;
		int nbSleeping = 9;
		int nbBathrooms = 3;
		boolean hasTerrace = true;
		double floorAreaTerrace = 25.32;
		String description = "A Random Description for A Random House in A Random Address";
		String title = "A Random House";
		boolean wifi = true;
		double pricePerNight = 45.95;
		int nbMinNight = 60;
		boolean tele = false;
		Builder apartBuilder = new Builder();
		Apartment apart = apartBuilder.setFloorArea(floorArea).setAddress(address).setNbBedrooms(nbBedrooms)
				.setNbSleeping(nbSleeping).setNbBathrooms(nbBathrooms).setTerrace(hasTerrace)
				.setFloorAreaTerrace(floorAreaTerrace).setDescription(description).setTitle(title).setWifi(wifi)
				.setPricePerNight(pricePerNight).setNbMinNight(nbMinNight).setTele(tele).build();

		assertEquals(floorArea, apart.getFloorArea());
		assertEquals(address, apart.getAddress());
		assertEquals(nbBedrooms, apart.getNbBedrooms());
		assertEquals(nbSleeping, apart.getNbSleeping());
		assertEquals(nbBathrooms, apart.getNbBathrooms());
		assertEquals(hasTerrace, apart.getTerrace());
		assertEquals(floorAreaTerrace, apart.getFloorAreaTerrace());
		assertEquals(description, apart.getDescription());
		assertEquals(title, apart.getTitle());
		assertEquals(wifi, apart.getWifi());
		assertEquals(pricePerNight, apart.getPricePerNight());
		assertEquals(nbMinNight, apart.getNbMinNight());
		assertEquals(tele, apart.getTele());
	}

	/**
	 * This function allow us to test if we have an exception thrown when the json
	 * path given in argument of the method is wrong.
	 */
	@Test
	public void generateApartmentFromJsonExceptionTest() {
		assertThrows(IOException.class, () -> ApartmentFactory.generateApartmentsFromJsonPath(Path.of("abc")));
	}

}
