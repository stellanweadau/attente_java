package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApartmentValueFunctionTest {

  ApartmentValueFunction valueFunction = new ApartmentValueFunction();

  Apartment a;

  /** Inits the apartmentValueFunction object and the apartment before each tests */
  @BeforeEach
  void initEach() {
    a =
        new Builder()
            .setFloorArea(250)
            .setAddress("108 rue de chat-ville Ville-d'Avray 92410")
            .setNbBedrooms(1)
            .setNbSleeping(4)
            .setNbBathrooms(1)
            .setTerrace(true)
            .setFloorAreaTerrace(40)
            .setDescription(
                "Une ferme rustique en compagnie de Dwight Schrute, interdit à Jim Halpert")
            .setTitle("Une ferme")
            .setWifi(false)
            .setPricePerNight(3.3)
            .setNbMinNight(3)
            .setTele(false)
            .build();

    LinearValueFunction floorAreaV = new LinearValueFunction(0d, 200d);
    valueFunction.setFloorAreaValueFunction(floorAreaV);

    LinearValueFunction nbSleepingV = new LinearValueFunction(3d, 5d);
    valueFunction.setNbSleepingValueFunction(nbSleepingV);

    ReversedLinearValueFunction nbMinNightV = new ReversedLinearValueFunction(7d, 30d);
    valueFunction.setNbMinNightValueFunction(nbMinNightV);

    BooleanValueFunction terraceV = new BooleanValueFunction(true);
    valueFunction.setTerraceValueFunction(terraceV);

    LinearValueFunction nbBedroomsV = new LinearValueFunction(3d, 4d);
    valueFunction.setNbBedroomsValueFunction(nbBedroomsV);

    ReversedLinearValueFunction pricePerNightV = new ReversedLinearValueFunction(20d, 40d);
    valueFunction.setPricePerNightValueFunction(pricePerNightV);

    BooleanValueFunction teleV = new BooleanValueFunction(true);
    valueFunction.setTeleValueFunction(teleV);

    BooleanValueFunction wifiV = new BooleanValueFunction(true);
    valueFunction.setWifiValueFunction(wifiV);

    LinearValueFunction nbBathroomsV = new LinearValueFunction(2d, 3d);
    valueFunction.setNbBathroomsValueFunction(nbBathroomsV);

    LinearValueFunction floorAreaTerraceV = new LinearValueFunction(30d, 50d);
    valueFunction.setFloorAreaTerraceValueFunction(floorAreaTerraceV);
  }

  /** Function to test the some setters */
  @Test
  void checkValue() {

    LinearValueFunction lvf = (LinearValueFunction) valueFunction.getNbSleepingValueFunction();
    assertEquals(5d, lvf.getInterval().upperEndpoint());
    lvf = (LinearValueFunction) valueFunction.getNbBedroomsValueFunction();
    assertEquals(4d, lvf.getInterval().upperEndpoint());
    lvf = (LinearValueFunction) valueFunction.getNbBathroomsValueFunction();
    assertEquals(3d, lvf.getInterval().upperEndpoint());
  }

  /** Function to test the computing of the subjective value of an apartment */
  @Test
  void apartmentValueFunctionTest() {

    assertEquals(0.5, valueFunction.getSubjectiveValue(a), 0.0001);

    valueFunction.setTeleSubjectiveValueWeight(10d);
    assertEquals(10d, valueFunction.getSubjectiveValueWeight(Criterion.TELE));
    assertEquals(0.04587, valueFunction.getSubjectiveValue(a), 0.00001);
  }

  /** Test if the weight setter throw a Illegal Argument Exception when needed */
  @Test
  void exceptionIllegalArgWeightSetter() {

    assertThrows(
        IllegalArgumentException.class, () -> valueFunction.setFloorAreaSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.setNbBedroomsSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.setNbSleepingSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.setNbBathroomsSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class, () -> valueFunction.setTerraceSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.setFloorAreaTerraceSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class, () -> valueFunction.setWifiSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.setPricePerNightSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.setNbMinNightSubjectiveValueWeight(-1d));
    assertThrows(
        IllegalArgumentException.class, () -> valueFunction.setTeleSubjectiveValueWeight(-1d));
  }

  /** Function to test the adaptation to the subjective value weight of a criteria */
  @Test
  void adaptWeightTest() {

    assertThrows(
        IllegalArgumentException.class,
        () -> valueFunction.adaptWeight(Criterion.TELE, Criterion.TELE));
    valueFunction.setTeleSubjectiveValueWeight(7d);
    valueFunction.setTerraceSubjectiveValueWeight(3d);
    assertEquals(7d, valueFunction.getSubjectiveValueWeight(Criterion.TELE));
    assertEquals(3d, valueFunction.getSubjectiveValueWeight(Criterion.TERRACE));
    valueFunction = valueFunction.adaptWeight(Criterion.TERRACE, Criterion.TELE);
    assertEquals(9d, valueFunction.getSubjectiveValueWeight(Criterion.TERRACE));
    assertEquals(1d, valueFunction.getSubjectiveValueWeight(Criterion.TELE));
  }

  /** Function to test if the bounds of an interval adapt well when needed */
  @Test
  void adaptBoundsTest() {

    assertThrows(
        IllegalArgumentException.class, () -> valueFunction.adaptBounds(Criterion.TELE, 0d, true));
    valueFunction = valueFunction.adaptBounds(Criterion.FLOOR_AREA_TERRACE, 25d, true);
    LinearValueFunction lvf =
        (LinearValueFunction) valueFunction.getFloorAreaTerraceValueFunction();
    assertEquals(25d, lvf.getInterval().lowerEndpoint());
    assertEquals(
        0.6,
        valueFunction
            .getFloorAreaTerraceValueFunction()
            .getSubjectiveValue(a.getFloorAreaTerrace()));
    valueFunction = valueFunction.adaptBounds(Criterion.NB_BEDROOMS, 8, false);
    double nbRooms = a.getNbBedrooms();
    assertEquals(0d, valueFunction.getNbBedroomsValueFunction().getSubjectiveValue(nbRooms));
    lvf = (LinearValueFunction) valueFunction.getNbBedroomsValueFunction();
    assertEquals(8, lvf.getInterval().upperEndpoint());
  }

  /** Function to test if a random apartment generated respects some criteria */
  @Test
  void getRandomApartmentValueFunctionTest() {

    ApartmentValueFunction apart = ApartmentValueFunction.getRandomApartmentValueFunction();
    assertEquals(1d, apart.getFloorAreaValueFunction().getSubjectiveValue(a.getFloorArea()));
    LinearValueFunction lvf = (LinearValueFunction) apart.getFloorAreaTerraceValueFunction();
    assertTrue(lvf.getInterval().upperEndpoint() <= 101d);
    assertTrue(apart.getSubjectiveValueWeight(Criterion.TELE) <= 1d);
    double sum =
        apart.getSubjectiveValueWeight(Criterion.TELE)
            + apart.getSubjectiveValueWeight(Criterion.FLOOR_AREA)
            + apart.getSubjectiveValueWeight(Criterion.FLOOR_AREA_TERRACE)
            + apart.getSubjectiveValueWeight(Criterion.NB_BATHROOMS)
            + apart.getSubjectiveValueWeight(Criterion.NB_BEDROOMS)
            + apart.getSubjectiveValueWeight(Criterion.NB_SLEEPING)
            + apart.getSubjectiveValueWeight(Criterion.NB_MIN_NIGHT)
            + apart.getSubjectiveValueWeight(Criterion.PRICE_PER_NIGHT)
            + apart.getSubjectiveValueWeight(Criterion.TERRACE)
            + apart.getSubjectiveValueWeight(Criterion.WIFI);
    assertEquals(1d, sum, 0.00001);
  }
}
