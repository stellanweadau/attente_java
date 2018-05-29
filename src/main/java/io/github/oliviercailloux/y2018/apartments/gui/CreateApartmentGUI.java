package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;

public class CreateApartmentGUI {
	
	private Display display;
	private Shell shell;
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);
	
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
			
			createPageTitle();
		    createFormFieldComposite("Title of the apartment : ");
		    createFormFieldComposite("Address : ");
		    createFormFieldComposite("Floor Area :" );
		    createButtonValidation();

			shell.pack();
			shell.setMinimumSize(400, 150);
			shell.setSize(400, 150);

			shell.open();

			while(!shell.isDisposed( )){
				if(!display.readAndDispatch( ))
					display.sleep( );
			}
			i.dispose();
			display.dispose();
		}
	}
	
	private void createFormFieldComposite(String label)
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
	
	private void createButtonValidation() {
		Composite compoForButton = new Composite(shell, SWT.CENTER);
	    GridLayout gl = new GridLayout(1, true);
	    compoForButton.setLayout(gl);
	    Button b = new Button(compoForButton, SWT.CENTER | SWT.PUSH);
	    b.setText("Valider");
	    Consumer<SelectionEvent> consu = (event) -> {
	    	LOGGER.info("Coucou");
	    };
	    consu.accept(null);
	    SelectionListener l = SelectionListener.widgetSelectedAdapter(consu);
	    b.addSelectionListener(l);
	    GridData a = new GridData(SWT.FILL, SWT.CENTER, true, false);
		a.minimumWidth = SWT.FILL;
		a.horizontalAlignment = SWT.CENTER;
		a.widthHint = 200;
		b.setLayoutData(a);
	}
	
	private void createPageTitle() {
		Composite compoForTitle = new Composite(shell, SWT.CENTER);
	    GridLayout gl = new GridLayout(1, true);
	    compoForTitle.setLayout(gl);
	    Label title = new Label(compoForTitle, SWT.FILL | SWT.CENTER); 
	    title.setText("CREATE AN APARTMENT");
	    GridData a = new GridData(SWT.FILL, SWT.CENTER, true, false);
		a.minimumWidth = SWT.FILL;
		a.horizontalAlignment = SWT.CENTER;
		a.widthHint = 200;
		title.setLayoutData(a);
	}
	static public void main(String args[]) throws IOException {
		CreateApartmentGUI c = new CreateApartmentGUI();
		c.screenDisplay();
	}
}
