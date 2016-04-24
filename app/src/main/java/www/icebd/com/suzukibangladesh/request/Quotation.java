package www.icebd.com.suzukibangladesh.request;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import www.icebd.com.suzukibangladesh.R;


public class Quotation extends Fragment {

    public static Quotation newInstance() {
        Quotation fragment = new Quotation();
        return fragment;
    }

    public Quotation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logout, container,
                false);


        return rootView;
    }
}
