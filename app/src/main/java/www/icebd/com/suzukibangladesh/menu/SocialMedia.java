package www.icebd.com.suzukibangladesh.menu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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

        boolean installed  =   appInstalledOrNot("com.facebook.katana");
        if(installed)
        {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getActivity().getString(R.string.facebook_page_address)));
                intent.setPackage("com.facebook.katana");
                startActivity(intent);
            } catch(Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
            }


        }
        else
        {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getActivity().getString(R.string.facebook_page_address)));
                startActivity(intent);
            } catch(Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
            }
        }


        return rootView;
    }
    private boolean appInstalledOrNot(String uri)
    {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try
        {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            app_installed = false;
        }
        return app_installed ;
    }
}
