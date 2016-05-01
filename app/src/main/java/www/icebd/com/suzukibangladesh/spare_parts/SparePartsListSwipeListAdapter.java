package www.icebd.com.suzukibangladesh.spare_parts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.utilities.Constant;
import www.icebd.com.suzukibangladesh.utilities.FontManager;
import www.icebd.com.suzukibangladesh.utilities.Utils;


/**
 * Created by Momen Dewan on 4/26/2016.
 */
public class SparePartsListSwipeListAdapter extends BaseAdapter implements Filterable
{
    //private Activity activity;
    private LayoutInflater inflater;
    private List<SparePartsListObject.SparePartsItem> listSparePartsItem;
    private List<SparePartsListObject.SparePartsItem> listSparePartsItemSearch;
    private String[] bgColors;
    public ImageLoader imageLoader = null;
    DisplayImageOptions options;
    Utils utilClass = new Utils();
    SparePartsList sparePartsListFragment;

    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();

    Context context;

    public SparePartsListSwipeListAdapter(Context context, List<SparePartsListObject.SparePartsItem> listSparePartsItem, SparePartsList sparePartsListFragment) {
        this.context = context;
        this.listSparePartsItem = listSparePartsItem;
        this.listSparePartsItemSearch = listSparePartsItem;
        this.sparePartsListFragment = sparePartsListFragment;
        mInflater = LayoutInflater.from(context);
        //bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)
                .showImageForEmptyUri(null)
                .showImageOnFail(null).cacheInMemory(false)
                .cacheOnDisk(false).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    @Override
    public int getCount() {
        return listSparePartsItem.size();
    }

    @Override
    public Object getItem(int location) {
        return listSparePartsItem.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder;
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /*if (convertView == null)
            convertView = inflater.inflate(R.layout.my_spare_parts_list_row, null);*/
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.my_spare_parts_list_row, null);
            holder = new Holder();
            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);

            holder.sparePartsImg = (ImageView) convertView.findViewById(R.id.id_spare_parts_img);
            holder.progressBar=(ProgressBar)convertView.findViewById(R.id.rn_progress);
            holder.txtSparePartsName = (TextView) convertView.findViewById(R.id.txt_spare_parts_name);
            holder.txtSparePartsBikeName = (TextView) convertView.findViewById(R.id.id_spareparts_bike_name);
            holder.txtSparePartsNumber = (TextView) convertView.findViewById(R.id.id_spareparts_number);
            holder.txtSparePartsPrice = (TextView) convertView.findViewById(R.id.id_spare_parts_price);

            holder.txtSparePartsAddToCart = (TextView) convertView.findViewById(R.id.add_to_cart_img);
            holder.txtSparePartsAddToCart.setTypeface(iconFont);


            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }

        //holder.bikeImg.setImageResource( bikeList.get(position).thumble_img );
        //holder.bikeImg.setImageIcon( Icon.createWithContentUri( bikeList.get(position).thumble_img ) );
        Log.v("Spare Parts Name : ",listSparePartsItem.get(position).getSpare_parts_name().toString());

        //BitmapDrawable drawableBitmap=new BitmapDrawable(Utils.getBitmapFromURL(listSparePartsItem.get(position).getThumble_img()));
        //holder.sparePartsImg.setBackgroundDrawable(drawableBitmap);

        imageLoader.displayImage(listSparePartsItem.get(position).getThumble_img(), holder.sparePartsImg, options,
                new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        holder.progressBar.setProgress(0);
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,FailReason failReason) {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view,int current, int total) {
                        holder.progressBar.setProgress(Math.round(100.0f * current/ total));
                    }
                });

        holder.txtSparePartsName.setText( listSparePartsItem.get(position).getSpare_parts_name().toString() );
        holder.txtSparePartsBikeName.setText( listSparePartsItem.get(position).getBike_name().toString() );
        holder.txtSparePartsNumber.setText( listSparePartsItem.get(position).getSpare_parts_code().toString() );
        holder.txtSparePartsPrice.setText( listSparePartsItem.get(position).getSpare_parts_price().toString() );
        holder.txtSparePartsName.setText( listSparePartsItem.get(position).getSpare_parts_name().toString() );

        holder.txtSparePartsAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You Clicked "+listSparePartsItem.get(position).getSpare_parts_name().toString(), Toast.LENGTH_LONG).show();
                /*SparePartsListObject obj_sparePartsList = new SparePartsListObject();
                SparePartsListObject.SparePartsItem obj_sparePartsItem = obj_sparePartsList.new SparePartsItem();

                MyCartObject myCartObject = new MyCartObject(obj_sparePartsItem,qnt+1);
                Constant.listMyCartObj.add(obj_sparePartsItem);
                */
                AddToGlobalArray(listSparePartsItem.get(position));
            }
        });



        //String color = bgColors[position % bgColors.length];
        //serial.setBackgroundColor(Color.parseColor(color));

        return convertView;
    }
    public void AddToGlobalArray( SparePartsListObject.SparePartsItem Item) {

        if (Constant.listMyCartObj.size() == 0)
        {
            Item.setaInteger(1);
            Constant.listMyCartObj.add(Item);
            //shopActivity.changeShopButtonText();
        }
        else
        {
            boolean b = true;
            for (SparePartsListObject.SparePartsItem s : Constant.listMyCartObj) {
                if (s.getSpare_parts_id().equals(Item.getSpare_parts_id()))
                {
                    //Toast.makeText(context,context.getResources().getString(R.string.toast_shop_cart_membership_alert),Toast.LENGTH_LONG).show();
                    s.setaInteger(s.getaInteger() + 1);
                    b = false;
                    //break;
                }
            }
            if (b)
            {
                Item.setaInteger(Item.getaInteger() + 1);
                Constant.listMyCartObj.add(Item);
                //shopActivity.changeShopButtonText();
            }
        }

    }

    @Override
    public Filter getFilter()
    {
        return mFilter;
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SparePartsListObject.SparePartsItem> list = listSparePartsItemSearch;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getSpare_parts_name();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listSparePartsItem = (ArrayList<SparePartsListObject.SparePartsItem>) results.values;
            notifyDataSetChanged();
        }

    }

    static class Holder
    {
        public ProgressBar progressBar;
        public ImageView sparePartsImg;
        public TextView txtSparePartsName;
        public TextView txtSparePartsBikeName;
        public TextView txtSparePartsNumber;
        public TextView txtSparePartsPrice;
        public TextView txtSparePartsAddToCart;
    }


}
