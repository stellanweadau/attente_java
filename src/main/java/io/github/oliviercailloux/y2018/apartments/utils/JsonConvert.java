package io.github.oliviercailloux.y2018.apartments.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

import javax.json.bind.JsonbBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import javax.json.bind.Jsonb;


/**
 * The Class JsonConvert contains all function to transform Apartment object to JSON and vice-versa.
 * @author Etienne CARTIER & Morgane FIOT
 */
public abstract class JsonConvert {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConvert.class);
	
	/** The Constant APARTMENT_PATH_JSON gives the default location for JSON file. */
	private static final String APARTMENT_PATH_JSON = "Apartment_Json.json";
	
	/**
	 * Converts an Apartment object to a JSON file with the default path APARTMENT_PATH_JSON.
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
	 * @param a <i>Apartment</i> object to convert into JSON
	 * @param jsonPath <i>String</i> the path where to create the JSON file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentToJson(Apartment a, String jsonPath) throws IOException {
		
		File jsonFile = new File(jsonPath);
		Jsonb jsonb = JsonbBuilder.create();
		
		
		try (Writer jsonFilePath = new BufferedWriter(new FileWriter(jsonFile))) {
			jsonFilePath.write(jsonb.toJson(a));
			jsonFilePath.close();
			
			LOGGER.info("Apartment has been converted with success");
		}		
		
	}
	
	/**
	 * Read a JSON file which contains an Apartment.
	 *
	 * @param jsonPath <i>String</i> the path where the JSON file is located
	 * @return <i>String</i> containing an Apartment into JSON format
	 * @throws IOException if the file can't be convert into JSON format.
	 */
	public static String readApartmentFromJson(String jsonPath) throws IOException {
		
		File jsonFile = new File(jsonPath);
		
		try (BufferedReader jsonFilePath = new BufferedReader(new FileReader(jsonFile))) {
			
			String jsonLine = jsonFilePath.readLine();
			String jsonRead = jsonLine;
			
			while (true) {
				jsonLine = jsonFilePath.readLine();
				if (jsonLine == null) {
					break;
				}
				jsonRead = jsonRead + "\n" + jsonLine;
			}
			
			jsonFilePath.close();
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
        String address = result.get("address").toString();
        
        if (address.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			return result.get("address").toString();
		}
    }
	
	/**
	 * Converts a JSON expression to an Apartment object.
	 *
	 * @param jsonString <i>String</i> the JSON expression to convert into Apartment object
	 * @return <i>Apartment</i> the Apartment generated
	 */
	public static Apartment jsonToApartment(String jsonString) {
		Jsonb jsonb = JsonbBuilder.create();
		LOGGER.info("Create Json builder");
		
		Apartment.Builder apartBuild = jsonb.fromJson(jsonString, Apartment.Builder.class);
		LOGGER.info("Apartment created from JSON");
		
		return apartBuild.build();
	}

	/**
	 * Converts a JSON expression to a list of Apartments.
	 *
	 * @param jsonString <i>String</i> the JSON expression to convert into a list of Apartments
	 * @return <i>ArrayList</i> the list of Apartments created
	 */
	public static List<Apartment> jsonToApartments(String jsonString) {
		List<Apartment.Builder> apartmentsBuild;
		List<Apartment> apartments = new ArrayList<Apartment>();
		LOGGER.info("Create ArrayList of Apartment");
		
		Jsonb jsonb = JsonbBuilder.create();
		LOGGER.info("Create Json builder");
		
		apartmentsBuild = jsonb.fromJson(jsonString, new ArrayList<Apartment.Builder>(){}.getClass().getGenericSuperclass());
		
		for (int i = 0; i < apartmentsBuild.size(); i++) {
			apartments.add(apartmentsBuild.get(i).build());
		}
		
		return apartments;
	}
	
	/**
	 * Converts a list of Apartments to a JSON file with the default path APARTMENT_PATH_JSON.
	 *
	 * @param a <i>ArrayList</i> object to convert into JSON
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentsToJson(List<Apartment> listApartments) throws IOException {
		apartmentsToJson(listApartments, APARTMENT_PATH_JSON);
	}
	
	/**
	 * Converts a list of Apartments to a JSON file.
	 *
	 * @param an <i>ArrayList</i> to convert into JSON
	 * @param jsonPath <i>String</i> the path where to create the JSON file
	 * @throws IOException if the JSON file can't be created.
	 */
	public static void apartmentsToJson(List<Apartment> listApartments, String jsonPath) throws IOException {
		
		File jsonFile = new File(jsonPath);
		Jsonb jsonb = JsonbBuilder.create();
		
		try (Writer jsonFilePath = new BufferedWriter(new FileWriter(jsonFile))) {
			jsonFilePath.write(jsonb.toJson(listApartments));
			jsonFilePath.close();
			
			LOGGER.info("Apartment have been converted with success");
		}
	}
	
}
