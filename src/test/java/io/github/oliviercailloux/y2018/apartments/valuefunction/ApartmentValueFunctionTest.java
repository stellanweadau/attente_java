package io.github.oliviercailloux.y2018.apartments.valuefunction;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.BooleanValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

class ApartmentValueFunctionTest {
	Apartment readApartmentTest() throws IllegalArgumentException, IllegalAccessException, IOException{
		ReadApartmentsXMLFormat r = new ReadApartmentsXMLFormat();

		InputStream f = ReadApartmentsXMLFormat.class.getResourceAsStream("xmlfileTest.xml");
		Apartment a = r.readApartment(f);
		return a;
	}
	void apartmentValueFunctionTest(){
		ApartmentValueFunction valueFunction = new ApartmentValueFunction();
		valueFunction.setFloorAreaValueFunction();
		valueFunction.setFloorAreaSubjectiveValueWeight(1);
	}
	
}


