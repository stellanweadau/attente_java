package io.github.oliviercailloux.y2018.apartments.iconDisplay;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import readApartmentsTest.ReadTwoApartmentsTest;

public class DisplayIcon {

	public static void main(String[] args) throws IOException {
		try(InputStream f = DisplayIcon.class.getClassLoader().getResourceAsStream("logo.png")){
			Display d = new Display( );
			Shell s = new Shell(d);
			s.setSize(500,500);
			s.setImage(new Image(d, f));
			s.setText("Apartments");
			s.open( );
			while(!s.isDisposed( )){
				if(!d.readAndDispatch( ))
					d.sleep( );
			}
			d.dispose( );
			s.dispose();
		}
	}
}
