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

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
//source of the XML writer http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;


import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Properties properties;
	
	private static JFileChooser dirpicker;
	
	public XMLProperties()
	{
		this.properties = new Properties();
	}
	
	public void toXML(Apartment a, String xmlFilePath) throws IOException
	{
		FileOutputStream s = new FileOutputStream(xmlFilePath);
		properties.storeToXML(s, "Generated file for the apartment " + a.getTitle() );
		
		s.close();
	}
	/**
	 * toXMLProperties transform an Apartment object into an XML file
	 * 
	 * @param a
	 *            the apartment to transform into an xml file
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DOMException 
	 * 
	 */
	public void toXMLProperties(Apartment a) throws DOMException, IllegalArgumentException, IllegalAccessException {

		dirpicker = new JFileChooser();
		dirpicker.setCurrentDirectory(new java.io.File("./xml/"));
		dirpicker.setDialogTitle("Pick a folder to save your xml ... ");
		dirpicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		dirpicker.setAcceptAllFileFilterUsed(false);

		if (dirpicker.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			try {

				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("properties");
				doc.appendChild(rootElement);

				// Comment
				Element comment = doc.createElement("comment");
				rootElement.appendChild(comment);
				comment.appendChild(doc.createTextNode("Apartments written with java"));

				for(Field f : a.getClass().getDeclaredFields()) {
					
						//Get the name of attribute
						String[] s = f.toString().split(" ")[2].split("\\.");
						// staff elements
						Element entry = doc.createElement("entry");
						rootElement.appendChild(entry);

						// set attribute to staff element
						System.out.println();
						entry.setAttribute("key", s[s.length-1]);	
						f.setAccessible(true);
						entry.appendChild(doc.createTextNode(f.get(a).toString()));
					
				}

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				DOMImplementation domImpl = doc.getImplementation();
				DocumentType doctype = domImpl.createDocumentType("doctype", "", "http://java.sun.com/dtd/properties.dtd");
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, doctype.getPublicId());
				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(dirpicker.getSelectedFile(), "GeneratedApartment.xml"));

				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);

				transformer.transform(source, result);

				System.out.println("File saved!");

			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
	}

	/**
	 * This main function
	 * 
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DOMException 
	 */
	public static void main(String[] args) throws DOMException, IllegalArgumentException, IllegalAccessException {
		XMLProperties j = new XMLProperties();
		Apartment a = new Apartment(80.5, "6 rue des paquerette 74000 Annecy", "Petit Manoir de campagne");
		j.toXMLProperties(a);

	}
}
