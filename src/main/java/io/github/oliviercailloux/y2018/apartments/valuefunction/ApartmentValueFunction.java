package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.utils.RandomRange;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The public class ApartmentValueFunction enables to compute the subjective values of apartments.
 * This is provided by the creation of an object ApartmentValueFunction which contains for each
 * valuable attribute of an apartment : An object of and an associated weight.
 */
public class ApartmentValueFunction {

  /**
   * The two nexts arguments are the objects used to compute the value function of the
   * characteristics of an apartment
   */
  private EnumMap<Criterion, PartialValueFunction<Boolean>> booleanValueFunctions;

  private EnumMap<Criterion, PartialValueFunction<Double>> doubleValueFunctions;

  /**
   * The next argument gives the weight of an apartment characteristic subjective value in the
   * calculation of the Apartment total subjective value
   */
  private EnumMap<Criterion, Double> weight;

  private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentValueFunction.class);

  /**
   * Constructor of the object. By default, all the objects are ConstantValueFunction objects. By
   * default, all the weights have the same value and their sum is 1. The setters functions enable
   * to set those two.
   */
  public ApartmentValueFunction() {
    this.booleanValueFunctions = new EnumMap<>(Criterion.class);
    this.doubleValueFunctions = new EnumMap<>(Criterion.class);
    Arrays.stream(Criterion.values())
        .forEach(
            criterion -> {
              if (criterion.hasBooleanDomain()) {
                setInternalBooleanValueFunction(criterion, new ConstantValueFunction<>(0.0d));
              } else {
                setInternalDoubleValueFunction(criterion, new ConstantValueFunction<>(0.0d));
              }
            });
    this.weight = new EnumMap<>(Criterion.class);
    Arrays.stream(Criterion.values())
        .forEach(criterion -> setWeightSubjectiveValue(criterion, 0.1d));
  }

  /**
   * Used to retrieve a partial boolean value function according to the <code>criterion</code>
   * passed as a parameter
   *
   * @param criterion the criterion associated with the <code>PartialValueFunction</code> that we
   *     want to obtain
   * @return the partial boolean value function associated with the <code>criterion</code> parameter
   */
  private PartialValueFunction<Boolean> getInternalBooleanValueFunction(Criterion criterion) {
    checkNotNull(criterion);
    checkArgument(criterion.hasBooleanDomain());
    return checkNotNull(this.booleanValueFunctions.get(criterion));
  }

  /**
   * Used to retrieve a partial double value function according to the <code>criterion</code> passed
   * as a parameter
   *
   * @param criterion the criterion associated with the <code>PartialValueFunction</code> that we
   *     want to obtain
   * @return the partial double value function associated with the <code>criterion</code> parameter
   */
  private PartialValueFunction<Double> getInternalDoubleValueFunction(Criterion criterion) {
    checkNotNull(criterion);
    checkArgument(criterion.hasDoubleDomain());
    return checkNotNull(this.doubleValueFunctions.get(criterion));
  }

  /**
   * Allows you to update the Double <code>PartialValueFunction p</code> according to the <code>
   * criterion</code>
   *
   * @param criterion the criterion associated with the <code>PartialValueFunction</code> that we
   *     want to set
   * @param p <code>PartialValueFunction</code> to update
   */
  private void setInternalDoubleValueFunction(Criterion criterion, PartialValueFunction<Double> p) {
    checkNotNull(p);
    checkNotNull(criterion);
    checkArgument(criterion.hasDoubleDomain());
    this.doubleValueFunctions.put(criterion, p);
  }

  /**
   * Allows you to update the Boolean <code>PartialValueFunction p</code> according to the <code>
   * criterion</code>
   *
   * @param criterion the criterion associated with the <code>PartialValueFunction</code> that we
   *     want to obtain
   * @param p <code>PartialValueFunction</code> to update
   */
  private void setInternalBooleanValueFunction(
      Criterion criterion, PartialValueFunction<Boolean> p) {
    checkNotNull(p);
    checkNotNull(criterion);
    checkArgument(criterion.hasBooleanDomain());
    this.booleanValueFunctions.put(criterion, p);
  }

  /**
   * Set the function which will be used to calculate the subjective value of the floor area.
   *
   * @param floorAreaValueFunction
   */
  public void setFloorAreaValueFunction(PartialValueFunction<Double> floorAreaValueFunction) {
    this.setInternalDoubleValueFunction(Criterion.FLOOR_AREA, floorAreaValueFunction);
    LOGGER.info("The floor area preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the number of
   * bedrooms.
   *
   * @param nbBedroomsValueFunction
   */
  public void setNbBedroomsValueFunction(PartialValueFunction<Double> nbBedroomsValueFunction) {
    this.setInternalDoubleValueFunction(Criterion.NB_BEDROOMS, nbBedroomsValueFunction);
    LOGGER.info("The number of bedrooms preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the accommodation
   * capacity.
   *
   * @param nbSleepingValueFunction
   */
  public void setNbSleepingValueFunction(PartialValueFunction<Double> nbSleepingValueFunction) {
    this.setInternalDoubleValueFunction(Criterion.NB_SLEEPING, nbSleepingValueFunction);
    LOGGER.info("The number of sleeping preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the number of
   * bathrooms.
   *
   * @param nbBathroomsValueFunction
   */
  public void setNbBathroomsValueFunction(PartialValueFunction<Double> nbBathroomsValueFunction) {
    this.setInternalDoubleValueFunction(Criterion.NB_BATHROOMS, nbBathroomsValueFunction);
    LOGGER.info("The number of bathrooms preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the presence of a
   * terrace.
   *
   * @param terraceValueFunction
   */
  public void setTerraceValueFunction(PartialValueFunction<Boolean> terraceValueFunction) {
    this.setInternalBooleanValueFunction(Criterion.TERRACE, terraceValueFunction);
    LOGGER.info("The terrace preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the floor area of an
   * existing terrace.
   *
   * @param floorAreaTerraceValueFunction
   */
  public void setFloorAreaTerraceValueFunction(
      PartialValueFunction<Double> floorAreaTerraceValueFunction) {
    this.setInternalDoubleValueFunction(
        Criterion.FLOOR_AREA_TERRACE, floorAreaTerraceValueFunction);
    LOGGER.info("The floor area of the terrace preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the presence of a
   * wireless connection.
   *
   * @param wifiValueFunction
   */
  public void setWifiValueFunction(PartialValueFunction<Boolean> wifiValueFunction) {
    this.setInternalBooleanValueFunction(Criterion.WIFI, wifiValueFunction);
    LOGGER.info("The wifi preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the price per night.
   *
   * @param pricePerNightValueFunction
   */
  public void setPricePerNightValueFunction(
      PartialValueFunction<Double> pricePerNightValueFunction) {
    this.setInternalDoubleValueFunction(Criterion.PRICE_PER_NIGHT, pricePerNightValueFunction);
    LOGGER.info("The price per night preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the minimum number of
   * nights.
   *
   * @param nbMinNightValueFunction
   */
  public void setNbMinNightValueFunction(PartialValueFunction<Double> nbMinNightValueFunction) {
    this.setInternalDoubleValueFunction(Criterion.NB_MIN_NIGHT, nbMinNightValueFunction);
    LOGGER.info("The number of minimum night preferencies has been set");
  }

  /**
   * Set the function which will be used to calculate the subjective value of the presence of a
   * television.
   *
   * @param teleValueFunction
   */
  public void setTeleValueFunction(PartialValueFunction<Boolean> teleValueFunction) {
    this.setInternalBooleanValueFunction(Criterion.TELE, teleValueFunction);
    LOGGER.info("The tele preferencies has been set");
  }

  /**
   * Set the weight of the floor area subjective value corresponding to the importance of the floor
   * area criteria.
   *
   * @param floorAreaSubjectiveValueWeight >= 0
   */
  public void setFloorAreaSubjectiveValueWeight(double floorAreaSubjectiveValueWeight) {
    checkArgument(
        floorAreaSubjectiveValueWeight >= 0, "The weight of the floor area cannot be negative");
    this.setWeightSubjectiveValue(Criterion.FLOOR_AREA, floorAreaSubjectiveValueWeight);
    LOGGER.info("The floor area weight has been set to {}", floorAreaSubjectiveValueWeight);
  }

  /**
   * Set the weight of the number of bedrooms subjective value corresponding to the importance of
   * the number of bedrooms criteria.
   *
   * @param nbBedroomsSubjectiveValueWeight >= 0
   */
  public void setNbBedroomsSubjectiveValueWeight(double nbBedroomsSubjectiveValueWeight) {
    checkArgument(
        nbBedroomsSubjectiveValueWeight >= 0,
        "The weight of the number of bedrooms cannot be negative");
    this.setWeightSubjectiveValue(Criterion.NB_BEDROOMS, nbBedroomsSubjectiveValueWeight);
    LOGGER.info(
        "The number of bedrooms weight has been set to {}", nbBedroomsSubjectiveValueWeight);
  }

  /**
   * set the weight of the number of sleeping subjective value corresponding to the importance of
   * the number of sleeping criteria.
   *
   * @param nbSleepingSubjectiveValueWeight >= 0
   */
  public void setNbSleepingSubjectiveValueWeight(double nbSleepingSubjectiveValueWeight) {
    checkArgument(
        nbSleepingSubjectiveValueWeight >= 0, "The weight of the sleeping cannot be negative");
    this.setWeightSubjectiveValue(Criterion.NB_SLEEPING, nbSleepingSubjectiveValueWeight);
    LOGGER.info(
        "The number of sleeping weight has been set to {}", nbSleepingSubjectiveValueWeight);
  }

  /**
   * Set the weight of the number of bathrooms subjective value corresponding to the importance of
   * the number of bathrooms criteria.
   *
   * @param nbBathroomsSubjectiveValueWeight >= 0
   */
  public void setNbBathroomsSubjectiveValueWeight(double nbBathroomsSubjectiveValueWeight) {
    checkArgument(
        nbBathroomsSubjectiveValueWeight >= 0,
        "The weight of the number of bathrooms cannot be negative");
    this.setWeightSubjectiveValue(Criterion.NB_BATHROOMS, nbBathroomsSubjectiveValueWeight);
    LOGGER.info(
        "The number of bathrooms weight has been set to {}", nbBathroomsSubjectiveValueWeight);
  }

  /**
   * Set the weight of the terrace subjective value corresponding to the importance of the terrace
   * criteria.
   *
   * @param terraceSubjectiveValueWeight >= 0
   */
  public void setTerraceSubjectiveValueWeight(double terraceSubjectiveValueWeight) {
    checkArgument(
        terraceSubjectiveValueWeight >= 0, "The weight of the terrace cannot be negative");
    this.setWeightSubjectiveValue(Criterion.TERRACE, terraceSubjectiveValueWeight);
    LOGGER.info("The terrace weight has been set to {}", terraceSubjectiveValueWeight);
  }

  /**
   * Set the weight of the terrace floor area subjective value corresponding to the importance of
   * the terrace floor area criteria.
   *
   * @param floorAreaTerraceSubjectiveValueWeight >= 0
   */
  public void setFloorAreaTerraceSubjectiveValueWeight(
      double floorAreaTerraceSubjectiveValueWeight) {
    checkArgument(
        floorAreaTerraceSubjectiveValueWeight >= 0,
        "The weight of the floor area terrace cannot be negative");
    this.setWeightSubjectiveValue(
        Criterion.FLOOR_AREA_TERRACE, floorAreaTerraceSubjectiveValueWeight);
    LOGGER.info(
        "The floor area of the terrace weight has been set to {}",
        floorAreaTerraceSubjectiveValueWeight);
  }

  /**
   * Set the weight of the WiFi subjective value corresponding to the importance of the WiFi
   * criteria.
   *
   * @param wifiSubjectiveValueWeight >= 0
   */
  public void setWifiSubjectiveValueWeight(double wifiSubjectiveValueWeight) {
    checkArgument(wifiSubjectiveValueWeight >= 0, "The weight of the wifi cannot be negative");
    this.setWeightSubjectiveValue(Criterion.WIFI, wifiSubjectiveValueWeight);
    LOGGER.info("The wifi weight has been set to {}", wifiSubjectiveValueWeight);
  }

  /**
   * Set the weight of the price per night subjective value corresponding to the importance of the
   * price per night criteria.
   *
   * @param pricePerNightSubjectiveValueWeight >= 0
   */
  public void setPricePerNightSubjectiveValueWeight(double pricePerNightSubjectiveValueWeight) {
    checkArgument(
        pricePerNightSubjectiveValueWeight >= 0,
        "The weight of the price per night cannot be negative");
    this.setWeightSubjectiveValue(Criterion.PRICE_PER_NIGHT, pricePerNightSubjectiveValueWeight);
    LOGGER.info(
        "The price per night weight has been set to {}", pricePerNightSubjectiveValueWeight);
  }

  /**
   * Set the weight of the minimum number of nights subjective value corresponding to the importance
   * of the minimum number of nights criteria.
   *
   * @param nbMinNightSubjectiveValueWeight >= 0
   */
  public void setNbMinNightSubjectiveValueWeight(double nbMinNightSubjectiveValueWeight) {
    checkArgument(
        nbMinNightSubjectiveValueWeight >= 0,
        "The weight of the minimum number of nights cannot be negative");
    this.setWeightSubjectiveValue(Criterion.NB_MIN_NIGHT, nbMinNightSubjectiveValueWeight);
    LOGGER.info(
        "The number of minimum night weight has been set to {}", nbMinNightSubjectiveValueWeight);
  }

  /**
   * Set the weight of the television subjective value corresponding to the importance of the
   * television criteria.
   *
   * @param teleSubjectiveValueWeight >= 0
   */
  public void setTeleSubjectiveValueWeight(double teleSubjectiveValueWeight) {
    checkArgument(teleSubjectiveValueWeight >= 0, "The weight of the tele cannot be negative");
    this.setWeightSubjectiveValue(Criterion.TELE, teleSubjectiveValueWeight);
    LOGGER.info("The tele weight has been set to {}", teleSubjectiveValueWeight);
  }

  /**
   * This function return the subjective value of the Apartment in parameter. For each valuable
   * attribute of this apartment, the subjective value is computed by the associated
   * PartialValueFunction object. The weighted sum of these subjective values is returned by the
   * function. When the PartialValueFunction object of an attribute hasn't been set, the subjective
   * value given to the corresponding attribute will be 0.
   *
   * @param apart an object of type Apartment
   * @return a double : the weighted sum of the apartment attributes subjective values
   */
  public double getSubjectiveValue(Apartment apart) {
    checkNotNull(apart);
    ImmutableMap<Criterion, Double> subjectiveValue =
        new ImmutableMap.Builder<Criterion, Double>()
            .put(
                Criterion.FLOOR_AREA,
                this.doubleValueFunctions
                    .get(Criterion.FLOOR_AREA)
                    .getSubjectiveValue(apart.getFloorArea()))
            .put(
                Criterion.NB_BEDROOMS,
                this.doubleValueFunctions
                    .get(Criterion.NB_BEDROOMS)
                    .getSubjectiveValue((double) apart.getNbBedrooms()))
            .put(
                Criterion.NB_SLEEPING,
                this.doubleValueFunctions
                    .get(Criterion.NB_SLEEPING)
                    .getSubjectiveValue((double) apart.getNbSleeping()))
            .put(
                Criterion.NB_BATHROOMS,
                this.doubleValueFunctions
                    .get(Criterion.NB_BATHROOMS)
                    .getSubjectiveValue((double) apart.getNbBathrooms()))
            .put(
                Criterion.TERRACE,
                this.booleanValueFunctions
                    .get(Criterion.TERRACE)
                    .getSubjectiveValue(apart.getTerrace()))
            .put(
                Criterion.FLOOR_AREA_TERRACE,
                this.doubleValueFunctions
                    .get(Criterion.FLOOR_AREA_TERRACE)
                    .getSubjectiveValue(apart.getFloorAreaTerrace()))
            .put(
                Criterion.WIFI,
                this.booleanValueFunctions.get(Criterion.WIFI).getSubjectiveValue(apart.getWifi()))
            .put(
                Criterion.PRICE_PER_NIGHT,
                this.doubleValueFunctions
                    .get(Criterion.PRICE_PER_NIGHT)
                    .getSubjectiveValue(apart.getPricePerNight()))
            .put(
                Criterion.NB_MIN_NIGHT,
                this.doubleValueFunctions
                    .get(Criterion.NB_MIN_NIGHT)
                    .getSubjectiveValue((double) apart.getNbMinNight()))
            .put(
                Criterion.TELE,
                this.booleanValueFunctions.get(Criterion.TELE).getSubjectiveValue(apart.getTele()))
            .build();

    // Check that the subjective values ​​do have a value between 0 and 1
    subjectiveValue.entrySet().stream()
        .filter(c -> c.getValue() < 0 || c.getValue() > 1)
        .forEach(
            (c) ->
                checkState(
                    false,
                    "The subjective value of " + c.getKey().name() + "must be between 0 and 1"));

    double sum =
        Arrays.stream(Criterion.values())
            .map(c -> this.weight.get(c) * subjectiveValue.get(c))
            .reduce(0.0d, Double::sum);
    double division = this.weight.values().stream().reduce(0.0d, Double::sum);
    return sum / division;
  }

  /**
   * Allows us to create a ApartmentValueFunction object with random values
   *
   * @return a randomized instance of an ApartmentValueFunction
   */
  public static ApartmentValueFunction getRandomApartmentValueFunction() {
    Random random = new Random();

    ApartmentValueFunction apartValueFunction = new ApartmentValueFunction();

    DiscreteValueFunction<Double> nbBedroomsEndBoundMap =
        DiscreteValueFunction.discreteValueFunctionBeetween(4, 6);
    DiscreteValueFunction<Double> nbSleepingEndBoundMap =
        DiscreteValueFunction.discreteValueFunctionBeetween(4, 6);
    DiscreteValueFunction<Double> nbBathroomsEndBoundMap =
        DiscreteValueFunction.discreteValueFunctionBeetween(1, 3);

    int floorAreaEndBound = random.nextInt(80) + 21;
    boolean terraceEndBound = (random.nextInt(2) == 1);
    int floorAreaTerraceEndBound = random.nextInt(80) + 21;
    boolean wifiEndBound = (random.nextInt(2) == 1);
    int pricePerNightEndBound = random.nextInt(180) + 21;
    int nbMinNightEndBound = random.nextInt(7) + 3;
    boolean teleEndBound = (random.nextInt(2) == 1);

    int floorAreaStartBound = random.nextInt(floorAreaEndBound);
    int floorAreaTerraceStartBound = random.nextInt(floorAreaTerraceEndBound);
    int pricePerNightStartBound = random.nextInt(pricePerNightEndBound);
    int nbMinNightStartBound = random.nextInt(nbMinNightEndBound);

    apartValueFunction.setInternalDoubleValueFunction(
        Criterion.FLOOR_AREA, new LinearValueFunction(floorAreaStartBound, floorAreaEndBound));
    apartValueFunction.setInternalDoubleValueFunction(Criterion.NB_BEDROOMS, nbBedroomsEndBoundMap);
    apartValueFunction.setInternalDoubleValueFunction(Criterion.NB_SLEEPING, nbSleepingEndBoundMap);
    apartValueFunction.setInternalDoubleValueFunction(
        Criterion.NB_BATHROOMS, nbBathroomsEndBoundMap);
    apartValueFunction.setInternalBooleanValueFunction(
        Criterion.TERRACE, new BooleanValueFunction(terraceEndBound));
    apartValueFunction.setInternalDoubleValueFunction(
        Criterion.FLOOR_AREA_TERRACE,
        new LinearValueFunction(floorAreaTerraceStartBound, floorAreaTerraceEndBound));
    apartValueFunction.setInternalBooleanValueFunction(
        Criterion.WIFI, new BooleanValueFunction(wifiEndBound));
    apartValueFunction.setInternalDoubleValueFunction(
        Criterion.PRICE_PER_NIGHT,
        new LinearValueFunction(pricePerNightStartBound, pricePerNightEndBound));
    apartValueFunction.setInternalDoubleValueFunction(
        Criterion.NB_MIN_NIGHT,
        new ReversedLinearValueFunction(nbMinNightStartBound, nbMinNightEndBound));
    apartValueFunction.setInternalBooleanValueFunction(
        Criterion.TELE, new BooleanValueFunction(teleEndBound));

    List<Double> weightRange = RandomRange.weightRangeOfSum(1d, 10);

    LOGGER.info("Weight has been set to : {}", weightRange);

    final int[] i = {0};
    ImmutableList.of(
            Criterion.FLOOR_AREA,
            Criterion.NB_BEDROOMS,
            Criterion.NB_SLEEPING,
            Criterion.NB_BATHROOMS,
            Criterion.TERRACE,
            Criterion.FLOOR_AREA_TERRACE,
            Criterion.WIFI,
            Criterion.PRICE_PER_NIGHT,
            Criterion.NB_MIN_NIGHT,
            Criterion.TELE)
        .forEach(
            criterion ->
                apartValueFunction.setWeightSubjectiveValue(criterion, weightRange.get(i[0]++)));
    return apartValueFunction;
  }

  /**
   * We make the assumption (by casting), that the runtime PartialValueFunction associated to
   * criteria is a LinearValueFunction, even if in real life it would be a discrete criteria (e.g.
   * the number of bedrooms)
   *
   * <p>The goal is to replace a LinearValueFunction's bound by a new bound Warning : The values of
   * the object should be instantiate before using this function or an error will appear
   *
   * @param criterion the criterion to adapt. This criterion should not be a boolean as TV for
   *     example.
   * @param newBound the new bound to define
   * @param lower true if we want to adapt the lower bound, false on the other case
   * @return an object ApartmentValueFunction
   */
  public ApartmentValueFunction adaptBounds(Criterion criterion, double newBound, boolean lower) {
    ApartmentValueFunction avf = this.cloneAVF();
    checkArgument(avf.getInternalDoubleValueFunction(criterion) instanceof LinearValueFunction);
    LinearValueFunction lvf = (LinearValueFunction) avf.getInternalDoubleValueFunction(criterion);
    avf.setInternalDoubleValueFunction(criterion, adaptLinearValueFunction(lvf, newBound, lower));
    return avf;
  }

  /**
   * Adapt linear value function by defining a new lower or upper bound
   *
   * @param oldLVF the old linear value function used
   * @param newBound the new lower or upper bound
   * @param lower used to say whether we change the lower or upper bound
   * @return an new object LinearValueFunction set with new bound
   */
  private static LinearValueFunction adaptLinearValueFunction(
      LinearValueFunction oldLVF, double newBound, boolean lower) {
    checkNotNull(oldLVF);
    if (lower) {
      return new LinearValueFunction(newBound, oldLVF.getInterval().upperEndpoint());
    }
    return new LinearValueFunction(oldLVF.getInterval().lowerEndpoint(), newBound);
  }

  /**
   * This method assumes that the preference between true and false is known but doesn't matter.
   *
   * @param moreImportant is the criterion that is to be prioritized in this object of
   *     ApartmentValueFunction
   * @param lessImportant is the criterion that is to be less important in this object of
   *     ApartmentValueFunction
   * @return an object ApartmentValueFunction
   */
  public ApartmentValueFunction adaptWeight(Criterion moreImportant, Criterion lessImportant) {
    checkNotNull(lessImportant, "This criterion cannot be null");
    checkNotNull(moreImportant, "This criterion cannot be null");
    checkArgument(!Objects.equals(moreImportant, lessImportant), "Both fields are the same.");
    ApartmentValueFunction avf = cloneAVF();
    double weightSum =
        avf.getWeightSubjectiveValue(moreImportant) + avf.getWeightSubjectiveValue(lessImportant);

    avf = avf.setSubjectiveValueWeight(moreImportant, 9 * weightSum / 10);
    avf = avf.setSubjectiveValueWeight(lessImportant, weightSum / 10);

    return avf;
  }

  /**
   * Gives the subjective value weight of a criterion <code>criterion</code>
   *
   * @param criterion the criterion we want to know the value
   * @return the subjective value weight
   */
  double getWeightSubjectiveValue(Criterion criterion) {
    checkArgument(this.weight.containsKey(criterion));
    return this.weight.get(criterion);
  }

  /**
   * Update the subjective value weight of a criterion <code>criterion</code>
   *
   * @param criterion the criterion we want to set the value
   */
  public void setWeightSubjectiveValue(final Criterion criterion, final double value) {
    checkNotNull(criterion);
    this.weight.put(criterion, value);
  }

  /**
   * Sets the subjective value weight of a criterion
   *
   * @param criterion the criterion we want to set
   * @param value the value we want to assign at this criterion
   * @return an object ApartmentValueFunction with the modified criterion
   */
  public ApartmentValueFunction setSubjectiveValueWeight(
      final Criterion criterion, final double value) {
    ApartmentValueFunction avf = cloneAVF();
    avf.getWeightSubjectiveValue(criterion);
    avf.setWeightSubjectiveValue(criterion, value);
    return avf;
  }

  /**
   * This function allows the user to clone an object ApartmentValueFunction
   *
   * @return an object ApartmentValueFunction
   */
  private ApartmentValueFunction cloneAVF() {
    ApartmentValueFunction avf = new ApartmentValueFunction();
    Arrays.stream(Criterion.values())
        .forEach(
            criterion -> {
              if (criterion.hasBooleanDomain()) {
                avf.booleanValueFunctions.put(criterion, this.booleanValueFunctions.get(criterion));
              } else {
                avf.doubleValueFunctions.put(criterion, this.doubleValueFunctions.get(criterion));
              }
            });

    Arrays.stream(Criterion.values())
        .forEach(criterion -> avf.weight.put(criterion, this.weight.get(criterion)));
    return avf;
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the floor Area
   *
   * @return the attribute floorAreaValueFunction
   */
  public PartialValueFunction<Double> getFloorAreaValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.FLOOR_AREA);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the floor Area
   * Terrace
   *
   * @return the attribute floorAreaTerraceValueFunction
   */
  public PartialValueFunction<Double> getFloorAreaTerraceValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.FLOOR_AREA_TERRACE);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the number of
   * bathrooms
   *
   * @return the attribute nbBathroomsValueFunction
   */
  public PartialValueFunction<Double> getNbBathroomsValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.NB_BATHROOMS);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the number of
   * bedrooms
   *
   * @return the attribute nbBedroomsValueFunction
   */
  public PartialValueFunction<Double> getNbBedroomsValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.NB_BEDROOMS);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the minimum number
   * of nights the user has to stay in
   *
   * @return the attribute nbMinNightValueFunction
   */
  public PartialValueFunction<Double> getNbMinNightValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.NB_MIN_NIGHT);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the number of
   * people who can sleep in
   *
   * @return the attribute nbSleepingValueFunction
   */
  public PartialValueFunction<Double> getNbSleepingValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.NB_SLEEPING);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the price per
   * night
   *
   * @return the attribute pricePerNightValueFunction
   */
  public PartialValueFunction<Double> getPricePerNightValueFunction() {
    return this.getInternalDoubleValueFunction(Criterion.PRICE_PER_NIGHT);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the presence of a
   * television
   *
   * @return the attribute teleValueFunction
   */
  public PartialValueFunction<Boolean> getTeleValueFunction() {
    return this.getInternalBooleanValueFunction(Criterion.TELE);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the presence of a
   * terrace
   *
   * @return the attribute terraceValueFunction
   */
  public PartialValueFunction<Boolean> getTerraceValueFunction() {
    return this.getInternalBooleanValueFunction(Criterion.TERRACE);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the presence of
   * the wifi
   *
   * @return the attribute wifiValueFunction
   */
  public PartialValueFunction<Boolean> getWifiValueFunction() {
    return this.getInternalBooleanValueFunction(Criterion.WIFI);
  }
}
