package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.*;

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


			LinearValueFunction floorAreaV = new LinearValueFunction(0,1182118.48);
			valueFunction.setFloorAreaValueFunction(floorAreaV);

			LinearValueFunction pricePerNightV = new LinearValueFunction(0,404);
			valueFunction.setPricePerNightValueFunction(pricePerNightV);

			BooleanValueFunction wifiV = new BooleanValueFunction(true);
			valueFunction.setWifiValueFunction(wifiV);

			BooleanValueFunction teleV = new BooleanValueFunction(true);
			valueFunction.setTeleValueFunction(teleV); 


			Assert.assertEquals(0.3, valueFunction.getSubjectiveValue(a),0);

			valueFunction.setTeleSubjectiveValueWeight(100.0);

			Assert.assertEquals(0.03, valueFunction.getSubjectiveValue(a),0);

		}

	}

}
