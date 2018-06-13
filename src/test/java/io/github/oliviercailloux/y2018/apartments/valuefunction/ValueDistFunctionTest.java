package io.github.oliviercailloux.y2018.apartments.valuefunction;


import org.junit.Assert;
import org.junit.jupiter.api.Test;


import io.github.oliviercailloux.y2018.apartments.localize.Location;

class ValueDistFunctionTest {

	private ValueDistFunction v;
	Location appart;
	Location interest1;
	Location interest2;
	Location interest3;
	
	void initializeValueDistFunction() throws Exception {
		appart = new Location("Ville d'Avray");
		interest1 = new Location("Paris");
		interest2 = new Location("Chaville");
		interest3 = new Location("Aeroport Roissy Charles de Gaulle");
		v = new ValueDistFunction(appart);
		
		v.addInterestLocation(interest1);
		v.addInterestLocation(interest2);
		v.addInterestLocation(interest3);
	}
	
	@Test 
	void getSubjectiveValueTest() throws Exception{
		initializeValueDistFunction();
		Assert.assertEquals(0.90919444444, v.getSubjectiveValue(interest1), 0.0001);
	}
	
	@Test
	void getMaxDurationTest() throws Exception {
		initializeValueDistFunction();
		Assert.assertEquals(5091.0, v.getMaxDuration(),0);
	}

}
