package com.example.shubzz.networkhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "http://192.168.43.191:8000/info/hello-viewset/";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TsunamiAsyncTask task = new TsunamiAsyncTask();
        task.execute();

//        String firstFourChars = "";     //subsctring containing first 4 characters
//
//        if (jsonResponse.length() > 14)
//        {
//            firstFourChars = jsonResponse.substring(0, 14);
//        }
//        else
//        {
//            firstFourChars = jsonResponse;
//        }
//
//        Log.v("yeah", firstFourChars);
    }

//    private URL createUrl(String stringUrl) {
//        URL url = null;
//        try {
//            url = new URL(stringUrl);
//        } catch (MalformedURLException exception) {
//            Log.e("ffadfa", "Error with creating URL", exception);
//            return null;
//        }
//        return url;
//    }
//
//    private String makeHttpRequest(URL url) throws IOException
//    {
//        String jsonResponse = "";
//        HttpURLConnection urlConnection = null;
//        InputStream inputStream = null;
//        try
//        {
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setReadTimeout(10000 /* milliseconds */);
//            urlConnection.setConnectTimeout(15000 /* milliseconds */);
//            urlConnection.connect();
//            inputStream = urlConnection.getInputStream();
//            jsonResponse = readFromStream(inputStream);
//        } catch (IOException e)
//        {
//            // TODO: Handle the exception
//        } finally
//        {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//            if (inputStream != null) {
//                // function must handle java.io.IOException here
//                inputStream.close();
//            }
//        }
//        return jsonResponse;
//    }
//
//    private String readFromStream(InputStream inputStream) throws IOException
//    {
//        StringBuilder output = new StringBuilder();
//        if (inputStream != null) {
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
//            BufferedReader reader = new BufferedReader(inputStreamReader);
//            String line = reader.readLine();
//            while (line != null) {
//                output.append(line);
//                line = reader.readLine();
//            }
//        }
//        return output.toString();
//    }

    private class TsunamiAsyncTask extends AsyncTask<URL, Void, String>
    {

        @Override
        protected String doInBackground(URL... urls)
        {
            // Create URL object
            URL url = createUrl(USGS_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try
            {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e)
            {
                // TODO Handle the IOException
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            String earthquake = (jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return earthquake;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link TsunamiAsyncTask}).
         */
        @Override
        protected void onPostExecute(String earthquake)
        {
            if (earthquake == null)
            {
                Log.v("yeah", "fuck");
                return;
            }
            Log.v("yeah", earthquake);
//            updateUi(earthquake);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl)
        {
            URL url = null;
            try
            {
                url = new URL(stringUrl);
            }
            catch (MalformedURLException exception)
            {
                Log.e("yeah", "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException
        {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try
            {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
//                urlConnection.setReadTimeout(10000 /* milliseconds */);
//                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                String data = URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode("Shubham", "UTF-8");

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write( data );
                wr.flush();
                wr.close();


                urlConnection.connect();


                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e)
            {
                Log.v("yeah", "fuck");
                // TODO: Handle the exception
            } finally
            {
                if (urlConnection != null) {
                    Log.v("yeah", "fuck1");
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    Log.v("yeah", "fuck2");
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException
        {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link String} object by parsing out information
         * about the first earthquake from the input earthquakeJSON string.
         */
//        Log.v("yeah", firstFourChars);
    }
}
