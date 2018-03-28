package io.github.oliviercailloux.y2018.apartments.piecewise;

import java.io.IOException;

public interface IPiecewiseLinearValueFunction {
	
	@Override
	public String toString();
	
	double getUtility(int key) throws IOException;

	int[] getInterval(int key) throws IOException;

	int getMinKey();

	int getMaxKey();

	double getOrdinateValue(int absA, int absB, double ordA, double ordB);

	double getLinearValue(int absA, int absB, double ordA, double ordB);

	void setUtility(int key, double value);

}
