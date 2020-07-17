package drawable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class browserAccessbility extends AppCompatActivity {
    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream in  = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data= reader.read();
                while(data !=1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }
                return result;

            } catch (IOException e) {
                e.printStackTrace();
                return "Done";
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String result = null;
        DownloadTask task = new DownloadTask();

        try {
            result = task.execute("www.google.com", "www.stackoverflow.com").get();
        } catch(Exception e) {
            e.printStackTrace();
        }
        Log.i("Result", result);
    }
}
