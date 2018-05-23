package io.github.oliviercailloux.y2018.apartments.guiapartment;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;

public class GuiApartment {

	private Apartment apartment;

	public GuiApartment() {
		apartment = new Apartment(0,"","");
	}

	static private void displayScreen() throws IOException {
		try(InputStream f = DisplayIcon.class.getResourceAsStream("logo.png")){
			Display d = new Display( );
			Shell s = new Shell(d);

			s.setSize(500,500);
			Image i = new Image(d, f);
			s.setImage(i);
			s.setText("Apartments");
			s.open( );
			
			while(!s.isDisposed( )){
				if(!d.readAndDispatch( ))
					d.sleep( );
			}
			i.dispose();
			s.dispose();
			d.dispose();
		}
	}
	
	public static void main (String args[]) throws IOException {
		displayScreen();
	}

}
