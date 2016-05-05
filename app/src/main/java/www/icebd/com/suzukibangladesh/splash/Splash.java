package www.icebd.com.suzukibangladesh.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.notification.MainActivity;


public class Splash extends Activity {
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

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

                finish();
                pref = getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", MODE_PRIVATE);
                editor = pref.edit();
                String notification_key = pref.getString("gcm_registration_token",null);
                editor.putString("running","no");
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

                //

                startActivity(i);
            }
        }, 4000);// delay in milliseconds (200)






    }
}
