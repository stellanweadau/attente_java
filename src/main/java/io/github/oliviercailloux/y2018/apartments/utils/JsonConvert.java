package io.github.oliviercailloux.y2018.apartments.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.VerifyException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

import javax.json.bind.JsonbBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import static com.google.common.base.Preconditions.checkArgument;

import javax.json.bind.Jsonb;

/**
 * The Class JsonConvert contains all function to transform Apartment object to
 * JSON and vice-versa.
 * 
 * @author Etienne CARTIER & Morgane FIOT
 */
public abstract class JsonConvert {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConvert.class);

	/**
	 * The path to export a list of apartments in a JSON file.
	 *
	 * @return <i>Path</i> of the JSON file at the root of the project
	 */
	public static final Path exportApartments() {
		return Path.of("exportApartments" + System.currentTimeMillis() + ".json");
	}

	/**
	 * The method return a default JSON file read by jsonToApartments.
	 * 
	 * @return <i>Path</i> where jsonToApartments will read.
	 */
	private static final Path startApartments() {
		try {
			URI ressource = JsonConvert.class.getResource("defaultJsonToApartments.json").toURI();
			return Path.of(ressource);
		} catch (URISyntaxException e) {
			throw new VerifyException(e);
		}
	}

	/**
	 * Gets the address field from an Address JSON.
	 *
	 * @param jsonString <i>String</i> the Address into JSON format
	 * @return <i>String</i> the address field
	 */
	public static String getAddressFromJson(String jsonString) {
		Jsonb jsonb = JsonbBuilder.create();

		final LinkedHashMap<?, ?> result = jsonb.fromJson(jsonString, LinkedHashMap.class);
		LOGGER.info("Get address");

		checkArgument(!result.get("address").toString().isEmpty() && result.containsKey("address"),
				"There is no field adress in the JSON file.");
		return result.get("address").toString();
	}

	/**
	 * Converts a JSON expression to a list of Apartments.
	 *
	 * @return <i>List</i> the list of Apartments created
	 * @throws IOException if the file doesn't exists
	 */
	public static List<Apartment> jsonToApartments() throws IOException {
		Path apartPath = startApartments();
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
		List<Apartment> apartments = new ArrayList<>();
		LOGGER.info("Create ArrayList of Apartment");

		try (Jsonb jsonb = JsonbBuilder.create()) {
			LOGGER.info("Create Json builder");
			apartmentsBuild = jsonb.fromJson(jsonString, new ArrayList<Apartment.Builder>() {
			}.getClass().getGenericSuperclass());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

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
	public static Path apartmentsToJson(List<Apartment> listApartments) throws IOException {
		Path defaultPath = exportApartments();
		apartmentsToJson(listApartments, defaultPath);
		return defaultPath.toAbsolutePath();
	}

	/**
	 * Converts a list of Apartments to a JSON file. If you call the function with
	 * the same path, the data in the file will be overwritten.
	 *
	 * @param listApartments <code>{@link List}</code> object to convert into JSON
	 *                       (<b>listApartments</b> could contain only one
	 *                       apartment)
	 * @param jsonPath       <i>String</i> the path where to create the JSON file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentsToJson(List<Apartment> listApartments, Path jsonPath) throws IOException {
		String toJson;
		try (Jsonb jsonb = JsonbBuilder.create()) {
			toJson = jsonb.toJson(listApartments);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		Files.writeString(jsonPath, toJson);
		LOGGER.info("Apartment have been converted with success");
	}
}
