package io.github.oliviercailloux.y2018.apartments.valuefunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Random;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.utils.RandomRange;

/**
 * The public class ApartmentValueFunction enables to compute the subjective
 * values of apartments. This is provided by the creation of an object
 * ApartmentValueFunction which contains for each valuable attribute of an
 * apartment : An object of {@link PartialValueFunction} and an associated
 * weight.
 */
public class ApartmentValueFunction {

	/**
	 * @floorAreaValueFunction A {@link PartialValueFunction} object on which the
	 *                         calculation of the floor area subjective values are
	 *                         based.
	 */
	private PartialValueFunction<Double> floorAreaValueFunction;

	/**
	 * @nbBedroomsValueFunction A {@link PartialValueFunction} object on which the
	 *                          calculation of the number of bedrooms subjective
	 *                          value is based
	 */
	private PartialValueFunction<Double> nbBedroomsValueFunction;

	/**
	 * @nbSleepingValueFunction A {@link PartialValueFunction} object on which the
	 *                          calculation of the number of accommodation capacity
	 *                          subjective values are based.
	 */
	private PartialValueFunction<Double> nbSleepingValueFunction;

	/**
	 * @nbBathroomsValueFunction A {@link PartialValueFunction} object on which the
	 *                           calculation of the number of bathrooms subjective
	 *                           values are based.
	 */
	private PartialValueFunction<Double> nbBathroomsValueFunction;

	/**
	 * @terraceValueFunction A {@link PartialValueFunction} object on which the
	 *                       calculation of the presence of a terrace subjective
	 *                       values are based.
	 */
	private PartialValueFunction<Boolean> terraceValueFunction;

	/**
	 * @floorAreaTerraceValueFunction A {@link PartialValueFunction} object on which
	 *                                the calculation of the floor area of an
	 *                                existing terrace subjective values are based.
	 */
	private PartialValueFunction<Double> floorAreaTerraceValueFunction;

	/**
	 * @wifiValueFunction A {@link PartialValueFunction} object on which the
	 *                    calculation of the wireless connection subjective values
	 *                    are based.
	 */
	private PartialValueFunction<Boolean> wifiValueFunction;

	/**
	 * @pricePerNightValueFunction A {@link PartialValueFunction} object on which
	 *                             the calculation of the price per night subjective
	 *                             values are based.
	 */
	private PartialValueFunction<Double> pricePerNightValueFunction;

	/**
	 * @nbMinNightValueFunction A {@link PartialValueFunction} object on which the
	 *                          calculation of the minimum number of nights
	 *                          subjective values are based.
	 */
	private PartialValueFunction<Double> nbMinNightValueFunction;

	/**
	 * @teleValueFunction A {@link PartialValueFunction} object on which the
	 *                    calculation of the presence of a television subjective
	 *                    values are based.
	 */
	private PartialValueFunction<Boolean> teleValueFunction;

	/**
	 * @floorAreaSubjectiveValueWeight The weight associated to the floor area
	 *                                 subjective value in the calculation of the
	 *                                 Apartment total subjective value
	 */
	private double floorAreaSubjectiveValueWeight;

	/**
	 * @nbBedroomsSubjectiveValueWeight The weight associated to the number of
	 *                                  bedrooms subjective value in the calculation
	 *                                  of the Apartment total subjective value
	 */
	private double nbBedroomsSubjectiveValueWeight;

	/**
	 * @nbSleepingSubjectiveValueWeight The weight associated to the accommodation
	 *                                  capacity subjective value in the calculation
	 *                                  of the Apartment total subjective value
	 */
	private double nbSleepingSubjectiveValueWeight;

	/**
	 * @nbBathroomsSubjectiveValueWeight The weight associated to the number of
	 *                                   bathrooms subjective value in the
	 *                                   calculation of the Apartment total
	 *                                   subjective value
	 */
	private double nbBathroomsSubjectiveValueWeight;

	/**
	 * @terraceSubjectiveValueWeight The weight associated to the presence of a
	 *                               terrace subjective value in the calculation of
	 *                               the Apartment total subjective value
	 */
	private double terraceSubjectiveValueWeight;

	/**
	 * @floorAreaTerraceSubjectiveValueWeight The weight associated to the floor
	 *                                        area of an existing terrace subjective
	 *                                        value in the calculation of the
	 *                                        Apartment total subjective value
	 */
	private double floorAreaTerraceSubjectiveValueWeight;

