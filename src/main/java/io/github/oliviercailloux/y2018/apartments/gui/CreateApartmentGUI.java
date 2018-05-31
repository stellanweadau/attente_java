package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.function.Consumer;

import org.eclipse.jface.dialogs.MessageDialog;
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

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.iconDisplay.DisplayIcon;
import io.github.oliviercailloux.y2018.apartments.toxmlproperties.XMLProperties;

public class CreateApartmentGUI {

	private Display display;
	private Shell shell;
	private String file;
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);

	public CreateApartmentGUI(String file) {
		display = new Display();
		shell = new Shell(display);
		this.file = file;
		LOGGER.info("The GUI was initialized with success.");
	}

	private void screenDisplay() throws IOException {
		try(InputStream f = DisplayIcon.class.getResourceAsStream("logo.png")){
			
			LOGGER.info("The logo has been loaded with success.");
			FillLayout r = new FillLayout();
			r.type = SWT.VERTICAL;
			shell.setLayout(r);
			Image i = new Image(display, f);
			shell.setImage(i);
			shell.setText("Apartments");


			createPageTitle();
			Text title = createFormFieldComposite("Title of the apartment : ");
			Text address = createFormFieldComposite("Address : ");
			Text floorArea = createFormFieldComposite("Floor Area :" );
			createButtonValidation(title,address,floorArea);

			shell.pack();
			shell.setMinimumSize(400, 150);
			shell.setSize(500, 500);

			shell.open();
			LOGGER.info("The Shell was opened with success.");

			while(!shell.isDisposed( )){
				if(!display.readAndDispatch( ))
					display.sleep( );
			}
			i.dispose();
			display.dispose();
			LOGGER.info("The screen was closed with success.");
		}
	}

	private Text createFormFieldComposite(String label)
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
		LOGGER.info("The Composite "+label+" was created.");
		return t;
	}

	private void createButtonValidation(Text title, Text address, Text floorArea) throws IllegalArgumentException {
		Composite compoForButton = new Composite(shell, SWT.CENTER);
		GridLayout gl = new GridLayout(1, true);
		compoForButton.setLayout(gl);
		Button b = new Button(compoForButton, SWT.CENTER | SWT.PUSH);
		b.setText("Valider");
		Consumer<SelectionEvent> consu = (event) -> {
			LOGGER.info("The button has been clicked");
			if (floorArea.getText().isEmpty()== false && title.getText().isEmpty()==false && address.getText().isEmpty()==false) {
				try {
					Double floorAreaDouble = Double.parseDouble(floorArea.getText());
					Apartment apart = new Apartment(floorAreaDouble,address.getText(),title.getText());
					System.out.println(apart);
					XMLProperties xmlFile = new XMLProperties();
					File f = new File("src/test/resources/io/github/oliviercailloux/y2018/apartments/gui/"+file+".xml");
					try(FileOutputStream s = new FileOutputStream(f.getAbsolutePath()))
					{
						xmlFile.toXML(apart, s);
						MessageDialog.openInformation(shell, "Information","Apartment created with success\n\n");
					}
					catch (Exception e) {
						MessageDialog.openError(shell, "Error","Insertion Problem in the XML File\n\nTry to restart the app");
						LOGGER.error("Error while inserting data into XML File"+e.getMessage());
						throw new IllegalStateException(e);
					}
					title.setText("");
					address.setText("");
					floorArea.setText("");

				} 
				catch(NumberFormatException e){
					MessageDialog.openError(shell,"Error","Please insert a correct number in the floor area field");
					LOGGER.error("The floor area field is not a number. Exception " + e.getMessage());
					floorArea.setText("");

				}
			}
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
		LOGGER.info("The Composite of the header was created.");
	}
	static public void main(String args[]) throws IOException {
		CreateApartmentGUI c = new CreateApartmentGUI("apartTest");
		c.screenDisplay();
	}
}
