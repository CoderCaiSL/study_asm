package com.caisl.study_asm

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.tv).setOnClickListener {
            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }
        findViewById<View>(R.id.btn_div).setOnClickListener {
            getPhoneNumber(this)
        }
    }

    @SuppressLint("MissingPermission")
    fun getPhoneNumber(context: Context) {
        try {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_SMS),
                0
            )
            val telephonyManager = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            Toast.makeText(this,telephonyManager.line1Number,Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}