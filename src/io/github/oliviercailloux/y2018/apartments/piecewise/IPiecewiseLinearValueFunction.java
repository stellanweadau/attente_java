package io.github.oliviercailloux.y2018.apartments.piecewise;

import java.io.IOException;

import com.google.common.collect.Range;

public interface IPiecewiseLinearValueFunction {
	
	@Override
	public String toString();
	
	public double getUtility(int key) throws IOException;

	public Range<Integer> getInterval(int key) throws IOException;

	public int getMinKey();

	public int getMaxKey();

	public void setUtility(int key, double value);

}
