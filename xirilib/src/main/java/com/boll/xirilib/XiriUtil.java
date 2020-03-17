package com.boll.xirilib;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * @author: caijianhui
 * @date: 2019/3/1 10:41
 * @description:
 */
public final class XiriUtil {

    /*public static final String KEY_DOWN_CMD = "keyDownCmd";*/
    /*public static final String KEY_UP_CMD = "keyUpCmd";
    public static final String KEY_BACK_CMD = "keyBackCmd";*/

    public static final String KEY_SHOW_FLOAT_ROBOT_CMD = "keyShowFloatRobotCmd";
    public static final String KEY_HIDE_FLOAT_ROBOT_CMD = "keyHideFloatRobotCmd";
    public static final String KEY_IS_FLOAT_ROBOT_SHOW_CMD = "isFloatRobotShowCmd";

    public static final String KEY_ENTER_WAKEUP_MODE_CMD = "enterWakeupModeCmd";
    public static final String KEY_EXIT_WAKEUP_MODE_CMD = "exitWakeupModeCmd";
    public static final String KEY_IS_WAKEUP_MODE_CMD = "isWakeupModeCmd";

    public static final String KEY_SWITCH_MODE_CMD = "switchMode";


    /*public static Bundle performKeyDown(Context context) {
        return performCmd(context, KEY_DOWN_CMD);
    }*/

    /*public static Bundle performKeyUp(Context context) {
        return performCmd(context, KEY_UP_CMD);
    }

    public static Bundle performKeyBack(Context context) {
        return performCmd(context, KEY_BACK_CMD);
    }*/

    public static Bundle performKeyShowFloatRobot(Context context) {
        return performCmd(context, KEY_SHOW_FLOAT_ROBOT_CMD);
    }

    public static Bundle performKeyHideFloatRobot(Context context) {
        return performCmd(context, KEY_HIDE_FLOAT_ROBOT_CMD);
    }

    public static Bundle performIsFloatRobotShow(Context context) {
        return performCmd(context, KEY_IS_FLOAT_ROBOT_SHOW_CMD);
    }

    public static Bundle performEnterWakeupMode(Context context) {
        return performCmd(context, KEY_ENTER_WAKEUP_MODE_CMD);
    }

    public static Bundle performExitWakeupMode(Context context) {
        return performCmd(context, KEY_EXIT_WAKEUP_MODE_CMD);
    }

    public static Bundle performIsWakeupMode(Context context) {
        return performCmd(context, KEY_IS_WAKEUP_MODE_CMD);
    }

    public static Bundle performSwitchEvaluation(Context context) {
        return performSwitchMode(context, 1, "", "", 14, 0, 0, true);
    }

    public static Bundle performSwitchKnowledgeVideo(Context context) {
        return performSwitchMode(context, 2, "", "", 14, 0, 0, true);
    }

    public static Bundle performSwitchLanguagePoint(Context context) {
        return performSwitchMode(context, 0, "", "", 14, 0, 0, true);
    }

    public static Bundle performSwitchFmxOS(Context context) {
        return performSwitchMode(context, 3, "", "", 14, 0, 0, true);
    }

    public static Bundle performSwitchMode(Context context, int mode, String drawableName, String packageName, int scale, int x, int y, boolean canMove) {
        Bundle extras = new Bundle();
        extras.putInt("mode", mode);
        extras.putString("drawableName", drawableName);
        extras.putString("packageName", packageName);
        extras.putInt("scale", scale);
        extras.putInt("x", x);
        extras.putInt("y", y);
        extras.putBoolean("canMove", canMove);
        return performCmdInner(context, KEY_SWITCH_MODE_CMD, null, extras);
    }

    private static Bundle performCmd(Context context, String cmd) {
        return performCmdInner(context, cmd, null, null);
    }

    private static Bundle performCmdInner(Context context, String cmd, String arg, Bundle extras) {
        if (null == context || TextUtils.isEmpty(cmd)) {
            return null;
        }
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.szhr.xiriview_provider");
        return contentResolver.call(uri, cmd, arg, extras);
    }

}
