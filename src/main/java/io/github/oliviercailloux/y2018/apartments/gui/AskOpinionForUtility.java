package io.github.oliviercailloux.y2018.apartments.gui;

import java.io.IOException;
import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import io.github.oliviercailloux.y2018.apartments.valuefunction.Criterion;
import io.github.oliviercailloux.y2018.apartments.valuefunction.ApartmentValueFunction;

import org.eclipse.swt.widgets.*;

/**
 * @author SALAME & SAKHO
 * 
 */

/**
 * This class is a class that will allow us to demonstrate. We ask a few
 * questions to the user at the beginning, we adapt his utility then we show him
 * a list of apartments according to his preferences
 */

public class AskOpinionForUtility {

	/**
	 * To move for questions
	 */
	int pointer = 0;

	/**
	 * This array will stock the user's answers (when he presses the button)
	 */
	ArrayList<String> attributsImportant;

	/**
	 * This array will stock the attribute that the user didn't answer (the
	 * unpressed button)
	 */
	ArrayList<String> attributsPasImportant;

	/**
	 * This array has the attributes for the first button (buttonchoix1), we can add
	 * how much we want
	 */
	ArrayList<Criterion> choix1;

	/**
	 * This array has attributes for the second button (buttonchoix2)
	 */
	ArrayList<Criterion> choix2;

	double surfaceMin;
	double nbBedMin;

	Display display;
	Shell shell;

	public AskOpinionForUtility() {
		this.attributsImportant = new ArrayList<>();
		this.attributsPasImportant = new ArrayList<>();
		this.choix1 = new ArrayList<>();
		this.choix2 = new ArrayList<>();
		this.surfaceMin = 0d;
		this.nbBedMin = 0d;
		this.pointer = 0;
		this.display = new Display();
		this.shell = new Shell(display);
	}
	
	/**
	 * This is the main function, it asks Questions , AdaptAnswers and then displays
	 * the list of Apartements
	 * 
	 * @param args
	 * @throws IllegalAccessException for the DisplayApps function
	 * @throws IOException
	 */
	public static void main(String[] args) throws IllegalAccessException, IOException {

		AskOpinionForUtility asker = new AskOpinionForUtility();
		ApartmentValueFunction avf = new ApartmentValueFunction();
		asker.askQuestions();
		asker.adaptAnswers(avf);
		Layout2 lay = new Layout2();
		lay.displayAppart();

	}

	/**
	 * This function will create and display the window (interface) to ask the user
	 * questions and to determine later the weight of his choices
	 * 
	 */
	public void askQuestions() {

		// we add the other two criteria
		choix1.add(Criterion.TELE);
		choix2.add(Criterion.PRICE_PER_NIGHT);

		shell.setText("Votre avis nous intéresse ;)");
		shell.setLayout(new GridLayout());
		shell.setBounds(500, 500, 600, 500);

		// for the first questions about minimum
		final Group group = new Group(shell, SWT.NONE);
		group.setText("Combien il vous faut pour accepter un apartement?");
		group.setLayout(new GridLayout(2, false));

		Label label1 = new Label(group, SWT.NONE);
		label1.setText("Surface minimum?");

		Text text1 = new Text(group, SWT.LEAD | SWT.BORDER);
		text1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label label2 = new Label(group, SWT.NONE);
		label2.setText("Nombre de chambre minimum?");

		Text text2 = new Text(group, SWT.LEAD | SWT.BORDER);
		text2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// question about the importance of an element for the user
		Group buttonGroup = new Group(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		buttonGroup.setLayout(gridLayout);
		buttonGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		buttonGroup.setText("Qu'est ce qui a le plus d'importance pour vous");

		// This is a submit button, it will close the shell when the user click on
		// Terminer
		final Button finish = new Button(shell, SWT.PUSH);
		finish.setText("Terminé");
		finish.pack();
		finish.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {

				surfaceMin = Double.parseDouble(text1.getText());
				nbBedMin = Double.parseDouble(text2.getText());

				shell.close();

				// some tests
				/*
				 * System.out.print("Les attribut important sont : "); for (int j = 0; j <
				 * attributImportant.size(); j++) {
				 * System.out.println(attributImportant.get(j)); }
				 * 
				 * System.out.print("Les attribut pas important sont : "); for (int j = 0; j <
				 * attributPasImportant.size(); j++) {
				 * 
				 * System.out.println(attributPasImportant.get(j)); }
				 * System.out.println("la valeur minmum de la surface est " + surfaceMin +
				 * "\nLa valeur minimum du nbre de chambre est " + nbBedMin);
				 */
			}

		});

