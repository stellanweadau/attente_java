package io.github.oliviercailloux.y2018.apartments.valuefunction;

import com.google.common.collect.ImmutableList;

public enum Criterion {
  TELE,
  TERRACE,
  WIFI,
  FLOOR_AREA,
  FLOOR_AREA_TERRACE,
  NB_BATHROOMS,
  NB_BEDROOMS,
  NB_SLEEPING,
  NB_MIN_NIGHT,
  PRICE_PER_NIGHT;

  public static ImmutableList<Criterion> getCriterias() {
    return ImmutableList.of(
        Criterion.TELE,
        Criterion.TERRACE,
        Criterion.NB_BEDROOMS,
        Criterion.PRICE_PER_NIGHT,
        Criterion.FLOOR_AREA_TERRACE,
        Criterion.FLOOR_AREA,
        Criterion.TERRACE,
        Criterion.NB_BATHROOMS,
        Criterion.WIFI,
        Criterion.NB_SLEEPING,
        Criterion.NB_MIN_NIGHT);
  }
}
