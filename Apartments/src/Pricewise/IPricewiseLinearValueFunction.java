package Pricewise;

public interface IPricewiseLinearValueFunction {

		public String ToString();

		double getLinearValue(int d1, int d2);

		double getUtility(int i, utilityMode u);
		
}

