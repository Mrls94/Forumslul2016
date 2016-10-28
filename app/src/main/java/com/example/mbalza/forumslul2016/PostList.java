package com.example.mbalza.forumslul2016;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostList extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    String content;
    String topicid;
    String Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        content = getIntent().getStringExtra("Content");

        topicid = content.split(";")[1];

        Userid = getIntent().getStringExtra("Userid");

        getPostListAsync postlist = new getPostListAsync();
        postlist.execute(topicid);




    }

    public void CreateNewPost(View view) {

        Intent intent = new Intent(this,NewPost.class);
        intent.putExtra("topicid",topicid);
        intent.putExtra("user",Userid);
        startActivity(intent);

    }

    public void onFinish(String result)
    {
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        String data = result;
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view2);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(data);
        recyclerView.setAdapter(mAdapter);
    }

    public class getPostListAsync extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PostList.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.hide();
            onFinish(s);

        }

        @Override
        protected String doInBackground(String... params) {

            String topicid = params[0];

            try {
                URL url = new URL("http://190.144.171.172/g1movil/getPostList.php");

                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpsURLConnection.getOutputStream()));

                String data = "topicid="+topicid;

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
