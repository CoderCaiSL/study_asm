package com.caisl.study_asm.traceutils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.caisl.study_asm.MyApplication;

/**
 * Created by will on 2018/3/9.
 */

public class TraceUtil {
    private final String TAG = "TraceUtil";

    /**
     * 当Activity执行了onCreate时触发
     *
     * @param activity
     */
    public static void onActivityCreate(Activity activity) {
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; ++i) {
            android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
        }
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数"));
        Toast.makeText(MyApplication.Companion.getInstance()
                , activity.getClass().getName() + "call onCreate"
                , Toast.LENGTH_LONG).show();
    }

    public static void onActivityPrintln(String name){
//        try {
//            System.out.println("测试"+"测试");
////            if (MyApplication.Companion.getInstance()!=null){
////                Toast.makeText(MyApplication.Companion.getInstance()
////                        , "onActivityPrintln"
////                        , Toast.LENGTH_SHORT).show();
////            }
//            System.out.println("方法名输出："+name+"");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }


    public static void onActivityLog(Activity activity){
        Toast.makeText(MyApplication.Companion.getInstance()
                ,  "onActivityLog"
                , Toast.LENGTH_LONG).show();
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for(int i = 0; i < stackTraceElements.length; ++i) {
            android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
        }
        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数"));
    }

    /**
     * 当Activity执行了onDestroy时触发
     *
     * @param activity
     */
    public static void onActivityDestroy(Activity activity) {
        Toast.makeText(activity
                , activity.getClass().getName() + "call onDestroy"
                , Toast.LENGTH_LONG).show();
//        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数"));
//        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//        for(int i = 0; i < stackTraceElements.length; ++i) {
//            android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
//        }
//        android.util.Log.e("MethodRecordSDK", String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数"));
    }
}
