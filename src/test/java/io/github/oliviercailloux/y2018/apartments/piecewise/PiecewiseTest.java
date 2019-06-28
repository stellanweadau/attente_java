package io.github.oliviercailloux.y2018.apartments.piecewise;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.piecewise.PiecewiseLinearValueFunction;

public class PiecewiseTest{

	@Test
	void getUtilityNormalTest() throws IOException {
		PiecewiseLinearValueFunction p = initializePieceWise();
		assertEquals(0.55, p.getUtility(55), 0);
	}
		
	@Test
	void getUtilityWithParamAboveMax() throws IOException {
		PiecewiseLinearValueFunction p = initializePieceWise();
		assertEquals(1, p.getUtility(70), 0);
	}

	@Test
	void getUtilityWithParamBelowMin() throws IOException{
		PiecewiseLinearValueFunction p = initializePieceWise();

		assertEquals(0, p.getUtility(20), 0);


	}

	private PiecewiseLinearValueFunction initializePieceWise()
	{
		PiecewiseLinearValueFunction p = new PiecewiseLinearValueFunction("Surface");
		p.setUtility(60, 0.6);
		p.setUtility(30, 0.3);
		p.setUtility(50, 0.5);
		return p;
	}
}
