package com.example.exceptions.deadobject;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ApplicationErrorReport;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;

import com.llew.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 定位Service not registered异常，确定后移除
 *
 * @author wangjian
 */

public final class ServiceMonitor {
    private static final HuaWeiVerifier IMPL;

    static {
        final int version = android.os.Build.VERSION.SDK_INT;
        if (version >= 28) {
            IMPL = new V28VerifierImpl();
        } else if (version >= 26) {
            IMPL = new V26VerifierImpl();
        } else if (version >= 24) {
            IMPL = new V24VerifierImpl();
        } else {
            IMPL = new BaseVerifierImpl();
        }
    }

    public static void hookHuaWeiVerifier(Application application) {
        hookHuaWeiVerifier(application, null);
    }

    public static void hookHuaWeiVerifier(Application application, TooManyBroadcastCallback callback) {
        try {
            if (null != application) {
                IMPL.verifier(application.getBaseContext(), callback);
            } else {
                Log.w(ServiceMonitor.class.getSimpleName(), "application is null ！！！");
            }
        } catch (Throwable ignored) {
            // ignore it
        }
    }

    private static class V28VerifierImpl extends V26VerifierImpl {
        private static final String ACTIVITY_MANAGER    = "android.app.IActivityManager";

        @Override
        public boolean verifier(Context baseContext, TooManyBroadcastCallback callback) throws Throwable {
            final boolean verified = super.verifier(baseContext, callback);
            Log.v(ServiceMonitor.class.getSimpleName(), "verified: " + verified);
            hookActivityManagerService(baseContext.getClassLoader(), callback);
            return verified;
        }

        private void hookActivityManagerService(ClassLoader loader, TooManyBroadcastCallback callback) {
            try {
                Object IActivityManagerSingletonObject = FieldUtils.readStaticField(ActivityManager.class.getName(), "IActivityManagerSingleton");
                if (null != IActivityManagerSingletonObject) {
                    Object IActivityManagerObject = FieldUtils.readField(IActivityManagerSingletonObject, "mInstance");
                    if (null != IActivityManagerObject) {
                        AmsInvocationHandler handler = new AmsInvocationHandler(IActivityManagerObject, callback);
                        Class<?> IActivityManagerClass = Class.forName(ACTIVITY_MANAGER);
                        Object proxy = Proxy.newProxyInstance(loader, new Class<?>[]{IActivityManagerClass}, handler);
                        FieldUtils.writeField(IActivityManagerSingletonObject, "mInstance", proxy);
                    }
                }
            } catch (Throwable ignored) {
                // ignore it
            }
        }

        private static class AmsInvocationHandler implements InvocationHandler {

            private Object mIActivityManagerObject;
            private String objStr;
            private int objHashCode;
            private int pid;

            private AmsInvocationHandler(Object mIActivityManagerObject, TooManyBroadcastCallback callback) {
                this.mIActivityManagerObject = mIActivityManagerObject;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                final String methodName = method.getName();
                if(TextUtils.equals("bindService", methodName)){
                    try {
                        Intent intent = (Intent) args[2];
                        Object obj = args[4];
                        ComponentName componentName = intent.getComponent();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(TextUtils.equals("unbindService", methodName)){
                    try {
                        Object obj = args[0];
                        objStr = obj.toString();
                        objHashCode = obj.hashCode();
                        pid = Process.myPid();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(TextUtils.equals("handleApplicationCrash", methodName)){
                    try {
                        ApplicationErrorReport.CrashInfo crashInfo = (ApplicationErrorReport.CrashInfo) args[1];
                        if(crashInfo.exceptionMessage.contains("Service not registered")){
//                            throw new NullPointerException("空指针");
                        }
                        return null;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if(TextUtils.equals("reportKillProcessEvent", methodName)){
                    return null;
                }
                return method.invoke(mIActivityManagerObject, args);
            }
        }
    }

    private static class V26VerifierImpl extends BaseVerifierImpl {

        private static final String WHITE_LIST = "mWhiteListMap";

        @Override
        public boolean verifier(Context baseContext, TooManyBroadcastCallback callback) throws Throwable {
            Object whiteListMapObject = getWhiteListObject(baseContext, WHITE_LIST);
            if (whiteListMapObject instanceof Map) {
                Map whiteListMap = (Map) whiteListMapObject;
                List whiteList = (List) whiteListMap.get(0);
                if (null == whiteList) {
                    whiteList = new ArrayList<>();
                    whiteListMap.put(0, whiteList);
                }
                whiteList.add(baseContext.getPackageName());
                return true;
            }
            return false;
        }
    }

    private static class V24VerifierImpl extends BaseVerifierImpl {

        private static final String WHITE_LIST = "mWhiteList";

        @Override
        public boolean verifier(Context baseContext, TooManyBroadcastCallback callback) throws Throwable {
            Object whiteListObject = getWhiteListObject(baseContext, WHITE_LIST);
            if (whiteListObject instanceof List) {
                List whiteList = (List) whiteListObject;
                whiteList.add(baseContext.getPackageName());
                return true;
            }
            return false;
        }
    }

    private static class BaseVerifierImpl implements HuaWeiVerifier {

        private static final String WHITE_LIST = "mWhiteList";

        @Override
        public boolean verifier(Context baseContext, TooManyBroadcastCallback callback) throws Throwable {
            Object receiverResourceObject = getReceiverResourceObject(baseContext);
            Object whiteListObject = getWhiteListObject(receiverResourceObject, WHITE_LIST);
            if (whiteListObject instanceof String[]) {
                String[] whiteList = (String[]) whiteListObject;
                List<String> newWhiteList = new ArrayList<>();
                newWhiteList.add(baseContext.getPackageName());
                Collections.addAll(newWhiteList, whiteList);
                FieldUtils.writeField(receiverResourceObject, WHITE_LIST, newWhiteList.toArray(new String[newWhiteList.size()]));
                return true;
            } else {
                if (null != receiverResourceObject) {
                    FieldUtils.writeField(receiverResourceObject, "mResourceConfig", null);
                }
            }
            return false;
        }

        Object getWhiteListObject(Context baseContext, String whiteList) {
            return getWhiteListObject(getReceiverResourceObject(baseContext), whiteList);
        }

        private Object getWhiteListObject(Object receiverResourceObject, String whiteList) {
            try {
                if (null != receiverResourceObject) {
                    return FieldUtils.readField(receiverResourceObject, whiteList);
                }
            } catch (Throwable ignored) {
            }
            return null;
        }

        private Object getReceiverResourceObject(Context baseContext) {
            try {
                Field receiverResourceField = FieldUtils.getDeclaredField("android.app.LoadedApk", "mReceiverResource", true);
                if (null != receiverResourceField) {
                    Field packageInfoField = FieldUtils.getDeclaredField("android.app.ContextImpl", "mPackageInfo", true);
                    if (null != packageInfoField) {
                        Object packageInfoObject = FieldUtils.readField(packageInfoField, baseContext);
                        if (null != packageInfoObject) {
                            return FieldUtils.readField(receiverResourceField, packageInfoObject, true);
                        }
                    }
                }
            } catch (Throwable ignored) {
            }
            return null;
        }
    }

    private interface HuaWeiVerifier {
        boolean verifier(Context baseContext, TooManyBroadcastCallback callback) throws Throwable;
    }

    public interface TooManyBroadcastCallback {
        void tooManyBroadcast(int registeredCount, int totalCount);
    }
}