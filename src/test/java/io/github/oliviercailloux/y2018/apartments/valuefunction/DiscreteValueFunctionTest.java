package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiscreteValueFunctionTest {

	@Test
	void getSubjectiveValueTest() {
		DiscreteValueFunction<String> f = new DiscreteValueFunction<>("Bad", "Medium", "Good");
		Assert.assertEquals(0.5, f.getSubjectiveValue("Medium"), 0);
	}

	@Test
	void applyTest() {
		DiscreteValueFunction<String> f = new DiscreteValueFunction<>("Bad", "Medium", "Good");
		Assert.assertEquals(0.5, f.apply("Medium"), 0);
	}

	@Test
	void ConstructorValueTest() {
		Map<Integer, Double> discreteMapTest = new HashMap<>();
		discreteMapTest.put(5, 0.0);
		discreteMapTest.put(10, 0.25);
		discreteMapTest.put(99, 1.0);
		DiscreteValueFunction<Integer> f = new DiscreteValueFunction<>(discreteMapTest);
		Assert.assertEquals(1.0, f.getSubjectiveValue(99), 0);
	}

	@Test
	void exceptionTesting() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Map<Integer, Double> discreteMapTest = new HashMap<>();
			discreteMapTest.put(5, 0.0);
			discreteMapTest.put(10, 0.25);
			discreteMapTest.put(99, 1.2);
			DiscreteValueFunction<Integer> f = new DiscreteValueFunction<>(discreteMapTest);
			Assert.assertEquals(1.2, f.getSubjectiveValue(99), 0);
		});
	}

	@Test
	void exceptionDiffArgsString() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			@SuppressWarnings("unused")
			DiscreteValueFunction<String> vF = new DiscreteValueFunction<>("Unique", "Unique", "Toto");
		});
	}

}
