package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties{

	
	private Properties properties;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(XMLProperties.class);
	
	public XMLProperties()
	{
		this.properties = new Properties();
	}
	/**
	 *  toXml transform an Apartment into an xml File. The user specify the file in parameter
	 * @param a
	 * 		the apartment to put into an xml file
	 * @param xmlFile
	 * 			an file object where the apartment will be store. Warning : if the file already exists, it will be erased.
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void toXML(Apartment a, OutputStream xmlFile) throws IOException, IllegalArgumentException, IllegalAccessException
	{
			
			for(Field f : a.getClass().getDeclaredFields()) {
				
				String[] fullName = f.toString().split(" ")[2].split("\\.");
				
				f.setAccessible(true);
				properties.setProperty(fullName[fullName.length-1],f.get(a).toString());
				
				LOGGER.info("Adding entry : " + fullName[fullName.length-1] + " : " + f.get(a));
			
			}
				properties.remove("apartment");
				properties.remove("LOGGER");
				properties.remove("Logger");
				properties.storeToXML(xmlFile, "Generated file for the apartment " + a.getTitle() );
				
				xmlFile.close();
				LOGGER.info("Stream has been closed");
//			}
		
	}
	

	/**
	 * This is the main function
	 * 
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DOMException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws DOMException, IllegalArgumentException, IllegalAccessException, IOException {
		XMLProperties j = new XMLProperties();
		Apartment a = new Apartment(80.5, "6 rue des paquerette 74000 Annecy", "Petit Manoir de campagne");
		File f = new File("src/test/resources/xmlfile.xml");
		try(FileOutputStream s = new FileOutputStream(f.getAbsolutePath()))
		{
			j.toXML(a, s);
		}

	}
}
