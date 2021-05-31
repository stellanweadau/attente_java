package io.github.oliviercailloux.y2021.apartments.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.json.JsonConvert;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class TestPlot {
	// https://zetcode.com/java/jfreechart/
	
	final static HashSet<String> listOfFeatures = new HashSet<>(Arrays.asList("address","description","floorArea","floorAreaTerrace","nbBathrooms","nbBedrooms","nbMinNight","nbSleeping","pricePerNight","tele","terrace","title","wifi"));
	static List<Apartment> listOfApartments = JsonConvert.getDefaultApartments();
	private static final Logger LOGGER = LoggerFactory.getLogger(SpiderPlot.class);

	public static Map<String, List<Double>> getDataAsAList(String featureName) {
		
		checkArgument(listOfFeatures.contains(featureName));
	    LOGGER.info("{} is part of our criteria",featureName);

		Map<String, List<Double>> dataMap = new HashMap<>();

		switch(featureName) {
		
		case "floorArea" : {
		    LOGGER.info("\nData about the floor area are available :");
			List <Double> floorAreaStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				floorAreaStats.add((double) Math.round(listOfApartments.get(i).getFloorArea()* 100.0) / 100.0);
			}
			dataMap.put(featureName, floorAreaStats);
			return dataMap;
		}
		
		case "floorAreaTerrace" : {
		    LOGGER.info("\nData about the floor area terrace are available :");
			List <Double> floorAreaTerraceStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				floorAreaTerraceStats.add((double) Math.round(listOfApartments.get(i).getFloorAreaTerrace()* 100.0) / 100.0);
			}
			dataMap.put(featureName, floorAreaTerraceStats);
			return dataMap;
		}
		case "pricePerNight" : {
		    LOGGER.info("\nData about the price per night statistics are available :");
			List <Double> pricePerNightStats = new ArrayList<Double>();
			for (int i=0; i< listOfApartments.size(); i++) {
				pricePerNightStats.add((double) Math.round(listOfApartments.get(i).getPricePerNight()* 100.0) / 100.0);
			}
			dataMap.put(featureName, pricePerNightStats);
			return dataMap;
		}
		default: 
		    LOGGER.info("\n{} isn't a Apartment feature",featureName);
			return null;
		}
	}
	public static void launchDiagram(Map<String, List<Double>>  dataMap) {
		 Entry<String, List<Double>> entry = dataMap.entrySet().iterator().next();
		 String feature = entry.getKey();
		 List<Double> dataList = entry.getValue();

        double[] data = dataList.stream().mapToDouble(Double::doubleValue).toArray();
        var dataset = new HistogramDataset();
	    dataset.addSeries("key", data, 50);
	    JFreeChart histogram = ChartFactory.createHistogram(feature+" statistics",feature, "Effectif", dataset);
	    try {
			ChartUtils.saveChartAsPNG(new File(feature+".png"), histogram, 450, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Image successfully created.");


	}

		public static void main(String[] args) throws IOException {

			launchDiagram(getDataAsAList("floorArea"));
			launchDiagram(getDataAsAList("floorAreaTerrace"));
			launchDiagram(getDataAsAList("pricePerNight"));

	    }
	
}
