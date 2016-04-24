package www.icebd.com.suzukibangladesh.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class RequestServices extends Fragment {

    public static RequestServices newInstance() {
        RequestServices fragment = new RequestServices();
        return fragment;
    }

    public RequestServices() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_request_service, container,
                false);
        return rootView;
    }
}
