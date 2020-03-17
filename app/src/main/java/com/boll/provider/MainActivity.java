package com.boll.provider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.boll.xirilib.Pcm.Pcm2Mp3;
import com.boll.xirilib.PluginUtils;
import com.boll.xirilib.ProcessUtils;
import com.boll.xirilib.XiriUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author caijianhui
 * @date 2019/3/1 14:20
 * @description
 */
public class MainActivity extends Activity {

    private TextView mTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.show_content);
        Log.i("hjc", "processname = " + ProcessUtils.getCurrentProcessName());

        //PluginUtils.getIdentifierInner2("common_robot_avatar", getPackageName());
        //PluginUtils.getThirdPartyDrawable("compress", "com.example.mainplugin");
        //updateBg(null);
    }

    public void doKeyShowFloatRobotCmd(View view) {
        final Bundle bundle = XiriUtil.performKeyShowFloatRobot(getApplicationContext());
        //final Bundle bundle = XiriUtil.performSwitchEvaluation(getApplicationContext());



        mTv.setText("doKeyShowFloatRobotCmd " + bundle.getBoolean("success"));

        //getIdentifierInner3("common_client_avatar", getPackageName());


        Pcm2Mp3.pcm2Mp3(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.pcm", Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.mp3");
    }

    public void doKeyHideFloatRobotCmd(View view) {
        final Bundle bundle = XiriUtil.performKeyHideFloatRobot(getApplicationContext());
        mTv.setText("doKeyHideFloatRobotCmd " + bundle.getBoolean("success"));
    }

    public void doIsFloatRobotShowCmd(View view) {
        final Bundle bundle = XiriUtil.performIsFloatRobotShow(getApplicationContext());
        mTv.setText("doIsFloatRobotShowCmd " + bundle.getBoolean("success"));
    }


    /*public void doKeyDownCmd(final View view) {
        final Bundle bundle = XiriUtil.INSTANCE.performKeyDown(getApplicationContext());
        mTv.setText("doKeyDownCmd " + bundle.getBoolean("success"));
       *//* mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                doKeyUpCmd(view);

                mTv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doKeyDownCmd(view);
                    }
                }, 2000);
            }
        }, 4000);*//*
    }*/

    /*public void doKeyUpCmd(final View view) {
        Bundle bundle = XiriUtil.performKeyUp(getApplicationContext());
        mTv.setText("doKeyUpCmd " + bundle.getBoolean("doKeyUpCmd success"));

    }

    public void doKeyBackCmd(View view) {
        Bundle bundle = XiriUtil.performKeyBack(getApplicationContext());
        mTv.setText("doKeyBackCmd " + bundle.getBoolean("doKeyBackCmd success"));
    }*/

    public void doKeyEnterWakeupModeCmd(View view) {
        final Bundle bundle = XiriUtil.performEnterWakeupMode(getApplicationContext());
        mTv.setText("doKeyEnterWakeupModeCmd " + bundle.getBoolean("success"));
    }

    public void doKeyExitWakeupModeCmd(View view) {
        final Bundle bundle = XiriUtil.performExitWakeupMode(getApplicationContext());
        mTv.setText("doKeyExitWakeupModeCmd " + bundle.getBoolean("success"));
    }

    public void doKeyIsWakeupModeCmd(View view) {
        final Bundle bundle = XiriUtil.performIsWakeupMode(getApplicationContext());
        mTv.setText("doKeyIsWakeupModeCmd " + bundle.getBoolean("success"));
    }

    public void doKeySwitchElistenSpeakModeCmd(View view) {
        final Bundle bundle = XiriUtil.performSwitchEvaluation(getApplicationContext());
        //final Bundle bundle = XiriUtil.performSwitchMode(getApplicationContext(), 1, "common_robot_avatar", getPackageName(), 27, 20, 50, true);
        mTv.setText("doKeySwitchElistenSpeakModeCmd " + bundle.getBoolean("success"));
    }

    public void doKeySwitchKnowledgeViewModeCmd(View view) {
        final Bundle bundle = XiriUtil.performSwitchKnowledgeVideo(getApplicationContext());
        //final Bundle bundle = XiriUtil.performSwitchMode(getApplicationContext(), 2, "", getPackageName(), 17, 1900 , 900, false);
        mTv.setText("doKeySwitchKnowledgeViewModeCmd " + bundle.getBoolean("success"));
    }

    public void doKeySwitchLanguagePointModeCmd(View view) {
        final Bundle bundle = XiriUtil.performSwitchLanguagePoint(getApplicationContext());
        //final Bundle bundle = XiriUtil.performSwitchMode(getApplicationContext(), 0, "common_robot_avatar", getPackageName(), 0, 0, 0, false);
        mTv.setText("doKeySwitchLanguagePointModeCmd " + bundle.getBoolean("success"));
    }

    private void shutDown() {
        try {
            //获得ServiceManager类
            Class ServiceManager = Class
                    .forName("android.os.ServiceManager");
            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            //获得IPowerManager.Stub类
            Class cStub = Class
                    .forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown", boolean.class, boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager, false, true);
        } catch (Exception e) {
        }

    }


    private void reBoot() {

        Intent i = new Intent(Intent.ACTION_REBOOT);
        i.putExtra("nowait", 1);
        i.putExtra("interval", 1);
        i.putExtra("window", 0);
        sendBroadcast(i);

    }

    /**
     * 通过Runtime，发送指令，重启系统，测试结果，不起作用，可能需要root
     */
    private void reBoot2() {

        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "reboot "}); //关机
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void reBoot3() {
        /*弹出重启设备菜单 */
        PowerManager pManager = (PowerManager) getSystemService(Context.POWER_SERVICE); //重启到fastboot模式
        pManager.reboot("重启");

    }

    private void shutDown2() {
        try {
            //获得ServiceManager类
            Class shutdownThread = Class
                    .forName("com.android.server.power.ShutdownThread");
            //获得ServiceManager的getService方法
            Method shutdown = shutdownThread.getMethod("shutdown", Context.class, boolean.class);
            //调用getService获取RemoteService
            shutdown.invoke(null, this, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getIdentifierInner3(final String resourceName, final String packageName) {

        try {
            //Context pluginContext = Utils.getApp().createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            //第一个参数为包含dex的apk或者jar的路径，第二个参数为父加载器
            PathClassLoader pathClassLoader = new PathClassLoader(MainActivity.this.getPackageResourcePath(), ClassLoader.getSystemClassLoader());
//        Class<?> clazz = pathClassLoader.loadClass(packageName + ".R$mipmap");//通过使用自身的加载器反射出mipmap类进而使用该类的功能
            //参数：1、类的全名，2、是否初始化类，3、加载时使用的类加载器
            Class<?> clazz = Class.forName(packageName + ".R$drawable", true, pathClassLoader);
            //使用上述两种方式都可以，这里我们得到R类中的内部类mipmap，通过它得到对应的图片id，进而给我们使用
            Field field = clazz.getDeclaredField(resourceName);
            int resourceId = field.getInt(R.mipmap.class);
            return resourceId;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }


    private Resources getPluginResources(String apkName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + apkName);
            Resources superRes = getResources();
            Resources mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            return mResources;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void updateBg(View view) {
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = pm.getPackageArchiveInfo(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PluginResource.zip", PackageManager.GET_ACTIVITIES);
        String name = packageInfo.applicationInfo.packageName;
        //String name = "com.example.pluginresource";
        File file = getDir("dex", MODE_PRIVATE);
        DexClassLoader dexClassLoader = new DexClassLoader(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "PluginResource.zip", file.getPath(), null, ClassLoader.getSystemClassLoader());
        try {
            Class clazz = dexClassLoader.loadClass(name + ".R$drawable");
            Field field = clazz.getDeclaredField("plugin60_100");
            int id = field.getInt(R.id.class);
            Resources resources = getPluginResources("PluginResource.zip");
            ((ImageView)view).setImageDrawable(resources.getDrawable(id));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
