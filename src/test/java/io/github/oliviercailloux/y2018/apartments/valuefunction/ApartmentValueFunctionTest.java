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
	/* 
	Apartment readApartmentTest() throws IllegalArgumentException, IllegalAccessException, IOException{
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		InputStream f = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml");
		Apartment a = r.readApartment(f);
		return a;
	} */
	@Test
	void apartmentValueFunctionTest() throws NumberFormatException, InvalidPropertiesFormatException, IOException{
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		InputStream f = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml");
		Apartment a = r.readApartment(f);

		ApartmentValueFunction valueFunction = new ApartmentValueFunction();

		LinearValueFunction floorAreaV = new LinearValueFunction(0,1182118.48);
		valueFunction.setFloorAreaValueFunction(floorAreaV);

		LinearValueFunction pricePerNightV = new LinearValueFunction(0,404);
		valueFunction.setPricePerNightValueFunction(pricePerNightV);
		
		BooleanValueFunction wifiV = new BooleanValueFunction(0.84,0.03);
		valueFunction.setWifiValueFunction(wifiV);
		
		
		BooleanValueFunction teleV = new BooleanValueFunction(0.03,0.83);
		valueFunction.setTeleValueFunction(teleV);
		
		Assert.assertEquals(3.67, valueFunction.computeValueFunction(a),0);
		
		Assert.assertEquals(3.67, valueFunction.getScale(),0);
		
		valueFunction.setPricePerNightSubjectiveValueWeight(3.0);
		
		Assert.assertEquals(5.67, valueFunction.computeValueFunction(a),0);
		Assert.assertEquals(5.67, valueFunction.getScale(),0);
		
	}



}
