package com.caisl.study_asm

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv).setOnClickListener {
            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }
        findViewById<View>(R.id.btn_div).setOnClickListener {
            getDeviceId(this@MainActivity)
        }
        findViewById<View>(R.id.btn_android_id).setOnClickListener {

        }
    }

    @SuppressLint("MissingPermission")
    fun getDeviceId(context: Context): String {
        return try {
            val telephonyManager =
                context.getSystemService(Service.TELEPHONY_SERVICE) as? TelephonyManager
            telephonyManager?.deviceId ?: ""
        } catch (e: Throwable) {
            e.printStackTrace()
            ""
        }
    }

    fun getAndroidId():String{
        return application.applicationInfo.i
    }
}