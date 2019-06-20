package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.w3c.dom.DOMException;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;
import io.github.oliviercailloux.y2018.apartments.toxmlproperties.XMLProperties;

import org.eclipse.swt.layout.*;

public class LayoutApartmentGUI {

	static Display display;
	static Shell shell;

	public static void main(String[] args) throws DOMException, IllegalAccessException, IOException {

		Label adresse;
		Label surface;
		Label prix;
		Label nbrChambres;
		Canvas photoCanevas;

		// definition d'un display
		display = new Display();
		shell = new Shell(display);
		shell.setText("Sélection d'un appartement");

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);

		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;

		Label label = new Label(shell, SWT.NULL);
		label.setText("Liste des appartements disponibles :");
		label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		new Label(shell, SWT.NULL);


		new Label(shell, SWT.NULL);

		gridData = new GridData(GridData.FILL_BOTH);
		gridData.verticalSpan = 3;

		ArrayList<Apartment> appart = new ArrayList<Apartment>(10);
		for (int i = 0; i < 50; ++i) {
			Apartment a = XMLProperties.generateRandomXML();
			appart.add(a);
		}
		final List list = new List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);

		for (Apartment a : appart) {
			System.out.println("Appart : " + a);
			list.add("Titre: " + a.getTitle() + "\t" + " Adresse : " + a.getAddress());
		}

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.verticalSpan = 4;
		gridData.heightHint = 400;
		list.setLayoutData(gridData);

		Group appartInfo = new Group(shell, SWT.NULL);
		appartInfo.setText("Détail dur l'appartement sélectionné :");
		// on refait un grisd coupé eb 2 pour aligner label et contenu.
		// on pourrai peut etre mettre en read - a tester pour voir si ca se rafraichi
		// quand meme

		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		appartInfo.setLayout(gridLayout);

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData.horizontalSpan = 2;
		appartInfo.setLayoutData(gridData);

		new Label(shell, SWT.NULL).setText("Photo : ");
		photoCanevas = new Canvas(shell, SWT.BORDER);

		new Label(appartInfo, SWT.NULL).setText("Adresse :");
		adresse = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		adresse.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Surface :");
		surface = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		surface.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Prix :");
		prix = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		prix.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(appartInfo, SWT.NULL).setText("Nombre de Chambres :");
		nbrChambres = new Label(appartInfo, SWT.SINGLE | SWT.BORDER);
		nbrChambres.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gridData.horizontalSpan = 3;

		list.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				int[] selectedItems = list.getSelectionIndices();
				// String outString = "";
				for (int loopIndex = 0; loopIndex < selectedItems.length; loopIndex++) {
					adresse.setText(appart.get(list.getSelectionIndex()).getAddress());
					surface.setText(" " + appart.get(list.getSelectionIndex()).getFloorArea());
					prix.setText(" " + appart.get(list.getSelectionIndex()).getPricePerNight());
					nbrChambres.setText(" " + appart.get(list.getSelectionIndex()).getNbBedrooms());
				}

			}

		});

		shell.setSize(1500, 550);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		// desallocation manuelle ( fichier gourmand - suppression au cas où garbadge
		// collector ne desaloue pas )
		if (photoCanevas != null) {
			photoCanevas.dispose();
		}
	}

}
