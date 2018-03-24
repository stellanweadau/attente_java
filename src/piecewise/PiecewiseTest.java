package piecewise;


import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PiecewiseTest {
	
	

	
	@Test
	void getUtilityNormalTest() {
		PiecewiseLinearValueFunction p = new PiecewiseLinearValueFunction("Surface");
		p.setUtility(30, 0.2);
		p.setUtility(40, 0.4);
		p.setUtility(50, 0.8);
		
		Assert.assertEquals(0.32, p.getUtility(36), 0);
		
	
	}
	@Test
	void getUtilityWithParamAboveMax() {
		PiecewiseLinearValueFunction p = new PiecewiseLinearValueFunction("Surface");
		p.setUtility(30, 0.2);
		p.setUtility(40, 0.4);
		p.setUtility(50, 0.8);
		
		Assert.assertEquals(1, p.getUtility(55), 0);
	}
	
	@Test
	void getUtilityWithParamBelowMin() {
		PiecewiseLinearValueFunction p = new PiecewiseLinearValueFunction("Surface");
		p.setUtility(30, 0.2);
		p.setUtility(40, 0.4);
		p.setUtility(50, 0.8);
		
		Assert.assertEquals(0, p.getUtility(25), 0);
	}
	
	@Test
	void getLinearValueWrongArgument() {
		PiecewiseLinearValueFunction p = new PiecewiseLinearValueFunction("Surface");
		p.setUtility(30, 0.2);
		p.setUtility(40, 0.4);
		p.setUtility(50, 0.8);
		Assertions.assertThrows(IllegalArgumentException.class, () -> p.getLinearValue(30, 30, 0.1, 0.1));	
	}
	

}
