package io.github.oliviercailloux.y2018.apartments.valuefunction;

import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.BooleanValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

class ApartmentValueFunctionTest {

	@Test
	void apartmentValueFunctionTest() throws NumberFormatException, InvalidPropertiesFormatException, IOException{
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		try(InputStream f = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml")){
			Apartment a = r.readApartment(f);

			ApartmentValueFunction valueFunction = new ApartmentValueFunction();


			LinearValueFunction floorAreaV = new LinearValueFunction(0,200.00);
			valueFunction.setFloorAreaValueFunction(floorAreaV);

			BooleanValueFunction wifiV = new BooleanValueFunction(true);
			valueFunction.setWifiValueFunction(wifiV);

			BooleanValueFunction teleV = new BooleanValueFunction(true);
			valueFunction.setTeleValueFunction(teleV); 


			Assert.assertEquals(0.1, valueFunction.getSubjectiveValue(a),0.0001);

			valueFunction.setTeleSubjectiveValueWeight(10.0);

			Assert.assertEquals(0.0091743, valueFunction.getSubjectiveValue(a),0.00001);

		}

	}

}
