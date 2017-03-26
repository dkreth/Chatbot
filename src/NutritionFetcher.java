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

public class NutritionFetcher 
{	
	static HashMap<String,String> startWebRequest(String ndbno) throws IOException
	{
		String nutritionURL = "https://api.nal.usda.gov/ndb/reports/?ndbno=" + ndbno + "&format=json&api_key=tYosEMFrhX8HI0cvZWUOnudAvElcihj2zUxV4DB6";
		
		  StringBuilder result = new StringBuilder(); //this is going to hold the JSON Response from the server
	      URL url = new URL(nutritionURL);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      System.out.println(result.toString()); 

	      HashMap<String,String> nutritionMap = parseJson(result.toString());

	      return nutritionMap;
	   }
	
	static HashMap<String,String> parseJson(String json) //json is a string of json, we get this from making our request
	{
		JsonElement jelement = new JsonParser().parse(json); //you will parse it first into a JSONElement
        JsonObject  MasterNutritionObject = jelement.getAsJsonObject();  //You will then take that jelement, and then break it down into a json object. Use the JSONEDITORONLINE website, basically, you are trying narrow down to whatever you want
        
        JsonObject reportObject = MasterNutritionObject.getAsJsonObject("report");
        
        HashMap<String,String> hmap = new HashMap<String,String>();
        
        JsonObject foodObject = reportObject.getAsJsonObject("food");
        String name = foodObject.get("name").getAsString();
        
        JsonArray nutrientsArray = foodObject.getAsJsonArray("nutrients");
        
        JsonObject kcalObject = nutrientsArray.get(1).getAsJsonObject();
        String kcalCount = kcalObject.get("value").getAsString();
        String kcalUnits = kcalObject.get("unit").getAsString();
        
        JsonObject proteinObject = nutrientsArray.get(2).getAsJsonObject();
        String proteinCount = proteinObject.get("value").getAsString();
        String proteinUnits = proteinObject.get("unit").getAsString();

        JsonObject totalFatObject = nutrientsArray.get(3).getAsJsonObject();
        String totalFatCount = totalFatObject.get("value").getAsString();
        String totalFatUnits = totalFatObject.get("unit").getAsString();

        JsonObject fiberObject = nutrientsArray.get(5).getAsJsonObject();
        String fiberCount = fiberObject.get("value").getAsString();
        String fiberUnits = fiberObject.get("unit").getAsString();
        
        JsonObject sugarObject = nutrientsArray.get(6).getAsJsonObject();
        String sugarCount = sugarObject.get("value").getAsString();
        String sugarUnits = sugarObject.get("unit").getAsString();

        hmap.put("kcalCount", kcalCount);
        hmap.put("kcalUnits", kcalUnits);
        hmap.put("proteinCount", proteinCount);
        hmap.put("proteinUnits", proteinUnits);
        hmap.put("totalFatCount", totalFatCount);
        hmap.put("totalFatUnits", totalFatUnits);
        hmap.put("fiberCount", fiberCount);
        hmap.put("fiberUnits", fiberUnits);
        hmap.put("sugarCount", sugarCount);
        hmap.put("sugarUnits", sugarUnits);
        hmap.put("name", name);


        return hmap;
        

        
        
        
		
	}
	
		
	}

