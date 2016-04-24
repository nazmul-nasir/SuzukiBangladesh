package www.icebd.com.suzukibangladesh.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class SpareParts extends Fragment {

    public static SpareParts newInstance() {
        SpareParts fragment = new SpareParts();
        return fragment;
    }

    public SpareParts() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spare_parts, container,
                false);
        return rootView;
    }
}
