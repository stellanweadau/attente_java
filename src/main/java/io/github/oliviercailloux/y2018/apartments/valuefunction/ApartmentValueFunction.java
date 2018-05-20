package io.github.oliviercailloux.y2018.apartments.valuefunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.valuefunction.LinearValueFunction;
import io.github.oliviercailloux.y2018.apartments.valuefunction.BooleanValueFunction;

/**
 * The public class ApartmentValueFunction enables the creation of an object which contains for each valuable attribute of an apartment : An object of {@link PartialValueFunction} and a weight. 
 * We can call on this object a function with an object of type Apartment as a parameter and each valuable attribute of this apartment will be computed with the {@link PartialValueFunction} associated to return its subjective value. 
 * The construction of an ApartmentPartialValueFunction object has to be done with for parameters : an object of type LinearValueFunction corresponding to the floor area and an other of the same type corresponding to the price per night.
 * Each others {@link PartialValueFunction} corresponding to the others valuable attributes can be set with the setters functions.
 * If the {@link PartialValueFunction} of an attribute is not set, when computed the subjective value of the corresponding attribute will be 0.
 */
public class ApartmentValueFunction {

	/**
	 * @floorAreaValueFunction A {@link LinearValueFunction} object on which the calculation of the floor area subjective value will be based
	 */
	private LinearValueFunction floorAreaValueFunction;
	private LinearValueFunction nbBedroomsValueFunction;
	private LinearValueFunction nbSleepingValueFunction;
	private LinearValueFunction nbBathroomsValueFunction;
	private BooleanValueFunction terraceValueFunction;
	private LinearValueFunction floorAreaTerraceValueFunction;
	private BooleanValueFunction wifiValueFunction;
	private LinearValueFunction pricePerNightValueFunction;
	private LinearValueFunction nbMinNightValueFunction;
	private BooleanValueFunction teleValueFunction;

	/**
	 * @floorAreaSubjectiveValueWeight the weight associated to the floor area subjective value in the calculation of the Apartment total subjective value
	 */
	private double floorAreaSubjectiveValueWeight;
	private double nbBedroomsSubjectiveValueWeight;
	private double nbSleepingSubjectiveValueWeight;
	private double nbBathroomsSubjectiveValueWeight;
	private double terraceSubjectiveValueWeight;
	private double floorAreaTerraceSubjectiveValueWeight;
	private double wifiSubjectiveValueWeight;
	private double pricePerNightSubjectiveValueWeight;
	private double nbMinNightSubjectiveValueWeight;
	private double teleSubjectiveValueWeight;

	static Logger LOGGER = LoggerFactory.getLogger(ApartmentValueFunction.class);


	/**
	 * Constructor of the object {@link ApartmentValueFunction}
	 * By default, all the {@link PartialValueFunction} objects are set to <code>null</code> and the weight to <code>1</code>.
	 * The setters functions enable to set those items. 
	 * If the {@link ApartmentValueFunction} objects aren't set, the subjective value of the corresponding attribute will be 0 in the calculation of the Apartment subjective value.
	 */
	//PartialValueFunction<String> partialValueString, PartialValueFunction<Double> partialValueDouble, String objDataString, double objDataDouble
	public ApartmentValueFunction() {
		this.floorAreaValueFunction = null;
		this.nbBedroomsValueFunction = null;
		this.nbSleepingValueFunction = null;
		this.nbBathroomsValueFunction = null;
		this.terraceValueFunction = null;
		this.floorAreaTerraceValueFunction = null;
		this.wifiValueFunction = null;
		this.pricePerNightValueFunction = null;
		this.nbMinNightValueFunction = null;
		this.teleValueFunction = null;

		this.floorAreaSubjectiveValueWeight= 1;
		this.nbBedroomsSubjectiveValueWeight= 1;
		this.nbSleepingSubjectiveValueWeight= 1;
		this.nbBathroomsSubjectiveValueWeight= 1;
		this.terraceSubjectiveValueWeight= 1;
		this.floorAreaTerraceSubjectiveValueWeight= 1;
		this.wifiSubjectiveValueWeight= 1;
		this.pricePerNightSubjectiveValueWeight= 1;
		this.nbMinNightSubjectiveValueWeight= 1;
		this.teleSubjectiveValueWeight= 1;
	}

