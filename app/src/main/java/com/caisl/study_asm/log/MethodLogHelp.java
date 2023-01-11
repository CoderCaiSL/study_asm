package com.caisl.study_asm.log;

import android.app.Activity;
import android.widget.Toast;


/**
 * @author: CaiSongL
 * @date: 2023/1/8 19:21
 */
public class MethodLogHelp {
    /**
     * 记录加载的敏感字段实现
     * @param filed
     */
    public synchronized static void recordLoadFiled(String filed) {
    }

    /**
     * 记录敏感函数调用
     * 对于实例方法，可以简单通过插入我们的方法记录堆栈
     *
     * @param from
     */
    public static void recordMethodCall(String from) {
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------敏感函数方法名称：%s------------------------\n\n", from));
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; ++i) {
            android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
        }
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数"));
    }


    public static void onActivityCreate(Activity activity) {
        Toast.makeText(activity
                , activity.getClass().getName() + "call onCreate---"
                , Toast.LENGTH_SHORT).show();
    }

    /**
     * 当Activity执行了onDestroy时触发
     *
     * @param activity
     */
    public static void onActivityDestroy(Activity activity) {
//        Toast.makeText(activity
//                , activity.getClass().getName() + "call onDestroy---"
//                , Toast.LENGTH_LONG).show();
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; ++i) {
            android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
        }
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数"));
    }

    public static void onActivityPrintln(String name){
        try {
            android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------敏感函数方法名称：%s------------------------\n\n", name));
            android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            for(int i = 0; i < stackTraceElements.length; ++i) {
                android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
            }
            android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
