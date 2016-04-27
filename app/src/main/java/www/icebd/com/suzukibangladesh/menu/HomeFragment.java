package www.icebd.com.suzukibangladesh.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.notification.MainActivity;
import www.icebd.com.suzukibangladesh.utilities.FontManager;


public class HomeFragment extends Fragment {


    private Button btnMyBike,btnSpareParts,btnRequestServices,btnNews_Events,btnPromtoins,btnInviteFriends;
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
        btnSpareParts = (Button) rootView.findViewById(R.id.btn_spare_parts);
        btnRequestServices = (Button) rootView.findViewById(R.id.btn_req_services);
        btnNews_Events = (Button) rootView.findViewById(R.id.btn_news_events);
        btnPromtoins = (Button) rootView.findViewById(R.id.btn_promotion);
        btnInviteFriends = (Button) rootView.findViewById(R.id.btn_invite_friends);

        btnMyBike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(1);


            }
        });
        btnSpareParts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(2);

            }
        });
        btnRequestServices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(3);

            }
        });
        btnNews_Events.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(4);

            }
        });
        btnPromtoins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(5);

            }
        });
        btnInviteFriends.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(8);

            }
        });

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
