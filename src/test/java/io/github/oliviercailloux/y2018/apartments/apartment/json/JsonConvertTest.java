package io.github.oliviercailloux.y2018.apartments.apartment.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.json.bind.JsonbConfig;
import javax.json.bind.adapter.JsonbAdapter;
import org.junit.jupiter.api.Test;

/**
 * Test class for JsonConvert
 *
 * @author Etienne CARTIER & Morgane FIOT
 */
public class JsonConvertTest {

  /**
   * Tests apartmentsToJson function. Verifies if the JSON file created by the function corresponds
   * to the expected file.
   *
   * @throws IOException if the file doesn't exist
   */
  @Test
  void apartmentsToJsonTest() throws IOException {
    Builder apartBuilder = new Apartment.Builder();
    ArrayList<Apartment> apartments = new ArrayList<>();
    apartments.add(
        apartBuilder
            .setAddress("118 rue du père noel 77480")
            .setFloorArea(1182118.48)
            .setTitle("Grand Igloo")
            .setTerrace(false)
            .setWifi(false)
            .setTele(false)
            .build());
    apartments.add(
        apartBuilder
            .setAddress("123 rue du soleil")
            .setFloorArea(1234567.89)
            .setTitle("Maison Test")
            .setTerrace(false)
            .setWifi(false)
            .setTele(false)
            .build());

    String expectedApartmentsJsonString =
        Files.readString(Path.of("expectedApartmentsJsonString.json"));
    assertEquals(expectedApartmentsJsonString, JsonConvert.apartmentsToJsonString(apartments));
  }

  /**
   * Tests jsonToApartments function. Verifies if the <i>ArrayList</i> of Apartment created by the
   * function corresponds to the expected <i>ArrayList</i>.
   *
   * @throws URISyntaxException if the file doesn't exists.
   * @throws IOException if the file can't be convert into JSON format.
   */
  @Test
  void jsonToApartmentsTest() throws IOException, URISyntaxException {
    Builder apartBuilder = new Apartment.Builder();
    List<Apartment> apartmentsRef = new ArrayList<>();
    apartmentsRef.add(
        apartBuilder
            .setAddress("118 rue du père noel 77480")
            .setFloorArea(1182118.48)
            .setTitle("Grand Igloo")
            .setTerrace(false)
            .setWifi(false)
            .setTele(false)
            .build());
    apartmentsRef.add(
        apartBuilder
            .setAddress("123 rue du soleil")
            .setFloorArea(1234567.89)
            .setTitle("Maison Test")
            .setTerrace(false)
            .setWifi(false)
            .setTele(false)
            .build());

    URI ressource = JsonConvertTest.class.getResource("jsonApartments.json").toURI();
    Path jsonPath = Path.of(ressource);

    List<Apartment> apartmentsTest = JsonConvert.jsonToApartments(jsonPath);

    assertEquals(apartmentsRef.get(0).hashCode(), apartmentsTest.get(0).hashCode());
    assertEquals(apartmentsRef.get(1).hashCode(), apartmentsTest.get(1).hashCode());
  }

  /** Test if the adapter method works */
  @Test
  void getAdapterTest() throws Exception {
    JsonbConfig defaultConfig = new JsonbConfig();
    JsonbAdapter<Apartment, JsonObject> adapter = JsonConvert.getAdapter();
    JsonbConfig config = new JsonbConfig().withAdapters(adapter);
    assertNotEquals(defaultConfig, config);
    // Verification of recovered fields
    Builder apartBuilder = new Apartment.Builder();
    Apartment a =
        apartBuilder
            .setAddress("Place du Maréchal de Lattre de Tassigny, 75016 Paris")
            .setFloorArea(1234567.89)
            .setTitle("Paris-Dauphine - PSL")
            .setTerrace(false)
            .setWifi(true)
            .setTele(true)
            .build();
    JsonObject json = adapter.adaptToJson(a);
    // We are only testing a string field, a boolean field, a double field
    assertEquals(
        json.get("title").toString(),
        "\"" + a.getTitle() + "\"",
        "Title need to be Paris-Dauphine - PSL");
    assertEquals(
        json.get("wifi").toString(),
        String.valueOf(a.getWifi()),
        "The wifi has been set to 'true'");
    assertEquals(
        json.get("floorArea").toString(),
        String.valueOf(a.getFloorArea()),
        "Floor area need to be 99999.99");
    // Test adaptFromJson
    Apartment b = adapter.adaptFromJson(json);
    assertTrue(a.equals(b), "Error comes from the adapter (function adaptFromJson)");
  }
}