	/**
	 * set the function which will be used to calculate the subjective value of the floor area
	 * @param floorAreaValueFunction an object of type {@link LinearValueFunction} used to calculate the subjective value of the floor area.
	 * @throws IllegalArgumentException
	 */
	public void setFloorAreaValueFunction(LinearValueFunction floorAreaValueFunction) throws IllegalArgumentException {
		this.floorAreaValueFunction = floorAreaValueFunction;
		LOGGER.info("The floor area preferencies has been set");
	}

	public void setNbBedroomsValueFunction(LinearValueFunction nbBedroomsValueFunction) throws IllegalArgumentException {
		this.nbBedroomsValueFunction = nbBedroomsValueFunction;
		LOGGER.info("The number of bedrooms preferencies has been set");
	}

	public void setNbSleepingValueFunction(LinearValueFunction nbSleepingValueFunction) throws IllegalArgumentException {
		this.nbSleepingValueFunction = nbSleepingValueFunction;
		LOGGER.info("The number of sleep-in preferencies has been set");
	}

	public void setNbBathroomsValueFunction(LinearValueFunction nbBathroomsValueFunction) throws IllegalArgumentException {
		this.nbBathroomsValueFunction = nbBathroomsValueFunction;
		LOGGER.info("The number of bathrooms preferencies has been set");
	}

	public void setTerraceValueFunction(BooleanValueFunction terraceValueFunction) throws IllegalArgumentException {
		this.terraceValueFunction = terraceValueFunction;
		LOGGER.info("The terrace preferencies has been set");
	}

	public void setFloorAreaTerraceValueFunction(LinearValueFunction floorAreaTerraceValueFunction) throws IllegalArgumentException {
		this.floorAreaTerraceValueFunction = floorAreaTerraceValueFunction;
		LOGGER.info("The floor area of the terrace preferencies has been set");
	}

	public void setWifiValueFunction(BooleanValueFunction wifiValueFunction) throws IllegalArgumentException {
		this.wifiValueFunction = wifiValueFunction;
		LOGGER.info("The wifi preferencies has been set");
	}

	public void setPricePerNightValueFunction(LinearValueFunction pricePerNightValueFunction) throws IllegalArgumentException {
		this.pricePerNightValueFunction = pricePerNightValueFunction;
		LOGGER.info("The price per night preferencies has been set");
	}

	public void setNbMinNightValueFunction(LinearValueFunction nbMinNightValueFunction) throws IllegalArgumentException {
		this.nbMinNightValueFunction = nbMinNightValueFunction;
		LOGGER.info("The number of minimum night preferencies has been set");
	}

	public void setTeleValueFunction(BooleanValueFunction teleValueFunction) throws IllegalArgumentException {
		this.teleValueFunction = teleValueFunction;
		LOGGER.info("The wifi preferencies has been set");
	}


	/**
	 * Check the value of an argument :
	 * Ensures the value of an expression involving one or more parameters to the calling method.
	 * @param test the value : it corresponds to the impossible case 
	 * @param ErrorMsg the message that will be showed because of the {@link IllegalArgumentException} 
	 */
	private void checkArgument(boolean test, String ErrorMsg) {
		if (test == true) {
			throw new IllegalArgumentException (ErrorMsg);
		}
	}

	/**
	 * set the weight of the floor area subjective value corresponding to the importance of the floor area criteria 
	 * @param floorAreaSubjectiveValueWeight a positive (or 0) double 
	 */
	public void setFloorAreaSubjectiveValueWeight(double floorAreaSubjectiveValueWeight) {
		checkArgument(floorAreaSubjectiveValueWeight < 0,"The weight of the floor area cannot be negative");
		this.floorAreaSubjectiveValueWeight = floorAreaSubjectiveValueWeight;
		LOGGER.info("The floor area weight has been set");
	}

	public void setNbBedroomsSubjectiveValueWeight(double nbBedroomsSubjectiveValueWeight) {
		checkArgument(nbBedroomsSubjectiveValueWeight < 0,"The weight of the number of bedrooms cannot be negative");
		this.nbBedroomsSubjectiveValueWeight = nbBedroomsSubjectiveValueWeight;
		LOGGER.info("The number of bedrooms weight has been set");
	}

	public void setNbSleepingSubjectiveValueWeight(double nbSleepingSubjectiveValueWeight) {
		checkArgument(nbSleepingSubjectiveValueWeight < 0,"The weight of the sleep-in cannot be negative");
		this.nbSleepingSubjectiveValueWeight = nbSleepingSubjectiveValueWeight;
		LOGGER.info("The number of sleep-in weight has been set");
	}

