package io.github.oliviercailloux.y2018.apartments.utils;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.VerifyException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

/**
 * The Class JsonConvert contains all function to transform Apartment object to
 * JSON and vice-versa.
 * 
 * @author Etienne CARTIER & Morgane FIOT
 */
public abstract class JsonConvert {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConvert.class);

	/**
	 * The default path to JSON file.
	 */
	public static final Path APARTMENT_PATH_JSON = Path.of("Apartment_Json.json");

	/**
	 * The method return a default JSON file read by jsonToApartments.
	 * 
	 * @return <i>Path</i> where jsonToApartments will read.
	 * @throws URISyntaxException if the resource cannot be found
	 */
	private static final Path startApartment() {
		try {
			URI ressource = JsonConvert.class.getResource("defaultJsonToApartments.json").toURI();
			return Path.of(ressource);
		} catch (URISyntaxException e) {
			throw new VerifyException();
		}
	}

	/**
	 * Converts an Apartment object to a JSON file with the default path
	 * APARTMENT_PATH_JSON.
	 *
	 * @param a <i>Apartment</i> object to convert into JSON
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentToJson(Apartment a) throws IOException {
		apartmentToJson(a, APARTMENT_PATH_JSON);
	}

	/**
	 * Converts an Apartment object to a JSON file.
	 *
	 * @param a        <i>Apartment</i> object to convert into JSON
	 * @param jsonPath <i>Path</i> the path where to create the JSON file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentToJson(Apartment a, Path jsonPath) throws IOException {
		Jsonb jsonb = JsonbBuilder.create();
		Files.writeString(jsonPath, jsonb.toJson(a));

		LOGGER.info("Apartment have been converted with success");
	}

	/**
	 * Gets the address field from an Address JSON.
	 * 
	 * We have to make sure that we have everything at every step <br>
	 * It is for this reason that the code is filled with check
	 * 
	 * <p>
	 * JsonString must be in shape
	 * <code>{"features": ["properties":{"label": "AdressHere"}]}</code>
	 * </p>
	 *
	 * @param jsonString <i>String</i> the Address into JSON format
	 * @return <i>String</i> the address field
	 * 
	 * @throws IllegalArgumentException in the case where the deserialization of the
	 *                                  JSON encounters a problem
	 */
	public static String getAddressFromJson(String jsonString) throws IllegalArgumentException {
		checkArgument(jsonString != null, "jsonString cannot be empty");
		// Convert jsonString to a map
		final Jsonb jsonb = JsonbBuilder.create();
		final Map<?, ?> jsonMap = jsonb.fromJson(jsonString, Map.class);
		// Get the features field and check that isn't null
		final List<?> featuresList = (List<?>) jsonMap.get("features");
		checkArgument(!featuresList.isEmpty(), "Features is empty");
		// The features field is a List with one element, a Map [{}]
		// So, we need to transform the List to a Map (and verify the content)
		final Map<?, ?> featuresMap = (Map<?, ?>) featuresList.get(0);
		checkArgument(featuresMap.get("properties") != null, "Properties is null");
		final Map<?, ?> properties = (Map<?, ?>) featuresMap.get("properties");
		checkArgument(properties.get("label") != null, "Label field is null");
		// TODO Implement pattern to verify the label field
		return properties.get("label").toString();
	}

	/**
	 * Converts a JSON expression to an Apartment object.
	 *
	 * @param jsonPath<i>Path</i> the JSON expression to convert into Apartment
	 *                            object
	 * @return <i>Apartment</i> the Apartment generated
	 * @throws IOException if the file can't be red
	 */
	public static Apartment jsonToApartment(Path jsonPath) throws IOException {
		String jsonString = Files.readString(jsonPath);
		Jsonb jsonb = JsonbBuilder.create();
		LOGGER.info("Create Json builder");

		Apartment.Builder apartBuild = jsonb.fromJson(jsonString, Apartment.Builder.class);
		LOGGER.info("Apartment created from JSON");

		return apartBuild.build();
	}

	/**
	 * Converts a JSON expression to a list of Apartments.
	 *
	 * @return <i>List</i> the list of Apartments created
	 * @throws IOException        if the file doesn't exists
	 * @throws URISyntaxException
	 */
	public static List<Apartment> jsonToApartments() throws IOException {
		Path apartPath = startApartment();
		return jsonToApartments(apartPath);
	}

	/**
	 * Converts a JSON expression to a list of Apartments.
	 *
	 * @param jsonPath <i>Path</i> the JSON expression to convert into a list of
	 *                 Apartments
	 * @return <i>List</i> the list of Apartments created
	 * @throws IOException if the file doesn't exists
	 */
	@SuppressWarnings("serial")
	public static List<Apartment> jsonToApartments(Path jsonPath) throws IOException {
		String jsonString = Files.readString(jsonPath);
		List<Apartment.Builder> apartmentsBuild;
		List<Apartment> apartments = new ArrayList<Apartment>();
		LOGGER.info("Create ArrayList of Apartment");

		Jsonb jsonb = JsonbBuilder.create();
		LOGGER.info("Create Json builder");

		apartmentsBuild = jsonb.fromJson(jsonString, new ArrayList<Apartment.Builder>() {
		}.getClass().getGenericSuperclass());

		for (int i = 0; i < apartmentsBuild.size(); i++) {
			apartments.add(apartmentsBuild.get(i).build());
		}
		return apartments;
	}

	/**
	 * Converts a list of Apartments to a JSON file with the default path
	 * APARTMENT_PATH_JSON.
	 *
	 * @param listApartments <code>{@link List}</code> object to convert into JSON
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentsToJson(List<Apartment> listApartments) throws IOException {
		apartmentsToJson(listApartments, APARTMENT_PATH_JSON);
	}

	/**
	 * Converts a list of Apartments to a JSON file.
	 *
	 * @param listApartments <code>{@link List}</code> object to convert into JSON
	 * @param jsonPath       <i>String</i> the path where to create the JSON file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentsToJson(List<Apartment> listApartments, Path jsonPath) throws IOException {
		Jsonb jsonb = JsonbBuilder.create();
		Files.writeString(jsonPath, jsonb.toJson(listApartments));

		LOGGER.info("Apartment have been converted with success");
	}
}
