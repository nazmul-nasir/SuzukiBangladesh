package www.icebd.com.suzukibangladesh.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.notification.QuickstartPreferences;
import www.icebd.com.suzukibangladesh.notification.RegistrationIntentService;


public class Splash extends Activity {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 5000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        pref = getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", MODE_PRIVATE);
        editor = pref.edit();
        String notification_key = pref.getString("gcm_registration_token", null);
        if (notification_key == null)
        {
            //progressDialog = ProgressDialog.show(getApplicationContext(),null,null);
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // Toast.makeText(this,"Received Notification ",Toast.LENGTH_LONG).show();
                    Toast.makeText(Splash.this, "Received Notification ", Toast.LENGTH_LONG).show();
                    //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    //progressDialog.dismiss();
                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sharedPreferences
                            .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                            /*if (sentToken) {
                                mInformationTextView.setText(getString(R.string.gcm_send_message));

                            } else {
                                mInformationTextView.setText(getString(R.string.token_error_message));
                            }*/

                            /*  Intent intent1 = new Intent(context.getApplicationContext(), FirstActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startService(intent1);*/

                    /*Intent i = new Intent();
                    i.setClassName("www.icebd.com.suzukibangladesh", "www.icebd.com.suzukibangladesh.FirstActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);*/

                    Log.i("Test", "I am from onReceive end");
                }
            };
            // Registering BroadcastReceiver
            //registerReceiver();

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(Splash.this, RegistrationIntentService.class);
                startService(intent);
            }
        }


        final ImageView iv = (ImageView) findViewById(R.id.img_logo);
        //  final ImageView iv1 = (ImageView) findViewById(R.id.img_txt);
        //final ImageView imgText = (ImageView) findViewById(R.id.imageViewText);


        //   final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        final Animation an3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out);
        //final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);


        //  iv1.startAnimation(an1);
        iv.startAnimation(an1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               /* iv1.setVisibility(ImageView.VISIBLE);
                 iv.setVisibility(ImageView.INVISIBLE);*/
               // iv1.startAnimation(an3);
                iv.startAnimation(an3);
            }
        }, 2000);// delay in milliseconds (200)


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {

                    finish();
                    pref = getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", MODE_PRIVATE);
                    editor = pref.edit();
                    String notification_key = pref.getString("gcm_registration_token", null);
                    editor.putString("running", "no");
                    editor.apply();
                    Intent i;
                    i = new Intent(getBaseContext(), FirstActivity.class);
                    /*if (notification_key==null)
                    {
                        i = new Intent(getBaseContext(), MainActivity.class);
                    }
                    else {
                        i = new Intent(getBaseContext(), FirstActivity.class);
                    }*/
                    startActivity(i);

                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                    Log.i("exception: ", ex.getMessage());
                }

                //startActivity(i);
            }
        }, 4000);// delay in milliseconds (200)
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                //Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
