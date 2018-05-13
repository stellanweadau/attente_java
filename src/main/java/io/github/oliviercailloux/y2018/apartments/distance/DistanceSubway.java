package io.github.oliviercailloux.y2018.apartments.distance;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

/**
 * Inspiration from https://www.programcreek.com/java-api-examples/index.php?api=com.google.maps.model.DistanceMatrix
 *
 */
public class DistanceSubway {
	
	private String url;
	private String api_key;
	private String startPoint;
	private String endPoint;
	
	public DistanceSubway(String api_key, String startPoint, String endPoint, String mode){
		url = "https://maps.googleapis.com/maps/api/distancematrix/xml?origins=" + startPoint + "&destinations=" + endPoint + "&mode=" + mode + "&language=fr-FR&key=" + api_key + "\\r\\n" ;
		this.api_key = api_key;
		this.endPoint = endPoint;
		this.startPoint = startPoint;
	}
	
	public long calculateDistance() throws ApiException, InterruptedException, IOException {
		
		GeoApiContext dist = new GeoApiContext.Builder()
				.apiKey(api_key)
				.build();
		
		DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(dist);
		
		DistanceMatrix result = request.origins(startPoint)
				.destinations(endPoint)
				.mode(TravelMode.TRANSIT)
				.language("fr-FR")
				.await();
		
		return result.rows[0].elements[0].distance.inMeters;
	}
	
	
	public static void main(String args[]) throws ApiException, InterruptedException, IOException {
		
		String api_key = "AIzaSyDuFlzxo-Sbee0E6eMLnfTvxcADSKQzaNs";
		
		DistanceSubway dist = new DistanceSubway(api_key,"Paris","Ville d'Avray","transit");
		
		System.out.println(dist.calculateDistance());
		System.out.println(dist.url);
	}
	

	
	
	
	
}