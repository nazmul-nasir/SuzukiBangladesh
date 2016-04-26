package www.icebd.com.suzukibangladesh.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import www.icebd.com.suzukibangladesh.Manifest;
import www.icebd.com.suzukibangladesh.R;


public class SOS extends Fragment implements View.OnClickListener {

    Button dialer;

    public static SOS newInstance() {
        SOS fragment = new SOS();
        return fragment;
    }

    public SOS() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sos, container,
                false);

        dialer = (Button) rootView.findViewById(R.id.dialer);
        dialer.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ((MainActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onClick(View v) {


        String number = "tel:" + getResources().getString(R.string.sos_value);
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        callIntent.setPackage("com.android.phone");
        startActivity(callIntent);
    }
}
