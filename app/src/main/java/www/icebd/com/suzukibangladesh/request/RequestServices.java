package www.icebd.com.suzukibangladesh.request;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.utilities.ConnectionManager;
import www.icebd.com.suzukibangladesh.utilities.FontManager;


public class RequestServices extends Fragment implements AsyncResponse, View.OnClickListener {

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    Spinner dropdown_bike_name;
    Button submit;
    RadioGroup service_type1,service_type2;
    CheckBox engine,electrical,suspension,while_tyre,brake,speedo_motor,gear,clutch_plate,oil_filter,body_parts;
    String auth_key;
    String[] bikeId;
    EditText userComments;
    TextView text_right;



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

        if(pref.getString("is_login","0").equals("1"))
        {
            //do nothing
        }
        else {
            Toast.makeText(getActivity(),"Please Login",Toast.LENGTH_LONG).show();

            ((FirstActivity)getActivity()).selectItem(11);
        }

        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.FONTAWESOME);
        text_right = (TextView) rootView.findViewById(R.id.txt_email_right);
        text_right.setTypeface(iconFont);


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
        userComments = (EditText) rootView.findViewById(R.id.et_say_something);



       auth_key= pref.getString("auth_key","empty");

        if(!auth_key.equals("empty"))
        {
            postData.put("auth_key",auth_key);
            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute(ConnectionManager.SERVER_URL+"getBikeList");

        }
        else
        {
            Toast.makeText(getActivity(),"Connect to internet and restart the app",Toast.LENGTH_LONG).show();
        }

       submit.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        HashMap<String, String> postData = new HashMap<String, String>();
       // postData.put("mobile","android");
       // postData.put("mobile","android");
        String user_id = pref.getString("user_id","Not found");

        if(!auth_key.equals("empty"))
        {
            postData.put("auth_key",auth_key);
            Log.i("Test","auth_key :"+auth_key);

            postData.put("app_user_id",user_id);
            Log.i("Test","app_user_id :"+user_id);
         //   postData.put("app_user_id",user_id);

            int position = dropdown_bike_name.getSelectedItemPosition();
            postData.put("bike_id",bikeId[position-1]);
            Log.i("Test","bike_id :"+bikeId[position-1]);
            postData.put("bike_name",dropdown_bike_name.getSelectedItem().toString());
           // postData.put("bike_name","GS150R");
            Log.i("Test","bike_name :"+dropdown_bike_name.getSelectedItem().toString());

            int selected_service_1= service_type1.getCheckedRadioButtonId();
            String value_service1="";
            switch (selected_service_1)
            {
                case R.id.free:
                    value_service1="free";
                    break;
                case R.id.paid:
                    value_service1 ="paid";
                    break;
                case R.id.warranty:
                    value_service1="warranty";
                    break;
                default:
                    break;
            }

            postData.put("service_type",value_service1);
            Log.i("Test","service_type :"+value_service1);



            int selected_service_2= service_type2.getCheckedRadioButtonId();
            String value_service2="";
            switch (selected_service_2)
            {
                case R.id.parts_change:
                    value_service2="parts_change";
                    break;
                case R.id.repair:
                    value_service2 ="repair";
                    break;
                default:
                    break;
            }

            postData.put("servicing_type",value_service2);
            Log.i("Test","servicing_type :"+value_service2);

            String service_option="";

           if (engine.isChecked())
           {
               if (service_option.equals(""))
               {
                   service_option=engine.getText().toString();
               }
               else
               {
                   service_option = service_option +","+engine.getText().toString();
               }
           }

            if (electrical.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=electrical.getText().toString();
                }
                else
                {
                    service_option = service_option +","+electrical.getText().toString();
                }
            }
            if (suspension.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=suspension.getText().toString();
                }
                else
                {
                    service_option = service_option +","+suspension.getText().toString();
                }
            }
            if (while_tyre.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=while_tyre.getText().toString();
                }
                else
                {
                    service_option = service_option +","+while_tyre.getText().toString();
                }
            }
            if (brake.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=brake.getText().toString();
                }
                else
                {
                    service_option = service_option +","+brake.getText().toString();
                }
            }
            if (speedo_motor.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=speedo_motor.getText().toString();
                }
                else
                {
                    service_option = service_option +","+speedo_motor.getText().toString();
                }
            }
            if (gear.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=gear.getText().toString();
                }
                else
                {
                    service_option = service_option +","+gear.getText().toString();
                }
            }
            if (clutch_plate.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=clutch_plate.getText().toString();
                }
                else
                {
                    service_option = service_option +","+clutch_plate.getText().toString();
                }
            }
            if (oil_filter.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=oil_filter.getText().toString();
                }
                else
                {
                    service_option = service_option +","+oil_filter.getText().toString();
                }
            }
            if (body_parts.isChecked())
            {
                if (service_option.equals(""))
                {
                    service_option=body_parts.getText().toString();
                }
                else
                {
                    service_option = service_option +","+body_parts.getText().toString();
                }
            }

            postData.put("service_option",service_option);
            Log.i("Test","service_option :"+service_option);

            postData.put("cust_comment",userComments.getText().toString());
            Log.i("Test","cust_comment :"+userComments.getText().toString());






            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute(ConnectionManager.SERVER_URL+"reqService");

        }
        else
        {
            Toast.makeText(getActivity(),"Connect to internet and restart the app",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void processFinish(String output) {
        Log.i("Test",output);


        try {
            Log.i("Test", "Enter");
            JSONObject object = new JSONObject(output);
            String message ="";
                  message =  object.getString("message");

           // Log.i("Test", "Enter");

            if (message.equals("Successful"))
            {
                Log.i("Test","I am successful");
                JSONArray bikeList = object.getJSONArray("bikeList");
                String[] string = new String[bikeList.length()+1];
                string [0] = "Model";
                bikeId = new String[bikeList.length()];
               // ArrayList<String> mylist = new ArrayList<String>();

                for (int i = 0; i <bikeList.length() ; i++) {
                    JSONObject bikeDetail = bikeList.getJSONObject(i);
                    String bike_name = bikeDetail.getString("bike_name");
                    String bike_cc = bikeDetail.getString("bike_cc");
                   // mylist.add(bike_name+"/"+bike_cc);
                    string[i+1]=bike_name+"/"+bike_cc;
                    bikeId[i]= bikeDetail.getString("bike_id");
                    Log.i("Test",bike_name+"/"+bike_cc);


                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, string);
                dropdown_bike_name.setAdapter(adapter);

            }
            else if (message.equals("Request failed please try again"))
            {
                Toast.makeText(getActivity(),"Request failed please try again",Toast.LENGTH_LONG).show();
            }
            else if (message.equals("Your request has been sent successfully"))
            {
                Toast.makeText(getActivity(),"Your request has been sent successfully",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), FirstActivity.class);
                startActivity(intent);

            }
            else
            {
                Log.i("Test",message);
            }

        } catch (JSONException e) {
         //   Log.i("Test", "Enter");

            try {
                JSONObject ob = new JSONObject(output);
                Log.i("Test", "Enter last try");
                String message = ob.getString("message");
                 if (message.equals("Request failed please try again"))
                {
                    Toast.makeText(getActivity(),"Request failed please try again",Toast.LENGTH_LONG).show();
                }
                else if (message.equals("Your request has been sent successfully"))
                {
                    Toast.makeText(getActivity(),"Your request has been sent successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), FirstActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Log.i("Test",message);
                }

            } catch (JSONException e1) {
                e1.printStackTrace();
                Log.i("Test", "Enter last catch");
            }
            e.printStackTrace();
        }

     //   Log.i("Test", "Enter");


    }


}