	public void setNbBathroomsSubjectiveValueWeight(double nbBathroomsSubjectiveValueWeight) {
		checkArgument(nbBathroomsSubjectiveValueWeight < 0,"The weight of the number of bathrooms cannot be negative");
		this.nbBathroomsSubjectiveValueWeight = nbBathroomsSubjectiveValueWeight;
		LOGGER.info("The number of bathrooms weight has been set");
	}


	public void setTerraceSubjectiveValueWeight(double terraceSubjectiveValueWeight) {
		checkArgument(terraceSubjectiveValueWeight < 0,"The weight of the terrace cannot be negative");
		this.terraceSubjectiveValueWeight = terraceSubjectiveValueWeight;
		LOGGER.info("The terrace weight has been set");
	}


	public void setFloorAreaTerraceSubjectiveValueWeight(double floorAreaTerraceSubjectiveValueWeight) {
		checkArgument(floorAreaTerraceSubjectiveValueWeight < 0,"The weight of the floor area terrace cannot be negative");
		this.floorAreaTerraceSubjectiveValueWeight = floorAreaTerraceSubjectiveValueWeight;
		LOGGER.info("The floor area of the terrace weight has been set");
	}

	public void setWifiSubjectiveValueWeight(double wifiSubjectiveValueWeight) {
		checkArgument(wifiSubjectiveValueWeight < 0,"The weight of the wifi cannot be negative");
		this.wifiSubjectiveValueWeight = wifiSubjectiveValueWeight;
		LOGGER.info("The wifi weight has been set");
	}

	public void setPricePerNightSubjectiveValueWeight(double pricePerNightSubjectiveValueWeight) {
		checkArgument(pricePerNightSubjectiveValueWeight < 0,"The weight of the price per night cannot be negative");
		this.pricePerNightSubjectiveValueWeight = pricePerNightSubjectiveValueWeight;
		LOGGER.info("The price per night weight has been set");
	}

	public void setNbMinNightSubjectiveValueWeight(double nbMinNightSubjectiveValueWeight) {
		checkArgument(nbMinNightSubjectiveValueWeight < 0,"The weight of the minimum number of nights cannot be negative");
		this.nbMinNightSubjectiveValueWeight = nbMinNightSubjectiveValueWeight;
		LOGGER.info("The number of minimum night weight has been set");
	}

	public void setTeleSubjectiveValueWeight(double teleSubjectiveValueWeight) {
		checkArgument(teleSubjectiveValueWeight < 0,"The weight of the tele cannot be negative");
		this.teleSubjectiveValueWeight = teleSubjectiveValueWeight;
		LOGGER.info("The wifi weight has been set");
	}

