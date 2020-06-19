package io.github.oliviercailloux.y2018.apartments.valuefunction;

public enum Criterion {
  TELE(ValueFunctionType.IS_BOOLEAN_CRESCENT),
  TERRACE(ValueFunctionType.IS_BOOLEAN_CRESCENT),
  WIFI(ValueFunctionType.IS_BOOLEAN_CRESCENT),
  FLOOR_AREA(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT),
  FLOOR_AREA_TERRACE(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT),
  NB_BATHROOMS(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT),
  NB_BEDROOMS(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT),
  NB_SLEEPING(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT),
  NB_MIN_NIGHT(ValueFunctionType.IS_NOT_BOOLEAN_DECREASE),
  PRICE_PER_NIGHT(ValueFunctionType.IS_NOT_BOOLEAN_DECREASE);

  private ValueFunctionType valueFunctionType;

  Criterion(ValueFunctionType valueFunctionType) {
    this.valueFunctionType = valueFunctionType;
  }

  public ValueFunctionType getValueFunctionType() {
    return this.valueFunctionType;
  }

  public boolean hasBooleanDomain() {
    return this.valueFunctionType.equals(ValueFunctionType.IS_BOOLEAN_CRESCENT)
        || this.valueFunctionType.equals(ValueFunctionType.IS_BOOLEAN_DECREASE);
  }

  public boolean hasDoubleDomain() {
    return this.valueFunctionType.equals(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT)
        || this.valueFunctionType.equals(ValueFunctionType.IS_NOT_BOOLEAN_DECREASE);
  }

  public boolean isValueFunctionType(ValueFunctionType vft) {
    return this.valueFunctionType.equals(vft);
  }

  public boolean isNotBooleanCrescent() {
    return this.valueFunctionType.equals(ValueFunctionType.IS_NOT_BOOLEAN_CRESCENT);
  }

  public boolean isNotBooleanDecrease() {
    return this.valueFunctionType.equals(ValueFunctionType.IS_NOT_BOOLEAN_DECREASE);
  }

  public boolean isBooleanCrescent() {
    return this.valueFunctionType.equals(ValueFunctionType.IS_BOOLEAN_CRESCENT);
  }

  public boolean isBooleanDecrease() {
    return this.valueFunctionType.equals(ValueFunctionType.IS_BOOLEAN_DECREASE);
  }
}
