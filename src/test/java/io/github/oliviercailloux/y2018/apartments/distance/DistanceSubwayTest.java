package io.github.oliviercailloux.y2018.apartments.distance;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.maps.errors.ApiException;

import io.github.oliviercailloux.y2018.apartments.valuefunction.DistanceMode;

/**
 * Inspiration for reading .txt file : https://www.ukonline.be/programmation/java/tutoriel/chapitre12/page3.php
 *
 */
class DistanceSubwayTest {

	@Test
	void calculateDistanceAddressTest() throws ApiException, InterruptedException, IOException {

		String api_key = new File("API_KEY.txt").getAbsolutePath();
		String geocode_api_key =new File("GEOCODE_API_KEY.txt").getAbsolutePath();
		String codeApiKey="";
		String codeGeocode="";

		try(FileReader fr = new FileReader (api_key)){
			int c = fr.read();
			

			while (c != -1)
			{
				codeApiKey+=(char) c;
				c = fr.read();
			}
		}
		catch (IOException exception)
		{
		    System.out.println ("Reading error : "+ exception.getMessage());
		}
		
		try(FileReader fr = new FileReader (geocode_api_key)){
			int c = fr.read();
			

			while (c != -1)
			{
				codeGeocode+=(char) c;
				c = fr.read();
			}
		}
		catch (IOException exception)
		{
		    System.out.println ("Reading error : " + exception.getMessage());
		}

		DistanceSubway dist = new DistanceSubway(codeApiKey,codeGeocode,"Paris","Ville d'Avray");
		Assert.assertEquals(0.96833, dist.calculateDistanceAddress(DistanceMode.ADDRESS), 0.0001);
	}

}
