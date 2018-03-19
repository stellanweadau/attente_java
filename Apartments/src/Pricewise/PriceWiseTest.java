package Pricewise;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PriceWiseTest {
	
	@Test
	void getUtilityNormalTest() {
		PricewiseLinearValueFunction priceWise = new PricewiseLinearValueFunction("Surface", 30, 0.7, 40, 0.9);
		Assert.assertEquals(0.8, priceWise.getUtility(35, utilityMode.limit), 0);
		Assert.assertEquals(0.8, priceWise.getUtility(35, utilityMode.reverse), 0);
	}
	@Test
	void getUtilityHigherThanOneTest() {
		PricewiseLinearValueFunction priceWise = new PricewiseLinearValueFunction("Surface", 30, 0.7, 40, 0.9);
		Assert.assertEquals(1, priceWise.getUtility(60, utilityMode.limit), 0);
		Assert.assertEquals(0.7, priceWise.getUtility(60, utilityMode.reverse), 0);
	}
	
	@Test
	void getUtilityBelowZeroTest() {
		PricewiseLinearValueFunction priceWise = new PricewiseLinearValueFunction("Surface", 30, 0.1, 40, 0.5);
		Assert.assertEquals(0, priceWise.getUtility(0, utilityMode.limit), 0);
		Assert.assertEquals(0, priceWise.getUtility(0, utilityMode.reverse), 0);
	}
	
	@Test
	void getLinearValueWrongArgument() {
		PricewiseLinearValueFunction priceWise = new PricewiseLinearValueFunction("Surface", 30, 0.1, 40, 0.5);
		try 
		{
			priceWise.getLinearValue(5, 30);
		}
		catch(IllegalArgumentException e)
		{
			Assert.assertEquals(e.getMessage(),"One of the argument is not a valid value of the Map utility");
		}
		
	}
	

}
