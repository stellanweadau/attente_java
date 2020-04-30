package io.github.oliviercailloux.y2018.apartments.utils;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
	 * @return {@link Path} where jsonToApartments will read.
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
	 * @param a {@link Apartment} object to convert into JSON
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentToJson(Apartment a) throws IOException {
		apartmentToJson(a, APARTMENT_PATH_JSON);
	}

	/**
	 * Converts an Apartment object to a JSON file.
	 *
	 * @param a        {@link Apartment} object to convert into JSON
	 * @param jsonPath {@link Path} the path where to create the JSON file
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
	 * <p>
	 * The contract for this API is returned either <code>"features": []</code> if
	 * the address cannot be found or in shape <code>{"features": ["properties":
	 * {"label": "AdressHere"}]}</code>
	 * </p>
	 *
	 * @param jsonString {@link String} the Address into JSON format
	 * @return {@link String} the address field
	 * 
	 * @throws InvalidObjectException   In case the <code>features</code> field is
	 *                                  empty
	 * @throws IllegalArgumentException in the case where the deserialization of the
	 *                                  JSON encounters a problem or if
	 *                                  <code>jsonString</code> is blank
	 */
	public static String getAddressFromJson(String jsonString) throws InvalidObjectException {
		checkNotNull(jsonString, "jsonString cannot be null");
		checkArgument(!jsonString.isBlank(), "jsonString cannot be blank");
		try (JsonReader jr = Json.createReader(new StringReader(jsonString))) {
			JsonObject json = jr.readObject();
			checkArgument(json.containsKey("features"),
					"The JSON passed in parameter is not valid : we don't have \"features\" key");
			JsonArray features = json.get("features").asJsonArray();
			if (features.isEmpty()) {
				throw new InvalidObjectException(
						"The JSON passed in parameter is not valid : We got this from jsonString \"features\": []");
			}
			JsonObject properties = features.get(0).asJsonObject().get("properties").asJsonObject();
			checkArgument(properties.containsKey("label"), "The field \"label\" is not here");
			return properties.getString("label");
		}
	}

	/**
	 * Converts a JSON expression to an Apartment object.
	 *
	 * @param jsonPath {@link Path} the JSON expression to convert into Apartment
	 *                 object
	 * @return {@link Apartment} the Apartment generated
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
	 * @return {@link List} the list of Apartments created
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
	 * @param jsonPath {@link Path} the JSON expression to convert into a list of
	 *                 Apartments
	 * @return {@link List} the list of Apartments created
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
	 * @param jsonPath       {@link String} the path where to create the JSON file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentsToJson(List<Apartment> listApartments, Path jsonPath) throws IOException {
		Jsonb jsonb = JsonbBuilder.create();
		Files.writeString(jsonPath, jsonb.toJson(listApartments));

		LOGGER.info("Apartment have been converted with success");
	}
}
