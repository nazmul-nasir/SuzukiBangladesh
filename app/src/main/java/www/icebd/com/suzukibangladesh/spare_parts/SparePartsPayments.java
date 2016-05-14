package www.icebd.com.suzukibangladesh.spare_parts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import www.icebd.com.suzukibangladesh.Manifest;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.utilities.Constant;


public class SparePartsPayments extends Fragment{

    Button dialer;
    Context context;

    public static SparePartsPayments newInstance() {
        SparePartsPayments fragment = new SparePartsPayments();
        return fragment;
    }

    public SparePartsPayments() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spare_parts_payments, container,
                false);
        context = getActivity().getApplicationContext();
        //dialer = (Button) rootView.findViewById(R.id.dialer);
        //dialer.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ((MainActivity) activity).onSectionAttached(1);
    }



}
