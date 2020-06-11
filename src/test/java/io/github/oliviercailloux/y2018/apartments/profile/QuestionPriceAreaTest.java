package io.github.oliviercailloux.y2018.apartments.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.oliviercailloux.y2018.apartments.valuefunction.profile.Profile;
import io.github.oliviercailloux.y2018.apartments.valuefunction.profile.QuestionPriceArea;
import org.junit.jupiter.api.Test;

public class QuestionPriceAreaTest {

  /** Verifying the initialization of the object QuestionPriceArea. */
  @Test
  void initializationTest() {

    QuestionPriceArea q = QuestionPriceArea.create(100, 10);
    assertEquals(100, q.getPrice());
    assertEquals(10, q.getSurface());
    assertEquals("Would you pay 100€ more for 10 m2 more?", q.getQuestion());
  }

  /**
   * Test if the resolve method works well. The code in comment needs the class Profile first to be
   * merge to work correctly
   */
  @Test
  void resolveTest() {
    QuestionPriceArea q = QuestionPriceArea.create(100, 10);
    assertThrows(NullPointerException.class, () -> q.resolve(new Profile(), true));
    // Profile p = new Profile();
    // Range<Double> surface = Range.closed(100d, 300d);
    // Range<Double> price = Range.closed(500d, 1500d);
    // p.setWeightRange(Criterion.FLOOR_AREA, surface);
    // p.setWeightRange(Criterion.PRICE_PER_NIGHT, price);
    // q.resolve(p, false);
    // assertEquals(700d, p.getWeightRange(Criterion.PRICE_PER_NIGHT).lowerEndpoint());
    // assertEquals(280d, p.getWeightRange(Criterion.FLOOR_AREA).upperEndpoint());
    // q.resolve(p, true)
    // assertEquals(198d, p.getWeightRange(Criterion.FLOOR_AREA).lowerEndpoint());
    // assertEquals(1390d, p.getWeightRange(Criterion.PRICE_PER_NIGHT.upperEndpoint());
  }
}
