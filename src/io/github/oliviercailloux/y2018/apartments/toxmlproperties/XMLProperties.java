package io.github.oliviercailloux.y2018.apartments.toxmlproperties;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
//source of the XML writer http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static JFileChooser dirpicker;
	
	public void toXMLProperties(Apartment a)
	{
		
		dirpicker = new JFileChooser(); 
		dirpicker.setCurrentDirectory(new java.io.File("./xml/"));
		dirpicker.setDialogTitle("Pick a folder to save your xml ... ");
		dirpicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		dirpicker.setAcceptAllFileFilterUsed(false);
	  
	    if (dirpicker.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): " 
	         +  dirpicker.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " 
	         +  dirpicker.getSelectedFile());
	    }
		
	}
	
	public static void main(String[] args) {
		XMLProperties j = new XMLProperties();
		Apartment a = new Apartment(80.5, "6 rue des paquerette 74000 Annecy", "Petit Manoir de campagne");
		j.toXMLProperties(a);
		  try {

				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("properties");
				doc.appendChild(rootElement);
					
			
				
				//Comment
				Element comment = doc.createElement("comment");
				rootElement.appendChild(comment);
				comment.appendChild(doc.createTextNode("Apartments written with java"));


				// staff elements
				Element entry = doc.createElement("entry");
				rootElement.appendChild(entry);

				// set attribute to staff element
				entry.setAttribute("key", "title");	
				entry.appendChild(doc.createTextNode("Poubelle"));
				
				Element entry1 = doc.createElement("entry");
				rootElement.appendChild(entry1);
				entry1.setAttribute("key", "address");	
				entry1.appendChild(doc.createTextNode("888 rue du jackpot"));
				
				Element entry11 = doc.createElement("entry");
				rootElement.appendChild(entry11);
				entry11.setAttribute("key", "floorArea");	
				entry11.appendChild(doc.createTextNode("77"));

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				DOMImplementation domImpl = doc.getImplementation();
				DocumentType doctype = domImpl.createDocumentType("doctype",
				    "",
				    "http://java.sun.com/dtd/properties.dtd");
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


