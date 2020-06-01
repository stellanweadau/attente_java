package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.maps.model.LatLng;
import io.github.oliviercailloux.y2018.apartments.localize.Localizer;
import io.github.oliviercailloux.y2018.apartments.utils.KeyManager;

class ValueDistFunctionTest {

  private ValueDistFunction v;
  LatLng appart;
  LatLng interest1;
  LatLng interest2;
  LatLng interest3;
  String apiKey;

  /**
   * This test does not run due to the absence of the file API_KEY.txt of Google MAps
   *
   * @throws Exception
   */
  void initializeValueDistFunction() throws Exception {
    apiKey = KeyManager.getApiKey();
    appart = Localizer.getGeometryLocation("Ville d'Avray", apiKey);
    interest1 = Localizer.getGeometryLocation("Paris", apiKey);
    interest2 = Localizer.getGeometryLocation("Chaville", apiKey);
    interest3 = Localizer.getGeometryLocation("Roissy Charles de Gaulle", apiKey);
    v = new ValueDistFunction(appart, apiKey);

    v.addInterestLocation(interest1);
    v.addInterestLocation(interest2);
    v.addInterestLocation(interest3);
  }

  void getSubjectiveValueTest() throws Exception {
    initializeValueDistFunction();
    assertEquals(0.90919444444, v.getSubjectiveValue(interest1));
  }

  void getMaxDurationTest() throws Exception {
    initializeValueDistFunction();
    assertEquals(5091.0, v.getMaxDuration());
  }
}
