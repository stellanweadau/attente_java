package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
	Text title;
	Text address;
	Text floorArea;
	Text nbBedrooms;
	Text nbSleeping ;
	Text nbBathrooms;
	Button terrace;
	Text floorAreaTerrace;
	Text pricePerNight;
	Text nbMinNight;
	Text description;
	Button wifi;
	Button tele ;
	File file;
	private final static Logger LOGGER = LoggerFactory.getLogger(CreateApartmentGUI.class);
	/**
	 * 
	 * @param fileCompleteName
	 */
	public CreateApartmentGUI(String fileCompleteName) {
		this.display = new Display();
		this.shell = new Shell(display);
		this.file = new File(fileCompleteName);
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
			createForm();
			createButtonValidation();

			shell.pack();
			shell.setMinimumSize(400, 150);
			shell.setSize(600, 600);

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

	private void createForm() {
		title = createFormFieldComposite("Title of the apartment*: ");
		address = createFormFieldComposite("Address*: ");
		floorArea = createFormFieldComposite("Floor Area*:" );
		nbBedrooms = createFormFieldComposite("Number of bedrooms: ");
		nbSleeping = createFormFieldComposite("Sleeping capacity: ");
		nbBathrooms = createFormFieldComposite("Number of bathrooms: ");
		terrace = createCheckboxComposite("Terrace: ");
		floorAreaTerrace = createFormFieldComposite("Floor area terrace: ");
		pricePerNight = createFormFieldComposite("Price per night: ");
		nbMinNight = createFormFieldComposite("Minimum nights to stay: ");
		wifi = createCheckboxComposite("WiFi: ");
		tele = createCheckboxComposite("Television: ");
		description = createFormFieldComposite("Description:");
		
		
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

	private Button createCheckboxComposite(String label)
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
		Button t = new Button(c, SWT.CHECK);
		t.setText("");
		t.setLayoutData(a);
		shell.pack();
		LOGGER.info("The Composite "+label+" was created.");
		return t;
	}

	private void createButtonValidation() throws IllegalArgumentException {
		Composite compoForButton = new Composite(shell, SWT.CENTER);
		GridLayout gl = new GridLayout(1, true);
		compoForButton.setLayout(gl);
		Button b = new Button(compoForButton, SWT.CENTER | SWT.PUSH);
		b.setText("Valider");

		Consumer<SelectionEvent> consu = (event) -> {
			Double floorAreaDouble = 0.0;
			LOGGER.info("The button has been clicked");
			if (floorArea.getText().isEmpty()== false && title.getText().isEmpty()==false && address.getText().isEmpty()==false) {
				try {
					floorAreaDouble = Double.parseDouble(floorArea.getText());

				} 
				catch(NumberFormatException e){
					MessageDialog.openError(shell,"Error","Please insert a correct number in the floor area field");
					LOGGER.error("The floor area field is not a number. Exception " + e.getMessage());
					floorArea.setText("");

				}

				Apartment apart = new Apartment(floorAreaDouble,address.getText(),title.getText());
				apart.setTerrace(terrace.getSelection());

				write(apart);
				reset();
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

	private void write(Apartment a) {
		XMLProperties xmlFile = new XMLProperties();
		try(FileOutputStream s = new FileOutputStream(file.getAbsolutePath()))
		{
			xmlFile.toXML(a, s);
			MessageDialog.openInformation(shell, "Information","Apartment created with success\n\n");
		}
		catch (Exception e) {
			MessageDialog.openError(shell, "Error","Insertion Problem in the XML File\n\nTry to restart the app");
			LOGGER.error("Error while inserting data into XML File"+e.getMessage());
			throw new IllegalStateException(e);
		}

	}

	private void reset() {
		title.setText("");
		address.setText("");
		floorArea.setText("");
		nbBedrooms.setText("");
		nbSleeping.setText("");
		nbBathrooms.setText("");
		terrace.setSelection(false);
		floorAreaTerrace.setText("");
		pricePerNight.setText("");
		nbMinNight.setText("");
		wifi.setSelection(false);
		tele.setSelection(false);
		description.setText("");
	}
	private void createPageTitle() {
		Composite compoForTitle = new Composite(shell, SWT.CENTER);
		GridLayout gl = new GridLayout(1, true);
		compoForTitle.setLayout(gl);
		Label titleLb = new Label(compoForTitle, SWT.FILL | SWT.CENTER); 
		titleLb.setText("CREATE AN APARTMENT");
		GridData a = new GridData(SWT.FILL, SWT.CENTER, true, false);
		a.minimumWidth = SWT.FILL;
		a.horizontalAlignment = SWT.CENTER;
		a.widthHint = 200;
		titleLb.setLayoutData(a);
		LOGGER.info("The Composite of the header was created.");
	}
	/**
	 * 
	 * @param args
	 * 	must contains as first parameter the complete name of the file (Full Path).
	 * @throws IOException
	 */
	static public void main(String args[]) throws IOException {
		CreateApartmentGUI c = new CreateApartmentGUI(args[0]);
		c.screenDisplay();
	}
}
