package com.caisl.study_asm.log

import android.app.Activity
import android.util.Log
import android.widget.Toast
import java.lang.Exception

/**
 * @author: CaiSongL
 * @date: 2023/1/8 19:21
 */
object MethodLogHelpK {
    /**
     * 记录加载的敏感字段实现
     * @param filed
     */
    @Synchronized
    fun recordLoadFiled(filed: String?) {
    }

    /**
     * 记录敏感函数调用
     * 对于实例方法，可以简单通过插入我们的方法记录堆栈
     *
     * @param from
     */
    fun recordMethodCall(from: String?) {
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数")
        )
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------敏感函数方法名称：%s------------------------\n\n", from)
        )
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数")
        )
        val stackTraceElements = Thread.currentThread().stackTrace
        for (i in stackTraceElements.indices) {
            Log.d("MethodRecordSDK", stackTraceElements[i].toString())
        }
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数")
        )
    }

    fun onActivityCreate(activity: Activity) {
        Toast.makeText(
            activity, activity.javaClass.name + "call onCreate---", Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * 当Activity执行了onDestroy时触发
     *
     * @param activity
     */
    fun onActivityDestroy(activity: Activity?) {
//        Toast.makeText(activity
//                , activity.getClass().getName() + "call onDestroy---"
//                , Toast.LENGTH_LONG).show();
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数")
        )
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------%s调用堆栈开始------------------------\n\n", "敏感函数")
        )
        val stackTraceElements = Thread.currentThread().stackTrace
        for (i in stackTraceElements.indices) {
            Log.d("MethodRecordSDK", stackTraceElements[i].toString())
        }
        Log.e(
            "MethodRecordSDK",
            String.format("\n\n----------------------%s调用堆栈结束------------------------\n\n", "敏感函数")
        )
    }

    fun onActivityPrintln(name: String?) {
        try {
            Log.e(
                "MethodRecordSDK",
                String.format(
                    "\n\n----------------------%s调用堆栈开始------------------------\n\n",
                    "敏感函数"
                )
            )
            Log.e(
                "MethodRecordSDK",
                String.format(
                    "\n\n----------------------%s调用堆栈开始------------------------\n\n",
                    "敏感函数"
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}