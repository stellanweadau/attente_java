package io.github.oliviercailloux.y2018.apartments.distance;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.maps.errors.ApiException;

class DistanceSubwayTest {

	@Test
	void calculateDistanceTest() throws ApiException, InterruptedException, IOException {
		
		String api_key = "AIzaSyDuFlzxo-Sbee0E6eMLnfTvxcADSKQzaNs";
		DistanceSubway dist = new DistanceSubway(api_key,"Paris","Ville d'Avray");
		
		Assert.assertEquals(17558, dist.calculateDistance(), 0);
	}

}
