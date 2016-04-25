package www.icebd.com.suzukibangladesh.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.utilities.FontManager;


public class HomeFragment extends Fragment {


    private Button btnMyBike;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);

        btnMyBike = (Button) rootView.findViewById(R.id.btn_my_parts);
        //btnMyBike.setText(getResources().getString(R.string.fa_motorcycle)+"\n\nMY BIKE");
        //btnMyBike.setTypeface(iconFont);


        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ((FirstActivity) activity).onSectionAttached(0);
    }
}
