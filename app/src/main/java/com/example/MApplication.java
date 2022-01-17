package com.example;

import android.app.Application;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.verify.LoadedApkHuaWei;

import org.jetbrains.annotations.NotNull;

/**
 * Des
 *
 * @author WangJian on 2021/2/22 17
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        hook();
    }

    public void hook() {
        LoadedApkHuaWei.hookHuaWeiVerifier(this);
    }
}
