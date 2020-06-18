package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableMap;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import java.util.Arrays;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The public class LinearAVF enables to compute the subjective values of apartments. This is
 * provided by the creation of an object LinearAVF which contains for each valuable attribute of an
 * apartment : An object of and an associated weight. Each Value function used are restricted to be
 * able to deal with profiles and questions
 */
public class LinearAVF {

  private static final Logger LOGGER = LoggerFactory.getLogger(LinearAVF.class);

  /**
   * The 10 next arguments are the objects used to compute the value function of the characteristics
   * of an apartment
   */
  private LinearValueFunction floorAreaValueFunction;

  private LinearValueFunction nbBedroomsValueFunction;
  private LinearValueFunction nbSleepingValueFunction;
  private LinearValueFunction nbBathroomsValueFunction;
  private LinearValueFunction floorAreaTerraceValueFunction;
  private BooleanValueFunction terraceValueFunction;
  private BooleanValueFunction wifiValueFunction;
  private BooleanValueFunction teleValueFunction;
  private ReversedLinearValueFunction pricePerNightValueFunction;
  private ReversedLinearValueFunction nbMinNightValueFunction;

  /**
   * The next argument gives the apartment characteristic subjective value weight in the calculation
   * of the Apartment total subjective value
   */
  private HashMap<Criterion, Double> weight;

