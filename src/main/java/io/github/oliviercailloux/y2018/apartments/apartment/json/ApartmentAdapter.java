package io.github.oliviercailloux.y2018.apartments.apartment.json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;

/**
 * A class implemented to avoid using ApartmentBuilder in JsonConvert. Code
 * inspired from the example in the course of Jean-Michel Doudoux available here
 * : https://www.jmdoudoux.fr/java/dej/chap-json-b.htm
 *
 */

public class ApartmentAdapter implements JsonbAdapter<Apartment, JsonObject> {

	@Override
	public JsonObject adaptToJson(Apartment apart) throws Exception {
		return Json.createObjectBuilder().add("title", apart.getTitle()).add("address", apart.getAddress())
				.add("description", apart.getDescription()).add("floorArea", apart.getFloorArea())
				.add("floorAreaTerrace", apart.getFloorAreaTerrace()).add("nbBedrooms", apart.getNbBedrooms())
				.add("nbBathrooms", apart.getNbBathrooms()).add("nbMinNight", apart.getNbMinNight())
				.add("nbSleeping", apart.getNbSleeping()).add("pricePerNight", apart.getPricePerNight())
				.add("wifi", apart.getWifi()).add("tele", apart.getTele()).add("terrace", apart.getTerrace()).build();
	}

	@Override
	public Apartment adaptFromJson(JsonObject obj) throws Exception {
		Builder apartToBuild = new Builder();
		return apartToBuild.setTitle(obj.getString("title")).setAddress(obj.getString("address"))
				.setDescription(obj.getString("description")).setFloorArea(obj.getJsonNumber("floorArea").doubleValue())
				.setFloorAreaTerrace(obj.getJsonNumber("floorAreaTerrace").doubleValue())
				.setNbBedrooms(obj.getInt("nbBedrooms")).setNbBathrooms(obj.getInt("nbBathrooms"))
				.setNbMinNight(obj.getInt("nbMinNight")).setNbSleeping(obj.getInt("nbSleeping"))
				.setPricePerNight(obj.getJsonNumber("pricePerNight").doubleValue()).setTele(obj.getBoolean("tele"))
				.setWifi(obj.getBoolean("wifi")).setTerrace(obj.getBoolean("terrace")).build();
	}

}