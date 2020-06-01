package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import org.junit.jupiter.api.Test;

public class PieceWiseLinearValueFunctionTest {

  @Test
  public void getSubjectiveValueNormalTest() throws IOException {

    PieceWiseLinearValueFunction p = initializePieceWise();

    assertEquals(0.75, p.getSubjectiveValue(20d));
    assertEquals(0.25, p.getSubjectiveValue(5d));
  }

  @Test
  public void getUtilityWithParamAboveMax() throws IOException {

    PieceWiseLinearValueFunction p = initializePieceWise();

    assertEquals(1d, p.getSubjectiveValue(70d));
  }

  @Test
  public void getUtilityWithParamBelowMin() throws IOException {

    PieceWiseLinearValueFunction p = initializePieceWise();

    assertEquals(0, p.getSubjectiveValue(-10d));
  }

  private static PieceWiseLinearValueFunction initializePieceWise() {

    SortedMap<Double, Double> map = new ConcurrentSkipListMap<>();
    map.put(0d, 0d);
    map.put(10d, 0.5);
    map.put(30d, 1d);

    return new PieceWiseLinearValueFunction(map);
  }
}
