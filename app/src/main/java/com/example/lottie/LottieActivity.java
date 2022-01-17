package com.example.lottie;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.airbnb.lottie.LottieAnimationView;
import com.example.bitmaprecycledemo.R;

/**
 * Des
 *
 * @author WangJian on 2021/3/15 16
 */
public class LottieActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Settings.Global.putFloat(getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE,1.0f);
        setContentView(R.layout.activity_lottie);
    }

    public void play(View view) {
        LottieAnimationView lotView = findViewById(R.id.anvCountDown);
        lotView.playAnimation();
    }
}
