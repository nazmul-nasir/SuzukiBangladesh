package www.icebd.com.suzukibangladesh.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class InviteFriends extends Fragment {

    public static InviteFriends newInstance() {
        InviteFriends fragment = new InviteFriends();
        return fragment;
    }

    public InviteFriends() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_invite_friends, container,
                false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ((MainActivity) activity).onSectionAttached(1);
    }
}
