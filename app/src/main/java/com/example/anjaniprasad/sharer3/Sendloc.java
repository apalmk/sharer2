package com.example.anjaniprasad.sharer3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Sendloc extends AppCompatActivity {
    TextView tx;
    InputStream is;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendloc);
        tx = (TextView) findViewById(R.id.txtview);
        new LongOperation().execute("");

    }
    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            String result="not";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = null;
                DefaultHttpClient client = new DefaultHttpClient();
                httppost = new HttpPost("http://192.168.43.91/sharer/android_connect/getmessage1.php");
                httppost.setHeader("Host","localhost");
                httppost.setHeader("Connection","keep-alive");
                httppost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
                httppost.setHeader("Upgrade-Insecure-Requests","1");
                httppost.setHeader(" Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8 ");
                httppost.setHeader("DNT","1");
                httppost.setHeader("Accept-Encoding","gzip, deflate, br");
                httppost.setHeader("Accept-Language","en-US,en;q=0.9,te-IN;q=0.8,te;q=0.7");
                httppost.setEntity(new UrlEncodedFormEntity(new ArrayList<NameValuePair>(), "UTF-8"));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection" + e.toString());
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                sb.append(reader.readLine() + "\n");
                String line = "0";
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                sb.deleteCharAt(sb.length() - 1);
                is.close();
                result = sb.toString();
            } catch (Exception e) {
                Log.e("log_tag", "Error converting result " + e.toString());
            }



            System.out.println(result);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            tx.setText(result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}


