package io.github.oliviercailloux.y2021.apartments.plot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.json.JsonConvert;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;

class SpiderPlotTest {

	/**
	 * Test if the image is created after the spider plot generation.
	 */
	@Test
	void testCreateImgChart() {
		File file = new File("Doc/img/saved_spiderplot.png");

		List<Apartment> listOfApartments = JsonConvert.getDefaultApartments();
		Apartment apartment1 = listOfApartments.get(0);
		Apartment apartment2 = listOfApartments.get(1);
		ApartmentValueFunction avf1 = ApartmentValueFunction.getRandomApartmentValueFunction();
		ApartmentValueFunction avf2 = ApartmentValueFunction.getRandomApartmentValueFunction();

		SpiderPlot.given(apartment1, apartment2, avf1, avf2);
		assertTrue(file.exists());
	}

}
