package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;

public class Profile {

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

  static double getMiddleOfRange(Range<Double> range) {
    checkNotNull(range);
    return (range.upperEndpoint() + range.lowerEndpoint()) / 2;
  }

  public Profile setWeightRange(Criterion crit, Range<Double> value) {
    return new Profile();
  }
}
