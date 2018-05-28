package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
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
			int compositeHeight = 90;
			int compositeWidth = 250;
			
			s.setSize(shellWidth,shellHeight);
			Image i = new Image(d, f);
			s.setImage(i);
			s.setText("Apartments");
			
			Composite composite = new Composite(s, SWT.FILL); 
		    Color color = new Color(d,13,133,131); 
		    composite.setBackground(color);
	
		    composite.setBounds((s.getBounds().width-compositeWidth)/2, 0, compositeWidth, compositeHeight);
	
		    
		    Label label = new Label(composite, SWT.NONE); 
		    label.setBackground(color); 
		    label.setText("Enter the announce title"); 
		    label.setBounds(10, 10, 100, 25); 

		    Text text = new Text(composite, SWT.BORDER); 
		    text.setText(""); 
		    text.setBounds((compositeWidth-100)/2, 30, 100, 25); 

		    Button button = new Button(composite, SWT.BORDER); 
		    button.setText("Valider"); 
		    button.setBounds((compositeWidth-100)/2, 60, 100, 25); 
		    
			label.pack();
			s.open();
			
			
			
			while(!s.isDisposed( )){
				if(!d.readAndDispatch( ))
					d.sleep( );
			}
			color.dispose();
			i.dispose();
			//s.dispose();
			d.dispose();
			//label.dispose();
		}
	}
	
	static public void main(String args[]) throws IOException {
		CreateApartmentGUI c = new CreateApartmentGUI();
		c.screenDisplay();
	}
}
