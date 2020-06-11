package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static com.google.common.base.Preconditions.checkNotNull;

public class QuestionPriceArea {
  int price;
  int surface;
  private final String question = "Would you pay %d€ more for %d m2 more?";

  private QuestionPriceArea(int price, int surface) {
    checkNotNull(price);
    checkNotNull(surface);
    this.price = price;
    this.surface = surface;
  }

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

  public void resolve(Profile p, boolean response) {
    // p.setWeightRange()
  }
}