  /**
   * Constructor of the object. By default, all the objects are ConstantValueFunction objects. By
   * default, all the weights have the same value and their sum is 1. The setters functions enable
   * to set those two.
   */
  private LinearAVF() {
    this.floorAreaValueFunction = null;
    this.nbBedroomsValueFunction = null;
    this.nbSleepingValueFunction = null;
    this.nbBathroomsValueFunction = null;
    this.terraceValueFunction = new BooleanValueFunction(true);
    this.floorAreaTerraceValueFunction = null;
    this.wifiValueFunction = new BooleanValueFunction(true);
    this.pricePerNightValueFunction = null;
    this.nbMinNightValueFunction = null;
    this.teleValueFunction = new BooleanValueFunction(true);

    this.weight = new HashMap<>();
    Arrays.stream(Criterion.values()).forEach(criterion -> weight.put(criterion, 0.0d));
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
    ImmutableMap<Criterion, Double> subjectiveValue =
        new ImmutableMap.Builder<Criterion, Double>()
            .put(
                Criterion.FLOOR_AREA,
                floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()))
            .put(
                Criterion.NB_BEDROOMS,
                nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms()))
            .put(
                Criterion.NB_SLEEPING,
                nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()))
            .put(
                Criterion.NB_BATHROOMS,
                nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()))
            .put(Criterion.TERRACE, terraceValueFunction.getSubjectiveValue(apart.getTerrace()))
            .put(
                Criterion.FLOOR_AREA_TERRACE,
                floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()))
            .put(Criterion.WIFI, wifiValueFunction.getSubjectiveValue(apart.getWifi()))
            .put(
                Criterion.PRICE_PER_NIGHT,
                pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()))
            .put(
                Criterion.NB_MIN_NIGHT,
                nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()))
            .put(Criterion.TELE, teleValueFunction.getSubjectiveValue(apart.getTele()))
            .build();

    // Check that the subjective values ​​do have a value between 0 and 1
    subjectiveValue.forEach(
        (criterion, aDouble) -> {
          LOGGER.debug("The {} subjective value has been set to {}", criterion.name(), aDouble);
          checkState(
              aDouble >= 0 && aDouble <= 1,
              "The subjective value of " + criterion.name() + "must be between 0 and 1");
        });

    double sum =
        Arrays.stream(Criterion.values())
            .map(c -> this.weight.get(c) * subjectiveValue.get(c))
            .reduce(0.0d, Double::sum);
    double division = this.weight.values().stream().reduce(0.0d, Double::sum);
    return sum / division;
  }

  /**
   * This function allows the user to clone an object LinearAVF
   *
   * @return an object LinearAVF
   */
  private LinearAVF cloneLinearAVF() {

    LinearAVF avf = new LinearAVF();

    avf.setFloorAreaValueFunction(this.floorAreaValueFunction);
    avf.setNbBedroomsValueFunction(this.nbBedroomsValueFunction);
    avf.setNbSleepingValueFunction(this.nbSleepingValueFunction);
    avf.setNbBathroomsValueFunction(this.nbBathroomsValueFunction);
    avf.setTerraceValueFunction(this.terraceValueFunction);
    avf.setFloorAreaTerraceValueFunction(this.floorAreaTerraceValueFunction);
    avf.setWifiValueFunction(this.wifiValueFunction);
    avf.setPricePerNightValueFunction(this.pricePerNightValueFunction);
    avf.setNbMinNightValueFunction(this.nbMinNightValueFunction);
    avf.setTeleValueFunction(this.teleValueFunction);
    Arrays.stream(Criterion.values())
        .forEach(criterion -> avf.weight.put(criterion, this.weight.get(criterion)));
    return avf;
  }

  /* Operation used for Weight */

  /**
   * Gives the subjective value weight of a criterion awt
   *
   * @param crit the criterion we want to know the value
   * @return the subjective value weight
   */
  public double getWeight(Criterion crit) {
    checkArgument(this.weight.containsKey(crit));
    return this.weight.get(crit);
  }

  /**
   * Sets the subjective value weight of a criterion
   *
   * @param awt the criterion we want to set
   * @param value the value we want to assign at this criterion
   * @return an object LinearAVF with the modified criterion
   */
  public LinearAVF setWeight(Criterion awt, double value) {
    checkArgument(value >= 0, "The given weight cannot be negative");
    LinearAVF avf = cloneLinearAVF();
    switch (awt) {
      case TELE:
        avf.setTeleSubjectiveValueWeight(value);
        break;
      case TERRACE:
        avf.setTerraceSubjectiveValueWeight(value);
        break;
      case WIFI:
        avf.setWifiSubjectiveValueWeight(value);
        break;
      case FLOOR_AREA:
        avf.setFloorAreaSubjectiveValueWeight(value);
        break;
      case FLOOR_AREA_TERRACE:
        avf.setFloorAreaTerraceSubjectiveValueWeight(value);
        break;
      case NB_BATHROOMS:
        avf.setNbBathroomsSubjectiveValueWeight(value);
        break;
      case NB_BEDROOMS:
        avf.setNbBedroomsSubjectiveValueWeight(value);
        break;
      case NB_SLEEPING:
        avf.setNbSleepingSubjectiveValueWeight(value);
        break;
      case NB_MIN_NIGHT:
        avf.setNbMinNightSubjectiveValueWeight(value);
        break;
      case PRICE_PER_NIGHT:
        avf.setPricePerNightSubjectiveValueWeight(value);
        break;
      default:
        throw new IllegalArgumentException();
    }

    return avf;
  }

  /**
   * Set the weight of the floor area subjective value corresponding to the importance of the floor
   * area criteria.
   *
   * @param value >= 0
   */
  private void setFloorAreaSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.FLOOR_AREA, value);
    LOGGER.debug("The floor area weight has been set to {}", value);
  }

  /**
   * Set the weight of the number of bedrooms subjective value corresponding to the importance of
   * the number of bedrooms criteria.
   *
   * @param value >= 0
   */
  private void setNbBedroomsSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.NB_BEDROOMS, value);
    LOGGER.debug("The number of bedrooms weight has been set to {}", value);
  }

  /**
   * set the weight of the number of sleeping subjective value corresponding to the importance of
   * the number of sleeping criteria.
   *
   * @param value >= 0
   */
  private void setNbSleepingSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.NB_SLEEPING, value);
    LOGGER.debug("The number of sleeping weight has been set to {}", value);
  }

  /**
   * Set the weight of the number of bathrooms subjective value corresponding to the importance of
   * the number of bathrooms criteria.
   *
   * @param value >= 0
   */
  private void setNbBathroomsSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.NB_BATHROOMS, value);
    LOGGER.debug("The number of bathrooms weight has been set to {}", value);
  }

  /**
   * Set the weight of the terrace subjective value corresponding to the importance of the terrace
   * criteria.
   *
   * @param value >= 0
   */
  private void setTerraceSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.TERRACE, value);
    LOGGER.debug("The terrace weight has been set to {}", value);
  }

  /**
   * Set the weight of the terrace floor area subjective value corresponding to the importance of
   * the terrace floor area criteria.
   *
   * @param value >= 0
   */
  private void setFloorAreaTerraceSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.FLOOR_AREA_TERRACE, value);
    LOGGER.debug("The floor area of the terrace weight has been set to {}", value);
  }

  /**
   * Set the weight of the WiFi subjective value corresponding to the importance of the WiFi
   * criteria.
   *
   * @param value >= 0
   */
  private void setWifiSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.WIFI, value);
    LOGGER.debug("The wifi weight has been set to {}", value);
  }

  /**
   * Set the weight of the price per night subjective value corresponding to the importance of the
   * price per night criteria.
   *
   * @param value >= 0
   */
  private void setPricePerNightSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.PRICE_PER_NIGHT, value);
    LOGGER.debug("The price per night weight has been set to {}", value);
  }

  /**
   * Set the weight of the minimum number of nights subjective value corresponding to the importance
   * of the minimum number of nights criteria.
   *
   * @param value >= 0
   */
  private void setNbMinNightSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.NB_MIN_NIGHT, value);
    LOGGER.debug("The number of minimum night weight has been set to {}", value);
  }

  /**
   * Set the weight of the television subjective value corresponding to the importance of the
   * television criteria.
   *
   * @param value >= 0
   */
  private void setTeleSubjectiveValueWeight(double value) {
    this.weight.put(Criterion.TELE, value);
    LOGGER.debug("The tele weight has been set to {}", value);
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the floor Area
   *
   * @return the attribute floorAreaValueFunction
   */
  public LinearValueFunction getFloorAreaValueFunction() {
    return this.floorAreaValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the floor area.
   *
   * @param floorAreaValueFunction the new LinearValueFunction for FloorArea
   */
  private void setFloorAreaValueFunction(LinearValueFunction floorAreaValueFunction) {
    this.floorAreaValueFunction = checkNotNull(floorAreaValueFunction);
    LOGGER.info("The floor area preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the floor Area
   * Terrace
   *
   * @return the attribute floorAreaTerraceValueFunction
   */
  public LinearValueFunction getFloorAreaTerraceValueFunction() {
    return this.floorAreaTerraceValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the floor area of an
   * existing terrace.
   *
   * @param floorAreaTerraceValueFunction the new LinearValueFunction for FloorAreaTerrace
   */
  private void setFloorAreaTerraceValueFunction(LinearValueFunction floorAreaTerraceValueFunction) {
    this.floorAreaTerraceValueFunction = checkNotNull(floorAreaTerraceValueFunction);
    LOGGER.info("The floor area of the terrace preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the number of
   * bathrooms
   *
   * @return the attribute nbBathroomsValueFunction
   */
  public LinearValueFunction getNbBathroomsValueFunction() {
    return this.nbBathroomsValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the number of
   * bathrooms.
   *
   * @param nbBathroomsValueFunction the new LinearValueFunction for NbBathrooms
   */
  private void setNbBathroomsValueFunction(LinearValueFunction nbBathroomsValueFunction) {
    this.nbBathroomsValueFunction = checkNotNull(nbBathroomsValueFunction);
    LOGGER.info("The number of bathrooms preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the number of
   * bedrooms
   *
   * @return the attribute nbBedroomsValueFunction
   */
  public LinearValueFunction getNbBedroomsValueFunction() {
    return this.nbBedroomsValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the number of
   * bedrooms.
   *
   * @param nbBedroomsValueFunction the new LinearValueFunction for NbBedrooms
   */
  private void setNbBedroomsValueFunction(LinearValueFunction nbBedroomsValueFunction) {
    this.nbBedroomsValueFunction = checkNotNull(nbBedroomsValueFunction);
    LOGGER.info("The number of bedrooms preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the minimum number
   * of nights the user has to stay in
   *
   * @return the attribute nbMinNightValueFunction
   */
  public ReversedLinearValueFunction getNbMinNightValueFunction() {
    return this.nbMinNightValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the minimum number of
   * nights.
   *
   * @param nbMinNightValueFunction the new ReversedLinearValueFunction for NbMinNight
   */
  private void setNbMinNightValueFunction(ReversedLinearValueFunction nbMinNightValueFunction) {
    this.nbMinNightValueFunction = checkNotNull(nbMinNightValueFunction);
    LOGGER.info("The number of minimum night preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the number of
   * people who can sleep in
   *
   * @return the attribute nbSleepingValueFunction
   */
  public LinearValueFunction getNbSleepingValueFunction() {
    return this.nbSleepingValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the accommodation
   * capacity.
   *
   * @param nbSleepingValueFunction the new LinearValueFunction for NbSleeping
   */
  private void setNbSleepingValueFunction(LinearValueFunction nbSleepingValueFunction) {
    this.nbSleepingValueFunction = checkNotNull(nbSleepingValueFunction);
    LOGGER.info("The number of sleeping preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the price per
   * night
   *
   * @return the attribute pricePerNightValueFunction
   */
  public ReversedLinearValueFunction getPricePerNightValueFunction() {
    return this.pricePerNightValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the price per night.
   *
   * @param pricePerNightValueFunction the new ReversedLinearValueFunction for PricePerNight
   */
  private void setPricePerNightValueFunction(
      ReversedLinearValueFunction pricePerNightValueFunction) {
    this.pricePerNightValueFunction = checkNotNull(pricePerNightValueFunction);
    LOGGER.info("The price per night preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the presence of a
   * television
   *
   * @return the attribute teleValueFunction
   */
  public BooleanValueFunction getTeleValueFunction() {
    return this.teleValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the presence of a
   * television.
   *
   * @param teleValueFunction the new BooleanValueFunction for Tele
   */
  private void setTeleValueFunction(BooleanValueFunction teleValueFunction) {
    this.teleValueFunction = checkNotNull(teleValueFunction);
    LOGGER.info("The tele preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the presence of a
   * terrace
   *
   * @return the attribute terraceValueFunction
   */
  public BooleanValueFunction getTerraceValueFunction() {
    return this.terraceValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the presence of a
   * terrace.
   *
   * @param terraceValueFunction the new BooleanValueFunction for Terrace
   */
  private void setTerraceValueFunction(BooleanValueFunction terraceValueFunction) {
    this.terraceValueFunction = checkNotNull(terraceValueFunction);
    LOGGER.info("The terrace preferencies has been set");
  }

  /**
   * Gets the object PartialValueFunction used to compute the subjective value of the presence of
   * the wifi
   *
   * @return the attribute wifiValueFunction
   */
  public BooleanValueFunction getWifiValueFunction() {
    return this.wifiValueFunction;
  }

  /**
   * Set the function which will be used to calculate the subjective value of the presence of a
   * wireless connection.
   *
   * @param wifiValueFunction the new BooleanValueFunction for Wifi
   */
  private void setWifiValueFunction(BooleanValueFunction wifiValueFunction) {
    this.wifiValueFunction = checkNotNull(wifiValueFunction);
    LOGGER.info("The wifi preferencies has been set");
  }

  public static class Builder {
    private LinearAVF toBuild;

    public Builder() {
      toBuild = new LinearAVF();
    }

    public LinearAVF build() {
      checkNotNull(toBuild.getFloorAreaTerraceValueFunction());
      checkNotNull(toBuild.getFloorAreaValueFunction());
      checkNotNull(toBuild.getNbBathroomsValueFunction());
      checkNotNull(toBuild.getNbBedroomsValueFunction());
      checkNotNull(toBuild.getNbMinNightValueFunction());
      checkNotNull(toBuild.getNbSleepingValueFunction());
      checkNotNull(toBuild.getPricePerNightValueFunction());
      checkNotNull(toBuild.getWifiValueFunction());
      checkNotNull(toBuild.getTeleValueFunction());
      checkNotNull(toBuild.getTerraceValueFunction());

      for (Criterion c : Criterion.values()) {
        checkNotNull(toBuild.getWeight(c));
      }

      return toBuild;
    }

    /**
     * Set the weight for the criterion given in parameters.
     *
     * @param crit the criterion concerned
     * @param value the value possible for this weight
     * @return the current instance of Builder
     */
    public Builder setWeight(Criterion crit, double value) {
      this.toBuild = toBuild.setWeight(crit, value);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area.
     *
     * @param floorAreaValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaValueFunction(LinearValueFunction floorAreaValueFunction) {
      toBuild.setFloorAreaValueFunction(floorAreaValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaValueFunction(double lowerValue, double upperValue) {
      toBuild.setFloorAreaValueFunction(new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bedrooms.
     *
     * @param nbBedroomsValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBedroomsValueFunction(LinearValueFunction nbBedroomsValueFunction) {
      toBuild.setNbBedroomsValueFunction(nbBedroomsValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bedrooms.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBedroomsValueFunction(double lowerValue, double upperValue) {
      toBuild.setNbBedroomsValueFunction(new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the accommodation
     * capacity.
     *
     * @param nbSleepingValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbSleepingValueFunction(LinearValueFunction nbSleepingValueFunction) {
      toBuild.setNbSleepingValueFunction(nbSleepingValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the accommodation
     * capacity.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbSleepingValueFunction(double lowerValue, double upperValue) {
      toBuild.setNbSleepingValueFunction(new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area of an
     * existing terrace.
     *
     * @param floorAreaTerraceValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaTerraceValueFunction(
        LinearValueFunction floorAreaTerraceValueFunction) {
      toBuild.setFloorAreaTerraceValueFunction(floorAreaTerraceValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the floor area of an
     * existing terrace.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setFloorAreaTerraceValueFunction(double lowerValue, double upperValue) {
      toBuild.setFloorAreaTerraceValueFunction(new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bathrooms.
     *
     * @param nbBathroomsValueFunction a LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBathroomsValueFunction(LinearValueFunction nbBathroomsValueFunction) {
      toBuild.setNbBathroomsValueFunction(nbBathroomsValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the number of
     * bathrooms.
     *
     * @param lowerValue the min value for this LinearValueFunction
     * @param upperValue the max value for this LinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbBathroomsValueFunction(double lowerValue, double upperValue) {
      toBuild.setNbBathroomsValueFunction(new LinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * terrace.
     *
     * @param terraceValueFunction a BooleanValueFunction
     * @return the current instance of Builder
     */
    public Builder setTerraceValueFunction(BooleanValueFunction terraceValueFunction) {
      toBuild.setTerraceValueFunction(terraceValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * terrace.
     *
     * @param value boolean true iif having a Terrace is a benefit
     * @return the current instance of Builder
     */
    public Builder setTerraceValueFunction(boolean value) {
      toBuild.setTerraceValueFunction(new BooleanValueFunction(value));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * television.
     *
     * @param teleValueFunction a BooleanValueFunction
     * @return the current instance of Builder
     */
    public Builder setTeleValueFunction(BooleanValueFunction teleValueFunction) {
      toBuild.setTeleValueFunction(teleValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * television.
     *
     * @param value boolean true iif having a TV is a benefit
     * @return the current instance of Builder
     */
    public Builder setTeleValueFunction(boolean value) {
      toBuild.setTeleValueFunction(new BooleanValueFunction(value));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * wireless connection.
     *
     * @param wifiValueFunction a BooleanValueFunction
     * @return the current instance of Builder
     */
    public Builder setWifiValueFunction(BooleanValueFunction wifiValueFunction) {
      toBuild.setWifiValueFunction(wifiValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the presence of a
     * wireless connection.
     *
     * @param value boolean true iif having Wifi is a benefit
     * @return the current instance of Builder
     */
    public Builder setWifiValueFunction(boolean value) {
      toBuild.setWifiValueFunction(new BooleanValueFunction(value));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the price per night.
     *
     * @param pricePerNightValueFunction a ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setPricePerNightValueFunction(
        ReversedLinearValueFunction pricePerNightValueFunction) {
      toBuild.setPricePerNightValueFunction(pricePerNightValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the price per night.
     *
     * @param lowerValue the min value for this ReversedLinearValueFunction
     * @param upperValue the max value for this ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setPricePerNightValueFunction(double lowerValue, double upperValue) {
      toBuild.setPricePerNightValueFunction(
          new ReversedLinearValueFunction(lowerValue, upperValue));
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the minimum number
     * of nights.
     *
     * @param nbMinNightValueFunction a ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbMinNightValueFunction(ReversedLinearValueFunction nbMinNightValueFunction) {
      toBuild.setNbMinNightValueFunction(nbMinNightValueFunction);
      return this;
    }

    /**
     * Set the function which will be used to calculate the subjective value of the minimum number
     * of nights.
     *
     * @param lowerValue the min value for this ReversedLinearValueFunction
     * @param upperValue the max value for this ReversedLinearValueFunction
     * @return the current instance of Builder
     */
    public Builder setNbMinNightValueFunction(double lowerValue, double upperValue) {
      toBuild.setNbMinNightValueFunction(new ReversedLinearValueFunction(lowerValue, upperValue));
      return this;
    }
  }
}
