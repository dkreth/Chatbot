//test change
import java.io.IOException;

import java.util.HashMap;

import org.jibble.pircbot.*;

public class MyBot extends PircBot {
	
	public MyBot() {
		this.setName("dkBot");
		sendMessage("#dkChatBot", "Hi, I'm dkBot! I can give you weather information about a certain area, or nutritional information about a certain food. Try typing 'help' for more info!");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(message.equalsIgnoreCase("help")) {
			sendMessage(channel, "@"+sender+"Type 'weather ' followed by a 5-digit zip code to find out the current temperature in that area (eg. try typing:weather 78050). Type 'nutrition ' followed by a 5-digit Nutrional Database Number to find out some nutritional info about that food (eg. try typing:nutrition 01009)");
		}

		if(message.equalsIgnoreCase("time")) {
			String time = new java.util.Date().toString();
			sendMessage(channel, sender + ": The time is now " + time);
		}
		
		if(message.toLowerCase().startsWith("weather ")) {
			try {
				String zip = message.substring(8, 13);
				try {
					HashMap<String, String> weatherMap = WeatherFetcher.startWebRequest(zip);
					String hiTemp = weatherMap.get("hiTemp").toString();
					String loTemp = weatherMap.get("loTemp").toString();
					String currentTemp = weatherMap.get("currentTemp").toString();
//					String pressure = weatherMap.get("pressure").toString();
//					String humidity = weatherMap.get("humidity").toString();
					String cityName = weatherMap.get("cityName").toString();
					String weatherConditions= weatherMap.get("weatherConditions").toString();
	
					sendMessage(channel, "@" + sender + "The weather today in " + cityName + " is " + weatherConditions + ". Currently the temperature is " + currentTemp + " with an upper bound of " + hiTemp + " and a lower bound of " + loTemp + ".");
				} 
				catch (IOException e) {
					sendMessage(channel, "@" + sender + " '" + zip + "' is not a valid zip code.");
	
					
				}
			}
			catch (StringIndexOutOfBoundsException e) {
				sendMessage(channel, "@" + sender + " Please enter a zip code.");
			}
			
		}
		

		
		if(message.toLowerCase().startsWith("nutrition ")) {
			try {
				String ndbno = message.substring(10, 15);
				try {
					HashMap<String,String> nutritionMap = NutritionFetcher.startWebRequest(ndbno);
					String name = nutritionMap.get("name");
			        String kcalCount = nutritionMap.get("kcalCount");
	//		        String kcalUnits = nutritionMap.get("unit").toString();
					String proteinCount = nutritionMap.get("proteinCount").toString();
					String proteinUnits = nutritionMap.get("proteinUnits").toString();
			        String totalFatCount = nutritionMap.get("totalFatCount").toString();
			        String totalFatUnits = nutritionMap.get("totalFatUnits").toString();
			        String fiberCount = nutritionMap.get("fiberCount").toString();
			        String fiberUnits = nutritionMap.get("fiberUnits").toString();
			        String sugarCount = nutritionMap.get("sugarCount").toString();
			        String sugarUnits = nutritionMap.get("sugarUnits").toString();
					sendMessage(channel,"@" + sender + " 100g of " + name + " has: " + kcalCount + " Calories, " + proteinCount + proteinUnits + " of protein, " + totalFatCount + totalFatUnits + " of total fat, " + fiberCount + fiberUnits + " of fiber, and " + sugarCount + sugarUnits + " of sugar.");
				} catch (IOException e) {
					sendMessage(channel, "@" + sender + " '" + ndbno + "' is not a valid Nutritional Database Number.");
				}
			}
			catch (StringIndexOutOfBoundsException e) {
				sendMessage(channel, "@" + sender + " Please enter a valid Nutritional Database Number.");

				
			}


			
		}		
		

	}

}
