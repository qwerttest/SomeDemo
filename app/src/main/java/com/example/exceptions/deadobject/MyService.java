package com.example.exceptions.deadobject;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class MyService extends Service {
    private int serviceId;
    private boolean unBind;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceId = startId;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        unBind = super.onUnbind(intent);
        return unBind;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public void send(String e) throws RemoteException {
            if(unBind){
                return;
            }
            System.out.println("Msg=" + e);
        }

        @Override
        public void stop() throws RemoteException {
            MyService.this.stopSelf();
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, FLAG_ONEWAY);
        }
    }
}