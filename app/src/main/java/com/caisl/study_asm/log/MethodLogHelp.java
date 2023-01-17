package com.caisl.study_asm.log;

import android.app.Activity;
import android.widget.Toast;


/**
 * @author: CaiSongL
 * @date: 2023/1/8 19:21
 */
public class MethodLogHelp {

    private static StringBuilder logStringBuilder = new StringBuilder();

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
            logStringBuilder.delete(0,logStringBuilder.length());
            android.util.Log.e("MethodRecordSDK", String.format("--敏感函数方法名称：%s---\n", name));
            android.util.Log.e("MethodRecordSDK", String.format("--%s调用堆栈开始--\n", "敏感函数"));
            String[] nameAndCall = name.split(">>");
            if (nameAndCall.length > 1){
                logStringBuilder.append("### 监控系统发现异常：(金十数据)\n\n");
                logStringBuilder.append("#### 调用敏感函数方法名称：\n");
                logStringBuilder.append(String.format("<font color=#FF0000>%s</font>\n\n",nameAndCall[2]+"/"+nameAndCall[3]));
                logStringBuilder.append("#### 敏感函数调用的类名：\n");
                logStringBuilder.append(String.format("%s\n\n", nameAndCall[0]));
                logStringBuilder.append("#### 敏感函数调用的方法名：\n");
                logStringBuilder.append(String.format("%s\n\n", nameAndCall[1]));
            }
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            logStringBuilder.append("<font color=#FF0000 size=9px face=\"黑体\">\n\n》》》》敏感函数调用栈开始《《《《《\n\n</font>");
            for(int i = 0; i < stackTraceElements.length; ++i) {
                logStringBuilder.append(stackTraceElements[i].toString());
                logStringBuilder.append("\n\n");
                android.util.Log.d("MethodRecordSDK", stackTraceElements[i].toString());
            }
            logStringBuilder.append("<font color=#FF0000 size=9px face=\"黑体\">\n\n》》》》敏感函数调用栈结束《《《《《\n\n</font>");
            logStringBuilder.append("<font color=#FF0000>请在正式发布前尽快处理！！！</font>");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DingTalkUtil.sendPostByMap(logStringBuilder.toString());
                }
            }).start();
            android.util.Log.e("MethodRecordSDK", String.format("\n--%s调用堆栈结束--\n", "敏感函数"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
