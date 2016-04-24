package www.icebd.com.suzukibangladesh.menu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class Login extends Fragment implements View.OnClickListener, AsyncResponse {
    EditText password,email;
    Button button;

    public static Login newInstance() {
        Login fragment = new Login();
        return fragment;
    }

    public Login() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container,
                false);

        email = (EditText) rootView.findViewById(R.id.login_email);
        password = (EditText) rootView.findViewById(R.id.login_password);

        button=(Button) rootView.findViewById(R.id.button);


        button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (isNetworkAvailable()) {

            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("auth_key","46dde59d2bf7149c4d070f8cba8314e0");
            postData.put("user_email",email.toString());
            postData.put("user_pass",password.toString());




            if (isNetworkAvailable()) {

                PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this, postData);
                loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/login");
            }

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void processFinish(String output) {

        Log.i("Test",output);

    }
}
