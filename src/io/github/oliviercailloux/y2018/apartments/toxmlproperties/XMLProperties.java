package io.github.oliviercailloux.y2018.apartments.toxmlproperties;


import javax.swing.JFileChooser;
import javax.swing.JPanel;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;

public class XMLProperties extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JFileChooser dirpicker;
	
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
	}
}
