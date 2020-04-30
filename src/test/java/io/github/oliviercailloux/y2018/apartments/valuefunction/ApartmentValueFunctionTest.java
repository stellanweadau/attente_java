package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.BooleanValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

class ApartmentValueFunctionTest {

	ApartmentValueFunction valueFunction = new ApartmentValueFunction();
	Apartment a;

	@Test
	void apartmentValueFunctionTest() throws NumberFormatException {

		a = new Builder().setFloorArea(250)
				.setAddress("108 rue de chat-ville Ville-d'Avray 92410")
				.setNbBedrooms(1)
				.setNbSleeping(4)
				.setNbBathrooms(1)
				.setTerrace(true)
				.setFloorAreaTerrace(40)
				.setDescription("Une ferme rustique en compagnie de Dwight Schrute, interdit à Jim Halpert")
				.setTitle("Une ferme")
				.setWifi(false)
				.setPricePerNight(3.3)
				.setNbMinNight(3)
				.setTele(false)
				.build();

		LinearValueFunction floorAreaV = new LinearValueFunction(0d, 200d);
		valueFunction.setFloorAreaValueFunction(floorAreaV);
		assertEquals(1d, floorAreaV.getSubjectiveValue(a.getFloorArea()));

		LinearValueFunction nbSleepingV = new LinearValueFunction(3d, 5d);
		valueFunction.setNbSleepingValueFunction(nbSleepingV);

		ReversedLinearValueFunction nbMinNightV = new ReversedLinearValueFunction(7d, 30d);
		valueFunction.setNbMinNightValueFunction(nbMinNightV);

		BooleanValueFunction terraceV = new BooleanValueFunction(true);
		valueFunction.setTerraceValueFunction(terraceV);

		LinearValueFunction nbBedroomsV = new LinearValueFunction(3d, 4d);
		valueFunction.setNbBedroomsValueFunction(nbBedroomsV);

		ReversedLinearValueFunction pricePerNightV = new ReversedLinearValueFunction(20d, 40d);
		valueFunction.setPricePerNightValueFunction(pricePerNightV);

		BooleanValueFunction teleV = new BooleanValueFunction(true);
		valueFunction.setTeleValueFunction(teleV);

		BooleanValueFunction wifiV = new BooleanValueFunction(true);
		valueFunction.setWifiValueFunction(wifiV);

		LinearValueFunction nbBathroomsV = new LinearValueFunction(2d, 3d);
		valueFunction.setNbBathroomsValueFunction(nbBathroomsV);

		LinearValueFunction floorAreaTerraceV = new LinearValueFunction(30d, 50d);
		valueFunction.setFloorAreaTerraceValueFunction(floorAreaTerraceV);

		assertEquals(0.5, valueFunction.getSubjectiveValue(a), 0.0001);

		valueFunction.setTeleSubjectiveValueWeight(10d);
		assertEquals(10d,valueFunction.getSubjectiveValueWeight(Criterion.TELE));

		assertEquals(0.04587, valueFunction.getSubjectiveValue(a), 0.00001);
	}

	@Test
	void exceptionIllegalArgWeightSetter() {

		assertThrows(IllegalArgumentException.class, () -> valueFunction.setFloorAreaSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setNbBedroomsSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setNbSleepingSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setNbBathroomsSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setTerraceSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setFloorAreaTerraceSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setWifiSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setPricePerNightSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setNbMinNightSubjectiveValueWeight(-1d));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setTeleSubjectiveValueWeight(-1d));

	}

	@Test
	void adaptWeightTest() {

		assertThrows(IllegalArgumentException.class, () -> valueFunction.adaptWeight(Criterion.TELE,Criterion.TELE));
		valueFunction.setTeleSubjectiveValueWeight(7d);
		valueFunction.setTerraceSubjectiveValueWeight(3d);
		assertEquals(7d, valueFunction.getSubjectiveValueWeight(Criterion.TELE));
		assertEquals(3d, valueFunction.getSubjectiveValueWeight(Criterion.TERRACE));
		valueFunction = valueFunction.adaptWeight(Criterion.TERRACE, Criterion.TELE);
		assertEquals(9d,valueFunction.getSubjectiveValueWeight(Criterion.TERRACE));
		assertEquals(1d,valueFunction.getSubjectiveValueWeight(Criterion.TELE));

	}

	@Test
	void adaptBoundsTest() {
		
		assertThrows(IllegalArgumentException.class, () -> valueFunction.adaptBounds(Criterion.TELE, 0d, true));
		valueFunction = valueFunction.adaptBounds(Criterion.FLOOR_AREA_TERRACE, 45d, true);

	}

}
