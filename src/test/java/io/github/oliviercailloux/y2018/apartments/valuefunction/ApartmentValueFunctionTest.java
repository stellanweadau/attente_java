package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.BooleanValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

class ApartmentValueFunctionTest {

	@Test
	void apartmentValueFunctionTest() throws NumberFormatException {
		Apartment a = new Apartment(250, "108 rue de chat-ville Ville-d'Avray 92410", "Une ferme");
		a.setNbSleeping(4);
		a.setNbMinNight(3);
		a.setTerrace(true);
		a.setNbBedrooms(1);
		a.setPricePerNight(3.3);
		a.setTele(false);
		a.setWifi(false);
		a.setNbBathrooms(1);
		a.setDescription("Une ferme rustique en compagnie de Dwight Schrute, interdit à Jim Halpert");
		a.setFloorAreaTerrace(40);

		ApartmentValueFunction valueFunction = new ApartmentValueFunction();

		LinearValueFunction floorAreaV = new LinearValueFunction(0, 200.00);
		valueFunction.setFloorAreaValueFunction(floorAreaV);

		LinearValueFunction nbSleepingV = new LinearValueFunction(3, 5);
		valueFunction.setNbSleepingValueFunction(nbSleepingV);

		ReversedLinearValueFunction nbMinNightV = new ReversedLinearValueFunction(7, 30);
		valueFunction.setNbMinNightValueFunction(nbMinNightV);

		BooleanValueFunction terraceV = new BooleanValueFunction(true);
		valueFunction.setTerraceValueFunction(terraceV);

		LinearValueFunction nbBedroomsV = new LinearValueFunction(3, 4);
		valueFunction.setNbBedroomsValueFunction(nbBedroomsV);

		ReversedLinearValueFunction pricePerNightV = new ReversedLinearValueFunction(20, 40);
		valueFunction.setPricePerNightValueFunction(pricePerNightV);

		BooleanValueFunction teleV = new BooleanValueFunction(true);
		valueFunction.setTeleValueFunction(teleV);

		BooleanValueFunction wifiV = new BooleanValueFunction(true);
		valueFunction.setWifiValueFunction(wifiV);

		LinearValueFunction nbBathroomsV = new LinearValueFunction(2, 3);
		valueFunction.setNbBathroomsValueFunction(nbBathroomsV);

		LinearValueFunction floorAreaTerraceV = new LinearValueFunction(30, 50);
		valueFunction.setFloorAreaTerraceValueFunction(floorAreaTerraceV);

		assertEquals(0.5, valueFunction.getSubjectiveValue(a), 0.0001);

		valueFunction.setTeleSubjectiveValueWeight(10);

		assertEquals(0.04587, valueFunction.getSubjectiveValue(a), 0.00001);
	}

	@Test
	void exceptionIllegalArgWeightSetter() {
		assertThrows(IllegalArgumentException.class, () -> {
			ApartmentValueFunction vF = new ApartmentValueFunction();
			vF.setFloorAreaSubjectiveValueWeight(-1);
		});
	}

}
