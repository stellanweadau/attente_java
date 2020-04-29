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

	@Test
	void apartmentValueFunctionTest() throws NumberFormatException {

		Apartment a = new Builder().setFloorArea(250)
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

		ApartmentValueFunction valueFunction = new ApartmentValueFunction();

		LinearValueFunction floorAreaV = new LinearValueFunction(0d, 200d);
		valueFunction.setFloorAreaValueFunction(floorAreaV);

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

		valueFunction.setTeleSubjectiveValueWeight(10);
		assertEquals(10,valueFunction.getSubjectiveValueWeight(Criterion.TELE));
		assertThrows(IllegalArgumentException.class, () -> valueFunction.setFloorAreaSubjectiveValueWeight(-1d));

		assertEquals(0.04587, valueFunction.getSubjectiveValue(a), 0.00001);
	}
	
	@Test
	void cloneAVFTest() {
		ApartmentValueFunction valueFunction = new ApartmentValueFunction();
		
	}

	@Test
	void exceptionIllegalArgWeightSetter() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			ApartmentValueFunction vF = new ApartmentValueFunction();
			vF.setFloorAreaSubjectiveValueWeight(-1);
		});
	}

}