	/**
	 * @wifiSubjectiveValueWeight The weight associated to the presence of a
	 *                            wireless connection subjective value in the
	 *                            calculation of the Apartment total subjective
	 *                            value
	 */
	private double wifiSubjectiveValueWeight;

	/**
	 * @pricePerNightSubjectiveValueWeight The weight associated to the price per
	 *                                     night subjective value in the calculation
	 *                                     of the Apartment total subjective value
	 */
	private double pricePerNightSubjectiveValueWeight;

	/**
	 * @nbMinNightSubjectiveValueWeight The weight associated to the minimum number
	 *                                  of nights subjective value in the
	 *                                  calculation of the Apartment total
	 *                                  subjective value
	 */
	private double nbMinNightSubjectiveValueWeight;

	/**
	 * @teleSubjectiveValueWeight The weight associated to the presence of a
	 *                            television subjective value in the calculation of
	 *                            the Apartment total subjective value
	 */
	private double teleSubjectiveValueWeight;

	private final static Logger LOGGER = LoggerFactory.getLogger(ApartmentValueFunction.class);

	/**
	 * Constructor of the object {@link ApartmentValueFunction} By default, all the
	 * {@link PartialValueFunction} objects are {@link ConstantValueFunction} By
	 * default, all the weights have the same value and their sum is 1. The setters
	 * functions enable to set those two.
	 */

