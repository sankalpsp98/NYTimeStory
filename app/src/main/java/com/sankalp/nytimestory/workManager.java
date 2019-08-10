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

    String url =  dataWire.getUrl();
       private static final String TAG = "MyPeriodicWork";
       results results=null;

       List<results> resultsList ;
    String urlLINK;
    boolean status =false;


    @NonNull
    @Override
    public Result doWork() {
        Log.e("url in work",""+dataWire.getUrl());

        resultsList = new ArrayList<>() ;

                  Log.e(TAG, "doWork: Work is done.  "+resultsList.size()+" ");
                  if (getData())
                  {
                      return Result.SUCCESS;
                  }else
                  {
                      return Result.RETRY;
                  }

    }

    public boolean getData()
    {

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
                        resultsList.clear();
                        JSONObject news =  new JSONObject(jsonData);
                        JSONArray resultsA= news.getJSONArray("results") ;
                        Log.e("result",resultsA.length()+"");
                        for (int i=0;i<resultsA.length();i++)
                        {

                            JSONObject o = resultsA.getJSONObject(i);

                            String section = String.valueOf(o.get("section"));
                            String title = String.valueOf(o.get("title"));
                            String abstrac = String.valueOf(o.get("abstract"));
                            String url= String.valueOf(o.get("url"));
                            String by = String.valueOf(o.get("byline"));
                            //FOR IMG
                            JSONArray mURLs =o.getJSONArray("multimedia");
                            try {
                                JSONObject imgURL = mURLs.getJSONObject(4);
                                urlLINK = String.valueOf(imgURL.get("url"));
                            }catch (Exception e)
                            {
                                urlLINK = null;
                            }

                            results = new results(section,title,abstrac,url,by,urlLINK) ;
                            resultsList.add(results);
                            status = true;


                        }



                        Log.d("size of result",resultsList.size()+"");
                        //adapter.notifyDataSetChanged();

                        dataWire.setResultsDataWire(resultsList);


                        Log.e(TAG, "doWork: Work ."+resultsList.size()+results.getAbstrac());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }



                }
            }
        });
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }


        return status;
    }


}
