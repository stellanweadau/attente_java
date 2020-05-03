package io.github.oliviercailloux.y2018.apartments.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import io.github.oliviercailloux.y2018.apartments.exception.AddressApiException;

/**
 * Test class for JsonConvert
 * 
 * @author Etienne CARTIER & Morgane FIOT
 */
public class JsonConvertTest {

	/**
	 * Tests apartmentToJson function. Verifies if the JSON file created by the
	 * function corresponds to the expected file.
	 * 
	 * @throws IOException        if the JSON file can't be created.
	 * @throws URISyntaxException if this URL is not formatted strictly according to
	 *                            RFC2396 and cannot be converted to a URI
	 */
	@Test
	void apartmentToJsonTest() throws IOException, URISyntaxException {
		Builder apartBuilder = new Apartment.Builder();
		Apartment a = apartBuilder.setAddress("118 rue du père noel 77480").setFloorArea(1182118.48)
				.setTitle("Grand Igloo").setTerrace(false).setWifi(false).setTele(false).build();
		String expectedApartment = "{\"address\":\"118 rue du père noel 77480\",\"description\":\"\",\"floorArea\":1182118.48,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Grand Igloo\",\"wifi\":false}";

		URI ressource = JsonConvertTest.class.getResource("jsonfileTest.json").toURI();
		Path jsonPath = Path.of(ressource);

		JsonConvert.apartmentToJson(a, jsonPath);
		JsonConvert.apartmentToJson(a);

		assertEquals(expectedApartment, Files.readString(jsonPath));
		assertEquals(expectedApartment, Files.readString(JsonConvert.APARTMENT_PATH_JSON));
		assertThrows(IOException.class, () -> JsonConvert.apartmentToJson(a, Paths.get("")));
	}

	/**
	 * Tests getAddressFromJson function. Verifies if the address extracted by the
	 * function corresponds to the expected address.
	 */
	@Test
	void getAddressFromJsonTest() {
		assertEquals("9 bis Rue la Fontaine 77400 Gouvernes", JsonConvert.getAddressFromJson(
				"{\"type\": \"FeatureCollection\", \"version\": \"draft\", \"features\": [{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [2.697788, 48.861515]}, \"properties\": {\"label\": \"9 bis Rue la Fontaine 77400 Gouvernes\", \"score\": 0.9999917537344281, \"housenumber\": \"9 bis\", \"id\": \"77209_0120_00009_bis\", \"type\": \"housenumber\", \"x\": 677827.72, \"y\": 6862429.24, \"importance\": 0.3329219426681952, \"name\": \"9 bis Rue la Fontaine\", \"postcode\": \"77400\", \"citycode\": \"77209\", \"city\": \"Gouvernes\", \"context\": \"77, Seine-et-Marne, \\u00cele-de-France\", \"street\": \"Rue la Fontaine\", \"distance\": 143}}], \"attribution\": \"BAN\", \"licence\": \"ETALAB-2.0\", \"limit\": 1}"),
				"Good JSON, must be return 9 bis Rue la Fontaine 77400 Gouvernes");
		assertEquals("37 Boulevard de Beaubourg 77184 Émerainville", JsonConvert.getAddressFromJson(
				"{\"type\": \"FeatureCollection\", \"version\": \"draft\", \"features\": [{\"type\": \"Feature\", \"geometry\": {\"type\": \"Point\", \"coordinates\": [2.615569, 48.817802]}, \"properties\": {\"label\": \"37 Boulevard de Beaubourg 77184 \\u00c9merainville\", \"score\": 0.9999995776812857, \"housenumber\": \"37\", \"id\": \"77169_0039_00037\", \"type\": \"housenumber\", \"x\": 671771.97, \"y\": 6857594.93, \"importance\": 0.42775013264058914, \"name\": \"37 Boulevard de Beaubourg\", \"postcode\": \"77184\", \"citycode\": \"77169\", \"city\": \"\\u00c9merainville\", \"context\": \"77, Seine-et-Marne, \\u00cele-de-France\", \"street\": \"Boulevard de Beaubourg\", \"distance\": 32}}], \"attribution\": \"BAN\", \"licence\": \"ETALAB-2.0\", \"limit\": 1}"),
				"Good JSON, must be return 37 Boulevard de Beaubourg 77184 Émerainville");
		assertThrows(AddressApiException.class, () -> JsonConvert.getAddressFromJson(
				"{\"type\": \"FeatureCollection\", \"version\": \"draft\", \"features\": [], \"attribution\": \"BAN\", \"licence\": \"ETALAB-2.0\", \"limit\": 1}"),
				"features is empty, need to get AddressApiException");
		assertThrows(NullPointerException.class, () -> JsonConvert.getAddressFromJson(null),
				"null need to return NullPointerException");
		// Check that the return format of the API has not changed
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://api-adresse.data.gouv.fr/reverse/?lon=2.2712946&lat=48.869962");
		String result = target.request(MediaType.TEXT_PLAIN).get(String.class);
		assertEquals("2 Chemin des Lacs à la Porte Dauphine 75016 Paris", JsonConvert.getAddressFromJson(result),
				"Call to the address retrieval API with a fixed longitude and attitude");

	}

