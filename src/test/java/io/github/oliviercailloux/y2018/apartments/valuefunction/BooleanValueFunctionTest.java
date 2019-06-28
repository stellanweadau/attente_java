package io.github.oliviercailloux.y2018.apartments.valuefunction;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class BooleanValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		BooleanValueFunction b = new BooleanValueFunction(true);
		Assert.assertEquals(1, b.getSubjectiveValue(true), 0);
		Assert.assertEquals(0, b.getSubjectiveValue(false), 0);
	}

	@Test
	void applyTest() {
		BooleanValueFunction bo = new BooleanValueFunction(false);
		Assert.assertEquals(0, bo.apply(true), 0);
		Assert.assertEquals(1, bo.apply(false), 0);

	}
}