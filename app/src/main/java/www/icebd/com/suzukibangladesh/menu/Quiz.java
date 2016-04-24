package www.icebd.com.suzukibangladesh.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class Quiz extends Fragment {

    public static Quiz newInstance() {
        Quiz fragment = new Quiz();
        return fragment;
    }

    public Quiz() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quizzes, container,
                false);
        return rootView;
    }
}
