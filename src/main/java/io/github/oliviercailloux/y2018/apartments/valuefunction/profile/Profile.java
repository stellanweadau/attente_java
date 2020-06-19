package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearAVF;
import java.util.EnumMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Profile {

  private static final Logger LOGGER = LoggerFactory.getLogger(Profile.class);

  private LinearAVF linearAvf;

  /**
   * rangeMap is a map of criterion containing the ranges of all the different range for these
   * criterion
   */
  private EnumMap<Criterion, Range<Double>> rangeMap;

  private Profile() {
    LinearAVF.Builder blavf =
        new LinearAVF.Builder()
            .setTeleValueFunction(true)
            .setTerraceValueFunction(true)
            .setWifiValueFunction(true)
            .setFloorAreaTerraceValueFunction(1d, 50d)
            .setFloorAreaValueFunction(9d, 200d)
            .setNbBathroomsValueFunction(1, 10)
            .setNbBedroomsValueFunction(0, 30)
            .setNbSleepingValueFunction(1, 30)
            .setNbMinNightValueFunction(1, 31)
            .setPricePerNightValueFunction(30d, 200d);
    for (Criterion c : Criterion.values()) {
      blavf.setWeight(c, 0d);
    }
    this.linearAvf = blavf.build();

    this.rangeMap = new EnumMap<>(Criterion.class);
    for (Criterion c : Criterion.values()) {
      this.rangeMap.put(c, null);
    }
  }

  /**
   * This function allows the user to clone an object Profile
   *
   * @return an object Profile
   */
  private Profile cloneProfile() {

    Profile prof = new Profile();

    prof.linearAvf = this.linearAvf;
    prof.rangeMap = this.rangeMap;

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
    checkArgument(this.rangeMap.containsKey(crit));
    return this.rangeMap.get(crit);
  }

  /**
   * Get the middle of the range of the given criterion
   *
   * @param crit the Criterion
   * @return the middle of the Range of the given Criterion
   */
  double getMiddleOfRange(Criterion crit) {
    return (this.getWeightRange(crit).upperEndpoint().doubleValue()
            + this.getWeightRange(crit).lowerEndpoint().doubleValue())
        / 2;
  }

  /**
   * Sets the linearAVF of a Profile
   *
   * @return Profile with its LinearAVF set
   */
  public Profile withLinearAVF(LinearAVF newLinearAvf) {
    Profile prof = cloneProfile();

    for (Criterion c : Criterion.values()) {
      checkWeightInRange(c, newLinearAvf);
    }

    prof.linearAvf = newLinearAvf;

    return prof;
  }

  /**
   * check if the weight of the LinearAVF of the given criterion is in the profile range for this
   * criterion
   *
   * @param crit the criterion to check the weight with the range
   * @param linearAVF the LinearAVF of check if its weight is in the range
   * @throws IllegalArgumentException when the weight of the LinearAVF is not in the range
   */
  public void checkWeightInRange(Criterion crit, LinearAVF linearAVF) {
    Range<Double> range = this.getWeightRange(crit);
    Double weight = Double.valueOf(linearAVF.getWeight(crit));
    checkNotNull(range);
    checkNotNull(weight);
    checkArgument(
        range.contains(weight), "A weight in the linear AVF is not in the targeted range");
  }

  /**
   * Sets the subjective value weight of a criterion
   *
   * @param crit the criterion we want to know the value
   * @return the subjective value weight
   */
  public void setWeightRange(Criterion crit, Range<Double> value) {
    checkArgument(this.rangeMap.containsKey(crit));
    checkRangeValidity(value);
    this.rangeMap.put(crit, value);
    this.linearAvf = this.linearAvf.setWeight(crit, getMiddleOfRange(crit));
    LOGGER.debug("The {} weight has been set to {}", crit, value);
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
      this.toBuild = toBuild.withLinearAVF(newLinearAvf);
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
      this.toBuild.setWeightRange(
          crit, Range.closed(Double.valueOf(lowerValue), Double.valueOf(upperValue)));
      return this;
    }

    public Builder setWeightRange(Criterion crit, Range<Double> value) {
      this.toBuild.setWeightRange(crit, value);
      return this;
    }
  }
}
