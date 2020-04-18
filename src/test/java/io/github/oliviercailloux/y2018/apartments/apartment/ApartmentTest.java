package io.github.oliviercailloux.y2018.apartments.apartment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ApartmentTest {

	private double floorArea = 456.56;
	private String address = "A Random Address";
	private int nbBedrooms = 5;
	private int nbSleeping = 9;
	private int nbBathrooms = 3;
	private boolean terrace = true;
	private double floorAreaTerrace = 25.32;
	private String description = "A Random Description for A Random House in A Random Address";
	private String title = "A Random House";
	private boolean wifi = true;
	private double pricePerNight = 45.95;
	private int nbMinNight = 60;
	private boolean tele = false;

	@Test
	void equalsTestTrue() {
		Apartment a1 = ApartmentFactory.generateApartment(floorArea, address, nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, title, wifi, pricePerNight, nbMinNight, tele);

		Apartment a2 = ApartmentFactory.generateApartment(floorArea, address, nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, title, wifi, pricePerNight, nbMinNight, tele);
		assertTrue(a1.equals(a2));
	}

	@Test
	void equalsTestFalse() {
		Apartment a1 = ApartmentFactory.generateApartment(floorArea, "Chaville", nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, "Marc's home", wifi, pricePerNight, nbMinNight, tele);
		Apartment a2 = ApartmentFactory.generateApartment(floorArea, "Arras", nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, "Micky's home", wifi, pricePerNight, nbMinNight, tele);

		assertFalse(a1.equals(a2));

	}

	@Test
	void hashCodeTestTrue() {
		Apartment a1 = ApartmentFactory.generateApartment(floorArea, address, nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, title, wifi, pricePerNight, nbMinNight, tele);

		Apartment a2 = ApartmentFactory.generateApartment(floorArea, address, nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, title, wifi, pricePerNight, nbMinNight, tele);

		assertEquals(a1.hashCode(), a2.hashCode());

	}

	@Test
	void hashCodeTestFalse() {
		Apartment a1 = ApartmentFactory.generateApartment(floorArea, "Chaville", nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, "Marc's home", wifi, pricePerNight, nbMinNight, tele);
		Apartment a2 = ApartmentFactory.generateApartment(floorArea, "Arras", nbBedrooms, nbSleeping, nbBathrooms,
				terrace, floorAreaTerrace, description, "Micky's home", wifi, pricePerNight, nbMinNight, tele);

		assertFalse(a1.hashCode() == a2.hashCode());

	}

}
