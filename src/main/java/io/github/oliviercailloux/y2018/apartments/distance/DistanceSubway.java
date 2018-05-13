package io.github.oliviercailloux.y2018.apartments.distance;

public class DistanceSubway {
	
	private String url;
	
	public DistanceSubway(String api_key, String startPoint, String endPoint, String mode){
		url = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + startPoint + "&destinations=" + endPoint + "&mode=" + mode + "&language=fr-FR&key=" + api_key + "\\r\\n" ;
	}
	
	
	public static void main(String args[]) {
		
		String api_key = "AIzaSyChfj0_E4TO9Nv7iMUJEygpoZU3qgYRoDQ";
		
		DistanceSubway dist = new DistanceSubway(api_key,"Vancouver","Seattle","bicycling");
		
		System.out.println(dist.url);
	}
	

	
	
	
	
}