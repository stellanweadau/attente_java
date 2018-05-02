package io.github.oliviercailloux.y2018.apartments.piecewise;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Range;

import io.github.oliviercailloux.y2018.apartments.readapartments.ReadApartmentsXMLFormat;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 
 * The public class PricewiseLinearValueFunction enables the creation of an object with a criteria 
 * which represents a property of an apartment, and two numbers which represent first the value 
 * of the criteria ( for example the number of rooms ) and the utility associated to this
 * criteria.
 * The class enables the user to know for any value of the criteria, the utility associated.
 */
public class PiecewiseLinearValueFunction implements IPiecewiseLinearValueFunction {

	private String criteria;
	private Map<Integer, Double> utility;
	static Logger piecewiseLinearValueFunction = LoggerFactory.getLogger(PiecewiseLinearValueFunction.class);

	/**
	 * Constructor of the object {@link PricewiseLinearValueFunction}
	 * @param crit
	 * 			Label of the criteria
	 */
	public PiecewiseLinearValueFunction(String crit)
	{
		utility = new HashMap<>();
		criteria = crit;
	}
	
	/**
	 * This method adds the couple (key,value) in the Map.
	 * @param key value (int) for the criteria
	 * @param value value (double) for the specified key
	 */
	@Override
	public void setUtility(int key, double value) {
		
		if (utility.containsKey(key) == true)
			throw new IllegalArgumentException("The key is already in the Map");
		
		if (key<0 || value<0 || value>1)
			throw new IllegalArgumentException("The key or the utility in parameter is not in keeping with the rules.");
		
		utility.put(key, value);
			
	
	}

	/**
	 * @return a description of the object {@link PiecewiseLinearValueFunction}
	 */
	@Override
	public String toString()
	{
		return  "This object is defining the following criteria :  "+ criteria + 
				". It contains " + this.utility.size() + " values";
	}

	/**
	 * This method calculates the linear value of a function. 
	 * If the point A and the point B are not different, the method returns
	 * an IllegalArgumentException.
	 * @param a the Point2D A
	 * @param b the Point2D B
	 * @return a double number which corresponds to the linear value.
	 */
	private double getLinearValue(Point2D a, Point2D b) {
		if ( a.getX() == b.getX())
			throw new IllegalArgumentException("The points are the same");

		return ( b.getY() - a.getY()) / (b.getX() - a.getX());
		
	}
	
	/**
	 * This method calculates the ordinate value of a function.
	 * @param a the Point2D A
	 * @param b the Point2D B
	 * @return a double number which corresponds to the ordinate value.
	 */
	private double getOrdinateValue(Point2D a, Point2D b) {
		double linearValue = getLinearValue(a,b);
		return a.getY() - (a.getX()* linearValue);
	}
	
	/**
	 * This method looks for the maximum of the keys and returns it.
	 * @return max the maximum of all keys
	 */
	@Override
	public int getMaxKey() {
		Iterator<Integer> k = utility.keySet().iterator();
		int max = 0;
		int tmp = 0;
		while (k.hasNext()) {
			tmp = k.next();
			if (max< tmp)
				max = tmp;
		}
		return max;
	}
	
	/**
	 * This method looks for the minimum of the keys and returns it.
	 * @return the minimum of all keys
	 */
	@Override
	public int getMinKey() {
		Iterator<Integer> k = utility.keySet().iterator();
		int min = -1;
		int tmp = 0;
		while (k.hasNext()) {
			tmp = k.next();
			if (min> tmp || min == -1)
				min = tmp;
		}
		return min;
	}
	
	/**
	 * The method returns an interval that is the closest to the key in parameter.
	 * The first one is calculated less than the key in parameter.
	 * The second one is calculated more than the key in parameter.
	 * @param key integer which corresponds to a key value
	 * @return an closed interval of integers with the two keys as bounds.
	 * @throws IllegalArgumentException
	 */
	@Override
	public Range<Integer> getInterval(int key) throws IOException {
		
		if (key>getMaxKey()) {
			throw new IllegalArgumentException("No coherent value to return for the range");
		}
		
		if (key<getMinKey()) {
			throw new IllegalArgumentException("No coherent value to return for the range");
		}
		
		Iterator<Integer> k = utility.keySet().iterator();
		int delta1= -1;
		int delta2 = -1;
		int key1 = 0;
		int key2 = 0;
		int tmp = 0;
		
		while (k.hasNext()) {		
			tmp = k.next();
			if (tmp < key) {
				if (delta1  == -1) {
					delta1 = key - tmp;
					key1 = tmp;
				}
				else if (delta1> (key - tmp)) {
					delta1 = (key - tmp);
					key1 = tmp;
				}
			}
			else if (tmp>key) {
				if (delta2 == -1) {
					delta2 = tmp - key;
					key2 = tmp;
				}
				else if (delta2> (tmp - key)) {
					delta2 = (tmp - key);
					key2 = tmp;
				} 
			}
		}
		
		Range<Integer> interval = Range.closed(key1, key2);
		
		return interval;
		
		
	}
	
	/**
	 * This method calculates and returns the value of the utility associated with the key
	 * in parameter.
	 * @param value of the key (int) which has to be more or equal than the min and strictly less or equal than the max 
	 * @return the value (double) of the utility associated with the key in parameter
	 * @throws IOException 
	 */
	@Override
	public double getUtility(int key) throws IOException {
		
		if (utility.containsKey(key))
			return utility.get(key);
		
		if (utility.size()<2)
			throw new IllegalStateException("Need more couples (minimum of 2) to identify the linear value");
		
		
		Range<Integer> intervalKey = getInterval(key);
		int lowerBound = intervalKey.lowerEndpoint();
		int upperBound = intervalKey.upperEndpoint();
		
		Point2D lowerBoundPoint = new Point2D.Double(lowerBound,utility.get(lowerBound));
		Point2D upperBoundPoint = new Point2D.Double(upperBound,utility.get(upperBound));
		
		double value = getOrdinateValue(lowerBoundPoint,upperBoundPoint);
		
		return key*getLinearValue(lowerBoundPoint,upperBoundPoint) + value;
	} 
		

}
