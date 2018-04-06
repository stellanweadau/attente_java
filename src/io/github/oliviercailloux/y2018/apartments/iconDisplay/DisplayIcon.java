package io.github.oliviercailloux.y2018.apartments.iconDisplay;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

public class DisplayIcon {

	public static void main(String[] args) {
		Display d = new Display( );
		Shell s = new Shell(d);
		s.setSize(500,500);
		s.setImage(new Image(d, "resources\\logo.png"));
		s.setText("Apartments");
		s.open( );
		while(!s.isDisposed( )){
			if(!d.readAndDispatch( ))
				d.sleep( );
		}
		d.dispose( );

	}

}
