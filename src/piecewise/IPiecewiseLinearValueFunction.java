package piecewise;

public interface IPiecewiseLinearValueFunction {
	
	public String toString();
	
	double getUtility(int key);

	int[] getInterval(int key);

	int getMinKey();

	int getMaxKey();

	double getOrdinateValue(int absA, int absB, double ordA, double ordB);

	double getLinearValue(int absA, int absB, double ordA, double ordB);

	void setUtility(int key, double value);

}
