package io.github.oliviercailloux.y2018.apartments.piecewise;

import java.io.IOException;

import com.google.common.collect.Range;

public interface IPiecewiseLinearValueFunction {

	@Override
	public String toString();

	/**
	 * This method calculates and returns the value of the utility associated with
	 * the key in parameter.
	 * 
	 * @param value of the key (int) which has to be more or equal than the min and
	 *              strictly less or equal than the max
	 * @return the value (double) of the utility associated with the key in
	 *         parameter
	 * @throws IOException
	 */
	public double getUtility(int key) throws IOException;

	/**
	 * The method returns an interval that is the closest to the key in parameter.
	 * The first one is calculated less than the key in parameter. The second one is
	 * calculated more than the key in parameter.
	 * 
	 * @param key integer which corresponds to a key value
	 * @return an closed interval of integers with the two keys as bounds.
	 * @throws IllegalArgumentException
	 */
	public Range<Integer> getInterval(int key) throws IOException;

	/**
	 * This method looks for the minimum of the keys and returns it.
	 * 
	 * @return the minimum of all keys
	 */
	public int getMinKey();

	/**
	 * This method looks for the maximum of the keys and returns it.
	 * 
	 * @return max the maximum of all keys
	 */
	public int getMaxKey();

	/**
	 * This method adds the couple (key,value) in the Map.
	 * 
	 * @param key   value (int) for the criteria which has to be less than the
	 *              maximum key or more than the minimum key.
	 * @param value value (double) for the specified key
	 */
	public void setUtility(int key, double value);

}
