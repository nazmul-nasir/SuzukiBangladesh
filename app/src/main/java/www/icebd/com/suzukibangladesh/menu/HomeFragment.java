package www.icebd.com.suzukibangladesh.menu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.app.Constants;
import www.icebd.com.suzukibangladesh.notification.MainActivity;
import www.icebd.com.suzukibangladesh.utilities.FontManager;


public class HomeFragment extends Fragment {


    private Button btnMyBike,btnSpareParts,btnRequestServices,btnNews_Events,btnPromtoins,btnInviteFriends;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);
        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);

        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));



        btnMyBike = (Button) rootView.findViewById(R.id.btn_my_parts);
        btnSpareParts = (Button) rootView.findViewById(R.id.btn_spare_parts);
        btnRequestServices = (Button) rootView.findViewById(R.id.btn_req_services);
        btnNews_Events = (Button) rootView.findViewById(R.id.btn_news_events);
        btnPromtoins = (Button) rootView.findViewById(R.id.btn_promotion);
        btnInviteFriends = (Button) rootView.findViewById(R.id.btn_invite_friends);

        btnMyBike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(1);


            }
        });
        btnSpareParts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(2);

            }
        });
        btnRequestServices.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(3);

            }
        });
        btnNews_Events.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(4);

            }
        });
        btnPromtoins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(5);

            }
        });
        btnInviteFriends.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                ((FirstActivity)getActivity()).selectItem(8);

            }
        });

        //btnMyBike.setText(getResources().getString(R.string.fa_motorcycle)+"\n\nMY BIKE");
        //btnMyBike.setTypeface(iconFont);


        ViewPager pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(new ImageAdapter(getActivity()));
       //pager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));


        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // ((FirstActivity) activity).onSectionAttached(0);
    }

    private static class ImageAdapter extends PagerAdapter {

        private static final String[] IMAGE_URLS = Constants.IMAGES;

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
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
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
