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

  public Class getValueFunctionClass() {
    return this.valueFunctionClass;
  }

  public static <E> E getValueFunction(PartialValueFunction partialValueFunction, Criterion criterion){
    if(criterion.getValueFunctionClass().equals(LinearValueFunction.class)){
      return (E) LinearValueFunction.class.cast(partialValueFunction);
    } else if (criterion.getValueFunctionClass().equals(BooleanValueFunction.class)){
      return (E) BooleanValueFunction.class.cast(partialValueFunction);
    } else if (criterion.getValueFunctionClass().equals(ReversedLinearValueFunction.class)){
      return (E) ReversedLinearValueFunction.class.cast(partialValueFunction);
    }
    throw new IllegalStateException();
  }
}
