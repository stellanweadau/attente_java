package io.github.oliviercailloux.y2018.apartments.valuefunction;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

class LinearValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		LinearValueFunction f = new LinearValueFunction(10,50);
		Assert.assertEquals(0.25, f.getSubjectiveValue(20.0), 0);
	}
	
	@Test
	void applyTest() {
		LinearValueFunction f = new LinearValueFunction(10,50);
		Assert.assertEquals(0.25, f.apply(20.0), 0);
	}
	
	@Test
	void getSubjectiveValueLowerTest() {
		LinearValueFunction f = new LinearValueFunction(10,50);
		Assert.assertEquals(1, f.getSubjectiveValue(100.0), 0);
	}
	
	
	@Test
	void getSubjectiveValueUpperTest() {
		LinearValueFunction f = new LinearValueFunction(10,50);
		Assert.assertEquals(0, f.getSubjectiveValue(9.0), 0);
	}
	

}