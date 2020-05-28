package io.github.oliviercailloux.y2018.apartments.readapartments;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment.Builder;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class enables the user to load an XML file for an apartment and transform it to an apartment
 * object.
 */
public class ReadApartmentsXMLFormat {

  private Properties prop;

  private static final Logger LOGGER = LoggerFactory.getLogger(ReadApartmentsXMLFormat.class);

  public ReadApartmentsXMLFormat() {
    prop = new Properties();
  }

  /**
   * This method enables to read a XML file and store the informations into an apartment object. The
   * XML file must contain at list a value for floorArea address and title. The key of each
   * parameters in the XML file is respectively the name of the parameter.
   *
   * @param input is the path of XML file
   * @return an apartment object with values for each parameters found in the XML files and default
   *     values for the other parameters.
   * @throws IOException, NumberFormatException, InvalidPropertiesFormatException
   */
  public Apartment readApartment(InputStream input)
      throws IOException, NumberFormatException, InvalidPropertiesFormatException {

    LOGGER.info("Enter readApartment method");

    prop.loadFromXML(input);

    LOGGER.info("XML Files loaded with success");

    if (prop.containsKey("floorArea") == false
        || prop.containsKey("address") == false
        || prop.containsKey("title") == false) {
      LOGGER.error(
          "Impossible to create an apartment if a floor Area, a title or an address is missing.");
      throw new InvalidPropertiesFormatException(
          "Capital information left for the creation of an Apartment Object");
    }

    Builder apartBuilder = new Builder();
    Apartment apartment =
        apartBuilder
            .setFloorArea(Double.parseDouble(prop.getProperty("floorArea")))
            .setAddress(prop.getProperty("address"))
            .setNbBedrooms(
                prop.containsKey("nbBedrooms")
                    ? Integer.parseInt(prop.getProperty("nbBedrooms"))
                    : 0)
            .setNbSleeping(
                prop.containsKey("nbSleeping")
                    ? Integer.parseInt(prop.getProperty("nbSleeping"))
                    : 0)
            .setNbBathrooms(
                prop.containsKey("nbBathrooms")
                    ? Integer.parseInt(prop.getProperty("nbBathrooms"))
                    : 0)
            .setTerrace(prop.containsKey("terrace") && Boolean.valueOf(prop.getProperty("terrace")))
            .setFloorAreaTerrace(
                prop.containsKey("floorAreaTerrace")
                    ? Double.parseDouble(prop.getProperty("floorAreaTerrace"))
                    : 0)
            .setDescription(prop.containsKey("description") ? prop.getProperty("description") : "")
            .setTitle(prop.getProperty("title"))
            .setWifi(prop.containsKey("wifi") ? Boolean.valueOf(prop.getProperty("wifi")) : false)
            .setPricePerNight(
                prop.containsKey("pricePerNight")
                    ? Double.parseDouble(prop.getProperty("pricePerNight"))
                    : 0)
            .setNbMinNight(
                prop.containsKey("nbMinNight")
                    ? Integer.parseInt(prop.getProperty("nbMinNight"))
                    : 0)
            .setTele(prop.containsKey("tele") && Boolean.valueOf(prop.getProperty("tele")))
            .build();

    LOGGER.info("Parameters inserted with success in the Apartment Object");
    LOGGER.info("Leave readApartment method");

    return apartment;
  }
}
