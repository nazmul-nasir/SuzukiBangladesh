package www.icebd.com.suzukibangladesh.notification;

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
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.menu.NewsEventsListAdapter;


public class Notification extends Fragment implements AsyncResponse {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    ArrayList<HashMap<String, String>> arrList;
    ListView list;
    ImageLoader imageLoader;
    private NotificationListAdapter notificationListAdapter = null;

    public static Notification newInstance() {
        Notification fragment = new Notification();
        return fragment;
    }

    public Notification() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container,
                false);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();

        list = (ListView) rootView.findViewById(R.id.notif_list);
        imageLoader = ImageLoader.getInstance();


        if(isNetworkAvailable())
        {
            Log.i("Test", "Network is available ");
            HashMap<String, String> postData = new HashMap<String, String>();
            String auth_key = pref.getString("auth_key","");
            Log.i("Test","Auth Key from shared preference "+auth_key);

            if ((auth_key!=""))
                postData.put("auth_key",auth_key);
           // postData.put("platform","1");




            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/getAllnotification");

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

        this.arrList = new ArrayList();

        try {
            JSONObject object = new JSONObject(output);
            String status_code = object.getString("status_code");
            String message = object.getString("message");
            String auth_key = object.getString("auth_key");
            if(status_code.equals("200"))
            {
                JSONArray notification = object.getJSONArray("notification");

                for (int i = 0; i <notification.length() ; i++) {

                    Log.i("Test","Inside news loop");

                    JSONObject newsDetails = notification.getJSONObject(i);
                    HashMap<String, String> map = new HashMap();
                    map.put("notification_id", newsDetails.getString("notification_id"));
                    map.put("notification_title", newsDetails.getString("notification_title"));
                    map.put("notification_message", newsDetails.getString("notification_message"));
                    map.put("notification_pic", newsDetails.getString("notification_pic"));
                    map.put("notification_date", newsDetails.getString("notification_date"));
                    map.put("notification_type", newsDetails.getString("notification_type"));

                    arrList.add(map);

                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Test","Arraylist: "+arrList.toString());
        if (!arrList.isEmpty())
        {

            /*ListAdapter adapter = new SimpleAdapter( getActivity(), arrList,
                    R.layout.list_item_news, new String[] { "news_event_title", "news_event_desc","news_event_img_url"},
                    new int[] { R.id.txt_title, R.id.description, R.id.image_list });*/

            notificationListAdapter = new NotificationListAdapter(getActivity(), arrList);
            list.setAdapter(notificationListAdapter);

        }


    }
}
