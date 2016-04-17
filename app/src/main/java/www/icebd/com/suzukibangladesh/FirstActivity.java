package www.icebd.com.suzukibangladesh;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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


public class FirstActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

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

        } else if (id == R.id.nav_login) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Login.newInstance())
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
