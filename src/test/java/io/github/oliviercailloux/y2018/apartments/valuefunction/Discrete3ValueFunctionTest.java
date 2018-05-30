package io.github.oliviercailloux.y2018.apartments.valuefunction;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

class Discrete3ValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		DiscreteValueFunction<String> f = new DiscreteValueFunction<>("Bad","Medium","Good");
		Assert.assertEquals(0.5, f.getSubjectiveValue("Medium"), 0);
	}
	
	@Test
	void applyTest() {
		DiscreteValueFunction<String> f = new DiscreteValueFunction<>("Bad","Medium","Good");
		Assert.assertEquals(0.5, f.apply("Medium"), 0);
	}

}
