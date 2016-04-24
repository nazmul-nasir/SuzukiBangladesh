package www.icebd.com.suzukibangladesh.reg;

/**
 * Created by Nasir on 11/19/2015.
 */
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.menu.HomeFragment;
import www.icebd.com.suzukibangladesh.menu.MyBikeFragment;

/**
 * Created by Nasir on 11/11/2015.
 */
public class Signup extends Fragment implements View.OnClickListener, AsyncResponse {

    EditText name,address,mobile_no,email,password,thana;
    String blood_group;
    Button button;


    public static Signup newInstance() {
        Signup fragment = new Signup();
        return fragment;
    }

    public Signup() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signup, container,
                false);

        name = (EditText) rootView.findViewById(R.id.name);
        address = (EditText) rootView.findViewById(R.id.address);
        mobile_no = (EditText) rootView.findViewById(R.id.mobile_no);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password);
        thana = (EditText)rootView.findViewById(R.id.thana);
        button=(Button) rootView.findViewById(R.id.button);


        button.setOnClickListener(this);

       // button.performClick();


        return rootView;
       // return  null;
    }



    @Override
    public void onClick(View v) {

        if(isNetworkAvailable())
        {
            Log.i("Test", "Network is available ");
            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("auth_key","46dde59d2bf7149c4d070f8cba8314e0");
            postData.put("app_user_name", name.getText().toString());
            postData.put("app_user_email", email.getText().toString());
            postData.put("app_user_address", address.getText().toString());
            postData.put("app_user_phone", mobile_no.getText().toString());
            postData.put("app_user_password", password.getText().toString() );
            postData.put("app_user_thana",  thana.getText().toString() );

           /* postData.put("app_user_name", "Nasir");
            postData.put("app_user_email", "emaple@email.com");
            postData.put("app_user_address", "dhanmondi");
            postData.put("app_user_phone", "015888888");
            postData.put("app_user_password", "asdf485" );
            postData.put("app_user_thana",  "Dhanmondi" );*/

           // Context context=getContext();

            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/registerUser");

        }
        else {
            Toast.makeText(getActivity(),"Please connect to the Internet",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void processFinish(String result) {

        Log.i("Test","result: "+result);

        try {
            JSONObject object = new JSONObject(result);
            String status_code = object.getString("status_code");
            String message = object.getString("message");
            String auth_key = object.getString("auth_key");
            String user_id = object.getString("user_id");

            FragmentManager fragmentManager = getFragmentManager();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user_id",user_id);
            editor.apply();

            if (status_code.equals("200"))
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();

               // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               /* fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commit();*/
                Intent intent = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
