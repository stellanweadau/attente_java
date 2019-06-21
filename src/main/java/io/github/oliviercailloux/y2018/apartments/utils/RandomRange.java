package io.github.oliviercailloux.y2018.apartments.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

/**
 * @author Amioplk, alexisperdereau Manages random ranges of doubles
 */
public class RandomRange {

	/**
	 * 
	 * @param s    the total sum of the list
	 * @param size the size of the list
	 * @return range of size values with sum s
	 */
	public static List<Double> weightRangeOfSum(double s, int size) {

		List<Double> weightRange = new ArrayList<Double>();

		for (int i = 0; i < size; ++i)
			weightRange.add(Math.random());

		/**
		 * Found on
		 * https://stackoverflow.com/questions/30125296/how-to-sum-a-list-of-integers-with-java-streams
		 */
		double sum = weightRange.stream().reduce(0d, Double::sum);
		weightRange = weightRange.stream().map(d -> (d / sum) * s).collect(ImmutableList.toImmutableList());

		return weightRange;

	}

	/**
	 * 
	 * @param min the minimum required for the random number
	 * @param max the maximum required for the random number
	 * @return range of values
	 */
	public static HashMap<Double, Double> mapBound(int min, int max) {
		Random random = new Random();
		int var = random.nextInt(max) + min;
		HashMap<Double, Double> varMap = new HashMap<>();
		for (double i = 0; i < var; ++i) {
			varMap.put(i, Double.valueOf(i) / var);
		}
		return varMap;
	}

}
