package com.sankalp.nytimestory;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import androidx.work.Worker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class workManager extends Worker {

    String url =  "https://api.nytimes.com/svc/topstories/v2/science.json";
       private static final String TAG = "MyPeriodicWork";
       results results=null;

       List<results> resultsList ;
    @NonNull
    @Override
    public Result doWork() {
        resultsList = new ArrayList<>() ;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("api-key","A4omrGdE8oSGK7WZdWgjkjesaxsvXiAv");
       String e = urlBuilder.build().toString();
         Log.e("url >>>>>>",e);
        //Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();

        final Request request =  new  Request.Builder().url(e).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful())
                    {
                       String jsonData  =  response.body().string();
                        Log.e("JSON DATA",jsonData);
                        try {
                            JSONObject news =  new JSONObject(jsonData);
                            JSONArray resultsA= news.getJSONArray("results") ;
                            Log.e("result",resultsA.length()+"");
                           for (int i=0;i<resultsA.length();i++)
                           {
                               JSONObject o = resultsA.getJSONObject(i);

                               results = new results(o.getString("section"),o.getString("title"),o.getString("abstract"),o.getString("url"),o.getString("byline")) ;
                               try {
                                   Thread.sleep(7);
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
                           }
                           resultsList.add(results);
                            dataWire.setResultsDataWire(resultsList);


                            Log.e(TAG, "doWork: Work is done.  "+resultsList.size()+results.getAbstrac());
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
            }
        });


                  Log.e(TAG, "doWork: Work is done.  "+resultsList.size());
        return Result.SUCCESS;
    }
}