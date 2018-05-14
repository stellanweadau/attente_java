package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
//source of the XML writer http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;


import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.distance.DistanceSubway;

public class XMLProperties{

	
	private Properties properties;
	
	static Logger xmlProperties = LoggerFactory.getLogger(XMLProperties.class);
	
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
	public void toXML(Apartment a, File xmlFile) throws IOException, IllegalArgumentException, IllegalAccessException
	{
		xmlProperties.info("Entrée de fonction - " + xmlFile.getAbsolutePath());
		if(xmlFile.createNewFile())
		{
			xmlProperties.info("File has been created");
		}
		else
		{
			xmlProperties.info("File already exists. (Erased)");
		}
		try(FileOutputStream s = new FileOutputStream(xmlFile.getAbsolutePath()))
			{
			
			for(Field f : a.getClass().getDeclaredFields()) {
				String[] fullName = f.toString().split(" ")[2].split("\\.");
				
				f.setAccessible(true);
				if(f.get(a) != "0" && f.get(a) != " ")
					properties.setProperty(fullName[fullName.length-1],f.get(a).toString());
				
				
				xmlProperties.info("Adding entry : " + fullName[fullName.length-1] + " : " + f.get(a));
			
			}
			
				properties.storeToXML(s, "Generated file for the apartment " + a.getTitle() );
				
				s.close();
				xmlProperties.info("Stream has been closed");
			}
		
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
		j.toXML(a, f);

	}
}
