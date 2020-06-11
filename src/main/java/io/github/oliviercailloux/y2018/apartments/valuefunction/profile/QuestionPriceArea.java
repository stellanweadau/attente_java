package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.Range;
import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;

public class QuestionPriceArea {

  int price;

  int surface;

  private final String question = "Would you pay %d€ more for %d m2 more?";

  /**
   * Instantiates a new question price/area.
   *
   * @param price the price shown to the user according to his/her profile
   * @param surface the surface shown to the user according to his/her profile
   */
  private QuestionPriceArea(int price, int surface) {
    checkNotNull(price);
    checkNotNull(surface);
    this.price = price;
    this.surface = surface;
  }

  /**
   * Creates the question.
   *
   * @param price the price
   * @param surface the surface
   * @return the question price area
   */
  public static QuestionPriceArea create(int price, int surface) {
    return new QuestionPriceArea(price, surface);
  }

  public int getPrice() {
    return this.price;
  }

  public int getSurface() {
    return this.surface;
  }

  public String getQuestion() {
    return String.format(this.question, this.getPrice(), this.getSurface());
  }

  /**
   * Adapt the interval of weight contained in a profile depending on his response
   *
   * @param p the profile we need to modify
   * @param response the response of the user
   */
  public void resolve(Profile p, boolean response) {
    Range<Double> floorAreaWeight = p.getWeightRange(Criterion.FLOOR_AREA);
    Range<Double> priceWeight = p.getWeightRange(Criterion.PRICE_PER_NIGHT);

    if (response) {

      double min =
          floorAreaWeight.lowerEndpoint() + Profile.getMiddleOfRange(floorAreaWeight) * 0.2;
      double max = priceWeight.upperEndpoint() - Profile.getMiddleOfRange(priceWeight) * 0.1;

      if (min >= floorAreaWeight.upperEndpoint()) {
        Range<Double> r =
            Range.closed(floorAreaWeight.upperEndpoint(), floorAreaWeight.upperEndpoint());
        p.setWeightRange(Criterion.FLOOR_AREA, r);
      } else {
        Range<Double> r = Range.closed(min, floorAreaWeight.upperEndpoint());
        p.setWeightRange(Criterion.FLOOR_AREA, r);
      }
      if (max <= priceWeight.lowerEndpoint()) {
        Range<Double> r = Range.closed(priceWeight.lowerEndpoint(), priceWeight.lowerEndpoint());
        p.setWeightRange(Criterion.PRICE_PER_NIGHT, r);
      } else {
        Range<Double> r = Range.closed(priceWeight.lowerEndpoint(), max);
        p.setWeightRange(Criterion.PRICE_PER_NIGHT, r);
      }
    } else {

      double min = priceWeight.lowerEndpoint() + Profile.getMiddleOfRange(priceWeight) * 0.2;
      double max =
          floorAreaWeight.upperEndpoint() - Profile.getMiddleOfRange(floorAreaWeight) * 0.1;

      if (min >= priceWeight.upperEndpoint()) {
        Range<Double> r = Range.closed(priceWeight.upperEndpoint(), priceWeight.upperEndpoint());
        p.setWeightRange(Criterion.PRICE_PER_NIGHT, r);
      } else {
        Range<Double> r = Range.closed(min, priceWeight.upperEndpoint());
        p.setWeightRange(Criterion.PRICE_PER_NIGHT, r);
      }
      if (max <= floorAreaWeight.lowerEndpoint()) {
        Range<Double> r =
            Range.closed(floorAreaWeight.lowerEndpoint(), floorAreaWeight.lowerEndpoint());
        p.setWeightRange(Criterion.FLOOR_AREA, r);
      } else {
        Range<Double> r = Range.closed(floorAreaWeight.lowerEndpoint(), max);
        p.setWeightRange(Criterion.FLOOR_AREA, r);
      }
    }
  }
}
