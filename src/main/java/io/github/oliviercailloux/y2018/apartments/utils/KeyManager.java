package io.github.oliviercailloux.y2018.apartments.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class KeyManager {
	
	public static String getApiKey() {
		String apiKey="";
		String fichier = new File("API_KEY.txt").getAbsolutePath();
		
		try(FileReader fr = new FileReader (fichier)){
			int c = fr.read();
			

			while (c != -1)
			{
				apiKey+=(char) c;
				c = fr.read();
			}
		}
		catch (IOException exception)
		{
		    System.out.println ("Reading error : "+ exception.getMessage());
		}
		
		return apiKey;
	}
	
	public static String getGeocodeApiKey() {
		
		String geocodeApiKey="";
		String fichier = new File("GEOCODE_API_KEY.txt").getAbsolutePath();
		
		try(FileReader fr = new FileReader (fichier)){
			int c = fr.read();
			

			while (c != -1)
			{
				geocodeApiKey+=(char) c;
				c = fr.read();
			}
		}
		catch (IOException exception)
		{
		    System.out.println ("Reading error : "+ exception.getMessage());
		}
		
		return geocodeApiKey;
	}

}
