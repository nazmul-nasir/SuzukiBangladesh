package www.icebd.com.suzukibangladesh.quiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import www.icebd.com.suzukibangladesh.FirstActivity;
import www.icebd.com.suzukibangladesh.R;
import www.icebd.com.suzukibangladesh.json.AsyncResponse;
import www.icebd.com.suzukibangladesh.json.PostResponseAsyncTask;


public class Quiz extends Fragment implements AsyncResponse, View.OnClickListener {
    private static final String TAG ="Suzuki Bangladesh" ;


    public static int questionsNo=0;
    ArrayList<HashMap<String, String>> arrList;
    List<Object> sections = new ArrayList <Object>();

    RadioButton rda,rdb,rdc,rdd;
    RadioGroup rdaGroupquiz;
    TextView txtQuestion, txtTitle,tv;
    Button btnNext;
    private Thread thread;
    boolean isRunning =false;
    boolean isQuizFinish =false;
    CountDownTimer countDownTimer = null;

    private static int index=0,callingIndex=0;
    View rootView;
    LayoutInflater inflater_temp;
    ViewGroup container_temp;

    JSONObject jsonObj ;
    JSONObject quizes ;
    String description ;
    String title ;
    String end ;
    String start;
    String auth_key,quizTitle,quizId;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    String question[]=new String[20] ,answerId[]=new String[20],questionId[]=new String[20],op1[]=new String[20],op2[]=new String[20],op3[]=new String[20],op4[]=new String[20],opId1[]=new String[20],opId2[]=new String[20],opId3[]=new String[20],opId4[]=new String[20];


    public static Quiz newInstance() {
        Quiz fragment = new Quiz();
        return fragment;
    }

