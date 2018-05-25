package io.github.oliviercailloux.y2018.apartments.valuefunction;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BooleanValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		BooleanValueFunction b = new BooleanValueFunction(0.40,0.80);
		Assert.assertEquals(0.4, b.getSubjectiveValue(true), 0);
		Assert.assertEquals(0.8, b.getSubjectiveValue(false), 0);
	}

	@Test
	void applyTest() {
		BooleanValueFunction bo = new BooleanValueFunction(0.42,0.50);
		Assert.assertEquals(0.42, bo.apply(true), 0);
		Assert.assertEquals(0.5, bo.apply(false), 0);
	}
}
