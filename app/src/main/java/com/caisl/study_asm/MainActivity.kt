package com.caisl.study_asm

import android.Manifest
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.net.NetworkInterface

class MainActivity : AppCompatActivity() {

    lateinit var tvContent : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvContent = findViewById(R.id.tv_content);
        findViewById<View>(R.id.tv).setOnClickListener {
            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }
        findViewById<View>(R.id.btn_div).setOnClickListener {
            getAndroidPrivateMethod()
        }
    }

    fun getPhoneNumber(context: Context): String? {
        val telephonyManager = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.line1Number
    }

    private fun getAndroidPrivateMethod() {
        try {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_PHONE_NUMBERS,Manifest.permission.READ_SMS),
                0
            )
            val stringBuilder = StringBuilder()
            getPhoneNumber(this@MainActivity)
            Settings.System.getString(this@MainActivity.contentResolver, Settings.Secure.ANDROID_ID)
            Settings.Secure.getString(this@MainActivity.contentResolver, Settings.Secure.ANDROID_ID)
            val packageManager = this@MainActivity.packageManager
            //获取所有已安装程序的包信息
            val packageInfos = packageManager.getInstalledPackages(0)
            val installedApplications = packageManager.getInstalledApplications(0)
            val tm = this@MainActivity.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            stringBuilder.append("设备信息：")
            stringBuilder.append(tm.line1Number)
            stringBuilder.append("\n")
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                tm.deviceId
//            } else {
//                val method = tm.javaClass.getMethod("getImei")
//                method.invoke(tm)
//            }
            val wifi = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifi.connectionInfo
            if (info != null) {
                stringBuilder.append("网络信息：")
                stringBuilder.append(info.macAddress)
                stringBuilder.append(info.ssid)
                stringBuilder.append("\n")
            }
            stringBuilder.append("IP信息：")
            stringBuilder.append(info.ipAddress)
            stringBuilder.append("\n")
            tvContent.text = stringBuilder.toString()
            val model = Build.MODEL
            getPsdnIp()
            val myProcess: RunningAppProcessInfo? = null
            val activityManager =
                this@MainActivity.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val appProcessList = activityManager
                .runningAppProcesses
            activityManager
                .getRunningServices(0)
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            val resolveInfos =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 用来获取手机拨号上网（包括CTWAP和CTNET）时由PDSN分配给手机终端的源IP地址。
     * @return
     * @author SHANHY
     */
    fun getPsdnIp(): String? {
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        return inetAddress.hostAddress.toString()
                    }
                }
            }
        } catch (e: Exception) {
        }
        return ""
    }
}