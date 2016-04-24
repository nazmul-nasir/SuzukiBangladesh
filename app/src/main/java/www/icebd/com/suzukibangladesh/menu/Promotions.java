package www.icebd.com.suzukibangladesh.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class Promotions extends Fragment {

    public static Promotions newInstance() {
        Promotions fragment = new Promotions();
        return fragment;
    }

    public Promotions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_promotion, container,
                false);
        return rootView;
    }
}
