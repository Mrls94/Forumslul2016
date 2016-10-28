package com.example.mbalza.forumslul2016;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Topics extends AppCompatActivity {

    ListView listView;

    /*String[] values = {"General \nPosts: 15   Participantes: 5", "Android Studio\nPosts: 15 Participantes: 5", "Movil " +
            "\nPosts: 15 Participantes: 5","Proyectos\n" +
            "Posts: 15 Participantes: 5","Librerias\n" +
            "Posts: 15 Participantes: 5"};*/
    String[] values = {" "};

    String Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        String Display = getIntent().getStringExtra("Displayname");
        String id = getIntent().getStringExtra("id");


        LoginAsync loginAsync = new LoginAsync();
        loginAsync.execute(id);

        int length = id.length();

        //Toast.makeText(this, Display+" "+length, Toast.LENGTH_SHORT).show();

        listView = (ListView) findViewById(R.id.TopicsListView);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenulayout,menu);

        //return super.onCreateOptionsMenu(menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.signout:
                finish();
                return true;
            case R.id.NewTopic:
                Intent intent = new Intent(this,NewTopic.class);
                startActivityForResult(intent,2);
            default:
                return super.onOptionsItemSelected(item);


        }


    }

    public void onPostLogin(String userid)
    {
        Userid = userid;
        getTopicsAsync gettopics = new getTopicsAsync();
        gettopics.execute(userid);
    }

    public void onPostgetTopics(String result)
    {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        String[] val = result.split("&");


        for (int i=1; i<val.length; i++)
        {
            values[i-1] = val[i];
        }

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1,values);

        listView.setAdapter(myadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Topics.this,PostList.class);
                intent.putExtra("Content", values[position]);
                intent.putExtra("user", Userid);
                startActivityForResult(intent,3);

            }
        });
    }

    public class getTopicsAsync extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Topics.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            onPostgetTopics(s);
        }

        @Override
        protected String doInBackground(String... params) {

            String userid = params[0];

            try {
                URL url = new URL("http://190.144.171.172/g1movil/getTopics.php");

                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpsURLConnection.getOutputStream()));

                String data = "userid="+userid;

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

    public class LoginAsync extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Topics.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            Toast.makeText(Topics.this,s,Toast.LENGTH_SHORT).show();
            onPostLogin(s);
        }

        @Override
        protected String doInBackground(String... params) {

            String token = params[0];

            try {
                URL url = new URL("http://190.144.171.172/g1movil/login.php");
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpsURLConnection.getOutputStream()));

                String data = "token="+token;

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