	public ApartmentValueFunction() {
		this.floorAreaValueFunction = new ConstantValueFunction<>(0d);
		this.nbBedroomsValueFunction = new ConstantValueFunction<>(0d);
		this.nbSleepingValueFunction = new ConstantValueFunction<>(0d);
		this.nbBathroomsValueFunction = new ConstantValueFunction<>(0d);
		this.terraceValueFunction = new ConstantValueFunction<>(0d);
		this.floorAreaTerraceValueFunction = new ConstantValueFunction<>(0d);
		this.wifiValueFunction = new ConstantValueFunction<>(0d);
		this.pricePerNightValueFunction = new ConstantValueFunction<>(0d);
		this.nbMinNightValueFunction = new ConstantValueFunction<>(0d);
		this.teleValueFunction = new ConstantValueFunction<>(0d);

		this.floorAreaSubjectiveValueWeight = 0.1;
		this.nbBedroomsSubjectiveValueWeight = 0.1;
		this.nbSleepingSubjectiveValueWeight = 0.1;
		this.nbBathroomsSubjectiveValueWeight = 0.1;
		this.terraceSubjectiveValueWeight = 0.1;
		this.floorAreaTerraceSubjectiveValueWeight = 0.1;
		this.wifiSubjectiveValueWeight = 0.1;
		this.pricePerNightSubjectiveValueWeight = 0.1;
		this.nbMinNightSubjectiveValueWeight = 0.1;
		this.teleSubjectiveValueWeight = 0.1;
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * floor area
	 * 
	 * @param floorAreaValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setFloorAreaValueFunction(PartialValueFunction<Double> floorAreaValueFunction)
			throws IllegalArgumentException {
		this.floorAreaValueFunction = floorAreaValueFunction;
		checkArgument(!(this.floorAreaValueFunction.equals(null)), "The floor area preferencies cannot be nulled");
		LOGGER.info("The floor area preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * number of bedrooms
	 * 
	 * @param nbBedroomsValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setNbBedroomsValueFunction(PartialValueFunction<Double> nbBedroomsValueFunction)
			throws IllegalArgumentException {
		this.nbBedroomsValueFunction = nbBedroomsValueFunction;
		checkArgument(!(this.nbBedroomsValueFunction.equals(null)),
				"The number of bedrooms preferencies cannot be nulled");
		LOGGER.info("The number of bedrooms preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * accommodation capacity
	 * 
	 * @param nbSleepingValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setNbSleepingValueFunction(PartialValueFunction<Double> nbSleepingValueFunction)
			throws IllegalArgumentException {
		this.nbSleepingValueFunction = nbSleepingValueFunction;
		checkArgument(!(this.nbSleepingValueFunction.equals(null)),
				"The number of sleep-in preferencies cannot be nulled");
		LOGGER.info("The number of sleep-in preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * number of bathrooms
	 * 
	 * @param nbBathroomsValueFunction an object of type
	 *                                 {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setNbBathroomsValueFunction(PartialValueFunction<Double> nbBathroomsValueFunction)
			throws IllegalArgumentException {
		this.nbBathroomsValueFunction = nbBathroomsValueFunction;
		checkArgument(!(this.nbBathroomsValueFunction.equals(null)),
				"The number of bathrooms preferencies cannot be nulled");
		LOGGER.info("The number of bathrooms preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * presence of a terrace
	 * 
	 * @param terraceValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setTerraceValueFunction(PartialValueFunction<Boolean> terraceValueFunction)
			throws IllegalArgumentException {
		this.terraceValueFunction = terraceValueFunction;
		checkArgument(!(this.terraceValueFunction.equals(null)), "The terrace preferencies cannot be nulled");
		LOGGER.info("The terrace preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * floor area of an existing terrace
	 * 
	 * @param floorAreaTerraceValueFunction an object of type
	 *                                      {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setFloorAreaTerraceValueFunction(PartialValueFunction<Double> floorAreaTerraceValueFunction)
			throws IllegalArgumentException {
		this.floorAreaTerraceValueFunction = floorAreaTerraceValueFunction;
		checkArgument(!(this.floorAreaTerraceValueFunction.equals(null)),
				"The floor area of the terrace preferencies cannot be nulled");
		LOGGER.info("The floor area of the terrace preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * presence of a wireless connection
	 * 
	 * @param wifiValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setWifiValueFunction(PartialValueFunction<Boolean> wifiValueFunction) throws IllegalArgumentException {
		this.wifiValueFunction = wifiValueFunction;
		checkArgument(!(this.wifiValueFunction.equals(null)), "The wifi preferencies cannot be nulled");
		LOGGER.info("The wifi preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * price per night
	 * 
	 * @param pricePerNightValueFunction an object of type
	 *                                   {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setPricePerNightValueFunction(PartialValueFunction<Double> pricePerNightValueFunction)
			throws IllegalArgumentException {
		this.pricePerNightValueFunction = pricePerNightValueFunction;
		checkArgument(!(this.pricePerNightValueFunction.equals(null)),
				"The price per night preferencies cannot be nulled");
		LOGGER.info("The price per night preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * minimum number of nights
	 * 
	 * @param nbMinNightValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setNbMinNightValueFunction(PartialValueFunction<Double> nbMinNightValueFunction)
			throws IllegalArgumentException {
		this.nbMinNightValueFunction = nbMinNightValueFunction;
		checkArgument(!(this.nbMinNightValueFunction.equals(null)),
				"The number of minimum night preferencies cannot be nulled");
		LOGGER.info("The number of minimum night preferencies has been set");
	}

	/**
	 * Set the function which will be used to calculate the subjective value of the
	 * presence of a television
	 * 
	 * @param teleValueFunction an object of type {@link PartialValueFunction}
	 * @throws IllegalArgumentException
	 */
	public void setTeleValueFunction(PartialValueFunction<Boolean> teleValueFunction) throws IllegalArgumentException {
		this.teleValueFunction = teleValueFunction;
		checkArgument(!(this.teleValueFunction.equals(null)), "the  cannot be nulled");
		LOGGER.info("The wifi preferencies has been set");
	}

	/**
	 * Set the weight of the floor area subjective value corresponding to the
	 * importance of the floor area criteria
	 * 
	 * @param floorAreaSubjectiveValueWeight a positive or zero double
	 */
	public void setFloorAreaSubjectiveValueWeight(double floorAreaSubjectiveValueWeight) {
		checkArgument(floorAreaSubjectiveValueWeight >= 0, "The weight of the floor area cannot be negative");
		this.floorAreaSubjectiveValueWeight = floorAreaSubjectiveValueWeight;
		LOGGER.info("The floor area weight has been set");
	}

	/**
	 * Set the weight of the number of bedrooms subjective value corresponding to
	 * the importance of the number of bedrooms criteria
	 * 
	 * @param nbBedroomsSubjectiveValueWeight a positive or zero double
	 */
	public void setNbBedroomsSubjectiveValueWeight(double nbBedroomsSubjectiveValueWeight) {
		checkArgument(nbBedroomsSubjectiveValueWeight >= 0, "The weight of the number of bedrooms cannot be negative");
		this.nbBedroomsSubjectiveValueWeight = nbBedroomsSubjectiveValueWeight;
		LOGGER.info("The number of bedrooms weight has been set");
	}

	/**
	 * set the weight of the number of sleeping subjective value corresponding to
	 * the importance of the number of sleeping criteria
	 * 
	 * @param nbSleepingSubjectiveValueWeight a positive zero double
	 */
	public void setNbSleepingSubjectiveValueWeight(double nbSleepingSubjectiveValueWeight) {
		checkArgument(nbSleepingSubjectiveValueWeight >= 0, "The weight of the sleep-in cannot be negative");
		this.nbSleepingSubjectiveValueWeight = nbSleepingSubjectiveValueWeight;
		LOGGER.info("The number of sleep-in weight has been set");
	}

	/**
	 * set the weight of the number of bathrooms subjective value corresponding to
	 * the importance of the number of bathrooms criteria
	 * 
	 * @param nbBathroomsSubjectiveValueWeight a positive or zero double
	 */
	public void setNbBathroomsSubjectiveValueWeight(double nbBathroomsSubjectiveValueWeight) {
		checkArgument(nbBathroomsSubjectiveValueWeight >= 0,
				"The weight of the number of bathrooms cannot be negative");
		this.nbBathroomsSubjectiveValueWeight = nbBathroomsSubjectiveValueWeight;
		LOGGER.info("The number of bathrooms weight has been set");
	}

	/**
	 * set the weight of the terrace subjective value corresponding to the
	 * importance of the terrace criteria
	 * 
	 * @param terraceSubjectiveValueWeight a positive zero double
	 */
	public void setTerraceSubjectiveValueWeight(double terraceSubjectiveValueWeight) {
		checkArgument(terraceSubjectiveValueWeight >= 0, "The weight of the terrace cannot be negative");
		this.terraceSubjectiveValueWeight = terraceSubjectiveValueWeight;
		LOGGER.info("The terrace weight has been set");
	}

	/**
	 * set the weight of the terrace floor area subjective value corresponding to
	 * the importance of the terrace floor area criteria
	 * 
	 * @param floorAreaTerraceSubjectiveValueWeight a positive or zero double
	 */
	public void setFloorAreaTerraceSubjectiveValueWeight(double floorAreaTerraceSubjectiveValueWeight) {
		checkArgument(floorAreaTerraceSubjectiveValueWeight >= 0,
				"The weight of the floor area terrace cannot be negative");
		this.floorAreaTerraceSubjectiveValueWeight = floorAreaTerraceSubjectiveValueWeight;
		LOGGER.info("The floor area of the terrace weight has been set");
	}

	/**
	 * set the weight of the WiFi subjective value corresponding to the importance
	 * of the WiFi criteria
	 * 
	 * @param wifiSubjectiveValueWeight a positive zero double
	 */
	public void setWifiSubjectiveValueWeight(double wifiSubjectiveValueWeight) {
		checkArgument(wifiSubjectiveValueWeight >= 0, "The weight of the wifi cannot be negative");
		this.wifiSubjectiveValueWeight = wifiSubjectiveValueWeight;
		LOGGER.info("The wifi weight has been set");
	}

	/**
	 * set the weight of the price per night subjective value corresponding to the
	 * importance of the price per night criteria
	 * 
	 * @param pricePerNightSubjectiveValueWeight a positive or zero double
	 */
	public void setPricePerNightSubjectiveValueWeight(double pricePerNightSubjectiveValueWeight) {
		checkArgument(pricePerNightSubjectiveValueWeight >= 0, "The weight of the price per night cannot be negative");
		this.pricePerNightSubjectiveValueWeight = pricePerNightSubjectiveValueWeight;
		LOGGER.info("The price per night weight has been set");
	}

	/**
	 * set the weight of the minimum number of nights subjective value corresponding
	 * to the importance of the minimum number of nights criteria
	 * 
	 * @param nbMinNightSubjectiveValueWeight a positive or zero double
	 */
	public void setNbMinNightSubjectiveValueWeight(double nbMinNightSubjectiveValueWeight) {
		checkArgument(nbMinNightSubjectiveValueWeight >= 0,
				"The weight of the minimum number of nights cannot be negative");
		this.nbMinNightSubjectiveValueWeight = nbMinNightSubjectiveValueWeight;
		LOGGER.info("The number of minimum night weight has been set");
	}

	/**
	 * set the weight of the television subjective value corresponding to the
	 * importance of the television criteria
	 * 
	 * @param teleSubjectiveValueWeight a positive or zero double
	 */
	public void setTeleSubjectiveValueWeight(double teleSubjectiveValueWeight) {
		checkArgument(teleSubjectiveValueWeight >= 0, "The weight of the tele cannot be negative");
		this.teleSubjectiveValueWeight = teleSubjectiveValueWeight;
		LOGGER.info("The wifi weight has been set");
	}

	/**
	 * This function return the subjective value of the Apartment in parameter. For
	 * each valuable attribute of this apartment, the subjective value is computed
	 * by the associated {@link PartialValueFunction}. The weighted sum of theses
	 * subjective values is returned by the function. When the
	 * {@link PartialValueFunction} of an attribute hasn't been set, the subjective
	 * value given to the corresponding attribute will be 0.
	 * 
	 * @param apart an object of type {@link Apartment}
	 * @return a double : the weighted sum of the apartment attributes subjective
	 *         values
	 */
	public double getSubjectiveValue(Apartment apart) {
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

		checkArgument(
				floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()) >= 0
						&& floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea()) <= 1,
				"The subjective value of floor area should be between 0 and 1");
		floorAreaSubjectiveValue = floorAreaValueFunction.getSubjectiveValue(apart.getFloorArea());
		LOGGER.info("the floor area subjective value has been set to " + floorAreaSubjectiveValue);

		checkArgument(
				nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms()) >= 0
						&& nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms()) <= 1,
				"The subjective value of the number of bedrooms should be between 0 and 1");
		nbBedroomsSubjectiveValue = nbBedroomsValueFunction.getSubjectiveValue((double) apart.getNbBedrooms());
		LOGGER.info("the number of bedrooms subjective value has been set to " + nbBedroomsSubjectiveValue);

		checkArgument(
				nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()) >= 0
						&& nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping()) <= 1,
				"The subjective value of the number of sleep-in should be between 0 and 1");
		nbSleepingSubjectiveValue = nbSleepingValueFunction.getSubjectiveValue((double) apart.getNbSleeping());
		LOGGER.info("the number of sleepings subjective value has been set to " + nbSleepingSubjectiveValue);

		checkArgument(
				nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()) >= 0
						&& nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms()) <= 1,
				"The subjective value of the number of bathrooms should be between 0 and 1");
		nbBathroomsSubjectiveValue = nbBathroomsValueFunction.getSubjectiveValue((double) apart.getNbBathrooms());
		LOGGER.info("the number of bathrooms subjective value has been set to " + nbBathroomsSubjectiveValue);

		checkArgument(
				terraceValueFunction.getSubjectiveValue(apart.getTerrace()) >= 0
						&& terraceValueFunction.getSubjectiveValue(apart.getTerrace()) <= 1,
				"The subjective value of the terrace should be between 0 and 1");
		terraceSubjectiveValue = terraceValueFunction.getSubjectiveValue(apart.getTerrace());
		LOGGER.info("the terrace subjective value has been set to " + terraceSubjectiveValue);

		checkArgument(
				floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()) >= 0
						&& floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace()) <= 1,
				"The subjective value of the floor area of the terrace should be between 0 and 1");
		floorAreaTerraceSubjectiveValue = floorAreaTerraceValueFunction.getSubjectiveValue(apart.getFloorAreaTerrace());
		LOGGER.info(
				"the floor area of the terrace subjective value has been set to " + floorAreaTerraceSubjectiveValue);

		checkArgument(
				wifiValueFunction.getSubjectiveValue(apart.getWifi()) >= 0
						&& wifiValueFunction.getSubjectiveValue(apart.getWifi()) <= 1,
				"The subjective value of the wifi should be between 0 and 1");
		wifiSubjectiveValue = wifiValueFunction.getSubjectiveValue(apart.getWifi());
		LOGGER.info("the wifi subjective value has been set to " + wifiSubjectiveValue);

		checkArgument(
				pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()) >= 0
						&& pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight()) <= 1,
				"The subjective value of the price per night should be between 0 and 1");
		pricePerNightSubjectiveValue = pricePerNightValueFunction.getSubjectiveValue(apart.getPricePerNight());
		LOGGER.info("the price per night subjective value has been set to " + pricePerNightSubjectiveValue);

		checkArgument(
				nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()) >= 0
						&& nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight()) <= 1,
				"The subjective value of the minimum number of nights should be between 0 and 1");
		nbMinNightSubjectiveValue = nbMinNightValueFunction.getSubjectiveValue((double) apart.getNbMinNight());
		LOGGER.info("the minimum number of nights subjective value has been set to " + nbMinNightSubjectiveValue);

		checkArgument(
				teleValueFunction.getSubjectiveValue(apart.getTele()) >= 0
						&& teleValueFunction.getSubjectiveValue(apart.getTele()) <= 1,
				"The subjective value of the presence of a tele should be between 0 and 1");
		teleSubjectiveValue = teleValueFunction.getSubjectiveValue(apart.getTele());
		LOGGER.info("the tele subjective value has been set to " + teleSubjectiveValue);

		return ((floorAreaSubjectiveValue * floorAreaSubjectiveValueWeight
				+ nbBedroomsSubjectiveValue * nbBedroomsSubjectiveValueWeight
				+ nbSleepingSubjectiveValue * nbSleepingSubjectiveValueWeight
				+ nbBathroomsSubjectiveValue * nbBathroomsSubjectiveValueWeight
				+ terraceSubjectiveValue * terraceSubjectiveValueWeight
				+ floorAreaTerraceSubjectiveValue * floorAreaTerraceSubjectiveValueWeight
				+ wifiSubjectiveValue * wifiSubjectiveValueWeight
				+ pricePerNightSubjectiveValue * pricePerNightSubjectiveValueWeight
				+ nbMinNightSubjectiveValue * nbMinNightSubjectiveValueWeight
				+ teleSubjectiveValue * teleSubjectiveValueWeight)
				/ (floorAreaSubjectiveValueWeight + nbBedroomsSubjectiveValueWeight + nbSleepingSubjectiveValueWeight
						+ nbBathroomsSubjectiveValueWeight + terraceSubjectiveValueWeight
						+ floorAreaTerraceSubjectiveValueWeight + wifiSubjectiveValueWeight
						+ pricePerNightSubjectiveValueWeight + nbMinNightSubjectiveValueWeight
						+ teleSubjectiveValueWeight));
	}

	private ApartmentValueFunction cloneAVF() {

		ApartmentValueFunction avf = new ApartmentValueFunction();

		setFloorAreaValueFunction(this.floorAreaValueFunction);
		setNbBedroomsValueFunction(this.nbBedroomsValueFunction);
		setNbSleepingValueFunction(this.nbSleepingValueFunction);
		setNbBathroomsValueFunction(this.nbBathroomsValueFunction);
		setTerraceValueFunction(this.terraceValueFunction);
		setFloorAreaTerraceValueFunction(this.floorAreaTerraceValueFunction);
		setWifiValueFunction(this.wifiValueFunction);
		setPricePerNightValueFunction(this.pricePerNightValueFunction);
		setNbMinNightValueFunction(this.nbMinNightValueFunction);
		setTeleValueFunction(this.teleValueFunction);

		avf.floorAreaSubjectiveValueWeight = this.floorAreaSubjectiveValueWeight;
		avf.nbBedroomsSubjectiveValueWeight = this.nbBedroomsSubjectiveValueWeight;
		avf.nbSleepingSubjectiveValueWeight = this.nbSleepingSubjectiveValueWeight;
		avf.nbBathroomsSubjectiveValueWeight = this.nbBathroomsSubjectiveValueWeight;
		avf.terraceSubjectiveValueWeight = this.terraceSubjectiveValueWeight;
		avf.floorAreaTerraceSubjectiveValueWeight = this.floorAreaTerraceSubjectiveValueWeight;
		avf.wifiSubjectiveValueWeight = this.wifiSubjectiveValueWeight;
		avf.pricePerNightSubjectiveValueWeight = this.pricePerNightSubjectiveValueWeight;
		avf.nbMinNightSubjectiveValueWeight = this.nbMinNightSubjectiveValueWeight;
		avf.teleSubjectiveValueWeight = this.teleSubjectiveValueWeight;

		return avf;

	}

	/**
	 * @return A randomized instance of an ApartmentValueFunction
	 */
	public static ApartmentValueFunction getRandomApartmentValueFunction() {

		ApartmentValueFunction apartValueFunction = new ApartmentValueFunction();
		Random random = new Random();

		DiscreteValueFunction<Double> nbBedroomsEndBoundMap = DiscreteValueFunction.discreteValueFunctionBeetween(4, 6);
		DiscreteValueFunction<Double> nbSleepingEndBoundMap = DiscreteValueFunction.discreteValueFunctionBeetween(4, 6);
		DiscreteValueFunction<Double> nbBathroomsEndBoundMap = DiscreteValueFunction.discreteValueFunctionBeetween(1,
				3);

		int floorAreaEndBound = random.nextInt(80) + 21;
		int terraceEndBoundInt = random.nextInt(2);
		boolean terraceEndBound = false;
		if (terraceEndBoundInt == 1)
			terraceEndBound = true;
		int floorAreaTerraceEndBound = random.nextInt(80) + 21;
		int wifiEndBoundInt = random.nextInt(2);
		boolean wifiEndBound = false;
		if (wifiEndBoundInt == 1)
			wifiEndBound = true;
		int pricePerNightEndBound = random.nextInt(180) + 21;
		int nbMinNightEndBound = random.nextInt(7) + 3;
		int teleEndBoundInt = random.nextInt(2);
		boolean teleEndBound = false;
		if (teleEndBoundInt == 1)
			teleEndBound = true;

		int floorAreaStartBound = random.nextInt(floorAreaEndBound);
		int floorAreaTerraceStartBound = random.nextInt(floorAreaTerraceEndBound);
		int pricePerNightStartBound = random.nextInt(pricePerNightEndBound);
		int nbMinNightStartBound = random.nextInt(nbMinNightEndBound);

		apartValueFunction.setFloorAreaValueFunction(new LinearValueFunction(floorAreaStartBound, floorAreaEndBound));
		apartValueFunction.setNbBedroomsValueFunction(nbBedroomsEndBoundMap);
		apartValueFunction.setNbSleepingValueFunction(nbSleepingEndBoundMap);
		apartValueFunction.setNbBathroomsValueFunction(nbBathroomsEndBoundMap);
		apartValueFunction.setTerraceValueFunction(new BooleanValueFunction(terraceEndBound));
		apartValueFunction.setFloorAreaTerraceValueFunction(
				new LinearValueFunction(floorAreaTerraceStartBound, floorAreaTerraceEndBound));
		apartValueFunction.setWifiValueFunction(new BooleanValueFunction(wifiEndBound));
		apartValueFunction
				.setPricePerNightValueFunction(new LinearValueFunction(pricePerNightStartBound, pricePerNightEndBound));
		apartValueFunction
				.setNbMinNightValueFunction(new ReversedLinearValueFunction(nbMinNightStartBound, nbMinNightEndBound));
		apartValueFunction.setTeleValueFunction(new BooleanValueFunction(teleEndBound));

		List<Double> weightRange = RandomRange.weightRangeOfSum(1d, 10);

		LOGGER.info("Weight has been set to : " + weightRange);

		apartValueFunction.floorAreaSubjectiveValueWeight = weightRange.get(0);
		apartValueFunction.nbBedroomsSubjectiveValueWeight = weightRange.get(1);
		apartValueFunction.nbSleepingSubjectiveValueWeight = weightRange.get(2);
		apartValueFunction.nbBathroomsSubjectiveValueWeight = weightRange.get(3);
		apartValueFunction.terraceSubjectiveValueWeight = weightRange.get(4);
		apartValueFunction.floorAreaTerraceSubjectiveValueWeight = weightRange.get(5);
		apartValueFunction.wifiSubjectiveValueWeight = weightRange.get(6);
		apartValueFunction.pricePerNightSubjectiveValueWeight = weightRange.get(7);
		apartValueFunction.nbMinNightSubjectiveValueWeight = weightRange.get(8);
		apartValueFunction.teleSubjectiveValueWeight = weightRange.get(9);

		return apartValueFunction;
	}

	/**
	 * We make the assumption (by casting), that the runtime PartialValueFunction
	 * associated to criteria is a LinearValueFunction, even if in real life it
	 * would be a discrete criteria (e.g. the number of bedrooms)
	 * 
	 * The goal is to replace a LinearValueFunction's bound by a new bound
	 * 
	 * @param criteria
	 * @param newBound
	 * @param lower    is true when we want to adapt the lower bound, false if we
	 *                 want to adapt the upper bound
	 */
	public ApartmentValueFunction adaptBounds(Criterion criterion, double newBound, boolean lower) {

		ApartmentValueFunction avf = cloneAVF();

		switch (criterion) {
		case FLOOR_AREA:
			avf.setFloorAreaValueFunction(
					avf.adaptLinearValueFunction((LinearValueFunction) avf.floorAreaValueFunction, newBound, lower));
			break;
		case FLOOR_AREA_TERRACE:
			avf.setFloorAreaTerraceValueFunction(avf.adaptLinearValueFunction(
					(LinearValueFunction) avf.floorAreaTerraceValueFunction, newBound, lower));
			break;
		case PRICE_PER_NIGHT:
			avf.setPricePerNightValueFunction(avf
					.adaptLinearValueFunction((LinearValueFunction) avf.pricePerNightValueFunction, newBound, lower));
			break;
		case NB_SLEEPING:
			avf.setNbSleepingValueFunction(
					avf.adaptLinearValueFunction((LinearValueFunction) avf.nbSleepingValueFunction, newBound, lower));
			break;
		case NB_BATHROOMS:
			avf.setNbBathroomsValueFunction(
					avf.adaptLinearValueFunction((LinearValueFunction) avf.nbBathroomsValueFunction, newBound, lower));
			break;
		case NB_BEDROOMS:
			avf.setNbBedroomsValueFunction(
					avf.adaptLinearValueFunction((LinearValueFunction) avf.nbBedroomsValueFunction, newBound, lower));
			break;
		case NB_MIN_NIGHT:
			avf.setNbMinNightValueFunction(
					this.adaptLinearValueFunction((LinearValueFunction) avf.nbMinNightValueFunction, newBound, lower));
			break;
		default:
			throw new IllegalArgumentException();
		}

		return avf;
	}

	private LinearValueFunction adaptLinearValueFunction(LinearValueFunction oldLVF, double newBound, boolean lower) {

		double remainingBound = 0d;
		if (lower) {
			remainingBound = oldLVF.getInterval().upperEndpoint();
			return new LinearValueFunction(newBound, remainingBound);
		} else {
			remainingBound = oldLVF.getInterval().lowerEndpoint();
			return new LinearValueFunction(remainingBound, newBound);
		}

	}

	/**
	 * This method assumes that the preference between true and false is known but
	 * doesn't matter.
	 * 
	 * @param moreImportant is the criterion that is to be prioritized in this
	 *                      object of ApartmentValueFunction
	 * @param lessImportant is the criterion that is to be less important in this
	 *                      object of ApartmentValueFunction
	 */
	public ApartmentValueFunction adaptWeight(Criterion moreImportant, Criterion lessImportant) {

		Preconditions.checkArgument(!lessImportant.equals(moreImportant), "Both fields are the same.");

		ApartmentValueFunction avf = cloneAVF();

		double weightSum = 0;
		weightSum += avf.getSubjectiveValueWeight(moreImportant);
		weightSum += avf.getSubjectiveValueWeight(lessImportant);

		avf = avf.setSubjectiveValueWeight(moreImportant, 9 * weightSum / 10);
		avf = avf.setSubjectiveValueWeight(lessImportant, weightSum / 10);

		return avf;
	}

	private double getSubjectiveValueWeight(Criterion awt) {
		switch (awt) {
		case TELE:
			return teleSubjectiveValueWeight;
		case TERRACE:
			return terraceSubjectiveValueWeight;
		case WIFI:
			return wifiSubjectiveValueWeight;
		case FLOOR_AREA:
			return floorAreaTerraceSubjectiveValueWeight;
		case FLOOR_AREA_TERRACE:
			return floorAreaTerraceSubjectiveValueWeight;
		case NB_BATHROOMS:
			return nbBathroomsSubjectiveValueWeight;
		case NB_BEDROOMS:
			return nbBedroomsSubjectiveValueWeight;
		case NB_SLEEPING:
			return nbSleepingSubjectiveValueWeight;
		case NB_MIN_NIGHT:
			return nbMinNightSubjectiveValueWeight;
		case PRICE_PER_NIGHT:
			return pricePerNightSubjectiveValueWeight;
		default:
			throw new IllegalArgumentException();
		}
	}

	public ApartmentValueFunction setSubjectiveValueWeight(Criterion awt, double value) {

		ApartmentValueFunction avf = cloneAVF();

		switch (awt) {
		case TELE:
			avf.setTeleSubjectiveValueWeight(value);
			break;
		case TERRACE:
			avf.setTerraceSubjectiveValueWeight(value);
			break;
		case WIFI:
			avf.setWifiSubjectiveValueWeight(value);
			break;
		case FLOOR_AREA:
			avf.setFloorAreaSubjectiveValueWeight(value);
			break;
		case FLOOR_AREA_TERRACE:
			avf.setFloorAreaTerraceSubjectiveValueWeight(value);
			break;
		case NB_BATHROOMS:
			avf.setNbBathroomsSubjectiveValueWeight(value);
			break;
		case NB_BEDROOMS:
			avf.setNbBedroomsSubjectiveValueWeight(value);
			break;
		case NB_SLEEPING:
			avf.setNbSleepingSubjectiveValueWeight(value);
			break;
		case NB_MIN_NIGHT:
			avf.setNbMinNightSubjectiveValueWeight(value);
			break;
		case PRICE_PER_NIGHT:
			avf.setPricePerNightSubjectiveValueWeight(value);
			break;
		default:
			throw new IllegalArgumentException();
		}

		return avf;

	}

}