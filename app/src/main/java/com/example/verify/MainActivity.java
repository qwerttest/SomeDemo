package com.example.verify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bitmaprecycledemo.R;
import com.example.bitmaprecycledemo.TestService;

/**
 * <br/><br/>
 *
 * @author llew
 * @date 2018/1/14
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
    }

    public void register(View view) {
        for (int i = 1; i <= 1; i++) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("hook index : " + i);
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                }
            }, filter);
            Log.e(getClass().getName(), "index：" + i);
        }
    }

    public void hook(View view) {
        // LoadedApkHuaWei.hookHuaWeiVerifier(SimpleApplication.INSTANCE);
        LoadedApkHuaWei.hookHuaWeiVerifier(getApplication(), new LoadedApkHuaWei.TooManyBroadcastCallback() {
            @Override
            public void tooManyBroadcast(int currentIndex, int totalCount) {
                Toast.makeText(getApplicationContext(), "最多只能注册" + totalCount + "个广播接收器", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void inflate(View view) {
        ViewStub stub = findViewById(R.id.stub);
        if(stub != null){
            stub.inflate();
        }
    }
}
