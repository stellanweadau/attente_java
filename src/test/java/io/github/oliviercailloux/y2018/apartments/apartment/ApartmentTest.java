package io.github.oliviercailloux.y2018.apartments.apartment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ApartmentTest {

	@Test
	void equalsTestTrue() {
		
		Apartment a1 = new Apartment(150,"Arras","Micky's home");
		Apartment a2 = new Apartment(150,"Arras","Micky's home");
		
		assertTrue(a1.equals(a2));
		
	}
	
	@Test
	void equalsTestFalse() {
		
		Apartment a1 = new Apartment(150,"Chaville","Marc's home");
		Apartment a2 = new Apartment(150,"Arras","Micky's home");
		
		assertFalse(a1.equals(a2));
		
	}
	
	@Test
	void hashCodeTestTrue() {
		
		Apartment a1 = new Apartment(150,"Arras","Micky's home");
		Apartment a2 = new Apartment(150,"Arras","Micky's home");
		
		assertEquals(a1.hashCode(), a2.hashCode());
		
	}
	
	@Test
	void hashCodeTestFalse() {
		
		Apartment a1 = new Apartment(150,"Chaville","Marc's home");
		Apartment a2 = new Apartment(150,"Arras","Micky's home");
		
		assertFalse(a1.hashCode() == a2.hashCode());
		
	}

}
