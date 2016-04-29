package www.icebd.com.suzukibangladesh.maps;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.app.LatLong;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class MapsActivity extends Fragment implements OnMapReadyCallback, AsyncResponse {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    private GoogleMap mMap;
    public static MapsActivity newInstance() {
        MapsActivity fragment = new MapsActivity();
        return fragment;
    }

    public MapsActivity () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container,
                false);
        SupportMapFragment mapFragment = (SupportMapFragment)  this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();

        HashMap<String, String> postData = new HashMap<String, String>();
        String auth_key = pref.getString("auth_key",null);
        if ((auth_key!=null))
        {
            postData.put("auth_key",auth_key);


            if (isNetworkAvailable()) {

                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
                loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/getLocation");
            }

        }
        else {
            Toast.makeText(getActivity(),"Please Connect to the Internet and Restart the app",Toast.LENGTH_LONG).show();

        }



        return rootView;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }*/


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void processFinish(String output) {
        Log.i("Test","Location: "+output);
        Log.i("Test",output);

        try {
            JSONObject object = new JSONObject(output);
            String status_code = object.getString("status_code");
            String message = object.getString("message");
            if (status_code.equals("200"))
            {
                JSONArray location = object.getJSONArray("location");

                for (int i = 0; i <location.length() ; i++) {
                    JSONObject locationDetails = location.getJSONObject(i);
                    String location_id = locationDetails.getString("location_id");
                    String location_type = locationDetails.getString("location_type");
                    String location_name = locationDetails.getString("location_name");
                    String location_address = locationDetails.getString("location_address");
                    String location_contact_person_name = locationDetails.getString("location_contact_person_name");
                    String location_contact_person_email = locationDetails.getString("location_contact_person_email");
                    String location_contact_person_phone = locationDetails.getString("location_contact_person_phone");

                    LatLng latlng = LatLong.getLocationFromAddress(getActivity(),location_address);
                    if (latlng != null) {
                        double lat = latlng.latitude;
                        double lng = latlng.longitude;
                        Log.i("Test","lat: "+lat);
                        Log.i("Test","lng: "+lng);

                       //GoogleMap Map;

                        // Add a marker in Sydney and move the camera
                        LatLng Address = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(Address).title(location_address));


                      //  mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 15));


                        mMap.moveCamera(CameraUpdateFactory.newLatLng(Address));
                     //   mMap.animateCamera(CameraUpdateFactory.zoomIn());
                       /* mMap.addMarker(new MarkerOptions().position(
                                new LatLng(lat,lng))
                                .title(Address.toString())
                                .snippet(Address.toString()));
*/
                    }

                 /*   LatLngBounds.Builder b = new LatLngBounds.Builder();
                    for (Marker m : markers) {
                        b.include(m.getPosition());
                    }
                    LatLngBounds bounds = b.build();
//Change the padding as per needed
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 25,25,5);
                    mMap.animateCamera(cu);*/




                }

            }
        }catch (JSONException e) {
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
