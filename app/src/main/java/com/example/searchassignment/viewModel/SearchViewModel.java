package com.example.searchassignment.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.searchassignment.model.ImagesModel;
import com.example.searchassignment.model.UrlsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchViewModel extends AndroidViewModel {
    ImagesModel imagesModel;
    UrlsModel urlsModel;
    ArrayList<ImagesModel> list;
    public MutableLiveData<ArrayList<ImagesModel>> listMutableLiveData = new MutableLiveData<>();
    public ObservableField<String> search_keyword = new ObservableField<>();

    public SearchViewModel(@NonNull Application application) {
        super(application);
        imagesModel = new ImagesModel();
        urlsModel = new UrlsModel();
        list = new ArrayList<>();
    }

    public void request(String url) {
        new AsyncHttpTask().execute(url);
    }


    /*Send request to get data*/
    public class AsyncHttpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;
            String response = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    response = convertInputStreamToString(inputStream);


                } else {
                }

            } catch (Exception e) {
                Log.d("ghada", e.getLocalizedMessage());
            }

            return response; //"Failed to fetch data!";
        }


        @Override
        protected void onPostExecute(String result) {
            /* Download complete. Lets update UI */
            JSONObject jsonObject;

            try {
                jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                String records = jsonObject.getString("total");

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject data = jsonArray.getJSONObject(i);
                        String blur_hash = data.getString("blur_hash");
                        String updated_at = data.getString("updated_at");
                        JSONObject urlsObj = data.getJSONObject("urls");
                        urlsModel = new UrlsModel(urlsObj.getString("regular"));

                        imagesModel = new ImagesModel(blur_hash, updated_at, urlsModel);

                        list.add(imagesModel);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listMutableLiveData.setValue(null);
                listMutableLiveData.setValue(list);
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }
}