		// buttonchoix1 and buttonchoix2 are two radio buttons, to let the user chose
		// between two options
		Button buttonchoix1 = new Button(buttonGroup, SWT.RADIO);
		buttonchoix1.setText("WIFI");

		Button buttonchoix2 = new Button(buttonGroup, SWT.RADIO);
		buttonchoix2.setText("TERRACE");

		// when cliquing on theses buttons
		clickOnButton(buttonchoix1, buttonchoix2);
		clickOnButton(buttonchoix2, buttonchoix1);

		// open the window
		shell.open();
		while (!shell.isDisposed()) {

			if (!display.readAndDispatch()) {

				display.sleep();
			}
		}
		display.dispose();
	}

	/**
	 * Method that responds when we click on a button. It needs to retrieve the two
	 * buttons that correspond to the items to choose
	 * 
	 * @param pressedButton   the choice of the user
	 * @param unPressedButton the other choice not selected
	 * 
	 */
	public void clickOnButton(Button pressedButton, Button unPressedButton) {
		Label boo = new Label(shell, SWT.NULL);
		pressedButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Button source1 = (Button) e.widget;

				if (source1.getSelection() && !attributsImportant.contains(source1.getText())) {
					attributsImportant.add(source1.getText()); // to add the pressed button answer to the array
																// attributImportant
					attributsPasImportant.add(unPressedButton.getText()); // to add the unpessed button answer to the
																			// array
																			// attributPasImporta
				}

				if (pointer == choix1.size() || pointer == choix2.size()) {

					pressedButton.setEnabled(false);
					unPressedButton.setEnabled(false);

					boo.setText("\"Merci pour vos choix, nous allons chercher pour vous les meilleurs apartements :) ");
					boo.pack();
				}

				else {

					pressedButton.setText(choix1.get(pointer).toString());
					unPressedButton.setText(choix2.get(pointer).toString());
					pointer++;
					/**
					 * this will change the text of the buttons by choosing the content of the
					 * arrays attributImportant and attributPasImportant
					 */
				}

				pressedButton
						.setSelection(false); /**
												 * to not be selected by default in the question in the next iteration
												 */
				pressedButton.pack();
				unPressedButton.pack();
			}

		});

	}

	/**
	 * This function will adapt the utility of the user using ApartmentValueFunction
	 * 
	 */
	public void adaptAnswers(ApartmentValueFunction avf) {

		// we collect the answers on the minimums and we adapt the utility of the user
		avf.setSubjectiveValueWeight(Criterion.NB_BEDROOMS, surfaceMin);
		avf.setSubjectiveValueWeight(Criterion.FLOOR_AREA, nbBedMin);

		// we collect the answer of the first Question and adapt the utility of the user
		if (attributsImportant.get(0).equals("WIFI") && attributsPasImportant.get(0).equals("TERRACE")) {
			avf.adaptWeight(Criterion.WIFI, Criterion.TERRACE);
		} else {
			avf.adaptWeight(Criterion.TERRACE, Criterion.WIFI);
		}

		// we collect the answer of the second Question and we adapt the utility of the
		// user
		if (attributsImportant.get(1).equals("TELE") && attributsPasImportant.get(1).equals("PRICE_PER_NIGHT low")) {
			avf.adaptWeight(Criterion.TELE, Criterion.PRICE_PER_NIGHT);
		} else {
			avf.adaptWeight(Criterion.PRICE_PER_NIGHT, Criterion.TELE);
		}

	}

}
