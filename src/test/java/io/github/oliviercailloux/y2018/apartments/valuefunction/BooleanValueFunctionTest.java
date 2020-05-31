package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BooleanValueFunctionTest {

  @Test
  void getSubjectiveValueOfTrueTest() {
    BooleanValueFunction b = new BooleanValueFunction(true);
    assertEquals(1, b.getSubjectiveValue(true));
    assertEquals(0, b.getSubjectiveValue(false));
  }

  @Test
  void getSubjectiveValueOfFalseTest() {
    BooleanValueFunction bo = new BooleanValueFunction(false);
    assertEquals(0, bo.getSubjectiveValue(true));
    assertEquals(1, bo.getSubjectiveValue(false));
  }
}
