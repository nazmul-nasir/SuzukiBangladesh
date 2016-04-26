package www.icebd.com.suzukibangladesh.reg;

/**
 * Created by Nasir on 11/19/2015.
 */
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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


public class ResetPassword extends Fragment implements View.OnClickListener, AsyncResponse {

    EditText email;
    Button btnReset;
    FragmentActivity activity;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    public static ResetPassword newInstance() {
        ResetPassword fragment = new ResetPassword();
        return fragment;
    }

    public ResetPassword() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reset_password, container,
                false);

        email=(EditText) rootView.findViewById(R.id.reset_email);
        btnReset = (Button) rootView.findViewById(R.id.button);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();

        btnReset.setOnClickListener(this);



        return rootView;
    }



    @Override
    public void onClick(View v) {

        if(isNetworkAvailable()) {
            HashMap<String, String> postData = new HashMap<String, String>();
            String auth_key = pref.getString("auth_key",null);
            String user_id = pref.getString("user_id",null);
            if (auth_key==null)
            {
                Toast.makeText(getActivity(),"Please Connect to the Internet and Restart the app",Toast.LENGTH_LONG).show();


            }
            else{
                postData.put("auth_key",auth_key);
                postData.put("user_email",email.getText().toString());
                postData.put("user_id", user_id);
                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(ResetPassword.this,postData);
                loginTask.execute( "http://icebd.com/suzuki/suzukiApi/Server/forgetPassword");

            }


        }
    }

    @Override
    public void processFinish(String output) {

        Log.i("Test","After forgot password"+ output);

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
                        .replace(R.id.container, AfterResetPassword.newInstance())
                        .commit();

            }
            else {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
