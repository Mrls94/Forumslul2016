package com.example.mbalza.forumslul2016;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewPost extends AppCompatActivity {

    String userid;
    String topicid;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);


        userid = getIntent().getStringExtra("user");
        topicid = getIntent().getStringExtra("topicid");



    }


    public void SendNewPost(View view) {
        content = ((EditText) findViewById(R.id.editText4)).getText().toString();

        SendPostAsync sendPostAsync = new SendPostAsync();
        sendPostAsync.execute(userid,content,topicid);

    }

    public class SendPostAsync extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewPost.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
        }

        @Override
        protected String doInBackground(String... params) {

            String userid = params[0];
            String content = params[1];
            String topicid = params[2];

            try {
                URL url = new URL("http://190.144.171.172/g1movil/createPost.php");

                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpsURLConnection.getOutputStream()));

                String data = "userid="+userid+"&content="+content+"&topicid="+topicid;

                bufferedWriter.write(data);
                bufferedWriter.flush();


                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));

                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine())!=null)
                {
                    result +=line;
                }


                return result;


            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";
        }
    }
}
