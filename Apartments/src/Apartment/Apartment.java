package Apartment;

public class Apartment implements InterfaceApartment {

  protected double floorArea; // Floor area of the Apartment in square meters
							
  protected String address; // Full address (number, street name, ZIP code, city, country) location of the apartment
						   
  protected int nbChambers; // Number of chambers
							
  protected int nbSleeping; // Accommodation capacity (number of sleeping available in the Apartment)
					
  protected int nbBR; // Number of bathroom
					 
  protected boolean terrace; // Does the Apartment has a terrace ?
						
  protected double floorAreaTerrace; // Floor area of the terrace if it exists
								
  protected String description; // Description of the announcement
								
  protected String title; // Title of the announcement
							
  protected boolean wifi; // Does the Apartment have WiFi ?
						 
  protected double priceN; // Price per night
						  
  protected int nbMinNight; // Number of minimum night to rent
							
  protected boolean tele; // Does the Apartment have a TV ?
						  
  
                                                     
	/**
	* This constructor without parameters gives default values to each characteristics of the Apartment class
	*/
                                                     
public Apartment() {
  floorArea = 0;
  address = "";
  nbChambers = 0;
  nbSleeping = 0 ;
  nbBR = 0;
  terrace = false ;
  floorAreaTerrace = 0 ;
  description = "";
  title = "";
  wifi = false ;
  priceN = 0;
  nbMinNight = 0 ;
  tele = false ;
}
                                                   
  /**
  *
  * @param floorArea is a real number superior or equal to zero with two decimals, it represents the floor area of the Apartment in square meters
  
  * @param address is a string of characters that gives the full location of the Apartment
  
  * @param nbChambers is an integer superior or equal to zero, it is the number of chamber available of use in the Apartment 
  
  * @param nbSleepings an integer superior or equal to zero corresponding of the accommodation capacity of the Apartment (nb of people that can sleep in the Apartment)
  
  * @param nbBR an integer superior or equal to zero which correspond to the number of bathroom
  
  * @param terrace a boolean (true/false) which indicates if there's a terrace or not 
  
  * @param floorAreaTerrace a real number superior or equal to zero with two decimals, it represents the floor area of the terrace of the Apartment if there's any
 
  * @param description a string of characters that describe the Apartment and the offer (its accommodations)
  
  * @param title a string of characters that represents the title of the announcement
  
  * @param wifi a boolean (true/false) which indicates which indicates if there's a WiFi or not
  
  * @param priceN a real number superior or equal to zero with two decimals, how much it cost (before any fees) to stay over night in euros
  
  * @param nbMinNight an integer superior or equal to zero, indicate how long in nights the customer will stays
  
  * @param tele a boolean (true/false) which indicates which indicates if there's a television or not
  
  */
                                                    
public Apartment (double floorArea, String adress, int nbChambers, int nbSleeping, int nbBR, boolean terrace, double floorAreaTerrace, String description, String title, boolean wifi, double priceN, int nbMinNight, boolean tele) {
  this.floorArea = floorArea;
  this.address = adress;
  this.nbChambers = nbChambers;
  this.nbSleeping = nbSleeping;
  this.nbBR = nbBR;
  this.terrace = terrace;
  this.floorAreaTerrace = floorAreaTerrace;
  this.description = description;
  this.title = title;
  this.wifi = wifi;
  this.priceN = priceN;
  this.nbMinNight = nbMinNight;
  this.tele = tele;
  
  if (priceN < 0)
   throw new IllegalArgumentException ("The price per night cannot be negattive");
  if (terrace = false & floorAreaTerrace > 0)
   throw new IllegalArgumentException ("The terrace cannot have a floor area if it doesn't exists");
  if (nbBR < 0)
   throw new IllegalArgumentException ("The number of bathroom cannot be negative");
  if (nbSleeping < 0)
  throw new IllegalArgumentException ("The accomodation capcity cannot be negative");
  if (nbChambers < 0)
   throw new IllegalArgumentException ("The number of chambers cannot be negative");
  if (floorArea < 0)
   throw new IllegalArgumentException ("The floor area cannot be negative");
  if (nbMinNight < 0)
   throw new IllegalArgumentException ("The number of minimum night staying cannot be negative");
}

   @Override                                                 
public double getFloorArea() {
  return floorArea;
}
   
   @Override                                                 
public String getAdress() {
  return address;
}
        
   @Override
public int getNbChambers() {
  return nbChambers;
}
   
   @Override
public int getNbSleeping() {
  return nbSleeping;
}
                         
   @Override
public int getNbBR() {
  return nbBR;
}
     
   @Override
public boolean getTerrace() {
  return terrace;
}
    
   @Override
public double getFloorAreaTerrace() {
  return floorAreaTerrace;
}
      
   @Override
 public String getDescription() {
  return description;
}
       
   @Override
public String getTitle() {
  return title;
}
     
   @Override
public boolean getWifi() {
  return wifi;
}
    
   @Override
public double getPriceN() {
  return priceN;
}
       
   @Override
public int getNbMinNight() {
  return nbMinNight;
}
     
   @Override
public boolean getTele() {
  return tele;
}
                                                    
}