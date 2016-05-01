package www.icebd.com.suzukibangladesh.utilities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class APIFactory {

	static ArrayList<NameValuePair> nameValuePairs;

	public ArrayList<NameValuePair> getBikeListInfo(String auth_key)
	{
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));

		return nameValuePairs;
	}

	public ArrayList<NameValuePair> getSparePartsListInfo(String auth_key)
	{
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));

		return nameValuePairs;
	}







}//end of main class