package com.boll.xirilib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @author: caijianhui
 * @date: 2019/7/11 14:45
 * @description:
 */
public class PluginUtils {

    private static final String SYSTEM_PACKAGE_NAME = "android";


    private PluginUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Resources getPluginResources(String packageName) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            Log.i("hehhe", "apk path = " + Utils.getApp().getPackageManager().getApplicationInfo(packageName, 0).sourceDir);
            addAssetPath.invoke(assetManager, Utils.getApp().getPackageManager().getApplicationInfo(packageName, 0).sourceDir);
            Resources superRes = Utils.getApp().getResources();
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
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getIdentifierInner(final Resources resources,
                                          final String resourceName,
                                          final String defType,
                                          final String pkgName,
                                          final boolean isSystem) {
        if (null == resources || isSpace(resourceName) || isSpace(defType) || isSpace(pkgName))
            return 0;
        return resources.getIdentifier(resourceName, defType, isSystem ? SYSTEM_PACKAGE_NAME : pkgName);
    }

    public static int getThirdPartyDrawable(final String drawableName, final String pkgName) {
        //return getIdentifierInner(getPluginResources(pkgName), drawableName, "drawable", pkgName, false);
        return getIdentifierInner3(drawableName, pkgName);
    }

    /**
     * 判断是字符串是否为空
     * @param s
     * @return
     */
    private static boolean isSpace(final String s) {
        if (null == s) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static int getIdentifierInner2(final String resourceName, final String packageName) {
        try {
            File file = Utils.getApp().getDir("dex", Context.MODE_PRIVATE);

            DexClassLoader dexClassLoader = new DexClassLoader(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "app-debug.zip", file.getPath(), null, ClassLoader.getSystemClassLoader());

            Class clazz = dexClassLoader.loadClass(packageName + ".R$drawable");
            Field field = clazz.getDeclaredField(resourceName);
            int id = field.getInt(R.id.class);
            return id;
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
        return 0;
    }


    /**
     * 加载已安装的apk
     * @param packageName 应用的包名
     * @return 对应资源的id
     */
    private static int getIdentifierInner3(final String resourceName, final String packageName) {


        try {
            Context pluginContext = Utils.getApp().createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            //第一个参数为包含dex的apk或者jar的路径，第二个参数为父加载器
            PathClassLoader pathClassLoader = new PathClassLoader(pluginContext.getPackageResourcePath(), ClassLoader.getSystemClassLoader());
//        Class<?> clazz = pathClassLoader.loadClass(packageName + ".R$mipmap");//通过使用自身的加载器反射出mipmap类进而使用该类的功能
            //参数：1、类的全名，2、是否初始化类，3、加载时使用的类加载器
            Class<?> clazz = Class.forName(packageName + ".R$drawable", true, pathClassLoader);
            //使用上述两种方式都可以，这里我们得到R类中的内部类mipmap，通过它得到对应的图片id，进而给我们使用
            Field field = clazz.getDeclaredField(resourceName);
            int resourceId = field.getInt(R.drawable.class);
            return resourceId;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }



}
