package io.github.oliviercailloux.y2018.apartments.valuefunction;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ReversedLinearValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		ReversedLinearValueFunction f = new ReversedLinearValueFunction(10,50);
		Assert.assertEquals(0.25, f.getSubjectiveValue(40.0), 0);
	}
	
	
	@Test
	void getSubjectiveValueLowerTest() {
		ReversedLinearValueFunction f = new ReversedLinearValueFunction(10,50);
		Assert.assertEquals(1, f.getSubjectiveValue(5.0), 0);
	}
	
	
	@Test
	void getSubjectiveValueUpperTest() {
		ReversedLinearValueFunction f = new ReversedLinearValueFunction(10,50);
		Assert.assertEquals(0, f.getSubjectiveValue(55.0), 0);
	}
	
	@Test
	void applyTest() {
		ReversedLinearValueFunction f = new ReversedLinearValueFunction(10,50);
		Assert.assertEquals(0.25, f.apply(40.0), 0);
	}

}