package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;

public class CreateApartmentGUI {
	
	private Display display;
	private Shell shell;
	
	public CreateApartmentGUI() {
		display = new Display();
		shell = new Shell(display);
	}

	private void screenDisplay() throws IOException {
		try(InputStream f = DisplayIcon.class.getResourceAsStream("logo.png")){
			

			// TODO : Ajouter un composite au Shell avec le RowLayout
			FillLayout r = new FillLayout();
			r.type = SWT.VERTICAL;
			shell.setLayout(r);
			Image i = new Image(display, f);
			shell.setImage(i);
			shell.setText("Apartments");
			
			
			
			Label title = new Label(shell, SWT.FILL | SWT.BORDER | SWT.CENTER); 
		    Color color = new Color(display,255,133,131); 
		    //title.setBackground(color);
		    title.setText("Create an apartment");

		    
		    createFormFieldComposite("Title of the apartment : ");
		    createFormFieldComposite("Floor Area :" );
		    
		    Composite compoForButton = new Composite(shell, SWT.CENTER);
		    GridLayout gl = new GridLayout(1, true);
		    compoForButton.setLayout(gl);
		    Button b = new Button(compoForButton, SWT.CENTER);
		    b.setText("Valider");
		    
		    Double.parseDouble(s)
//	    Label label = new Label(s, SWT.PUSH); 
//		    label.setBackground(new Color(d, 255, 255 ,255)); 
//	    label.setText("Enter the announce title"); 
//		  // label.setBounds(10, 10, 100, 25); 
//		    
//		    label.setLayoutData(new GridData(GridData.CENTER, GridData.BEGINNING, true, true));
//		    label.getParent().layout();
//		    
//		    
//		    Text text = new Text(s, SWT.BORDER); 
//		    text.setText(""); 
//		    text.setBackground(new Color(d, 255,255,255));
//		    GridData gridDataTitleText = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
//		    gridDataTitleText.
//		    text.setLayoutData(gridDataTitleText);
//		    text.getParent().layout();
		    // text.setBounds((compositeWidth-100)/2, 30, 100, 25); 
//
//		    Button button = new Button(composite, SWT.BORDER); 
//		    button.setText("Valider"); 
//		  //  button.setBounds((compositeWidth-100)/2, 60, 100, 25); 
		    
			shell.pack();
			shell.setMinimumSize(400, 150);
			shell.setSize(400, 150);

			shell.open();
			
			
			
			while(!shell.isDisposed( )){
				if(!display.readAndDispatch( ))
					display.sleep( );
			}
			color.dispose();
			i.dispose();
			display.dispose();
		}
	}
	
	private void createFormFieldComposite(String label )
	{
		Composite c = new Composite(shell, SWT.PUSH);
		
		GridLayout f = new GridLayout(2, false);
		c.setLayout(f);
		GridData a = new GridData(SWT.FILL, SWT.CENTER, true, false);
		a.minimumWidth = SWT.FILL;
		a.horizontalAlignment = SWT.CENTER;
		a.widthHint = 200;
		Label lb = new Label(c, SWT.FILL);
		lb.setText(label);
		lb.setLayoutData(a);
		Text t = new Text(c, SWT.FILL);
		t.setText("");
		t.setLayoutData(a);
		shell.pack();
	}
	static public void main(String args[]) throws IOException {
		CreateApartmentGUI c = new CreateApartmentGUI();
		c.screenDisplay();
	}
}
