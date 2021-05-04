package io.github.oliviercailloux.y2021.apartments.plot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;

import org.slf4j.Logger;

/**
 * The public class <b>SpiderPlot</b> allows to display a SpiderPlot from 2
 * apartements and their valuefunction.
 */

public class SpiderPlot {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpiderPlot.class);

	public static SpiderPlot given(Apartment apartment1, Apartment apartment2, ApartmentValueFunction avf1,
			ApartmentValueFunction avf2) {
		return new SpiderPlot(apartment1, apartment2, avf1, avf2);
	}

	private SpiderPlot(Apartment apartment1, Apartment apartment2, ApartmentValueFunction avf1,
			ApartmentValueFunction avf2) {
		createImgChart(createChart(toCategoryDataset(apartment1, apartment2, avf1, avf2)));
	}

	/*
	 * The following createChart is taken from (not the logger infos):
	 * https://stackoverflow.com/questions/32862913/how-to-draw-a-spiderchart-above-
	 * a-existing-jfreechart/32885067#32885067
	 */

	/**
	 * Create the spider-plot chart of an input dataset
	 * 
	 * @param dataset : Set of values in the CategoryDataset type
	 * @return A Spider-plot chart of the corresponding dataset
	 */
	private static JFreeChart createChart(CategoryDataset dataset) {
		LOGGER.info("Compare : {} and {}", dataset.getRowKey(0), dataset.getRowKey(1));
		SpiderWebPlot plot = new SpiderWebPlot(dataset);
		JFreeChart chart = new JFreeChart("Compare them !", plot);
		LOGGER.info("Chart successfully created.");
		return chart;
	}

	/**
	 * Convert a list of apartments and their valuefunction into a CategoryDataset
	 * 
	 * @param selectedApartments : list of the selected apartments
	 * @param correspondingAVF   : list of their corresponding valuefonction
	 * @return a dataset of type CategoryDataset
	 */
	private static CategoryDataset toCategoryDataset(Apartment apartment1, Apartment apartment2,
			ApartmentValueFunction avf1, ApartmentValueFunction avf2) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(avf1.getFloorAreaTerraceValueFunction().getSubjectiveValue(apartment1.getFloorArea()),
				apartment1.getAddress(), "Terrace area");
		dataset.addValue(avf1.getFloorAreaValueFunction().getSubjectiveValue(apartment1.getFloorArea()),
				apartment1.getAddress(), "Floor area");
		dataset.addValue(avf1.getWifiValueFunction().getSubjectiveValue(apartment1.getWifi()), apartment1.getAddress(),
				"WIFI");
		dataset.addValue(avf1.getTeleValueFunction().getSubjectiveValue(apartment1.getTele()), apartment1.getAddress(),
				"Tele");
		dataset.addValue(avf1.getTerraceValueFunction().getSubjectiveValue(apartment1.getTerrace()),
				apartment1.getAddress(), "Terrace");
		dataset.addValue(avf1.getPricePerNightValueFunction().getSubjectiveValue(apartment1.getPricePerNight()),
				apartment1.getAddress(), "Price");
		dataset.addValue(avf1.getNbMinNightValueFunction().getSubjectiveValue((double) apartment1.getNbMinNight()),
				apartment1.getAddress(), "Night");

		dataset.addValue(avf2.getFloorAreaTerraceValueFunction().getSubjectiveValue(apartment2.getFloorArea()),
				apartment2.getAddress(), "Terrace area");
		dataset.addValue(avf2.getFloorAreaValueFunction().getSubjectiveValue(apartment2.getFloorArea()),
				apartment2.getAddress(), "Floor area");
		dataset.addValue(avf2.getWifiValueFunction().getSubjectiveValue(apartment2.getWifi()), apartment2.getAddress(),
				"WIFI");
		dataset.addValue(avf2.getTeleValueFunction().getSubjectiveValue(apartment2.getTele()), apartment2.getAddress(),
				"Tele");
		dataset.addValue(avf2.getTerraceValueFunction().getSubjectiveValue(apartment2.getTerrace()),
				apartment2.getAddress(), "Terrace");
		dataset.addValue(avf2.getPricePerNightValueFunction().getSubjectiveValue(apartment2.getPricePerNight()),
				apartment2.getAddress(), "Price");
		dataset.addValue(avf2.getNbMinNightValueFunction().getSubjectiveValue((double) apartment2.getNbMinNight()),
				apartment2.getAddress(), "Night");

		LOGGER.info("Successfully converted to CategoryDataset.");
		return dataset;
	}

	/**
	 * Create a PNG from a JFreeChart plot.
	 * 
	 * @param jfreechart : a JFreeChart plot
	 * @throws IOException
	 */
	private static void createImgChart(JFreeChart jfreechart) {
		BufferedImage imgToDisplay = jfreechart.createBufferedImage(400, 400);
		File outputfile = new File("Doc/img/saved_spiderplot.png");
		try {
			ImageIO.write(imgToDisplay, "png", outputfile);
		} catch (IOException e) {

			e.printStackTrace();
		}
		LOGGER.info("Img successfully created.");

	}

}