package www.icebd.com.suzukibangladesh;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.app.CheckNetworkConnection;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.maps.MapsActivity;
import www.icebd.com.suzukibangladesh.menu.HomeFragment;
import www.icebd.com.suzukibangladesh.menu.InviteFriends;
import www.icebd.com.suzukibangladesh.menu.MediaLink;
import www.icebd.com.suzukibangladesh.menu.NewsEvents;
import www.icebd.com.suzukibangladesh.notification.Notification;
import www.icebd.com.suzukibangladesh.notification.QuickstartPreferences;
import www.icebd.com.suzukibangladesh.reg.Login;
import www.icebd.com.suzukibangladesh.menu.MyBikeFragment;
import www.icebd.com.suzukibangladesh.menu.Promotions;
import www.icebd.com.suzukibangladesh.quiz.Quiz;
import www.icebd.com.suzukibangladesh.request.RequestServices;
import www.icebd.com.suzukibangladesh.menu.SOS;
import www.icebd.com.suzukibangladesh.menu.SocialMedia;
import www.icebd.com.suzukibangladesh.menu.SpareParts;
import www.icebd.com.suzukibangladesh.reg.ChangePassword;
import www.icebd.com.suzukibangladesh.reg.Logout;
import www.icebd.com.suzukibangladesh.reg.ResetPassword;
import www.icebd.com.suzukibangladesh.reg.Signup;
import www.icebd.com.suzukibangladesh.request.Quotation;
import www.icebd.com.suzukibangladesh.utilities.ConnectionManager;
import www.icebd.com.suzukibangladesh.utilities.CustomDialog;
import www.icebd.com.suzukibangladesh.utilities.DrawerItemCustomAdapter;
import www.icebd.com.suzukibangladesh.utilities.FontManager;
import www.icebd.com.suzukibangladesh.utilities.JsonParser;
import www.icebd.com.suzukibangladesh.utilities.ObjectDrawerItem;


public class FirstActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