    public Quiz() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_quiz, container,
                false);

        container_temp=container;
        inflater_temp=inflater;

        txtQuestion=(TextView)rootView.findViewById(R.id.textView1);
        txtTitle=(TextView)rootView.findViewById(R.id.textView);

        tv=(TextView)rootView.findViewById(R.id.tv);
        rda=(RadioButton)rootView.findViewById(R.id.radio0);
        rdb=(RadioButton)rootView.findViewById(R.id.radio1);
        rdc=(RadioButton)rootView.findViewById(R.id.radio2);
        rdd=(RadioButton)rootView.findViewById(R.id.radio3);
        btnNext=(Button)rootView.findViewById(R.id.button1);
        rdaGroupquiz = (RadioGroup)rootView.findViewById(R.id.radioGroup1);

        pref = getActivity().getApplicationContext().getSharedPreferences("SuzukiBangladeshPref", getActivity().MODE_PRIVATE);
        editor = pref.edit();


        auth_key= pref.getString("auth_key","empty");

        if(!auth_key.equals("empty"))
        {
            HashMap<String, String> postData = new HashMap<String, String>();

            postData.put("auth_key",auth_key);

            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/getquizDetail");

        }
        else
        {
            Toast.makeText(getActivity(),"Connect to internet and restart the app",Toast.LENGTH_LONG).show();
        }




        btnNext.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void processFinish(String output) {

        Log.i("Test","quiz: "+output);

        try {
            JSONObject object = new JSONObject(output);
            String message =object.getString("message");
            String status_code =object.getString("status_code");

            if(message.equals("Success"))
            {
                JSONObject quizes =object.getJSONObject("quizes");
                String title=quizes.getString("title");
                quizId = quizes.getString("id");
                quizTitle=title;
                Log.i("Test","title :"+title);

                JSONArray questions =quizes.getJSONArray("questions");
                txtTitle.setText(title);

                questionsNo= questions.length();

                for (int i = 0; i <questions.length() ; i++) {

                    HashMap<String, String> map = new HashMap();
                    JSONObject question_details=questions.getJSONObject(i);
                    question[i] = question_details.getString("question");
                    questionId[i] = question_details.getString("question_id");

                    // map.put("question",question);
                    // map.put("question_id",question_id);

                    Log.i("Test","question :"+question[i]);

                    JSONArray options = question_details.getJSONArray("options");
                    // for (int j = 0; j <options.length() ; j++) {
                    JSONObject options_details = options.getJSONObject(0);
                    op1[i] =options_details.getString("option");
                    opId1[i] = options_details.getString("option_id");
                    Log.i("Test","option1 : "+ op1[i]);

                    JSONObject options_details1 = options.getJSONObject(1);
                    op2[i] =options_details1.getString("option");
                    opId2[i] = options_details1.getString("option_id");
                    Log.i("Test","option2 : "+ op2[i]);

                    JSONObject options_details2 = options.getJSONObject(2);
                    op3[i] =options_details2.getString("option");
                    opId3[i] = options_details2.getString("option_id");
                    Log.i("Test","option3 : "+ op3[i]);

                    JSONObject options_details3 = options.getJSONObject(3);
                    op4[i] =options_details3.getString("option");
                    opId4[i] = options_details3.getString("option_id");
                    Log.i("Test","option4 : "+ op4[i]);





                }
            }
            else if(status_code.equals("109"))
            {
                rootView = inflater_temp.inflate(R.layout.fragment_quiz, container_temp,
                        false);



            }



        } catch (JSONException e) {
            try {
                JSONObject answer = new JSONObject(output);
                Log.i("Test","Quiz Answer"+output);
               // String
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        showQuizes(callingIndex);

    }

    void showQuizes(int indexNow)
    {

        //setContentView(R.layout.fragment_quiz);


        if (questionsNo>indexNow) {

            txtQuestion.setText(question[indexNow]);
            rda.setText(op1[indexNow]);
            rdb.setText(op2[indexNow]);
            rdc.setText(op3[indexNow]);
            rdd.setText(op4[indexNow]);

            reverseTimer(30);
        }
        else {
            txtQuestion.setText("Quiz Completed");
            rda.setVisibility(View.INVISIBLE);
            rdb.setVisibility(View.INVISIBLE);
            rdc.setVisibility(View.INVISIBLE);
            rdd.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
            btnNext.setText("Finish");
            isQuizFinish=true;

        }


    }

    public void reverseTimer(int Seconds){


        if (!isRunning) {
            isRunning=true;
            countDownTimer = new CountDownTimer(Seconds * 1000 + 1000, 1000) {



                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    seconds = seconds % 60;
                    tv.setText("TIME : " + String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds));
                }

                public void onFinish() {
                    tv.setText("Completed");
                    showQuizes(++callingIndex);
                }
            }.start();
        }
        else {
            countDownTimer.cancel();
            countDownTimer.start();

        }


    }

    /*public void nextBtnClicked(View view) {


    }*/

    @Override
    public void onClick(View v) {
      //  answerId[callingIndex];



        if(isQuizFinish)
        {

            HashMap<String, String> postData = new HashMap<String, String>();

            String user_id = pref.getString("user_id","424");


            postData.put("auth_key",auth_key);
            Log.i("auth_key",auth_key);
            postData.put("user_id",user_id);
            Log.i("user_id",user_id);
            postData.put("quiz_id",quizId);
            Log.i("quiz_id",quizId);
            String quiz_answer="[";
            ArrayList<HashMap<String, String>> qa=new ArrayList();

            for (int i = 0; i <questionsNo ; i++) {
                quiz_answer =quiz_answer + "{\'question_id\':\'"+questionId[i]+"\',"
                        +"\'answer_id\':\'"+answerId[i]+"\'}";
                /*HashMap<String, String> map1 = new HashMap();
                map1.put("question_id",questionId[i]);
                map1.put("answer_id",answerId[i]);

                qa.add(map1);
*/
            }
            quiz_answer = quiz_answer+"]";

            Log.i("quiz_answer",quiz_answer);

            postData.put("quiz_answer",quiz_answer);

            PostResponseAsyncTask loginTask = new PostResponseAsyncTask(this,postData);
            loginTask.execute("http://icebd.com/suzuki/suzukiApi/Server/quizResult");



            //getActivity().finish();
         //   ((FirstActivity)getActivity()).selectItem(0);

            // Toast.makeText(QuizStarter.this,"You have finished quiz successfully");
        }


        int selectId = rdaGroupquiz.getCheckedRadioButtonId();
        switch (selectId)
        {
            case R.id.radio0:
               answerId[callingIndex]=opId1[callingIndex];
                break;
            case R.id.radio1:
                answerId[callingIndex]=opId2[callingIndex];
                break;
            case R.id.radio2:
                answerId[callingIndex]=opId3[callingIndex];
                break;
            case R.id.radio3:
                answerId[callingIndex]=opId4[callingIndex];
                break;
            default:
                break;
        }

        showQuizes(++callingIndex);

    }
}
