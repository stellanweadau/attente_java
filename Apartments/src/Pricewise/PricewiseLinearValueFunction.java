package Pricewise;

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
public class PricewiseLinearValueFunction implements IPricewiseLinearValueFunction {
	
	private String criteria;
	private Map<Integer, Double> utility;

	/**
	 * Constructor of the object {@link PricewiseLinearValueFunction}
	 * @param crit
	 * 			Label of the criteria
	 * @param data1
	 * 			First value of the criteria
	 * @param data2
	 * 			Utility bind to the first value
	 * @param data3
	 * 			Second value of the criteria
	 * @param data4
	 * 			Utility bind to the second value
	 */
	public PricewiseLinearValueFunction(String crit, int data1, double data2, int data3, double data4)
	{
		this.utility = new HashMap<Integer, Double>();
		this.criteria = crit;
		utility.put(data1, data2);
		utility.put(data3, data4);
	}

	/**
	 * @return a description of the object {@link PricewiseLinearValueFunction}
	 */
	@Override
	public String ToString()
	{
		return  "This object is defining the following criteria :  "+ criteria + 
				". It contains " + this.utility.size() + " values";
	}
	
	/**
	 * Get the utility of a value using the linear interpolation of the two existing points
	 * @param i
	 * 		value of the criteria you want to recover the utility
	 * @param u
	 * 		value of the calculation's mode of the utility
	 * 			<code>limit</code> : the function will calculate the linear interpolation but it will
	 * 								fix value above 1 to 1 and value below 0 to 0
	 * 			<code>reverse</code> : the function assuming that the utility above 1 is symmetrical to 
	 * 								 the linear interpolation and will calculate a decreasing utility above 1.
	 * 								 The value above 0 is fix to 0.
	 * @return the utility of the value i between 0 and 1.
	 * 		  
	 */
	@Override
	public double getUtility(int i, utilityMode u) {
		
		Iterator<Integer> k = utility.keySet().iterator();
		int d1 = k.next();
		int d2 = k.next();
		
		double result = (utility.get(d1) + (i - d1)*getLinearValue(d1, d2));
		if(result > 1)
		{
			if( u == utilityMode.limit)
			{
				return 1;
			}
			else if(u == utilityMode.reverse)
			{
				return 1 - (1 - result);
			}
		}
		
		else if( result <= 0)
		{
			return 0;
		}
		
		return result;
	}
	
	/**
	 * Get the linear value based on the two points (xA,yA) and (xB,yB).
	 * @param d1 is a key of the Map utility
	 * @param d2 is a key of the Map utility (different from <code>d1</code>
	 * @return the slope of the linear function between the point <code>d1</code> and <code>d2</code>
	 * 		
	 */
	@Override
	public double getLinearValue(int d1, int d2) {
		if(utility.keySet().contains(d1) && utility.keySet().contains(d2))
		{
			return ((utility.get(d2) - utility.get(d1)) / (d2 - d1));
		}
			throw new IllegalArgumentException("One of the argument is not a valid value of the Map utility");
	}


}
