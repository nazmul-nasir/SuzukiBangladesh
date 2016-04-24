package www.icebd.com.suzukibangladesh.menu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class MyBikeFragment extends Fragment {
    public static MyBikeFragment newInstance() {
        MyBikeFragment fragment = new MyBikeFragment();
        return fragment;
    }

    public MyBikeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_bike, container,
                false);
        return rootView;
    }

}
