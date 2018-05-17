package io.github.oliviercailloux.y2018.apartments.distance;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.maps.errors.ApiException;

import io.github.oliviercailloux.y2018.apartments.utils.KeyManager;
import io.github.oliviercailloux.y2018.apartments.valuefunction.DistanceMode;

/**
 * Inspiration for reading .txt file : https://www.ukonline.be/programmation/java/tutoriel/chapitre12/page3.php
 *
 */
class DistanceSubwayTest {

	@Test
	void calculateDistanceAddressTest() throws ApiException, InterruptedException, IOException {

		
		String apiKey= KeyManager.getApiKey();


		DistanceSubway dist = new DistanceSubway(apiKey,"Paris","Ville d'Avray");
		Assert.assertEquals(0.96833, dist.calculateDistanceAddress(DistanceMode.ADDRESS), 0.0001);
	}

}
