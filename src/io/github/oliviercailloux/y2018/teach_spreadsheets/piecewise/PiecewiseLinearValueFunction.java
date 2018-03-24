package io.github.oliviercailloux.y2018.teach_spreadsheets.piecewise;

import java.util.Map;
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

	/**
	 * Constructor of the object {@link PricewiseLinearValueFunction}
	 * @param crit
	 * 			Label of the criteria
	 */
	public PiecewiseLinearValueFunction(String crit)
	{
		this.utility = new HashMap<Integer, Double>();
		this.criteria = crit;
	}
	
	/**
	 * This method adds the couple (key,value) in the Map.
	 * @param key value (int) for the criteria
	 * @param value value (double) for the specified key
	 */
	@Override
	public void setUtility(int key, double value) {
		if (utility.containsKey(key) == false) {
			if (key>=0 && value>=0 && value<=1)
				utility.put(key, value);
			else
				throw new IllegalArgumentException("The key or the utility in parameter is not in keeping with the rules.");
		}	
		else
			throw new IllegalArgumentException("The key is already in the Map");
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
	 * @param absA abscissa of the point A
	 * @param absB abscissa of the point B
	 * @param ordA ordinate of the point A
	 * @param ordB ordinate of the point B
	 * @return a double number which corresponds to the linear value.
	 */
	@Override
	public double getLinearValue(int absA, int absB, double ordA, double ordB) {
		if ( absA != absB) {
			return ( ordB - ordA) / (absB - absA);
		}
		throw new IllegalArgumentException("The points are the same");
	}
	
	/**
	 * This method calculates the ordinate value of a function.
	 * @param absA abscissa of the point A
	 * @param absB abscissa of the point B
	 * @param ordA ordinate of the point A
	 * @param ordB ordinate of the point B
	 * @return a double number which corresponds to the ordinate value.
	 */
	@Override
	public double getOrdinateValue(int absA, int absB, double ordA, double ordB) {
		double linearValue = getLinearValue(absA, absB, ordA, ordB);
		return ordA - (absA* linearValue);
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
	 * @return min the minimum of all keys
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
	 * The method returns a couple of keys that are the closer to the key in parameter.
	 * The first one is calculated less than the key in parameter.
	 * The second one is calculated more than the key in parameter.
	 * @param key integer which corresponds to a key value
	 * @return a table of integers with the two keys.
	 * If the key in parameter is above the maximum of the key, the table returned is [0,0].
	 * If the key in parameter is below the minimum of the key, the table returned is [-1,-1].
	 */
	@Override
	public int[] getInterval(int key) {
		int[] tab = new int[2];
		
		Iterator<Integer> k = utility.keySet().iterator();
		int delta1= -1; // difference between the parameter key and the current key of the set, 
		int delta2 = -1;
		int key1 = 0;
		int key2 = 0;
		int tmp = 0; // temporary variable used for stocking the value of the current key
		
		if (key>getMaxKey()) {
			tab[0]= 0;
			tab[1]= 0;
			return tab;
		}
		
		if (key<getMinKey()) {
			tab[0]= -1;
			tab[1]= -1;
			return tab;
		}
		
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
		
		tab[0] = key1;
		tab[1] = key2;
		
		return tab;
		
		
	}
	
	/**
	 * This method calculates and returns the value of the utility associated with the key
	 * in parameter.
	 * @param value of the key
	 * @return the value (double) of the utility associated with the key in parameter
	 */
	@Override
	public double getUtility(int key) {
		double value = -1;
		
		if (utility.containsKey(key))
			return utility.get(key);
		
		if (utility.size()<2)
			throw new IllegalArgumentException("Need more couples (minimum of 2) to identify the linear value");
		
		int[] tabKey = getInterval(key);
		
		if (tabKey[0]==0 && tabKey[1]==0)
			return 1;
		
		if (tabKey[0]==-1 && tabKey[1]==-1)
			return 0;
		
		value = getOrdinateValue(tabKey[0], tabKey[1],utility.get(tabKey[0]), utility.get(tabKey[1]));
		
		return key*getLinearValue(tabKey[0], tabKey[1],utility.get(tabKey[0]), utility.get(tabKey[1])) + value;
	} 
		
	public static void main (String args[]) {
		PiecewiseLinearValueFunction f = new PiecewiseLinearValueFunction("Superficie");
		f.setUtility(60, 0.6);
		f.setUtility(30, 0.3);
		f.setUtility(50, 0.5);
		System.out.println(f.getUtility(20));
		System.out.println(f.getMinKey());
	}

}
