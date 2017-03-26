import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherFetcher 
{
	
	static HashMap<String, String> startWebRequest(String zip) throws IOException
	{
		String weatherURL = "http://api.openweathermap.org/data/2.5/weather?zip=" + zip + ",us&APPID=dba6df47f30e04f87cb5fe282886f78c"; 			//please get your own token to use from API.Openweathermap


		
		  StringBuilder result = new StringBuilder(); //this is going to hold the JSON Response from the server
	      URL url = new URL(weatherURL);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      System.out.println(result.toString()); 
	     
//	     double longitude =  parseJson(result.toString());
//	     
//	     System.out.println("Longitude is "+ longitude);

	      HashMap<String, String> weatherMap = parseJson(result.toString());
	      
//	     return result.toString();
	     return weatherMap;
	   }
	
	static HashMap<String, String> parseJson(String json) //json is a string of json, we get this from making our request
	{
		JsonElement jelement = new JsonParser().parse(json); //you will parse it first into a JSONElement
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();  //You will then take that jelement, and then break it down into a json object. Use the JSONEDITORONLINE website, basically, you are trying narrow down to whatever you want
        
        HashMap<String, String> hmap = new HashMap<String, String>();        

        JsonObject mainObject = MasterWeatherObject.getAsJsonObject("main");
//        JsonObject weatherObject = MasterWeatherObject.getAsJsonObject("weather");
        JsonArray weatherArray = MasterWeatherObject.getAsJsonArray("weather");
        JsonObject weatherObject = weatherArray.get(0).getAsJsonObject();

//        JsonArray weatherObject = (JsonArray) MasterWeatherObject.getAsJsonObject("weather");

        String weatherConditions = weatherObject.get("description").getAsString();
        
        int hiTemp = (int) toFar(mainObject.get("temp_max").getAsDouble());
        int loTemp = (int) toFar(mainObject.get("temp_min").getAsDouble());
        int currentTemp = (int) toFar(mainObject.get("temp").getAsDouble());
        String pressure = mainObject.get("pressure").getAsString();
        String humidity = mainObject.get("humidity").getAsString();
        String cityName = MasterWeatherObject.get("name").getAsString();

        hmap.put("hiTemp", ""+hiTemp);
        hmap.put("loTemp", ""+loTemp);
        hmap.put("currentTemp", ""+currentTemp);
        hmap.put("pressure", pressure);
        hmap.put("humidity", humidity);
        hmap.put("cityName", cityName);
        hmap.put("weatherConditions", weatherConditions);
        
        return hmap;
        
        
        
        //return  toFar(hiTemp);
        
        
//        JsonObject  coordinateObject = MasterWeatherObject.getAsJsonObject("coord"); //we will get the coordinate object 
//        double  longitude = coordinateObject.get(lon).getAsDouble(); //now we will narrow down to get the value of the longitude
//        return longitude;  //return our longitude
        
        
		
	}
	
	static double toFar(double kelvTemp){
		double farTemp = 1.8*(kelvTemp - 273.0) + 32;
		return farTemp;
	}
		
	}