{
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    NavigationView navigationView;

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    final Context context = this;
    CustomDialog customDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);

        pref = getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", MODE_PRIVATE);
        editor = pref.edit();



        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        //mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerListener(toggle);
        }
        toggle.syncState();


        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[13];

        drawerItem[0] = new ObjectDrawerItem(getResources().getString(R.string.fa_home), mNavigationDrawerItemTitles[0]);
        drawerItem[1] = new ObjectDrawerItem(getResources().getString(R.string.fa_motorcycle), mNavigationDrawerItemTitles[1]);
        drawerItem[2] = new ObjectDrawerItem(getResources().getString(R.string.fa_spare_parts), mNavigationDrawerItemTitles[2]);
        drawerItem[3] = new ObjectDrawerItem(getResources().getString(R.string.fa_request_service), mNavigationDrawerItemTitles[3]);
        drawerItem[4] = new ObjectDrawerItem(getResources().getString(R.string.fa_news_events), mNavigationDrawerItemTitles[4]);
        drawerItem[5] = new ObjectDrawerItem(getResources().getString(R.string.fa_promotions), mNavigationDrawerItemTitles[5]);
        drawerItem[6] = new ObjectDrawerItem(getResources().getString(R.string.fa_quizzes), mNavigationDrawerItemTitles[6]);
        drawerItem[7] = new ObjectDrawerItem(getResources().getString(R.string.fa_phone), mNavigationDrawerItemTitles[7]);
        drawerItem[8] = new ObjectDrawerItem(getResources().getString(R.string.fa_invite_friends), mNavigationDrawerItemTitles[8]);
        drawerItem[9] = new ObjectDrawerItem(getResources().getString(R.string.fa_facebook_square), mNavigationDrawerItemTitles[9]);
        drawerItem[10] = new ObjectDrawerItem(getResources().getString(R.string.fa_change_pass), mNavigationDrawerItemTitles[10]);
        drawerItem[11] = new ObjectDrawerItem(getResources().getString(R.string.fa_sign_in), mNavigationDrawerItemTitles[11]);
        drawerItem[12] = new ObjectDrawerItem(getResources().getString(R.string.fa_sign_out), mNavigationDrawerItemTitles[12]);

        //drawerItem[13] = new ObjectDrawerItem(getResources().getString(R.string.fa_home), mNavigationDrawerItemTitles[13]);


        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                selectItem(position);
            }
        });

        //Select home by default




        /*String auth_key = pref.getString("auth_key",null);
        //String notification_key = pref.getString("gcm_registration_token",null);
        Log.i("Test","GCM registration token :"+pref.getString("gcm_registration_token",null));

        if (auth_key==null)
        {
            HashMap<String, String> postData = new HashMap<String, String>();

            String android_id = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            Log.i("Test","Android ID : "+android_id);
            Log.i("Test","Notification key : "+pref.getString("gcm_registration_token",null));
            //Log.i("Test","Auth_key : "+auth_key);

            postData.put("unique_device_id",android_id);
            postData.put("notification_key", pref.getString("gcm_registration_token",null));
            postData.put("platform","1");
            customDialog = new CustomDialog(context);
            if(CheckNetworkConnection.isConnectionAvailable(context) == true)
            {
                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this, postData);
                loginTask.execute(ConnectionManager.SERVER_URL+"getAuthKey");
            }
            else
            {
                customDialog.alertDialog("ERROR", getString(R.string.error_no_internet));
            }

        }
        else
        {
            selectItem(0);
        }*/
        selectItem(0);
    /*    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String uid = telephonyManager.getDeviceId();
        Log.i("Test",uid);*/


    }

    public void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment().newInstance();
                break;
            case 1:
                fragment = new MyBikeFragment().newInstance();
                break;
            case 2:
                fragment = new SpareParts().newInstance();
                break;
            case 3:
                fragment = new RequestServices().newInstance();
                break;
            case 4:
                fragment = new NewsEvents().newInstance();
                break;
            case 5:
                fragment = new Promotions().newInstance();
                break;
            case 6:
                fragment = new Quiz().newInstance();
                break;
            case 7:
                fragment = new SOS().newInstance();
                break;
            case 8:
                //fragment = new InviteFriends().newInstance();
                try {
                    JsonParser jsonParser = new JsonParser();
                    String shareBody = "Welcome to Suzuki Bangladesh Official Mobile Apps\n" +
                            "\n" +
                            jsonParser.mediaLink.getPlay_store()+"\n" +
                            "\n" +
                            jsonParser.mediaLink.getApp_store()+"\n" +
                            "\n" +
                            jsonParser.mediaLink.getFb();
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));


                }catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
                }
                break;
            case 9:
                //fragment = new SocialMedia().newInstance();
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
                break;
            case 10:
                fragment = new ChangePassword().newInstance();
                break;
            case 11:
                fragment = new Login().newInstance();
                break;
            case 12:
                fragment = new Logout().newInstance();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            //getActionBar().setTitle(mNavigationDrawerItemTitles[position]);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            packageManager.getPackageInfo("com.facebook.katana", 0);
            System.out.println("facebook version code: "+versionCode);

            return getResources().getString(R.string.facebook_url_schemes);
            /*if (versionCode >= 3002850) { //newer versions of fb app
                System.out.println("facebook new version");
                return "fb://facewebmodal/f?href=" + getResources().getString(R.string.facebook_page_address);
            } else { //older versions of fb app
                System.out.println("facebook old version");
                return getResources().getString(R.string.facebook_url_schemes);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("err facebook: ",e.getMessage());
            return getResources().getString(R.string.facebook_page_address); //normal web url
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifiction) {


            fragmentManager.beginTransaction()
                    .replace(R.id.container, Notification.newInstance())
                    .commit();
            return true;


        }
        else if (id==R.id.action_find_location)
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MapsActivity.newInstance())
                    .commit();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }

       // return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fragmentManager = getSupportFragmentManager();

        int id = item.getItemId();

        if (id == R.id.nav_my_bike) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MyBikeFragment.newInstance())
                    .commit();
        } else if (id == R.id.nav_home) {

            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commit();

        } else if (id == R.id.nav_spare_parts) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SpareParts.newInstance())
                    .commit();

        } else if (id == R.id.nav_request_services) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, RequestServices.newInstance())
                    .commit();

        } else if (id == R.id.nav_news_events) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Quotation.newInstance())
                    .commit();

        } else if (id == R.id.nav_promotions) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Promotions.newInstance())
                    .commit();

        }else if (id == R.id.nav_quizzes) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Quiz.newInstance())
                    .commit();

        } else if (id == R.id.nav_sos) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SOS.newInstance())
                    .commit();

        } else if (id == R.id.nav_invite_friends) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, InviteFriends.newInstance())
                    .commit();

        }
        else if (id == R.id.nav_social_media) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, SocialMedia.newInstance())
                    .commit();

        }else if (id == R.id.nav_logout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Logout.newInstance())
                    .commit();

        }else if (id == R.id.nav_change_password) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ChangePassword.newInstance())
                    .commit();

        }else if (id == R.id.nav_reset_password) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ResetPassword.newInstance())
                    .commit();

        }

        else if (id == R.id.nav_login) {


          /*  fragmentManager.beginTransaction()
                    .replace(R.id.container, Login.newInstance())
                    .commit();*/
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Login.newInstance())
                    .commit();

        }
        else if (id == R.id.nav_sign_up) {


          /*  fragmentManager.beginTransaction()
                    .replace(R.id.container, Login.newInstance())
                    .commit();*/
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Signup.newInstance())
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*public void onSectionAttached(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();



        if (position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, MyBikeFragment.newInstance())
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }*/


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
