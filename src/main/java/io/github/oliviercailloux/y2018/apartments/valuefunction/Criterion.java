package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static com.google.common.base.Preconditions.checkArgument;

public enum Criterion {
  TELE(BooleanValueFunction.class),
  TERRACE(BooleanValueFunction.class),
  WIFI(BooleanValueFunction.class),
  FLOOR_AREA(LinearValueFunction.class),
  FLOOR_AREA_TERRACE(LinearValueFunction.class),
  NB_BATHROOMS(LinearValueFunction.class),
  NB_BEDROOMS(LinearValueFunction.class),
  NB_SLEEPING(LinearValueFunction.class),
  NB_MIN_NIGHT(ReversedLinearValueFunction.class),
  PRICE_PER_NIGHT(ReversedLinearValueFunction.class);

  private Class valueFunctionClass;

  private Criterion(Class c) {
    checkArgument(
        PartialValueFunction.class.isAssignableFrom(c),
        "The class must implement PartialValueFunction");
    this.valueFunctionClass = c;
  }

  private Class getValueFunctionClass() {
    return this.valueFunctionClass;
  }
}
