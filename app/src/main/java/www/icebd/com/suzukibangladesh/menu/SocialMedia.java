package www.icebd.com.suzukibangladesh.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class SocialMedia extends Fragment {

    public static SocialMedia newInstance() {
        SocialMedia fragment = new SocialMedia();
        return fragment;
    }

    public SocialMedia() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_social_media, container,
                false);
        return rootView;
    }
}
