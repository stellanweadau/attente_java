package io.github.oliviercailloux.y2018.apartments.valuefunction;


import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscreteValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		DiscreteValueFunction<String> f = new DiscreteValueFunction<String>("Bad","Medium","Good");
		Assert.assertEquals(0.5, f.getSubjectiveValue("Medium"), 0);
	}
	
	@Test
	void applyTest() {
		DiscreteValueFunction<String> f = new DiscreteValueFunction<String>("Bad","Medium","Good");
		Assert.assertEquals(0.5, f.apply("Medium"), 0);
	}

	@Test
	void ConstructorValueTest() {
		Map<Integer,Double> discreteMapTest = new HashMap<Integer,Double>();
		discreteMapTest.put(5, 0.0);
		discreteMapTest.put(10, 0.25);
		discreteMapTest.put(99, 1.0);
		DiscreteValueFunction<Integer> f = new DiscreteValueFunction<Integer>(discreteMapTest);
		Assert.assertEquals(1.0,f.getSubjectiveValue(99),0);
	}
	
	@Test
	void exceptionTesting() {
	    Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Map<Integer,Double> discreteMapTest = new HashMap<Integer,Double>();
			discreteMapTest.put(5, 0.0);
			discreteMapTest.put(10, 0.25);
			discreteMapTest.put(99, 1.2);
			DiscreteValueFunction<Integer> f = new DiscreteValueFunction<Integer>(discreteMapTest);
			Assert.assertEquals(1.2,f.getSubjectiveValue(99),0);;
	      });
	}
	
	
	
	
}
