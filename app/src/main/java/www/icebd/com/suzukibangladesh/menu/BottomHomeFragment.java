package www.icebd.com.suzukibangladesh.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;
import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.app.Constants;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.utilities.FontManager;


public class BottomHomeFragment extends Fragment implements AsyncResponse, AdapterView.OnItemSelectedListener {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    String[] bikeId;
    String[] bikeID_cc;
    ViewPager pager;
    String[] IMAGE_URLS;
    Spinner dropdown_bike_name;
    public ImageLoader imageLoader = ImageLoader.getInstance();

    TextView text_left;
    TextView text_right;


    public static BottomHomeFragment newInstance() {
        BottomHomeFragment fragment = new BottomHomeFragment();
        return fragment;
    }

    public BottomHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bottom_home, container,
                false);
        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();
        Typeface iconFont = FontManager.getTypeface(getActivity().getApplicationContext(), FontManager.FONTAWESOME);

        text_left = (TextView) rootView.findViewById(R.id.text_left);
        text_left.setTypeface(iconFont);
       /* text_right = (TextView) rootView.findViewById(R.id.text_right);
        text_right.setTypeface(iconFont);*/

        HashMap<String, String> postData = new HashMap<String, String>();
        String auth_key= pref.getString("auth_key","empty");

        if(!auth_key.equals("empty"))
        {
            postData.put("auth_key",auth_key);
            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/getBikeList");

        }
        else
        {
            Toast.makeText(getActivity(),"Connect to internet and restart the app",Toast.LENGTH_LONG).show();
        }
        dropdown_bike_name =(Spinner)rootView.findViewById(R.id.txt_dropdown);
         pager = (ViewPager) rootView.findViewById(R.id.pager_bottom);

        return rootView;
    }

    @Override
    public void processFinish(String output) {
        Log.i("Test",output);


        try {
            Log.i("Test", "Enter");
            JSONObject object = new JSONObject(output);
            String message ="";
            message =  object.getString("message");

            // Log.i("Test", "Enter");

            if (message.equals("Successful"))
            {
                Log.i("Test","I am successful");
                JSONArray bikeList = object.getJSONArray("bikeList");
                String[] string = new String[bikeList.length()+1];
                string[0]="Model";
                bikeId = new String[bikeList.length()];
                bikeID_cc = new String[bikeList.length()];
                IMAGE_URLS = new String[bikeList.length()];
                // ArrayList<String> mylist = new ArrayList<String>();

                for (int i = 0; i <bikeList.length() ; i++) {
                    JSONObject bikeDetail = bikeList.getJSONObject(i);
                    String bike_name = bikeDetail.getString("bike_name");
                    String bike_cc = bikeDetail.getString("bike_cc");
                    // mylist.add(bike_name+"/"+bike_cc);
                    string[i+1]=bike_name+"/"+bike_cc;
                    bikeId[i]= bikeDetail.getString("bike_id");
                    Log.i("Test",bike_name+"/"+bike_cc);
                    IMAGE_URLS[i]=bikeDetail.getString("thumble_img");
                    Log.i("Image",IMAGE_URLS[i]);


                }
                bikeID_cc =string;
//                String dropdownstr = "Model" +string;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, string);
                dropdown_bike_name.setAdapter(adapter);


                dropdown_bike_name.setOnItemSelectedListener(this);



            }


        } catch (JSONException e) {
            //   Log.i("Test", "Enter");
            e.printStackTrace();
        }
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));



        pager.setAdapter(new ImageAdapter((getActivity())));










    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0) // position 0 = "Model" , default string
        pager.setCurrentItem(position-1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class ImageAdapter extends PagerAdapter {



       // private String[] IMAGE_URLS = Constants.IMAGES;

        private LayoutInflater inflater;
        private DisplayImageOptions options;

        ImageAdapter(Context context) {
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.ic_error)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
          //  Log.i("Test","Count : "+IMAGE_URLS.length );
            return IMAGE_URLS.length;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image_bottom, view, false);
            assert imageLayout != null;
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

            ImageLoader.getInstance().displayImage(IMAGE_URLS[position], imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    String message = null;
                    switch (failReason.getType()) {
                        case IO_ERROR:
                            message = "Input/Output error";
                            break;
                        case DECODING_ERROR:
                            message = "Image can't be decoded";
                            break;
                        case NETWORK_DENIED:
                            message = "Downloads are denied";
                            break;
                        case OUT_OF_MEMORY:
                            message = "Out Of Memory error";
                            break;
                        case UNKNOWN:
                            message = "Unknown error";
                            break;
                    }
                    Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();

                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });

            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}
