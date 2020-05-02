package io.github.oliviercailloux.y2018.apartments.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.VerifyException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

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
	private static Path getUniqueExportPath() {
		return Path.of("exportApartments" + System.currentTimeMillis() + ".json");
	}

	/**
	 * The method return a default JSON file read by jsonToApartments.
	 * 
	 * @return <i>Path</i> where jsonToApartments will read.
	 */
	private static Path startApartments() {
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
	public static List<Apartment> getDefaultApartments() throws IOException {
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

		for (Builder apartmentToBuild : apartmentsBuild) {
			apartments.add(apartmentToBuild.build());
		}
		return apartments;
	}

	/**
	 * Converts a list of Apartments to a JSON file with the default path
	 * APARTMENT_PATH_JSON.
	 *
	 * @param listApartments <code>{@link List}</code> object to convert into JSON
	 * @return <i>Path</i> of the created file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static Path apartmentsToJson(List<Apartment> listApartments) throws IOException {
		Path defaultPath = getUniqueExportPath();
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
		Files.writeString(jsonPath, apartmentsToJsonString(listApartments));
		LOGGER.info("Apartment have been converted with success");
	}

	/**
	 * Converts a list of Apartments to a JSON file.
	 * 
	 * @param listApartments <code>{@link List}</code> object to convert into JSON
	 *                       (<b>listApartments</b> could contain only one
	 *                       apartment)
	 * @return <i>String</i> containing the list of Apartments in JSON format
	 */
	public static String apartmentsToJsonString(List<Apartment> listApartments) {
		try (Jsonb jsonb = JsonbBuilder.create()) {
			return jsonb.toJson(listApartments);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
