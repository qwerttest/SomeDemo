package com.example.exceptions.deadobject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bitmaprecycledemo.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainActivity extends Activity{
    private IMyAidlInterface myAidlInterface;
    StringBuilder builder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println(name);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadobject);

        String a = "dfdafsdfafdasdfadfadsfafd";
        builder = new StringBuilder();
        int count = 0;
        while (count < 2000){
            builder.append(a);
            count++;
        }

        builder.append(builder);
        Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
    }

    public void start(View v) {
        final String a = builder.toString();
        try {
            new MyThread(a).start();
            new MyThread(a).start();
            new MyThread(a).start();
            new MyThread(a).start();
            new MyThread(a).start();
        } catch (Exception e) {

        }
    }

    public void bindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        view.getContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(View view) {
        view.getContext().unbindService(serviceConnection);
//        try {
//            myAidlInterface.stop();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unBindService(null);
//        unBindService(null);
    }

    public void hook(View view) {
        ServiceMonitor.hookHuaWeiVerifier(getApplication());
    }

    class MyThread extends Thread{
        String name;

        public MyThread(@NonNull String name) {
            super();
            this.name = name;
        }

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    myAidlInterface.send(name);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                System.out.println("Msg=Send=" + Thread.currentThread().getName());
            }
        }
    }


    public static void hookContext() throws Exception {
//        // 第一步：反射得到 ListenerInfo 对象
//        Method method = Context.class.getDeclaredMethod("unbindService");
//        method.setAccessible(true);
//        Object[] objs = method.g
//
//        Object listenerInfo = method.invoke(view);
//        // 第二步：得到原始的 OnClickListener事件方法
//        Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
//        Field mOnClickListener = listenerInfoClz.getDeclaredField("mOnClickListener");
//        mOnClickListener.setAccessible(true);
//        View.OnClickListener originOnClickListener = (View.OnClickListener) mOnClickListener.get(listenerInfo);
//        // 第三步：用 Hook代理类 替换原始的 OnClickListener
//        View.OnClickListener hookedOnClickListener = new HookedClickListenerProxy(originOnClickListener);
//        mOnClickListener.set(listenerInfo, hookedOnClickListener);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        try {
            super.unbindService(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendBroadcast(Intent intent) {
        super.sendBroadcast(intent);
    }
}