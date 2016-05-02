package www.icebd.com.suzukibangladesh.reg;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.menu.HomeFragment;
import www.icebd.com.suzukibangladesh.utilities.ConnectionManager;


public class ChangePassword extends Fragment implements View.OnClickListener, AsyncResponse {


    EditText oldPassword,newPassword;

    Button button;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;


    public static ChangePassword newInstance() {
        ChangePassword fragment = new ChangePassword();
        return fragment;
    }

    public ChangePassword() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_password, container,
                false);


        oldPassword = (EditText) rootView.findViewById(R.id.old_password);
        newPassword = (EditText) rootView.findViewById(R.id.new_password);
        button=(Button) rootView.findViewById(R.id.button);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();



        button.setOnClickListener(this);
        return rootView;
    }



    @Override
    public void onClick(View v) {
        HashMap<String, String> postData = new HashMap<String, String>();
        String auth_key = pref.getString("auth_key",null);
        String user_id = pref.getString("user_id",null);
        if (auth_key==null)
        {
            Toast.makeText(getActivity(),"Please Connect to the Internet and Restart the app",Toast.LENGTH_LONG).show();


        }
        else {
            postData.put("auth_key",auth_key);
            postData.put("old_password",oldPassword.getText().toString());
            postData.put("new_password",newPassword.getText().toString());
            postData.put("user_id",user_id);

            if(isNetworkAvailable()) {
                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
                loginTask.execute(ConnectionManager.SERVER_URL+"changePassword");


            }

        }

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
        JSONObject object = null;
        try {
            object = new JSONObject(output);
            String status_code = object.getString("status_code");
            String message = object.getString("message");
            FragmentManager fragmentManager = getFragmentManager();



            if (status_code.equals("200"))
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

                // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commit();

            }
            else {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
