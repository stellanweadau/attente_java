package io.github.oliviercailloux.y2018.apartments.valuefunction.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;

public class ProfileTest {

	Profile profile;
	
	 /** Inits the profile object before each tests */
	  @BeforeEach
	  void initEach() {
		  Profile.Builder profileBuilder = new Profile.Builder();
		  
		  for (Criterion c : Criterion.values()) {
		      profileBuilder.setWeightRange(c, Range.range(Double.valueOf(10d), BoundType.CLOSED, Double.valueOf(0d), BoundType.CLOSED));
		    }	
		  
		  profile = profileBuilder.build();
	  }
	  
	  /** Function to test the basic Profile implementation */
	  @Test
	  void profileTest() {
		  assertEquals(Double.valueOf(5d), profile.getLinearAVF().getWeight(Criterion.FLOOR_AREA), 0.0001);
		  assertEquals(Range.range(Double.valueOf(10d), BoundType.CLOSED, Double.valueOf(0d), BoundType.CLOSED), profile.getWeightRange(Criterion.NB_BEDROOMS));
	  }
	  
	  /** Function to test the Profile builder */
	  @Test
	  void builderTest() {
		  Profile.Builder profileBuilder = new Profile.Builder();
		  assertThrows(IllegalArgumentException.class, () -> profileBuilder.setWeightRange(Criterion.NB_SLEEPING, 10d, 0d));
	  }
}