	/**
	 * This function is called on a ApartmentValueFunction type object, it takes an apartment in parameter and use all the PartialValueFunction of the ApartmentValueFunction object to calculate the subjective values of the corresponding apartment attributes.
	 * When the PartialValueFunction of an attribute is not set, the subjective value given to the corresponding attribute will be 0.
	 * @param apart an object of type {@link Apartment}
	 * @return the weighted sum of the apartment attributes subjective values
	 */
	public double computeValueFunction (Apartment apart) {
		double floorAreaSubjectiveValue;
		double nbBedroomsSubjectiveValue;
		double nbSleepingSubjectiveValue;
		double nbBathroomsSubjectiveValue;
		double terraceSubjectiveValue;
		double floorAreaTerraceSubjectiveValue;
		double wifiSubjectiveValue;
		double pricePerNightSubjectiveValue;
		double nbMinNightSubjectiveValue;
		double teleSubjectiveValue;
		
		if ( floorAreaValueFunction == null) {
			floorAreaSubjectiveValue = 0.0; 
		} else {
			checkArgument(floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()) < 0 || floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()) > 1 , "The subjective value of floor area should be between 0 and 1");
			floorAreaSubjectiveValue = floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea());	
		}
		LOGGER.info("the floor area subjective value has been set to "+ floorAreaSubjectiveValue);

		if ( nbBedroomsValueFunction  == null) {
			nbBedroomsSubjectiveValue  = 0; 
		} else {
			checkArgument(nbBedroomsValueFunction .getSubjectiveValue((double) apart.getNbBedrooms()) < 0 || nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms()) > 1 , "The subjective value of the number of bedrooms should be between 0 and 1");
			nbBedroomsSubjectiveValue = nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms());	
		}
		LOGGER.info("the number of bedrooms subjective value has been set to "+ nbBedroomsSubjectiveValue);
		
		if ( nbSleepingValueFunction == null) {
			nbSleepingSubjectiveValue = 0; 
		} else {
			checkArgument(nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()) < 0 || nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()) > 1 , "The subjective value of the number of sleep-in should be between 0 and 1");
			nbSleepingSubjectiveValue = nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping());	
		}
		
		
		if ( nbBathroomsValueFunction == null) {
			nbBathroomsSubjectiveValue = 0; 
		} else {
			checkArgument(nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()) < 0 || nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()) > 1 , "The subjective value of the number of bathrooms should be between 0 and 1");
			nbBathroomsSubjectiveValue = nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms());	
		}
		
		if ( terraceValueFunction == null) {
			terraceSubjectiveValue = 0; 
		} else {
			checkArgument(terraceValueFunction.getSubjectiveValue(apart.getTerrace()) < 0 || terraceValueFunction.getSubjectiveValue(apart.getTerrace()) > 1 , "The subjective value of the terrace should be between 0 and 1");
			terraceSubjectiveValue = terraceValueFunction.getSubjectiveValue(apart.getTerrace());	
		}
		
		if ( floorAreaTerraceValueFunction == null) {
			floorAreaTerraceSubjectiveValue = 0; 
		} else {
			checkArgument(floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()) < 0 || floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()) > 1 , "The subjective value of the floor area of the terrace should be between 0 and 1");
			floorAreaTerraceSubjectiveValue = floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace());	
		}
		
		if ( wifiValueFunction == null) {
			wifiSubjectiveValue = 0; 
		} else {
			checkArgument(wifiValueFunction.getSubjectiveValue(apart.getWifi()) < 0 || wifiValueFunction.getSubjectiveValue(apart.getWifi()) > 1 , "The subjective value of the wifi should be between 0 and 1");
			wifiSubjectiveValue = wifiValueFunction.getSubjectiveValue(apart.getWifi());	
		}
		
		if ( pricePerNightValueFunction == null) {
			pricePerNightSubjectiveValue = 0; 
		} else {
			checkArgument(pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()) < 0 || pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()) > 1 , "The subjective value of the price per night should be between 0 and 1");
			pricePerNightSubjectiveValue = pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight());	
		}
		
		if ( nbMinNightValueFunction == null) {
			nbMinNightSubjectiveValue = 0; 
		} else {
			checkArgument(nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()) < 0 || nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()) > 1 , "The subjective value of the minimum number of nights should be between 0 and 1");
			nbMinNightSubjectiveValue = nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight());	
		}
		
		if ( teleValueFunction == null) {
			teleSubjectiveValue = 0; 
		} else {
			checkArgument(teleValueFunction.getSubjectiveValue(apart.getTele()) < 0 || teleValueFunction.getSubjectiveValue(apart.getTele()) > 1 , "The subjective value of the presence of a tele should be between 0 and 1");
			teleSubjectiveValue = teleValueFunction.getSubjectiveValue(apart.getTele());	
		}
		
		return floorAreaSubjectiveValue * floorAreaSubjectiveValueWeight + nbBedroomsSubjectiveValue * nbBedroomsSubjectiveValueWeight + nbSleepingSubjectiveValue * nbSleepingSubjectiveValueWeight + nbBathroomsSubjectiveValue * nbBathroomsSubjectiveValueWeight + terraceSubjectiveValue * terraceSubjectiveValueWeight + floorAreaTerraceSubjectiveValue * floorAreaTerraceSubjectiveValueWeight + wifiSubjectiveValue * wifiSubjectiveValueWeight + pricePerNightSubjectiveValue * pricePerNightSubjectiveValueWeight + nbMinNightSubjectiveValue * nbMinNightSubjectiveValueWeight + teleSubjectiveValue * teleSubjectiveValueWeight; 
		
	}
	
	/**
	 * This function enables to know the sum of the different subjective values weight, so it is the greatest number that the subjective value of an apartment can take with this configuration (since the biggest number of each subjective value is 1).
	 * @return a double positive or equal to 0
	 */
	public double getScale() {
		return floorAreaSubjectiveValueWeight + nbBedroomsSubjectiveValueWeight + nbSleepingSubjectiveValueWeight + nbBathroomsSubjectiveValueWeight + terraceSubjectiveValueWeight + floorAreaTerraceSubjectiveValueWeight + wifiSubjectiveValueWeight + pricePerNightSubjectiveValueWeight + nbMinNightSubjectiveValueWeight + teleSubjectiveValueWeight; 
		
	}



}
