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

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class NewsEvents extends Fragment implements AsyncResponse {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

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

    }
}
