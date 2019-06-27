package io.github.oliviercailloux.y2018.apartments.gui;


import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWT.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2018.apartments.apartment.Apartment;


/**
 * @author SALAME & SAKHO
 * 
 */

/**
 * this class displays a list of apartments sorted according to the user's
 * utilities
 */

public class AskAvisForUtility {
	
	static int i = 0; 
	static int j =0 ; 
	static Display display = new Display();
	static Shell shell = new Shell(display);
	
	

	
	
	
	public static void main(String[] args) {
		
		ArrayList<String> choix1 = new ArrayList<String>();
		choix1.add("Surface");
		choix1.add("tele");
		
		ArrayList<String> choix2 = new ArrayList<String>();
		choix2.add("wifi");
		choix2.add("prix");
		
		
		
		Label title = new Label(shell, SWT.CENTER);
		title.setText("Donnez moi votre avis :)");
		title.setLocation(25, 10);
		title.pack();
		
////////////////////
		
		Label question1 = new Label(shell, SWT.CENTER);
		
		question1.setText("Qu'est-ce qui a le plus d'importance pour vous ?");
		
		
		final Button buttonChoix1 = new Button(shell, SWT.BUTTON_MASK);
		buttonChoix1.setText("wifi");
		
	    buttonChoix1.setLocation(25,80);
	    buttonChoix1.pack();
	 
		
	    final Button buttonChoix2 = new Button(shell, SWT.BUTTON_MASK);
		buttonChoix2.setText("Terrace");
		buttonChoix2.setLocation(200,80);
		buttonChoix2.pack();
		
		
		question1.setLocation(25,50);
		question1.pack();
		
		
		final Button button1 = new Button(shell, SWT.BUTTON4);
	    button1.setText("suivant ->");
	    button1.setLocation(100,100);
	    button1.setSize(new Point(70,30));
	    
		button1.addSelectionListener(new SelectionAdapter() {
			   public void widgetSelected(SelectionEvent arg0) {
			/*if (i < 2) i++;
				   
				   else 
					i=0;*/
				   
					
				   if (j < choix1.size()) {
						
					
					buttonChoix1.setText(choix1.get(j));
			
					buttonChoix2.setText(choix2.get(j));
				
					j++; 
				   }
				   
					
					button1.pack();
					buttonChoix1.pack();
					buttonChoix2.pack();
			   }
			 
			   
		});
	////////////////////////////////////////	
		
		/*Label question2 = new Label(shell, SWT.CENTER);
		
		question2.setText("Qu'est-ce qui a le plus d'importance pour vous ?");
		
		Label choix2_1 = new Label(shell, SWT.CENTER);
		choix2_1.setText("surface");
		choix2_1.setLocation(25,180);
		choix2_1.pack();
		
		Label choix2_2 = new Label(shell, SWT.CENTER);
		choix2_2.setText("metro");
		choix2_2.setLocation(200,180);
		choix2_2.pack();
		
		
		question2.setLocation(25,150);
		question2.pack();
		
		
	////////////////////////
		
		Label question3 = new Label(shell, SWT.CENTER);
		
		question3.setText("Qu'est-ce qui a le plus d'importance pour vous ?");
		
		Label choix3_1 = new Label(shell, SWT.CENTER);
		choix2_1.setLocation(25,210);
		choix2_1.pack();
		
		Label choix3_2 = new Label(shell, SWT.CENTER);
		choix3_2.setText("terrace");
		choix3_2.setLocation(200,210);
		choix3_2.pack();
		
		
		question3.setLocation(25,250);
		question3.pack();

*/
		shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep(); 
			}
		display.dispose();
		title.dispose();
		}
	
	//public static void AskAvis() {
		
	}
	