	/**
	 * Tests apartmentsToJson function. Verifies if the JSON file created by the
	 * function corresponds to the expected file.
	 *
	 * @throws IOException if the JSON file can't be created.
	 */
	@Test
	void apartmentsToJsonTest() throws IOException, URISyntaxException {
		Builder apartBuilder = new Apartment.Builder();
		ArrayList<Apartment> apartments = new ArrayList<>();
		apartments.add(apartBuilder.setAddress("118 rue du père noel 77480").setFloorArea(1182118.48)
				.setTitle("Grand Igloo").setTerrace(false).setWifi(false).setTele(false).build());
		apartments.add(apartBuilder.setAddress("123 rue du soleil").setFloorArea(1234567.89).setTitle("Maison Test")
				.setTerrace(false).setWifi(false).setTele(false).build());

		String expectedApartment = "[{\"address\":\"118 rue du père noel 77480\",\"description\":\"\",\"floorArea\":1182118.48,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Grand Igloo\",\"wifi\":false},{\"address\":\"123 rue du soleil\",\"description\":\"\",\"floorArea\":1234567.89,\"floorAreaTerrace\":0.0,\"nbBathrooms\":0,\"nbBedrooms\":0,\"nbMinNight\":0,\"nbSleeping\":0,\"pricePerNight\":0.0,\"tele\":false,\"terrace\":false,\"title\":\"Maison Test\",\"wifi\":false}]";

		URI ressource = JsonConvertTest.class.getResource("jsonListTest.json").toURI();
		Path jsonPath = Path.of(ressource);

		JsonConvert.apartmentsToJson(apartments, jsonPath);

		assertEquals(expectedApartment, Files.readString(jsonPath));
		assertThrows(IOException.class, () -> JsonConvert.apartmentsToJson(apartments, Paths.get("")));
	}

	/**
	 * Tests jsonToApartment function. Verifies if the Apartment created by the
	 * function corresponds to the expected Apartment.
	 *
	 * @throws FileNotFoundException if the file doesn't exists.
	 * @throws IOException           if the file can't be convert into JSON format.
	 */
	@Test
	void jsonToApartmentTest() throws IOException, URISyntaxException {
		Builder apartBuilder = new Apartment.Builder();
		Apartment apartmentRef = apartBuilder.setAddress("118 rue du père noel 77480").setFloorArea(1182118.48)
				.setTitle("Grand Igloo").setTerrace(false).setWifi(false).setTele(false).build();

		URI ressource = JsonConvertTest.class.getResource("jsonfileTest.json").toURI();
		Path jsonPath = Path.of(ressource);

		Apartment apartmentTest = JsonConvert.jsonToApartment(jsonPath);

		assertEquals(apartmentRef.hashCode(), apartmentTest.hashCode());
	}

	/**
	 * Tests jsonToApartments function. Verifies if the <i>ArrayList</i> of
	 * Apartment created by the function corresponds to the expected
	 * <i>ArrayList</i>.
	 *
	 * @throws FileNotFoundException if the file doesn't exists.
	 * @throws IOException           if the file can't be convert into JSON format.
	 */
	@Test
	void jsonToApartmentsTest() throws IOException, URISyntaxException {
		Builder apartBuilder = new Apartment.Builder();
		List<Apartment> apartmentsRef = new ArrayList<>();
		apartmentsRef.add(apartBuilder.setAddress("118 rue du père noel 77480").setFloorArea(1182118.48)
				.setTitle("Grand Igloo").setTerrace(false).setWifi(false).setTele(false).build());
		apartmentsRef.add(apartBuilder.setAddress("123 rue du soleil").setFloorArea(1234567.89).setTitle("Maison Test")
				.setTerrace(false).setWifi(false).setTele(false).build());

		URI ressource = JsonConvertTest.class.getResource("jsonListTest.json").toURI();
		Path jsonPath = Path.of(ressource);

		List<Apartment> apartmentsTest = JsonConvert.jsonToApartments(jsonPath);

		assertEquals(apartmentsRef.get(0).hashCode(), apartmentsTest.get(0).hashCode());
		assertEquals(apartmentsRef.get(1).hashCode(), apartmentsTest.get(1).hashCode());
	}
}