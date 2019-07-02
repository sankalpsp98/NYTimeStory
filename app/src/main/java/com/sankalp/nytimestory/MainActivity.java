package com.sankalp.nytimestory;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //test push one
    private RecyclerView recyclerView ;
    private  RecyclerView.Adapter adapter;
    List<results> resultsList;
    results results;
    String url =  "https://api.nytimes.com/svc/topstories/v2/science.json";
    private static final String TAG = "MyPeriodicWork";
    SwipeRefreshLayout swipeContainer;
    dataWire dataWire = new dataWire();
    OneTimeWorkRequest oneTimeWorkRequest1;
    int a=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        oneTimeWorkRequest1 = new   OneTimeWorkRequest.Builder(workManager.class).addTag("newsWorker").build();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsList = new ArrayList<>();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (dataWire.getResultsDataWire().size()==0) {
            swipeContainer.setRefreshing(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    WorkManager.getInstance().beginWith(oneTimeWorkRequest1).enqueue();

                }
            }, 10);
        }else
        {
            resultsList=dataWire.getResultsDataWire();

            Log.e("works Status ", "finished"+resultsList.size());
            adapter = new newsAdapter(resultsList,getApplicationContext());
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI

                    recyclerView.setAdapter(adapter);
                }
            });


        }




        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter!=null)
                        {
                            adapter.notifyDataSetChanged();
                            swipeContainer.setRefreshing(false);
                        }else {
                            WorkManager.getInstance().beginWith(oneTimeWorkRequest1).enqueue();
                        }
                    }
                },2000);

            }
        });





     /*   for (int i = 0; i < 4; i++) {
            a++;
            results = new results("hello heding", "abtract" +a, "hjsajbsjhbjsvascas", "dsdsd", "wddbjkdnjsk");
            Log.e("num",""+(i+1));
            resultsList.add(results);
        }*/





        WorkManager.getInstance().getStatusById(oneTimeWorkRequest1.getId()).observe(this, new Observer<WorkStatus>() {
            @Override
            public void onChanged(@Nullable WorkStatus listLiveData) {
                if (listLiveData != null && listLiveData.getState().isFinished()) {
                    try {
                        Thread.sleep(4000);
                        resultsList=dataWire.getResultsDataWire();

                        Log.e("works Status ", "finished"+resultsList.size());
                        adapter = new newsAdapter(resultsList,getApplicationContext());
                        adapter.notifyDataSetChanged();
                        swipeContainer.setRefreshing(false);
                    }catch (Exception e) {
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            // Stuff that updates the UI

                            recyclerView.setAdapter(adapter);
                        }
                    });




                }
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                adapter.notifyDataSetChanged();

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {

        super.onStart();
    }
}
