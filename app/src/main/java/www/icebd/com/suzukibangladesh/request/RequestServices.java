package www.icebd.com.suzukibangladesh.request;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class RequestServices extends Fragment implements AsyncResponse {

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    Spinner dropdown_bike_name;
    Button submit;
    RadioGroup service_type1,service_type2;
    CheckBox engine,electrical,suspension,while_tyre,brake,speedo_motor,gear,clutch_plate,oil_filter,body_parts;



    public static RequestServices newInstance() {
        RequestServices fragment = new RequestServices();
        return fragment;
    }

    public RequestServices() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container,
                false);


        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();

        HashMap<String, String> postData = new HashMap<String, String>();



        dropdown_bike_name =(Spinner)rootView.findViewById(R.id.txt_dropdown);
        submit = (Button)rootView.findViewById(R.id.btn_service_submit);
        service_type1 = (RadioGroup) rootView.findViewById(R.id.rdo_grp_service_type_1);
        service_type2 = (RadioGroup) rootView.findViewById(R.id.rdo_grb_service_type_2);
        engine = (CheckBox)rootView.findViewById(R.id.chk_engine);
        electrical = (CheckBox)rootView.findViewById(R.id.chk_electrical) ;
        suspension = (CheckBox)rootView.findViewById(R.id.chk_suspension);
        while_tyre = (CheckBox)rootView.findViewById(R.id.chk_while_tyre);
        brake = (CheckBox)rootView.findViewById(R.id.chk_brake);
        speedo_motor = (CheckBox)rootView.findViewById(R.id.chk_speedo_motor);
        gear = (CheckBox)rootView.findViewById(R.id.chk_gear);
        clutch_plate = (CheckBox)rootView.findViewById(R.id.chk_clutch_plate);
        oil_filter = (CheckBox)rootView.findViewById(R.id.chk_oil_filter);
        body_parts = (CheckBox)rootView.findViewById(R.id.chk_body_parts);



        String auth_key= pref.getString("auth_key","empty");

        if(!auth_key.equals("empty"))
        {
            postData.put("auth_key",auth_key);
            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/getBikeList");

        }
        else
        {
            Toast.makeText(getActivity(),"Connect to internet and restart the app",Toast.LENGTH_LONG).show();
        }

      // submit.setOnClickListener(this);








        return rootView;
    }

    @Override
    public void processFinish(String output) {
        Log.i("Test",output);

        try {
            JSONObject object = new JSONObject(output);
            String message = object.getString("message ");

            if (message.equals("Successfull"))
            {
                Log.i("Test","I am successful");
                JSONArray bikeList = object.getJSONArray("bikeList");
                String[] string = new String[bikeList.length()];
               // ArrayList<String> mylist = new ArrayList<String>();

                for (int i = 0; i <bikeList.length() ; i++) {
                    JSONObject bikeDetail = bikeList.getJSONObject(i);
                    String bike_name = bikeDetail.getString("bike_name");
                    String bike_cc = bikeDetail.getString("bike_cc");
                   // mylist.add(bike_name+"/"+bike_cc);
                    string[i]=bike_name+"/"+bike_cc;
                    Log.i("Test",bike_name+"/"+bike_cc);


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, string);
                dropdown_bike_name.setAdapter(adapter);



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
