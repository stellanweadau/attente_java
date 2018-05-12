package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Discrete3ValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		Discrete3ValueFunction f = new Discrete3ValueFunction("Bad","Medium","Good");
		Assert.assertEquals(0.5, f.getSubjectiveValue("Medium"), 0);
	}
	
	@Test
	void applyTest() {
		Discrete3ValueFunction f = new Discrete3ValueFunction("Bad","Medium","Good");
		Assert.assertEquals(0.5, f.apply("Medium"), 0);
	}

}
