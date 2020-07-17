package com.rizin.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public class DownloadTask extends AsyncTask<String,Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {
                url =new URL(urls[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while(data !=-1){
                    char current = (char) data ;
                    result += current;
                    data = reader.read();
                }
                return result;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String wheatherInfo = jsonObject.getString("wheather");
                Log.i("wheather content", wheatherInfo);
                JSONArray arr = new JSONArray(wheatherInfo);
                for (int i  = 0 ;i<arr.length();i++){
                    JSONObject jsonPart = arr.getJSONObject(i);
                    Log.i("Main",jsonPart.getString("main"));
                    Log.i("Main",jsonPart.getString("description"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadTask task =new DownloadTask();
        String result= null;

        try{
            result = task.execute("http://www.google.com").get();
            String[] splitResult = result.split("");

            Pattern p = Pattern.compile("\"(.*?)\"");
        }catch (Exception e){

        }
    }
}
