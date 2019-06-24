package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.io.IOException;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.Assert;
import org.junit.Test;

public class PieceWiseLinearValueFunctionTest {

	@Test
	public void getSubjectiveValueNormalTest() throws IOException {

		PieceWiseLinearValueFunction p = initializePieceWise();

		Assert.assertEquals(0.75, p.getSubjectiveValue(20d), 0.001);
		Assert.assertEquals(0.25, p.getSubjectiveValue(5d), 0.001);
	}

	@Test
	public void getUtilityWithParamAboveMax() throws IOException {

		PieceWiseLinearValueFunction p = initializePieceWise();

		Assert.assertEquals(1d, p.getSubjectiveValue(70d), 0);

	}

	@Test
	public void getUtilityWithParamBelowMin() throws IOException {

		PieceWiseLinearValueFunction p = initializePieceWise();
		
		Assert.assertEquals(0, p.getSubjectiveValue(-10d), 0);

	}

	private static PieceWiseLinearValueFunction initializePieceWise() {

		SortedMap<Double, Double> map = new ConcurrentSkipListMap<>();
		map.put(0d, 0d);
		map.put(10d, 0.5);
		map.put(30d, 1d);
		
		return new PieceWiseLinearValueFunction(map);
	}
}
