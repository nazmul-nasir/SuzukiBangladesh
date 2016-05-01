package www.icebd.com.suzukibangladesh.spare_parts;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import www.icebd.com.suzukibangladesh.R;


public class SparePartsMyCart extends Fragment
{
    Context context;

    public static SparePartsMyCart newInstance() {
        SparePartsMyCart fragment = new SparePartsMyCart();
        return fragment;
    }

    public SparePartsMyCart() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spare_parts_my_cart, container,
                false);
        context = getActivity().getApplicationContext();
        Bundle bundle = this.getArguments();
        int myInt = bundle.getInt("selectedTab", 0);

        return rootView;
    }
}
