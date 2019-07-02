package com.sankalp.nytimestory;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

import com.airbnb.lottie.LottieAnimationView;

public class spalshScreen extends AppCompatActivity {
    OneTimeWorkRequest oneTimeWorkRequest1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        dataWire.setUrl("https://api.nytimes.com/svc/topstories/v2/science.json");
        LottieAnimationView lottieAnimationView=findViewById(R.id.animation_view);
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();
        oneTimeWorkRequest1 = new   OneTimeWorkRequest.Builder(workManager.class).addTag("newsWorker").build();
        WorkManager.getInstance().beginWith(oneTimeWorkRequest1).enqueue();


        WorkManager.getInstance().getStatusById(oneTimeWorkRequest1.getId()).observe(this, new Observer<WorkStatus>() {
            @Override
            public void onChanged(@Nullable WorkStatus listLiveData) {
                if (listLiveData != null && listLiveData.getState().isFinished()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            oneTimeWorkRequest1 = new   OneTimeWorkRequest.Builder(workManager.class).addTag("newsWorker").build();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    },2500);

                }

            }
        });
    }
}
