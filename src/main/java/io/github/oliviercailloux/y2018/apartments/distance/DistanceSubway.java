package io.github.oliviercailloux.y2018.apartments.distance;


public class DistanceSubway {
	
	static private String api_key = "AIzaSyChfj0_E4TO9Nv7iMUJEygpoZU3qgYRoDQ";
	
	static private String url = "https://maps.googleapis.com/maps/api/distancematrix/xml?";
	static private String startPoint = "Vancouver";
	static private String endPoint = "Seattle";
	
	static private String mode ="bicycling";
	
	
	static void main(String args[]) {
		url = url + "origins=" + startPoint + "&destinations=" + endPoint + "&mode=" + mode + "&language=fr-FR&key=" + api_key + "\\r\\n" ;
	}
	

	
	
	
	
}
