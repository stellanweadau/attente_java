package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearAVF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Profile {

  private static final Logger LOGGER = LoggerFactory.getLogger(Profile.class);

  private LinearAVF linearAvf;

  /**
   * The 10 next arguments gives the Range of apartment characteristic subjective value weight in
   * the calculation of the Apartment total subjective value
   */
  private Range<Double> floorAreaWeightRange;

  private Range<Double> nbBedroomsWeightRange;
  private Range<Double> nbSleepingWeightRange;
  private Range<Double> nbBathroomsWeightRange;
  private Range<Double> terraceWeightRange;
  private Range<Double> floorAreaTerraceWeightRange;
  private Range<Double> wifiSubjectiveValueWeightRange;
  private Range<Double> pricePerNightSubjectiveValueWeightRange;
  private Range<Double> nbMinNightSubjectiveValueWeightRange;
  private Range<Double> teleSubjectiveValueWeightRange;

  private Profile() {
    this.linearAvf = null;
    this.floorAreaWeightRange = null;
    this.nbBedroomsWeightRange = null;
    this.nbSleepingWeightRange = null;
    this.nbBathroomsWeightRange = null;
    this.terraceWeightRange = null;
    this.floorAreaTerraceWeightRange = null;
    this.wifiSubjectiveValueWeightRange = null;
    this.pricePerNightSubjectiveValueWeightRange = null;
    this.nbMinNightSubjectiveValueWeightRange = null;
    this.teleSubjectiveValueWeightRange = null;
  }

  /**
   * This function allows the user to clone an object Profile
   *
   * @return an object Profile
   */
  private Profile cloneProfile() {

    Profile prof = new Profile();

    prof.linearAvf = this.linearAvf;
    prof.floorAreaWeightRange = this.floorAreaWeightRange;
    prof.nbBedroomsWeightRange = this.nbBedroomsWeightRange;
    prof.nbSleepingWeightRange = this.nbSleepingWeightRange;
    prof.nbBathroomsWeightRange = this.nbBathroomsWeightRange;
    prof.terraceWeightRange = this.terraceWeightRange;
    prof.floorAreaTerraceWeightRange = this.floorAreaTerraceWeightRange;
    prof.wifiSubjectiveValueWeightRange = this.wifiSubjectiveValueWeightRange;
    prof.pricePerNightSubjectiveValueWeightRange = this.pricePerNightSubjectiveValueWeightRange;
    prof.nbMinNightSubjectiveValueWeightRange = this.nbMinNightSubjectiveValueWeightRange;
    prof.teleSubjectiveValueWeightRange = this.teleSubjectiveValueWeightRange;

    return prof;
  }

  /**
   * Gives the linearAVF of a Profile
   *
   * @return the LinearAVF of the Profile
   */
  public LinearAVF getLinearAVF() {
    return this.linearAvf;
  }

  /**
   * Gives the subjective value weight of a criterion
   *
   * @param crit the criterion we want to know the value
   * @return the range of the subjective value weight
   */
  public Range<Double> getWeightRange(Criterion crit) {
    switch (crit) {
      case TELE:
        return teleSubjectiveValueWeightRange;
      case TERRACE:
        return terraceWeightRange;
      case WIFI:
        return wifiSubjectiveValueWeightRange;
      case FLOOR_AREA:
        return floorAreaWeightRange;
      case FLOOR_AREA_TERRACE:
        return floorAreaTerraceWeightRange;
      case NB_BATHROOMS:
        return nbBathroomsWeightRange;
      case NB_BEDROOMS:
        return nbBedroomsWeightRange;
      case NB_SLEEPING:
        return nbSleepingWeightRange;
      case NB_MIN_NIGHT:
        return nbMinNightSubjectiveValueWeightRange;
      case PRICE_PER_NIGHT:
        return pricePerNightSubjectiveValueWeightRange;
      default:
        throw new IllegalArgumentException();
    }
  }

  /**
   * Get the middle of the range of the given criterion
   *
   * @param crit the Criterion
   * @return the middle of the Range of the given Criterion
   */
  double getMiddleOfRange(Criterion crit) {
    return (this.getWeightRange(crit).upperEndpoint() + this.getWeightRange(crit).lowerEndpoint())
        / 2;
  }

  /**
   * Sets the linearAVF of a Profile
   *
   * @return Profile with its LinearAVF set
   */
  public Profile setLinearAVF(LinearAVF newLinearAvf) {
    Profile prof = cloneProfile();

    prof.linearAvf = newLinearAvf;

    return prof;
  }

  /**
   * Sets the subjective value weight of a criterion
   *
   * @param crit the criterion we want to know the value
   * @return the subjective value weight
   */
  public Profile setWeightRange(Criterion crit, Range<Double> value) {
    checkRangeValidity(value);
    Profile prof = cloneProfile();
    switch (crit) {
      case TELE:
        prof.setTeleSubjectiveValueWeight(value);
        break;
      case TERRACE:
        prof.setTerraceSubjectiveValueWeight(value);
        break;
      case WIFI:
        prof.setWifiSubjectiveValueWeight(value);
        break;
      case FLOOR_AREA:
        prof.setFloorAreaSubjectiveValueWeight(value);
        break;
      case FLOOR_AREA_TERRACE:
        prof.setFloorAreaTerraceSubjectiveValueWeight(value);
        break;
      case NB_BATHROOMS:
        prof.setNbBathroomsSubjectiveValueWeight(value);
        break;
      case NB_BEDROOMS:
        prof.setNbBedroomsSubjectiveValueWeight(value);
        break;
      case NB_SLEEPING:
        prof.setNbSleepingSubjectiveValueWeight(value);
        break;
      case NB_MIN_NIGHT:
        prof.setNbMinNightSubjectiveValueWeight(value);
        break;
      case PRICE_PER_NIGHT:
        prof.setPricePerNightSubjectiveValueWeight(value);
        break;
      default:
        throw new IllegalArgumentException();
    }

    return prof;
  }

  /**
   * check if the given range is valid for this context
   *
   * @param value the Range to check
   * @throws IllegalArgumentException when doubles contained are < 0 or if there are only 1 value in
   *     the range
   */
  private void checkRangeValidity(Range<Double> value) {
    checkNotNull(value, "The rage cannot be null");
    checkArgument(value.hasLowerBound() && value.hasUpperBound(), "The given range is not valid");
    checkArgument(
        value.lowerBoundType().equals(BoundType.CLOSED)
            && value.upperBoundType().equals(BoundType.CLOSED),
        "The given range must have closed bounds");
    checkArgument(
        value.lowerEndpoint() >= 0 && value.upperEndpoint() > 0,
        "The weight of the range cannot be negative");
  }

  /**
   * Set the weight of the floor area subjective value corresponding to the importance of the floor
   * area criteria.
   *
   * @param value >= 0
   */
  private void setFloorAreaSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.floorAreaWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(Criterion.FLOOR_AREA, getMiddleOfRange(Criterion.FLOOR_AREA));
    LOGGER.debug("The floor area weight has been set to {}", value);
  }

  /**
   * Set the weight of the number of bedrooms subjective value corresponding to the importance of
   * the number of bedrooms criteria.
   *
   * @param value >= 0
   */
  private void setNbBedroomsSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.nbBedroomsWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(Criterion.NB_BEDROOMS, getMiddleOfRange(Criterion.NB_BEDROOMS));
    LOGGER.debug("The number of bedrooms weight has been set to {}", value);
  }

  /**
   * set the weight of the number of sleeping subjective value corresponding to the importance of
   * the number of sleeping criteria.
   *
   * @param value >= 0
   */
  private void setNbSleepingSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.nbSleepingWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(Criterion.NB_SLEEPING, getMiddleOfRange(Criterion.NB_SLEEPING));
    LOGGER.debug("The number of sleeping weight has been set to {}", value);
  }

  /**
   * Set the weight of the number of bathrooms subjective value corresponding to the importance of
   * the number of bathrooms criteria.
   *
   * @param value >= 0
   */
  private void setNbBathroomsSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.nbBathroomsWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(Criterion.NB_BATHROOMS, getMiddleOfRange(Criterion.NB_BATHROOMS));
    LOGGER.debug("The number of bathrooms weight has been set to {}", value);
  }

  /**
   * Set the weight of the terrace subjective value corresponding to the importance of the terrace
   * criteria.
   *
   * @param value >= 0
   */
  private void setTerraceSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.terraceWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(Criterion.TERRACE, getMiddleOfRange(Criterion.TERRACE));
    LOGGER.debug("The terrace weight has been set to {}", value);
  }

  /**
   * Set the weight of the terrace floor area subjective value corresponding to the importance of
   * the terrace floor area criteria.
   *
   * @param value >= 0
   */
  private void setFloorAreaTerraceSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.floorAreaTerraceWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(
            Criterion.FLOOR_AREA_TERRACE, getMiddleOfRange(Criterion.FLOOR_AREA_TERRACE));
    LOGGER.debug("The floor area of the terrace weight has been set to {}", value);
  }

  /**
   * Set the weight of the WiFi subjective value corresponding to the importance of the WiFi
   * criteria.
   *
   * @param value >= 0
   */
  private void setWifiSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.wifiSubjectiveValueWeightRange = value;
    this.linearAvf = this.linearAvf.setWeight(Criterion.WIFI, getMiddleOfRange(Criterion.WIFI));
    LOGGER.debug("The wifi weight has been set to {}", value);
  }

  /**
   * Set the weight of the price per night subjective value corresponding to the importance of the
   * price per night criteria.
   *
   * @param value >= 0
   */
  private void setPricePerNightSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.pricePerNightSubjectiveValueWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(
            Criterion.PRICE_PER_NIGHT, getMiddleOfRange(Criterion.PRICE_PER_NIGHT));
    LOGGER.debug("The price per night weight has been set to {}", value);
  }

  /**
   * Set the weight of the minimum number of nights subjective value corresponding to the importance
   * of the minimum number of nights criteria.
   *
   * @param value >= 0
   */
  private void setNbMinNightSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.nbMinNightSubjectiveValueWeightRange = value;
    this.linearAvf =
        this.linearAvf.setWeight(Criterion.NB_MIN_NIGHT, getMiddleOfRange(Criterion.NB_MIN_NIGHT));
    LOGGER.debug("The number of minimum night weight has been set to {}", value);
  }

  /**
   * Set the weight of the television subjective value corresponding to the importance of the
   * television criteria.
   *
   * @param value >= 0
   */
  private void setTeleSubjectiveValueWeight(Range<Double> value) {
    checkRangeValidity(value);
    this.teleSubjectiveValueWeightRange = value;
    this.linearAvf = this.linearAvf.setWeight(Criterion.TELE, getMiddleOfRange(Criterion.TELE));
    LOGGER.debug("The tele weight has been set to {}", value);
  }

  public static class Builder {
    private Profile toBuild;

    public Builder() {
      toBuild = new Profile();
    }

    public Profile build() {
      for (Criterion c : Criterion.values()) {
        checkNotNull(toBuild.getWeightRange(c), c.name() + " is null");
      }
      return toBuild;
    }

    public Builder setLinearAVF(LinearAVF newLinearAvf) {
      this.toBuild = toBuild.setLinearAVF(newLinearAvf);
      return this;
    }

    /**
     * Set the range of weight for the criterion given in parameters.
     *
     * @param crit the criterion concerned
     * @param lowerValue the minimal value possible for this weight
     * @param upperValue the maximal value possible for this weight
     * @return the current instance of Builder
     */
    public Builder setWeightRange(Criterion crit, double lowerValue, double upperValue) {
      this.toBuild =
          toBuild.setWeightRange(
              crit,
              Range.range(
                  Double.valueOf(lowerValue),
                  BoundType.CLOSED,
                  Double.valueOf(upperValue),
                  BoundType.CLOSED));
      return this;
    }

    public Builder setWeightRange(Criterion crit, Range<Double> value) {
      this.toBuild = toBuild.setWeightRange(crit, value);
      return this;
    }
  }
}
