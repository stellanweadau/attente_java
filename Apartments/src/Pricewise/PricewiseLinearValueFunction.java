package Pricewise;

import java.util.Map;
import java.util.HashMap;
public class PricewiseLinearValueFunction implements IPricewiseLinearValueFunction {
	
	private String critere;
	private Map<Integer, Double> utility;
	public double getLinearValue(double critere) {
		// TODO Auto-generated method stub
		return 0;
	}
	public PricewiseLinearValueFunction()
	{
		this.utility = new HashMap<Integer, Double>();
	}
	
	public PricewiseLinearValueFunction(String crit)
	{
		super();
		this.critere = crit;
	}
	@Override
	public double getUtility(int value)
	{
		if(!utility.containsKey(value))
		{
			throw new IllegalArgumentException("La valeur n'existe pas dans l'utilité");
		}
		return utility.get(value);
	}
	
	public void addUtility(int value, double util)
	{


	}

}
