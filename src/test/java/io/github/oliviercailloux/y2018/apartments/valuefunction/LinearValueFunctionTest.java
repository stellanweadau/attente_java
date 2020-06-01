package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LinearValueFunctionTest {

  @Test
  void getSubjectiveValueTest() {
    LinearValueFunction f = new LinearValueFunction(10, 50);
    assertEquals(0.25, f.getSubjectiveValue(20.0));
  }

  @Test
  void getSubjectiveValueLowerTest() {
    LinearValueFunction f = new LinearValueFunction(10, 50);
    assertEquals(1, f.getSubjectiveValue(100.0));
  }

  @Test
  void getSubjectiveValueUpperTest() {
    LinearValueFunction f = new LinearValueFunction(10, 50);
    assertEquals(0, f.getSubjectiveValue(9.0));
  }

  @Test
  void exceptionIllegalArgEquals() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          @SuppressWarnings("unused")
          LinearValueFunction e = new LinearValueFunction(10, 10);
        });
  }
}
