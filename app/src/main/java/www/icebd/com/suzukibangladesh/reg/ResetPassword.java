package www.icebd.com.suzukibangladesh.reg;

/**
 * Created by Nasir on 11/19/2015.
 */
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class ResetPassword extends Fragment implements View.OnClickListener, AsyncResponse {

    EditText email;
    Button btnReset;
    FragmentActivity activity;

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

        btnReset.setOnClickListener(this);



        return rootView;
    }



    @Override
    public void onClick(View v) {

        if(isNetworkAvailable()) {
            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("auth_key","46dde59d2bf7149c4d070f8cba8314e0");
            postData.put("user_email","nazmul.nasir@icebd.com");
            postData.put("user_id", "409");
            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(ResetPassword.this,postData);
            loginTask.execute( "http://icebd.com/suzuki/suzukiApi/Server/forgetPassword");

        }
    }

    @Override
    public void processFinish(String output) {

        Log.i("Test","After forgot password"+ output);

        try {
            Log.i("Test","Inside Try");
            JSONObject object = new JSONObject(output);
            String message =object.getString("message");
           // Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            Log.i("Test","message : "+ message);

            Log.i("Test","Inside Try");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Test","Outside Try");

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
