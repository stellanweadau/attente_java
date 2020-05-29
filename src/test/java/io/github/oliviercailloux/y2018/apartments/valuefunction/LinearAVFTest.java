package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Range;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

class LinearAVFTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LinearAVFTest.class);
	
	LinearAVF linearAVF;
	Apartment a;

	/**
	 * Inits the apartmentValueFunction object and the apartment before each tests
	 */
	@BeforeEach
	void initEach() {
		LinearAVF.Builder builderLinearAVF = new LinearAVF.Builder();
		a = new Builder().setFloorArea(250).setAddress("108 rue de chat-ville Ville-d'Avray 92410").setNbBedrooms(1)
				.setNbSleeping(4).setNbBathrooms(1).setTerrace(true).setFloorAreaTerrace(40)
				.setDescription("Une ferme rustique en compagnie de Dwight Schrute, interdit à Jim Halpert")
				.setTitle("Une ferme").setWifi(false).setPricePerNight(3.3).setNbMinNight(3).setTele(false).build();

		LinearValueFunction floorAreaV = new LinearValueFunction(0d, 200d);
		builderLinearAVF.setFloorAreaValueFunction(floorAreaV);

		LinearValueFunction nbSleepingV = new LinearValueFunction(3d, 5d);
		builderLinearAVF.setNbSleepingValueFunction(nbSleepingV);

		ReversedLinearValueFunction nbMinNightV = new ReversedLinearValueFunction(7d, 30d);
		builderLinearAVF.setNbMinNightValueFunction(nbMinNightV);

		BooleanValueFunction terraceV = new BooleanValueFunction(true);
		builderLinearAVF.setTerraceValueFunction(terraceV);

		LinearValueFunction nbBedroomsV = new LinearValueFunction(3d, 4d);
		builderLinearAVF.setNbBedroomsValueFunction(nbBedroomsV);

		ReversedLinearValueFunction pricePerNightV = new ReversedLinearValueFunction(20d, 40d);
		builderLinearAVF.setPricePerNightValueFunction(pricePerNightV);

		BooleanValueFunction teleV = new BooleanValueFunction(true);
		builderLinearAVF.setTeleValueFunction(teleV);

		BooleanValueFunction wifiV = new BooleanValueFunction(true);
		builderLinearAVF.setWifiValueFunction(wifiV);

		LinearValueFunction nbBathroomsV = new LinearValueFunction(2d, 3d);
		builderLinearAVF.setNbBathroomsValueFunction(nbBathroomsV);

		LinearValueFunction floorAreaTerraceV = new LinearValueFunction(30d, 50d);
		builderLinearAVF.setFloorAreaTerraceValueFunction(floorAreaTerraceV);
		for (Criterion c : Criterion.getCriterias()) {
			builderLinearAVF.setWeightRange(c, Range.closed(5d, 15d));
		}
		linearAVF = builderLinearAVF.build();
	}

	/**
	 * Function to test the some setters
	 */
	@Test
	void checkValue() {
		LinearValueFunction lvf = linearAVF.getNbSleepingValueFunction();
		assertEquals(5d, lvf.getInterval().upperEndpoint());
		lvf = linearAVF.getNbBedroomsValueFunction();
		assertEquals(4d, lvf.getInterval().upperEndpoint());
		lvf = linearAVF.getNbBathroomsValueFunction();
		assertEquals(3d, lvf.getInterval().upperEndpoint());
	}

	/**
	 * Function to test the computing of the subjective value of an apartment
	 */
	@Test
	void linearAVFTest() {
		assertEquals(0.5, linearAVF.getSubjectiveValue(a), 0.0001);
		linearAVF.setWeightRange(Criterion.TELE, Range.closed(2d, 8d));
		assertEquals(10d, linearAVF.getMiddleOfRange(Criterion.TELE));
		assertEquals(0.5, linearAVF.getSubjectiveValue(a), 0.00001);
	}

	/**
	 * Test if the weight setter throw a Illegal Argument Exception when needed
	 */
	@Test
	void exceptionIllegalArgWeightSetter() {
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.FLOOR_AREA, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.NB_BEDROOMS, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.NB_SLEEPING, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.NB_BATHROOMS, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.TERRACE, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.FLOOR_AREA_TERRACE, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.WIFI, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.PRICE_PER_NIGHT, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.NB_MIN_NIGHT, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.TELE, Range.closed(-2d, 0d)));
	}

	/**
	 * Function to test the adaptation to the subjective value weight of a criteria
	 */
	@Test
	void adaptWeightTest() {
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.TELE, Range.closed(-2d, 0d)));
		assertThrows(IllegalArgumentException.class,
				() -> linearAVF.setWeightRange(Criterion.TERRACE, Range.closed(0d, 0d)));
		assertEquals(Range.closed(5d, 15d), linearAVF.getWeightRange(Criterion.TELE));
		assertEquals(Range.closed(5d, 15d), linearAVF.getWeightRange(Criterion.TERRACE));
		linearAVF = linearAVF.adaptWeightRange(Criterion.TERRACE, true);
		LOGGER.info("************" + linearAVF.getWeightRange(Criterion.TERRACE).lowerEndpoint() + "/"
				+ linearAVF.getWeightRange(Criterion.TERRACE).upperEndpoint());
		assertEquals(Range.closed(10d, 15d), linearAVF.getWeightRange(Criterion.TERRACE));
		assertEquals(12.5d, linearAVF.getMiddleOfRange(Criterion.TERRACE));
		linearAVF = linearAVF.adaptWeightRange(Criterion.TELE, false);
		assertEquals(Range.closed(5d, 10d), linearAVF.getWeightRange(Criterion.TELE));
		assertEquals(7.5d, linearAVF.getMiddleOfRange(Criterion.TELE));
	}

	/**
	 * Function to test if the bounds of an interval adapt well when needed
	 */
	@Test
	void adaptBoundsTest() {
		assertThrows(IllegalArgumentException.class, () -> linearAVF.adaptBounds(Criterion.TELE, 0d, true));
		linearAVF = linearAVF.adaptBounds(Criterion.FLOOR_AREA_TERRACE, 25d, true);
		LinearValueFunction lvf = linearAVF.getFloorAreaTerraceValueFunction();
		assertEquals(25d, lvf.getInterval().lowerEndpoint());
		assertEquals(0.6, linearAVF.getFloorAreaTerraceValueFunction().getSubjectiveValue(a.getFloorAreaTerrace()));
		linearAVF = linearAVF.adaptBounds(Criterion.NB_BEDROOMS, 8, false);
		double nbRooms = a.getNbBedrooms();
		assertEquals(0d, linearAVF.getNbBedroomsValueFunction().getSubjectiveValue(nbRooms));
		lvf = linearAVF.getNbBedroomsValueFunction();
		assertEquals(8, lvf.getInterval().upperEndpoint());
	}

	/**
	 * Function to test if a random apartment generated respects some criteria
	 */
	@Test
	void getRandomApartmentValueFunctionTest() {
		ApartmentValueFunction apart = ApartmentValueFunction.getRandomApartmentValueFunction();
		assertEquals(1d, apart.getFloorAreaValueFunction().getSubjectiveValue(a.getFloorArea()));
		LinearValueFunction lvf = (LinearValueFunction) apart.getFloorAreaTerraceValueFunction();
		assertTrue(lvf.getInterval().upperEndpoint() <= 101d);
		assertTrue(apart.getSubjectiveValueWeight(Criterion.TELE) <= 1d);
		double sum = apart.getSubjectiveValueWeight(Criterion.TELE)
				+ apart.getSubjectiveValueWeight(Criterion.FLOOR_AREA)
				+ apart.getSubjectiveValueWeight(Criterion.FLOOR_AREA_TERRACE)
				+ apart.getSubjectiveValueWeight(Criterion.NB_BATHROOMS)
				+ apart.getSubjectiveValueWeight(Criterion.NB_BEDROOMS)
				+ apart.getSubjectiveValueWeight(Criterion.NB_SLEEPING)
				+ apart.getSubjectiveValueWeight(Criterion.NB_MIN_NIGHT)
				+ apart.getSubjectiveValueWeight(Criterion.PRICE_PER_NIGHT)
				+ apart.getSubjectiveValueWeight(Criterion.TERRACE) + apart.getSubjectiveValueWeight(Criterion.WIFI);
		assertEquals(1d, sum, 0.00001);
	}
}
