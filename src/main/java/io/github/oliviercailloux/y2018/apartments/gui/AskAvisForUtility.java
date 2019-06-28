package io.github.oliviercailloux.y2018.apartments.gui;
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
import org.eclipse.swt.widgets.*;


  

public class AskAvisForUtility {

	static int j=0; 

	
	
/* This function will create and display the window (interface)to ask the 
user questions and to detrmine later the weight of his choices */
	
   public static void main(String[] args)  {
	   
	   Display display = new Display();
	   Shell shell = new Shell(display);
	   

	  	
	   ArrayList<String> attributImportant = new ArrayList<String>();    // this array will stock the user's answers  ( when he presses the button)  
	   ArrayList<String> attributPasImportant= new ArrayList<String>(); // this array will stock the attribut that the user did'nt answer  (the unpressed button) 

	  
	   ArrayList<String> choix1 = new ArrayList<String>(); // this array has the attributs for the first button (buttonchoix1) , we can add how much we want 

		choix1.add("Surface");

		choix1.add("tele");
		
		


		ArrayList<String> choix2 = new ArrayList<String>(); // this array hay attributs for the first button ( buttonchoix2) 

		choix2.add("Terrace");

		choix2.add("prix");
	
		
	
      

      // The question on top of the window 

		Label question = new Label(shell, SWT.CENTER);

		question.setText("Qu'est ce qui a le plus d'importance pour vous ");

		question.setLocation(25,50);

		question.pack();

		shell.setBounds(200, 200, 300, 200);
     
		// The title of the window 

		shell.setText("Votre avis nous intéresse");

		shell.setLayout(new GridLayout());
      
  
		// A Group will collect the buttons with borders 

		Group buttonGroup = new Group(shell, SWT.NONE);
	
		GridLayout gridLayout = new GridLayout();

		gridLayout.numColumns = 1;

		buttonGroup.setLayout(gridLayout);

		buttonGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
      
		// This label will be set on the window everytime the users uses an option 
		
		Label labelAnswer = new Label(shell, SWT.NONE);
		
		labelAnswer.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
       

     // This is a submit button, it will close the shell, and show us content of the 2 arrays 
		final Button terminer = new Button (shell, SWT.PUSH);
		terminer.setText ("Terminé");
      
		terminer.pack();
      
		terminer.addListener(SWT.Selection, new Listener() 
    
		{
          public void handleEvent(Event event) 
          {
        	  shell.close();
        	  System.out.print("Les attribut important sont : ");
        	  for (int j=0; j<attributImportant.size();j=j+1)
              {
       
                  
                 System.out.println( attributImportant.get(j));
              }
        	  
        	  
        	  System.out.print("Les attribut pas important sont : ");
        	  for (int j=0; j<attributPasImportant.size();j=j+1)
              {
       
                  
                 System.out.println( attributPasImportant.get(j));
              }
          }
      
      });
      
// buttonchoix1 and buttonchoix2 are two radio buttons, to let the user chose between two options 

      Button buttonchoix1 = new Button(buttonGroup, SWT.RADIO);

      buttonchoix1.setText("WIFI");
    

      Button buttonchoix2 = new Button(buttonGroup, SWT.RADIO);

      buttonchoix2.setText("foss");

 // it will be notified when buttonchoix1 is selected   
      
      buttonchoix1.addSelectionListener(new SelectionAdapter() {
    	  public void widgetSelected(SelectionEvent e) {
              Button source1=  (Button) e.widget;
            
              if(source1.getSelection() && ! attributImportant.contains(source1.getText())) 
            
              { attributImportant.add(source1.getText());  // to add the pressed button answer to the array attributImportant
                attributPasImportant.add(buttonchoix2.getText());  // to add the unpessed button answer to the array attributPasImportant
              	labelAnswer.setText("tu as choisi " + source1.getText());
    			labelAnswer.pack();
              }
               
              if (j < choix1.size() && j < choix1.size()) {

					buttonchoix1.setText(choix1.get(j));  
					buttonchoix2.setText(choix2.get(j));
					j++;					
															/* this will change the text of the buttons 
															by choosing the content of the arrays attributImportant 
															and attributPasImportant */
					}
              
              buttonchoix1.setSelection(false); /* to not be slected by default in the 
              										question */	
              buttonchoix1.pack();
              buttonchoix2.pack();
          }
           
      });

      
      // Same with buttonchoix1 above 
      buttonchoix2.addSelectionListener(new SelectionAdapter() {
    	 
    	  public void widgetSelected(SelectionEvent e) {
              
    		  Button source2=  (Button) e.widget;
            
              if(source2.getSelection() && ! attributImportant.contains(source2.getText())) {
            	  attributImportant.add(source2.getText()); 
            	  attributPasImportant.add(buttonchoix1.getText()); 
              	  labelAnswer.setText("tu as choisi " + source2.getText());
              	  labelAnswer.pack();
    	
              }
            
              if (j < choix1.size()) {

					buttonchoix1.setText(choix1.get(j));
					buttonchoix2.setText(choix2.get(j));

						j++; 
            }
              buttonchoix2.setSelection(false);



					buttonchoix1.pack();

					buttonchoix2.pack();

          }
           
      });

      // open the window  
      shell.open();

  

      while (!shell.isDisposed()) {

         if (!display.readAndDispatch()) {

            display.sleep();

         }

      }
      
    
    
      display.dispose();

   }
   


}