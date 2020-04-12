package io.github.oliviercailloux.y2018.apartments.utils;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JsonConvertTests {
	
	@Test
	void ApartmentToJsonTest() throws IOException {
		
		Apartment a = new Apartment(1182118.48, "118 rue du père noel 77480", "Grand Igloo");
		String expectedApartment = "{\"address\":\"118 rue du père noel 77480\",\"description\":\"\",\"floorArea\":1182118.48,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Grand Igloo\",\"wifi\":false}";
		String jsonPath = "src/test/resources/io/github/oliviercailloux/y2018/apartments/readapartments/jsonfileTest.json";
		
		JsonConvert.ApartmentToJson(a, jsonPath);
		
		assertEquals(expectedApartment, JsonConvert.ReadApartmentFromJson(jsonPath));
	}
	
	
	@Test
	void getAddressFromJsonTest() throws FileNotFoundException, IOException {
		
		String adressJson = "{\"data\":{\"latitude\":48.91777636365895,\"longitude\":2.4954686289120067,\"formattedAddress\":\"Allée de Turenne, Nonneville, Les Pavillons-sous-Bois, Le Raincy, Seine-Saint-Denis, Île-de-France, France métropolitaine, 93320, France\",\"country\":\"France\",\"city\":\"Les Pavillons-sous-Bois\",\"state\":\"Île-de-France\",\"zipcode\":\"93320\",\"streetName\":\"Allée de Turenne\",\"countryCode\":\"FR\",\"neighbourhood\":\"\",\"provider\":\"openstreetmap\"},\"address\":\"1 Allée de Turenne, 93320 Les Pavillons-sous-Bois\"}";
		System.out.println(JsonConvert.getAddressFromJson(adressJson));
		assertEquals("1 Allée de Turenne, 93320 Les Pavillons-sous-Bois", JsonConvert.getAddressFromJson(JsonConvert.ReadApartmentFromJson(adressJson)));
		
	}

}
