package www.icebd.com.suzukibangladesh.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


import android.util.Log;

import www.icebd.com.suzukibangladesh.bikelist.BikeList;
import www.icebd.com.suzukibangladesh.spare_parts.SparePartsListObject;

public class JsonParser 
{
	//JSONParser jsonParser = new JSONParser();
	JSONObject jsonResponse = null;
	StringBuilder builder = null;
	InputStream is = null;
	JSONArray jarray = null;
	String jsonData = "";
	BufferedReader reader=null;
	
	private ArrayList<NameValuePair> jsonArrayList;



	public ArrayList<BikeList> parseAPIgetBikeListInfo(InputStream json) throws Exception
	{
		ArrayList<BikeList> arrBikeList = new ArrayList<BikeList>();
		ArrayList<BikeList.BikeItem> arrBikeItem = new ArrayList<BikeList.BikeItem>();
		String oneObjectsItem_status = null;

		String bike_code;
		String bike_id;
		String bike_name;
		String bike_cc;
		String bike_mileage;
		String thumble_img;

		try
		{
			reader = new BufferedReader(new InputStreamReader(json, "iso-8859-1"), 8);
			builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				System.out.println("read line : "+line);
				builder.append(line + "\n");
			}
			json.close();

			jsonData = builder.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		try
		{
			jsonResponse = new JSONObject(jsonData);
			BikeList obj_bikeList = new BikeList();
			jarray = jsonResponse.getJSONArray("bikeList");

			boolean status = jsonResponse.getBoolean("status");
			obj_bikeList.setStatus(status);
			String message = jsonResponse.getString("message");
			obj_bikeList.setMessage(message);
			int status_code = jsonResponse.getInt("status_code");
			obj_bikeList.setStatus_code(status_code);

			for (int i=0; i < jarray.length(); i++)
			{

				jsonResponse = jarray.getJSONObject(i);
				BikeList.BikeItem obj_bikeItem = obj_bikeList.new BikeItem();

				bike_code = jsonResponse.getString("bike_code");
				obj_bikeItem.setBike_code(bike_code);
				bike_id = jsonResponse.getString("bike_id");
				obj_bikeItem.setBike_id(bike_id);
				bike_name = jsonResponse.getString("bike_name");
				obj_bikeItem.setBike_name(bike_name);
				bike_cc = jsonResponse.getString("bike_cc");
				obj_bikeItem.setBike_cc(bike_cc);
				bike_mileage = jsonResponse.getString("bike_mileage");
				obj_bikeItem.setBike_mileage(bike_mileage);
				thumble_img = jsonResponse.getString("thumble_img");
				obj_bikeItem.setThumble_img(thumble_img);

				arrBikeItem.add(obj_bikeItem);
			}
			obj_bikeList.setBikeItemsList(arrBikeItem);// set bike items to array list
			arrBikeList.add(obj_bikeList);
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		// return JSON String
		return arrBikeList;
	}
	public ArrayList<SparePartsListObject> parseAPIgetSparePartsListInfo(InputStream json) throws Exception
	{
		ArrayList<SparePartsListObject> arrSparePartsList = new ArrayList<SparePartsListObject>();
		ArrayList<SparePartsListObject.SparePartsItem> arrSparePartsItem = new ArrayList<SparePartsListObject.SparePartsItem>();
		String oneObjectsItem_status = null;

		String spare_parts_id;
		String spare_parts_name;
		String spare_parts_price;
		String spare_parts_code;
		String parts_type;
		String bike_name;
		String thumble_img;

		try
		{
			reader = new BufferedReader(new InputStreamReader(json, "iso-8859-1"), 8);
			builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				System.out.println("read line : "+line);
				builder.append(line + "\n");
			}
			json.close();

			jsonData = builder.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		try
		{
			jsonResponse = new JSONObject(jsonData);
			SparePartsListObject obj_sparePartsList = new SparePartsListObject();
			jarray = jsonResponse.getJSONArray("spare");

			boolean status = jsonResponse.getBoolean("status");
			obj_sparePartsList.setStatus(status);
			String message = jsonResponse.getString("message");
			obj_sparePartsList.setMessage(message);
			int status_code = jsonResponse.getInt("status_code");
			obj_sparePartsList.setStatus_code(status_code);

			for (int i=0; i < jarray.length(); i++)
			{

				jsonResponse = jarray.getJSONObject(i);
				SparePartsListObject.SparePartsItem obj_sparePartsItem = obj_sparePartsList.new SparePartsItem();

				spare_parts_id = jsonResponse.getString("spare_parts_id");
				obj_sparePartsItem.setSpare_parts_id(spare_parts_id);
				spare_parts_name = jsonResponse.getString("spare_parts_name");
				obj_sparePartsItem.setSpare_parts_name(spare_parts_name);

				spare_parts_price = jsonResponse.getString("spare_parts_price");
				obj_sparePartsItem.setSpare_parts_price(spare_parts_price);
				spare_parts_code = jsonResponse.getString("spare_parts_code");
				obj_sparePartsItem.setSpare_parts_code(spare_parts_code);
				parts_type = jsonResponse.getString("parts_type");
				obj_sparePartsItem.setParts_type(parts_type);
				bike_name = jsonResponse.getString("bike_name");
				obj_sparePartsItem.setBike_name(bike_name);
				thumble_img = jsonResponse.getString("large_image_name");
				obj_sparePartsItem.setThumble_img(thumble_img);

				arrSparePartsItem.add(obj_sparePartsItem);
			}
			obj_sparePartsList.setSparePartsItemsList(arrSparePartsItem);// set spare parts items to array list
			arrSparePartsList.add(obj_sparePartsList);
		}
		catch(JSONException e)
		{
			e.printStackTrace();
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		// return JSON String
		return arrSparePartsList;
	}
	

}
