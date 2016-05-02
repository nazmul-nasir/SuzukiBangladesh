package www.icebd.com.suzukibangladesh.request;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.menu.HomeFragment;
import www.icebd.com.suzukibangladesh.utilities.ConnectionManager;


public class Quotation extends Fragment implements AsyncResponse, View.OnClickListener {

    Button submit_btn_quation;
    TextView name,email,cell,address,miles,cc;
    EditText say_Something;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    public static Quotation newInstance() {
        Quotation fragment = new Quotation();
        return fragment;
    }

    public Quotation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quotation, container,
                false);
        say_Something = (EditText) rootView.findViewById(R.id.et_say_something_quotation);

        submit_btn_quation = (Button) rootView.findViewById(R.id.quotation_btn_submit);

        name = (TextView) rootView.findViewById(R.id.txt_name_quo);
        email = (TextView) rootView.findViewById(R.id.txt_email_quo);
        address = (TextView) rootView.findViewById(R.id.txt_address_quo);
        cell = (TextView) rootView.findViewById(R.id.txt_cell_no_quo);
        miles = (TextView) rootView.findViewById(R.id.txt_miles_quo);
        cc = (TextView) rootView.findViewById(R.id.txt_cc_quo);


        submit_btn_quation.setOnClickListener(this);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();


        name.setText(pref.getString("name","Name not found"));
        email.setText(pref.getString("email","Email not found"));
        address.setText(pref.getString("address","Address not found"));
        cell.setText(pref.getString("mobile_no","Cell not found"));
       // name.setText(pref.getString("name","Name not found"));


        return rootView;
    }

    @Override
    public void processFinish(String output) {

        Log.i("Test",output);

        try {
            JSONObject object = new JSONObject(output);
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

    @Override
    public void onClick(View v) {
        HashMap<String, String> postData = new HashMap<String, String>();

        Log.i("Test","Name : "+pref.getString("name","not found"));

        postData.put("auth_key","3c227bbba98cd9360006d095558d09a9");
        postData.put("app_user_name","Nasir");
        postData.put("bike_id","27");
        postData.put("bike_name","GS150R");
        postData.put("app_user_email","nazmul.nasir@icebd.com");
        postData.put("app_user_phone","01671100021");
        postData.put("app_user_address","Dhaka");
        postData.put("app_user_comment",say_Something.toString());


        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
        loginTask.execute(ConnectionManager.SERVER_URL+"reqQuotation");

    }
}
