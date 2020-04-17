package io.github.oliviercailloux.y2018.apartments.apartment;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class for ApartmentFactory
 * @author Gabriel GUISSET & Clémence COUSIN
 */
class ApartmentFactoryTest {

	//Logger for AparmentFactoryTest class
	private static final Logger LOGGER = LoggerFactory.getLogger(ApartmentFactoryTest.class);

	/**
	 * Test GenerateApartment function 
	 * tests the correspondence between the results sent and those recorded
	 * @author Gabriel GUISSET & Clémence COUSIN
	 */
	@Test
	void testGenerateApartmentFromParameters() {
		double floorArea = 456.56; 
		String address = "A Random Address";
		int nbBedrooms = 5;
		int nbSleeping = 9;
		int nbBathrooms = 3;
		boolean terrace = true;
		double floorAreaTerrace = 25.32; 
		String description = "A Random Description for A Random House in A Random Address"; 
		String title ="A Random House"; 
		boolean wifi = true;
		double pricePerNight = 45.95;
		int nbMinNight = 60; 
		boolean tele = false;
		Apartment apart = ApartmentFactory.generateApartment(floorArea,address,nbBedrooms,
				nbSleeping,nbBathrooms,terrace,floorAreaTerrace, description, 
				title, wifi, pricePerNight,nbMinNight, tele);

		assertEquals(floorArea, apart.getFloorArea());
		assertEquals(address, apart.getAddress());
		assertEquals(nbBedrooms, apart.getNbBedrooms());
		assertEquals(nbSleeping, apart.getNbSleeping());
		assertEquals(nbBathrooms, apart.getNbBathrooms());
		assertEquals(terrace, apart.getTerrace());
		assertEquals(floorAreaTerrace, apart.getFloorAreaTerrace());
		assertEquals(description, apart.getDescription());
		assertEquals(title, apart.getTitle());
		assertEquals(wifi, apart.getWifi());
		assertEquals(pricePerNight, apart.getPricePerNight());
		assertEquals(nbMinNight, apart.getNbMinNight());
		assertEquals(tele, apart.getTele());
	}

	/**
	 * this function tests the immutability of an instance of Apartment using
	 * Method invoke to check the accessibility of setters.
	 */
	@Test
	void testImmutableApartment() {		
		Apartment apart = ApartmentFactory.generateRandomApartment();
		//Test boolean Setters Permissions
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setWifi", boolean.class).invoke(apart,true);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setTele", boolean.class).invoke(apart,true);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setTerrace", boolean.class).invoke(apart,true);  });

		//Test double Setters Permissions
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setFloorArea", double.class).invoke(apart,5.5);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setFloorAreaTerrace", double.class).invoke(apart,5.5);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setPricePerNight", double.class).invoke(apart,5.5);  });

		//Test String Setters Permissions
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setAddress", String.class).invoke(apart,"Don't");  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setDescription", String.class).invoke(apart,"Do");  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setTitle", String.class).invoke(apart,"That ! :o");  });

		//Test int Setters Permissions
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setNbBedrooms", int.class).invoke(apart,5);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setNbSleeping", int.class).invoke(apart,5);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setNbBathrooms", int.class).invoke(apart,5);  });
		assertThrows(NoSuchMethodException.class,
				()->{apart.getClass().getMethod("setNbMinNight", int.class).invoke(apart,5);  });
	}

	/**
	 * This class tests the validity of randomly generated apartment
	 * by the function generateRandomApartment() used by generateRandomApartmentList()
	 * The test are done with 100 apartments in order to have a representative list of Apartments.
	 * It will allow us to have a huge range of values and check the validity of all parameters.
	 */
	@Test
	void TestGenerateRandomAparmentsList() {
		int nbAparts = 15; 
		List<Apartment> aparts = ApartmentFactory.generateRandomApartmentList(nbAparts);
		assertEquals(aparts.size(),nbAparts);
		for(Apartment a : aparts) {
			assertNotEquals("",a.getAddress());
			assertNotNull(a.getAddress());
			assertTrue(a.getAddress().length()>=12); //Min size of an adress
			assertTrue(a.getAddress().trim().length()>0);
			assertNotEquals("",a.getDescription() );
			assertNotNull(a.getDescription());
			assertNotEquals("",a.getTitle());
			assertNotNull(a.getTitle());
			LOGGER.debug("Floor Area : "+a.getFloorArea());
			assertTrue(a.getFloorArea()>0);
			if(a.getTerrace()) {
				assertTrue(a.getFloorAreaTerrace()>0);
			}
			LOGGER.debug("Price per night : "+a.getPricePerNight());
			assertTrue(a.getPricePerNight()>=0);
			assertTrue(a.getNbMinNight()>0);
			assertTrue(a.getNbBedrooms()>=1);
			assertTrue(a.getNbSleeping()>=1);
			assertTrue(a.getNbBathrooms()>=1);

		}
	}
}
