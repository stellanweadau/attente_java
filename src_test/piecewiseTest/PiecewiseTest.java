package piecewiseTest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.piecewise.PiecewiseLinearValueFunction;

public class PiecewiseTest{

	@Test
	void getUtilityNormalTest() throws IOException {
		PiecewiseLinearValueFunction p = initializePieceWise();
		Assert.assertEquals(0.32, p.getUtility(36), 0);
	}
	
	@Test
	void getUtilityWithParamAboveMax() throws IOException {
		PiecewiseLinearValueFunction p = initializePieceWise();
		Assert.assertEquals(1, p.getUtility(55), 0);
	}

	@Test
	void getUtilityWithParamBelowMin() throws IOException{
		PiecewiseLinearValueFunction p = initializePieceWise();

		Assert.assertEquals(0, p.getUtility(25), 0);


	}

	@Test
	void getLinearValueWrongArgument() {
		PiecewiseLinearValueFunction p = initializePieceWise();
		Assertions.assertThrows(IllegalArgumentException.class, () -> p.getLinearValue(30, 30, 0.1, 0.1));	
	}

	private PiecewiseLinearValueFunction initializePieceWise()
	{
		PiecewiseLinearValueFunction p = new PiecewiseLinearValueFunction("Surface");
		p.setUtility(30, 0.2);
		p.setUtility(40, 0.4);
		p.setUtility(50, 0.8);
		return p;
	}
}
