package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;

public class CreateApartmentGUI {

	private void screenDisplay() throws IOException {
		try(InputStream f = DisplayIcon.class.getResourceAsStream("logo.png")){
			Display d = new Display( );
			Shell s = new Shell(d);
			
			int shellHeight = 500;
			int shellWidth = 500;
			int compositeHeight = 250;
			int compositeWidth = 250;
			
			s.setSize(shellWidth,shellHeight);
			Image i = new Image(d, f);
			s.setImage(i);
			s.setText("Apartments");

			Composite composite = new Composite(s, SWT.NONE); 
		    Color color = new Color(d,100,100,100); 
		    composite.setBackground(color);
		    composite.setBounds((s.getBounds().width-compositeWidth)/2, 40, compositeWidth, compositeHeight);
		    
		    Label titleLb = new Label(composite, SWT.NONE); 
		    titleLb.setBackground(color); 
		    titleLb.setText("Title"); 
		    titleLb.setBounds(30, 10, 100, 25); 
		    
		    Text titleText = new Text(composite, SWT.BORDER); 
		    titleText.setText(""); 
		    titleText.setBounds((compositeWidth-90)*3/4, 10, 100, 25);

		    Label floorAreaLb = new Label(composite, SWT.NONE); 
		    floorAreaLb.setBackground(color); 
		    floorAreaLb.setText("Floor Area"); 
		    floorAreaLb.setBounds(30, 60, 100, 25); 
		    
		    Text floorAreaText = new Text(composite, SWT.BORDER); 
		    floorAreaText.setText(""); 
		    floorAreaText.setBounds((compositeWidth-90)*3/4, 60, 100, 25);
		    
		    Label addressLb = new Label(composite, SWT.NONE); 
		    addressLb.setBackground(color); 
		    addressLb.setText("Address"); 
		    addressLb.setBounds(30, 110, 100, 25); 
		    
		    Text addressText = new Text(composite, SWT.BORDER); 
		    addressText.setText(""); 
		    addressText.setBounds((compositeWidth-90)*3/4, 110, 100, 25); 

		    Button button = new Button(composite, SWT.BORDER); 
		    button.setText("Valider"); 
		    button.setBounds((compositeWidth-100)/2,200, 100, 25); 
		    
		    Composite title = new Composite(s, SWT.NONE);
			title.setBounds(155, 0, compositeWidth, 25);
			Label lbTitle = new Label(title, SWT.CENTER);
			lbTitle.setText("CREATE AN APARTMENT");
		    
			floorAreaLb.pack();
			titleLb.pack();
			lbTitle.pack();
			addressLb.pack();
			s.open();
			
			
			
			while(!s.isDisposed( )){
				if(!d.readAndDispatch( ))
					d.sleep( );
			}
			color.dispose();
			i.dispose();
			d.dispose();
		}
	}
	
	static public void main(String args[]) throws IOException {
		CreateApartmentGUI c = new CreateApartmentGUI();
		c.screenDisplay();
	}
}
