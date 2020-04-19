package io.github.oliviercailloux.y2018.apartments.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

import javax.json.bind.JsonbBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

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
	 * The method return the default path to JSON file.
	 * 
	 * @return <i>Path</i> where to read and write JSON.
	 */
	private static final Path apartmentPathJson() {
		Path path = Paths.get("Apartment_Json.json");
		return path;
	}

	/**
	 * The method return a default JSON file read by jsonToApartments.
	 * 
	 * @return <i>Path</i> where jsonToApartments will read.
	 */
	private static final Path startApartment() {
		Path path = Paths
				.get("src/main/resources/io/github/oliviercailloux/y2018/jsonResources/defaultJsonToApartments.json");
		return path;
	}

	/**
	 * Converts an Apartment object to a JSON file with the default path
	 * APARTMENT_PATH_JSON.
	 *
	 * @param a <i>Apartment</i> object to convert into JSON
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentToJson(Apartment a) throws IOException {
		apartmentToJson(a, apartmentPathJson());
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

		try (BufferedWriter writer = Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8)) {
			writer.write(jsonb.toJson(a));
			writer.close();

			LOGGER.info("Apartment have been converted with success");
		}

	}

	/**
	 * Read a JSON file which contains an Apartment.
	 *
	 * @param jsonPath <i>Path</i> the path where the JSON file is located
	 * @return <i>String</i> containing an Apartment into JSON format
	 * @throws IOException if the file can't be convert into JSON format.
	 */
	protected static String readApartmentFromJson(Path jsonPath) throws IOException {

		try (BufferedReader reader = Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8)) {
			String jsonLine = reader.readLine();
			String jsonRead = jsonLine;

			while ((jsonLine = reader.readLine()) != null) {
				jsonRead = jsonRead + "\n" + jsonLine;
			}

			reader.close();
			LOGGER.info("Apartment has been read with success");

			return jsonRead;
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

		if (result.get("address").toString().isEmpty() || !result.containsKey("address")) {
			throw new IllegalArgumentException();
		} else {
			return result.get("address").toString();
		}
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
		String jsonString = readApartmentFromJson(jsonPath);
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
	 * @throws IOException if the file doesn't exists
	 */
	public static List<Apartment> jsonToApartments() throws IOException {
		return jsonToApartments(startApartment());
	}

	/**
	 * Converts a JSON expression to a list of Apartments.
	 *
	 * @param jsonPath <i>Path</i> the JSON expression to convert into a list of
	 *                 Apartments
	 * @return <i>List</i> the list of Apartments created
	 * @throws IOException if the file doesn't exists
	 */
	public static List<Apartment> jsonToApartments(Path jsonPath) throws IOException {
		String jsonString = readApartmentFromJson(jsonPath);
		List<Apartment.Builder> apartmentsBuild;
		List<Apartment> apartments = new ArrayList<Apartment>();
		LOGGER.info("Create ArrayList of Apartment");

		Jsonb jsonb = JsonbBuilder.create();
		LOGGER.info("Create Json builder");

		apartmentsBuild = jsonb.fromJson(jsonString, new ArrayList<Apartment.Builder>() {

			/** The Constant serialVersionUID for apartmentsBuild. */
			private static final long serialVersionUID = 2876323727155064950L;
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
		apartmentsToJson(listApartments, apartmentPathJson());
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

		try (BufferedWriter writer = Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8)) {
			writer.write(jsonb.toJson(listApartments));
			writer.close();

			LOGGER.info("Apartment have been converted with success");
		}
	}
}
