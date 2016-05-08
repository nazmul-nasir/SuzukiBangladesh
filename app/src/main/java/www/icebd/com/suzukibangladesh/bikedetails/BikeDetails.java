package www.icebd.com.suzukibangladesh.bikedetails;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.utilities.ConnectionManager;

import static com.google.android.gms.internal.zzir.runOnUiThread;

public class BikeDetails extends Fragment implements AsyncResponse {
    TableLayout tableLayout;
    TextView bike_name_tv;
    WebView webView;

    String gallery_image [];
    int NUM_PAGES;
    CircleIndicator indicator;
    int  currentPage=0;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    ViewPager pager;

    Context context;

    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    public static BikeDetails newInstance() {
        BikeDetails fragment = new BikeDetails();
        return fragment;
    }

    public BikeDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bike_details, container,
                false);

        context = getActivity().getApplicationContext();
        pref = context.getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();

        Bundle bundle = this.getArguments();
        int bike_id = bundle.getInt("bike_id");


        webView = (WebView)rootView.findViewById(R.id.help_webview);

        Log.i("Bike ID: ",String.valueOf(bike_id));
        tableLayout=(TableLayout)rootView.findViewById(R.id.main_table);
        bike_name_tv = (TextView)rootView.findViewById(R.id.bike_name);

        HashMap<String, String> postData = new HashMap<String, String>();

        String auth_key = pref.getString("auth_key",null);
        postData.put("auth_key",auth_key);
        postData.put("bike_id",String.valueOf(bike_id));

        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
        loginTask.execute(ConnectionManager.SERVER_URL+"getBikeDetail");

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        indicator = (CircleIndicator) rootView.findViewById(R.id.indicator);



        return rootView;
    }

    @Override
    public void processFinish(String output) {
        Log.i("Test","output : "+output);

        TableRow row = new TableRow(getActivity());
        row.setLayoutParams(new TableRow.LayoutParams(300,
                TableLayout.LayoutParams.WRAP_CONTENT));



        try {
            JSONObject object = new JSONObject(output);
            String message = object.getString("message");
            String auth_key = object.getString("auth_key");
            boolean status = object.getBoolean("status");

            //if(message.equals("Success"))
            if( status == true)
            {
                JSONObject bikeDetails = object.getJSONObject("bikeDetails");
                JSONArray basic = bikeDetails.getJSONArray("basic");
                JSONArray images = bikeDetails.getJSONArray("images");
                JSONObject basicObject = basic.getJSONObject(0);
                String bike_code = basicObject.getString("bike_code");
                String bike_id = basicObject.getString("bike_id");
                String bike_name = basicObject.getString("bike_name");
                String thumble_img = basicObject.getString("thumble_img");
                String video_url = basicObject.getString("video_url");


                if (bike_name != null) {
                    bike_name_tv.setText(bike_name);
                }
                if( ! video_url.equals("")) {

                    Log.i("url", video_url);


                    // webView.getSettings().setJavaScriptEnabled(true);
                    // webView.setVisibility(View.VISIBLE);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setDomStorageEnabled(true);
                    webView.setWebChromeClient(new WebChromeClient() {
                    });
                    webView.loadUrl(video_url);

                    WebSettings w = webView.getSettings();
                    w.setPluginState(WebSettings.PluginState.ON);
                }
                
                

                
                


                JSONObject specification = bikeDetails.getJSONObject("specification");
                JSONArray Electrical =specification.getJSONArray("Electrical");
                JSONArray Suspension =specification.getJSONArray("Suspension");
                JSONArray Tyre_Size =specification.getJSONArray("Tyre-Size");
                JSONArray Brake =specification.getJSONArray("Brake");
                JSONArray Dimensions =specification.getJSONArray("Dimensions");
                JSONArray Engine =specification.getJSONArray("Engine");



               /* View space1 = new View(this);
                space1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                //   v.setBackgroundColor(Color.rgb(51, 51, 51));
                tableLayout.addView(space1);*/



                /*Engine Started from here */

                TextView engineTextView=new TextView(getActivity());
                engineTextView.setText("Engine");
                row.addView(engineTextView);
                row.setPadding(50,0,0,10);
                row.setBackgroundColor(Color.parseColor("#939393"));
                tableLayout.addView(row);


                View space = new View(getActivity());
                space.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                //   v.setBackgroundColor(Color.rgb(51, 51, 51));
                tableLayout.addView(space);


                for (int i = 0; i <Engine.length() ; i++) {
                    JSONObject EngineObject = Engine.getJSONObject(i);
                    String specification_title = EngineObject.getString("specification_title");
                    String specification_value = EngineObject.getString("specification_value");

                    TableRow row_engine = new TableRow(getActivity());
                    row_engine.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView engine_Title_TextView=new TextView(getActivity());
                    TextView engine_Value_TextView=new TextView(getActivity());
                    engine_Title_TextView.setText(specification_title);
                    engine_Value_TextView.setText(" : "+specification_value);
                    if((i%2)==0)
                    {
                        engine_Title_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        engine_Value_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    }
                    row_engine.addView(engine_Title_TextView);
                    row_engine.addView(engine_Value_TextView);
                    row_engine.setPadding(50,5,50,5);

                   /* if((i%2)==0)
                    row_engine.setBackgroundColor(Color.parseColor("#d8d8d8"));*/
                    tableLayout.addView(row_engine);

                    Log.i("Test","specification_title" +" : "+specification_title);
                    Log.i("Test","specification_value" +" : "+specification_value);
                }

                 /*Engine end from here */


                View space1 = new View(getActivity());
                space1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                //   v.setBackgroundColor(Color.rgb(51, 51, 51));
                tableLayout.addView(space1);


                 /*Electrical Started from here */

                TableRow rowelctrical = new TableRow(getActivity());
                rowelctrical.setLayoutParams(new TableRow.LayoutParams(300,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView electricalTextView=new TextView(getActivity());
                electricalTextView.setText("Electrical");
                rowelctrical.addView(electricalTextView);
                rowelctrical.setPadding(50,0,0,10);
                rowelctrical.setBackgroundColor(Color.parseColor("#939393"));
                tableLayout.addView(rowelctrical);


                View space2 = new View(getActivity());
                space2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space2);



                for (int i = 0; i <Electrical.length() ; i++) {
                    JSONObject ElectricalObject = Electrical.getJSONObject(i);
                    String specification_title = ElectricalObject.getString("specification_title");
                    String specification_value = ElectricalObject.getString("specification_value");


                    TableRow dynamic_row = new TableRow(getActivity());
                    dynamic_row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView title_TextView=new TextView(getActivity());
                    TextView value_TextView=new TextView(getActivity());
                    title_TextView.setText(specification_title);
                    value_TextView.setText(" : "+specification_value);
                    if((i%2)==0)
                    {
                        title_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        value_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    }
                    dynamic_row.addView(title_TextView);
                    dynamic_row.addView(value_TextView);
                    dynamic_row.setPadding(50,5,50,5);

                   /* if((i%2)==0)
                        dynamic_row.setBackgroundColor(Color.parseColor("#d8d8d8"));*/
                    tableLayout.addView(dynamic_row);

                    Log.i("Test","specification_title "+" : "+specification_title);
                    Log.i("Test","specification_value" +" : "+specification_value);
                }
                 /*Electrical End from here */

                View space3 = new View(getActivity());
                space3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space3);


                /* Suspension started from here */
                TableRow row_susp = new TableRow(getActivity());
                row_susp.setLayoutParams(new TableRow.LayoutParams(300,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView susp_TextView=new TextView(getActivity());
                susp_TextView.setText("Suspension");
                row_susp.addView(susp_TextView);
                row_susp.setPadding(50,0,0,10);
                row_susp.setBackgroundColor(Color.parseColor("#939393"));
                tableLayout.addView(row_susp);

                View space13 = new View(getActivity());
                space13.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space13);

                for (int i = 0; i <Suspension.length() ; i++) {
                    JSONObject SuspensionObject = Suspension.getJSONObject(i);
                    String specification_title = SuspensionObject.getString("specification_title");
                    String specification_value = SuspensionObject.getString("specification_value");

                    TableRow dynamic_row = new TableRow(getActivity());
                    dynamic_row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView title_TextView=new TextView(getActivity());
                    TextView value_TextView=new TextView(getActivity());
                    //  title_TextView.setSingleLine(false);
                    //  title_TextView.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

                    value_TextView.setSingleLine(false);
                    value_TextView.setEllipsize(TextUtils.TruncateAt.END);
                    // value_TextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                  /*  value_TextView.setLayoutParams(new android.widget.TableRow.LayoutParams(android.widget.TableRow.LayoutParams.WRAP_CONTENT,
                            android.widget.TableRow.LayoutParams.WRAP_CONTENT, 1.0f));*/
                    value_TextView.setMaxLines(3);
                    // value_TextView.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    title_TextView.setText(specification_title);
                    value_TextView.setText(" : "+specification_value);
                    if((i%2)==0)
                    {
                        title_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        value_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    }
                    dynamic_row.addView(title_TextView);
                    dynamic_row.addView(value_TextView);
                    dynamic_row.setPadding(50,5,50,5);

                /*    if((i%2)==0)
                        dynamic_row.setBackgroundColor(Color.parseColor("#d8d8d8"));*/
                    tableLayout.addView(dynamic_row);
                    Log.i("Test","specification_title "+" : "+specification_title);
                    Log.i("Test","specification_value" +" : "+specification_value);
                }

                 /* Suspension end from here */

                View space4 = new View(getActivity());
                space4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space4);


                 /* Tyre started from here */

                TableRow row_tyre = new TableRow(getActivity());
                row_tyre.setLayoutParams(new TableRow.LayoutParams(300,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView tyre_TextView=new TextView(getActivity());
                tyre_TextView.setText("Tyre");
                row_tyre.addView(tyre_TextView);
                row_tyre.setPadding(50,0,0,10);
                row_tyre.setBackgroundColor(Color.parseColor("#939393"));
                tableLayout.addView(row_tyre);

                View space14 = new View(getActivity());
                space14.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space14);

                for (int i = 0; i <Tyre_Size.length() ; i++) {
                    JSONObject Tyre_SizeObject = Tyre_Size.getJSONObject(i);
                    String specification_title = Tyre_SizeObject.getString("specification_title");
                    String specification_value = Tyre_SizeObject.getString("specification_value");

                    TableRow dynamic_row = new TableRow(getActivity());
                    dynamic_row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView title_TextView=new TextView(getActivity());
                    TextView value_TextView=new TextView(getActivity());
                    title_TextView.setText(specification_title);
                    value_TextView.setText(" : "+specification_value);
                    if((i%2)==0)
                    {
                        title_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        value_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    }
                    dynamic_row.addView(title_TextView);
                    dynamic_row.addView(value_TextView);
                    dynamic_row.setPadding(50,5,50,5);

                   /* if((i%2)==0)
                        dynamic_row.setBackgroundColor(Color.parseColor("#d8d8d8"));*/
                    tableLayout.addView(dynamic_row);

                    Log.i("Test","specification_title "+" : "+specification_title);
                    Log.i("Test","specification_value" +" : "+specification_value);
                }

                 /* Type end from here */
                View space5 = new View(getActivity());
                space5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space5);

                 /* Brake started from here */
                TableRow row_brake = new TableRow(getActivity());
                row_brake.setLayoutParams(new TableRow.LayoutParams(300,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView brake_TextView=new TextView(getActivity());
                brake_TextView.setText("Brake");
                row_brake.addView(brake_TextView);
                row_brake.setPadding(50,0,0,10);
                row_brake.setBackgroundColor(Color.parseColor("#939393"));
                tableLayout.addView(row_brake);

                View space15 = new View(getActivity());
                space15.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space15);



                for (int i = 0; i <Brake.length() ; i++) {
                    JSONObject BrakeObject = Brake.getJSONObject(i);
                    String specification_title = BrakeObject.getString("specification_title");
                    String specification_value = BrakeObject.getString("specification_value");

                    TableRow dynamic_row = new TableRow(getActivity());
                    dynamic_row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView title_TextView=new TextView(getActivity());
                    TextView value_TextView=new TextView(getActivity());
                    title_TextView.setText(specification_title);
                    value_TextView.setText(" : "+specification_value);

                    if((i%2)==0)
                    {
                        title_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        value_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    }
                    dynamic_row.addView(title_TextView);
                    dynamic_row.addView(value_TextView);
                    dynamic_row.setPadding(50,5,50,5);

                   /* if((i%2)==0)
                        dynamic_row.setBackgroundColor(Color.parseColor("#d8d8d8"));*/
                    tableLayout.addView(dynamic_row);

                    Log.i("Test","specification_title "+" : "+specification_title);
                    Log.i("Test","specification_value" +" : "+specification_value);
                }

                 /* Brake end from here */

                View space6 = new View(getActivity());
                space6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space6);

                 /* Dimensions started from here */

                TableRow row_dimension = new TableRow(getActivity());
                row_dimension.setLayoutParams(new TableRow.LayoutParams(300,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                TextView dimension_TextView=new TextView(getActivity());
                dimension_TextView.setText("Dimensions");
                row_dimension.addView(dimension_TextView);
                row_dimension.setPadding(50,0,0,10);
                row_dimension.setBackgroundColor(Color.parseColor("#939393"));
                tableLayout.addView(row_dimension);

                View space16 = new View(getActivity());
                space16.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space16);

                for (int i = 0; i <Dimensions.length() ; i++) {
                    JSONObject DimensionsObject = Dimensions.getJSONObject(i);
                    String specification_title = DimensionsObject.getString("specification_title");
                    String specification_value = DimensionsObject.getString("specification_value");

                    TableRow dynamic_row = new TableRow(getActivity());
                    dynamic_row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView title_TextView=new TextView(getActivity());
                    TextView value_TextView=new TextView(getActivity());
                    title_TextView.setText(specification_title);

                    value_TextView.setText(" : "+specification_value);
                    if((i%2)==0)
                    {
                        title_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                        value_TextView.setBackgroundColor(Color.parseColor("#d8d8d8"));
                    }
                    dynamic_row.addView(title_TextView);
                    dynamic_row.addView(value_TextView);
                    dynamic_row.setPadding(50,5,50,5);

                    /*if((i%2)==0)
                        dynamic_row.setBackgroundColor(Color.parseColor("#d8d8d8"));*/
                    tableLayout.addView(dynamic_row);

                    Log.i("Test","specification_title "+" : "+specification_title);
                    Log.i("Test","specification_value" +" : "+specification_value);
                }

                 /* Dimensions end from here */
                View space7 = new View(getActivity());
                space7.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 15));
                tableLayout.addView(space7);


                NUM_PAGES = images.length();
                gallery_image =new String[NUM_PAGES];

                for (int i = 0; i <images.length(); i++) {

                    JSONObject imagesDetails = images.getJSONObject(i);
                    gallery_image[i] =imagesDetails.getString("large_image_link");
                    // gallery_image[i]=g_image;
                    Log.i("Test large", gallery_image[i]);

                }

            }
            else
            {
                Toast.makeText(getActivity(), "Data Not Found !", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));



        pager.setAdapter(new ImageAdapter((getActivity())));



    }

    private class ImageAdapter extends PagerAdapter {



        private String[] IMAGE_URLS = gallery_image;

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
            return IMAGE_URLS.length;
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image_bikedetails, view, false);
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
