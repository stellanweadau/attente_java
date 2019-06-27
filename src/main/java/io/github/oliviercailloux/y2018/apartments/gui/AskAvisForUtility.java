package io.github.oliviercailloux.y2018.apartments.gui;


import org.eclipse.swt.SWT;
import org.eclipse.swt.SWT.*;
import org.eclipse.swt.widgets.*;


/**
 * @author SALAME & SAKHO
 * 
 */

/**
 * this class displays a list of apartments sorted according to the user's
 * utilities
 */

public class AskAvisForUtility {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Label label = new Label(shell, SWT.CENTER);
		label.setText("Bonjour!");
		label.pack();
		shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()); 
			display.sleep();
			display.dispose();
			label.dispose();
		}
	}
	
	
	
	
	

	
	
	

	public static void AskAvis() {
		
	}
	

	
}
