package www.icebd.com.suzukibangladesh.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class NewsEvents extends Fragment implements AsyncResponse {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    ArrayList<HashMap<String, String>> arrList;

    public static NewsEvents newInstance() {
        NewsEvents fragment = new NewsEvents();
        return fragment;
    }

    public NewsEvents() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_events, container,
                false);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();


        if(isNetworkAvailable())
        {
            Log.i("Test", "Network is available ");
            HashMap<String, String> postData = new HashMap<String, String>();
            String auth_key = pref.getString("auth_key","");
            Log.i("Test","Auth Key from shared preference "+auth_key);

            if ((auth_key!=""))
                postData.put("auth_key",auth_key);




            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/newsList");

        }
        else {
            Toast.makeText(getActivity(),"Please connect to the Internet",Toast.LENGTH_LONG).show();
        }



        return rootView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void processFinish(String output) {

        Log.i("Test",output);

        try {
            JSONObject object = new JSONObject(output);
            String status_code = object.getString("status_code");
            String message = object.getString("message");
            String auth_key = object.getString("auth_key");

            if(status_code.equals("200"))
            {
                JSONArray news = object.getJSONArray("news");

                for (int i = 0; i <news.length() ; i++) {

                    JSONObject newsDetails = news.getJSONObject(i);
                    HashMap<String, String> map = new HashMap();
                    map.put("news_event_id", newsDetails.getString("news_event_id"));
                    map.put("type", newsDetails.getString("type"));
                    map.put("news_event_title", newsDetails.getString("news_event_title"));
                    map.put("news_event_desc", newsDetails.getString("news_event_desc"));
                    map.put("news_event_img_url", newsDetails.getString("news_event_img_url"));
                    map.put("start_date", newsDetails.getString("start_date"));
                    map.put("end_date", newsDetails.getString("end_date"));

                    arrList.add(map);


                }




            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
