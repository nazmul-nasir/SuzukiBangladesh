package www.icebd.com.suzukibangladesh.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.notification.MainActivity;


public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);
        final ImageView iv1 = (ImageView) findViewById(R.id.imageView1);
        final ImageView imgText = (ImageView) findViewById(R.id.imageViewText);


        final Animation an = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        final Animation an1 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
        final Animation an3 = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_out);
        //final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);


                iv1.startAnimation(an1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               /* iv1.setVisibility(ImageView.VISIBLE);
                 iv.setVisibility(ImageView.INVISIBLE);*/
                iv1.startAnimation(an3);
            }
        }, 2000);// delay in milliseconds (200)


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
            }
        }, 4000);// delay in milliseconds (200)





        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Toast.makeText(Splash.this,"I am start",Toast.LENGTH_LONG).show();

            }



            @Override
            public void onAnimationEnd(Animation animation) {
               /* Toast.makeText(Splash.this,"I am end",Toast.LENGTH_LONG).show();
                iv.setVisibility(View.GONE);
                iv1.setVisibility(View.VISIBLE);

                iv1.startAnimation(an1);
                iv1.startAnimation(an3);

                // iv.startAnimation(an2);
                finish();
                Intent i = new Intent(getBaseContext(),MainActivity.class);
                startActivity(i);*/
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(Splash.this,"I am end",Toast.LENGTH_LONG).show();


            }
        });
    }
}
