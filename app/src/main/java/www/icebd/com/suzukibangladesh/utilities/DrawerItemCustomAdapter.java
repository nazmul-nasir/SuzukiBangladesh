package www.icebd.com.suzukibangladesh.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import www.icebd.com.suzukibangladesh.utilities.ObjectDrawerItem;
import www.icebd.com.suzukibangladesh.R;

/**
 * Created by Momen Dewan on 4/26/2016.
 */
public class DrawerItemCustomAdapter extends ArrayAdapter<ObjectDrawerItem>
{

    Context mContext;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[] data)
    {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        Typeface iconFont = FontManager.getTypeface(this.getContext(), FontManager.FONTAWESOME);

        TextView imageViewIcon = (TextView) listItem.findViewById(R.id.imageViewIcon);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);

        ObjectDrawerItem folder = data[position];


        imageViewIcon.setText(folder.icon);
        imageViewIcon.setTypeface(iconFont);
        textViewName.setText(folder.name);

        return listItem;
    }

}
