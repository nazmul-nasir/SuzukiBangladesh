package www.icebd.com.suzukibangladesh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;
import www.icebd.com.suzukibangladesh.menu.HomeFragment;
import www.icebd.com.suzukibangladesh.menu.InviteFriends;
import www.icebd.com.suzukibangladesh.menu.Login;
import www.icebd.com.suzukibangladesh.menu.MyBikeFragment;
import www.icebd.com.suzukibangladesh.menu.NewsEvents;
import www.icebd.com.suzukibangladesh.menu.Promotions;
import www.icebd.com.suzukibangladesh.menu.Quiz;
import www.icebd.com.suzukibangladesh.menu.RequestServices;
import www.icebd.com.suzukibangladesh.menu.SOS;
import www.icebd.com.suzukibangladesh.menu.SocialMedia;
import www.icebd.com.suzukibangladesh.menu.SpareParts;
import www.icebd.com.suzukibangladesh.reg.ChangePassword;
import www.icebd.com.suzukibangladesh.reg.Logout;
import www.icebd.com.suzukibangladesh.reg.ResetPassword;
import www.icebd.com.suzukibangladesh.reg.Signup;


public class FirstActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse

{

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(0);
            //navigationView.getMenu().getItem(0).setChecked(true);
        }


    /*    TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String uid = telephonyManager.getDeviceId();
        Log.i("Test",uid);*/

      /*  SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth_key",);
        editor.apply();*/

       /* HashMap<String, String> postData = new HashMap<String, String>();
        postData.put("unique_device_id","152698785698536562214852");
        postData.put("notification_key", "2");
        postData.put("platform","2");
        PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
        loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/getAuthKey");*/
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_notifiction) {
            return true;
        }
        else if (id==R.id.action_find_location)
        {
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
                    .replace(R.id.container, NewsEvents.newInstance())
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


    @Override
    public void processFinish(String output) {
        Log.i("Test",output);

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
}
