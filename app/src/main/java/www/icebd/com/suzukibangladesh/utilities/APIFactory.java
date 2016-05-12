package www.icebd.com.suzukibangladesh.utilities;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class APIFactory {

	static ArrayList<NameValuePair> nameValuePairs;

	public ArrayList<NameValuePair> getAuthKeyInfo(String unique_device_id,String notification_key,String platform)
	{
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("unique_device_id", unique_device_id));
		nameValuePairs.add(new BasicNameValuePair("notification_key", notification_key));
		nameValuePairs.add(new BasicNameValuePair("platform", platform));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> getBikeListInfo(String auth_key)
	{
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> getQuizResultInfo(String auth_key,String user_id,String quiz_id, String quiz_answer)
	{

		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));
		nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
		nameValuePairs.add(new BasicNameValuePair("quiz_id", quiz_id));
		nameValuePairs.add(new BasicNameValuePair("quiz_answer", quiz_answer));


		return nameValuePairs;
	}

	public ArrayList<NameValuePair> getSparePartsListInfo(String auth_key)
	{
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));

		return nameValuePairs;
	}
	public ArrayList<NameValuePair> getMediaInfo(String auth_key)
	{
		nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("auth_key", auth_key));

		return nameValuePairs;
	}







}//end of main class