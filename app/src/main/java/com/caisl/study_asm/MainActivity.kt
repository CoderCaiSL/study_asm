package com.caisl.study_asm

import android.Manifest
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
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
    val stringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvContent = findViewById(R.id.tv_content);
        findViewById<View>(R.id.tv).setOnClickListener {
            startActivity(Intent(this@MainActivity,SecondActivity::class.java))
        }
        findViewById<View>(R.id.btn_div).setOnClickListener {
            //获取手机卡信息
            stringBuilder.delete(0,stringBuilder.length)
            getPhoneNumber(this@MainActivity)
        }
        findViewById<View>(R.id.btn_android_id).setOnClickListener {
            //android_id
            val androidId = Settings.Secure.getString(this@MainActivity.contentResolver, Settings.Secure.ANDROID_ID)
            stringBuilder.append(0,"androidId："+androidId)
            tvContent.text = stringBuilder.toString()
        }
        findViewById<View>(R.id.btn_wifi).setOnClickListener {
            stringBuilder.delete(0,stringBuilder.length)
            //网络信息
            val wifi = this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifi.connectionInfo
            if (info != null) {
                stringBuilder.append("网络信息：")
                stringBuilder.append(info.macAddress+"")
                stringBuilder.append(info.ssid)
                stringBuilder.append("\n")
            }
            stringBuilder.append("IP信息：")
            stringBuilder.append(info.ipAddress)
            stringBuilder.append("\n")
            tvContent.text = stringBuilder.toString()
        }
        findViewById<View>(R.id.btn_net).setOnClickListener {
            //获取实际上网的数据
            stringBuilder.append(0,"联网数据："+getPsdnIp())
            tvContent.text = stringBuilder.toString()
        }
        findViewById<View>(R.id.btn_pic).setOnClickListener {
            //读取相册

        }
        findViewById<View>(R.id.btn_mk).setOnClickListener {
            //获取麦克风

        }
        findViewById<View>(R.id.btn_cg).setOnClickListener {
            //获取传感器
            val sensorService =  this@MainActivity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensorService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }
        findViewById<View>(R.id.btn_cam).setOnClickListener {
            //获取摄像头

        }
        findViewById<View>(R.id.btn_blue_tooth).setOnClickListener {
            //获取蓝牙数据
            var blueAdapter : BluetoothAdapter ?= null
            blueAdapter = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN){
                BluetoothAdapter.getDefaultAdapter()
            }else{
                (this@MainActivity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
            }
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                0
            )
            stringBuilder.append(0,"蓝牙设备信息："+blueAdapter.address.toString())
            stringBuilder.append(0,"蓝牙的私密数据："+blueAdapter.name)
            tvContent.text = stringBuilder.toString()

        }
        findViewById<View>(R.id.btn_jc).setOnClickListener {
            //获取进程数据
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

        }
        findViewById<View>(R.id.btn_jq).setOnClickListener {
            //获取剪切板数据
            val clipDataService =  this@MainActivity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            stringBuilder.append(0,"获取剪切板数据:"+clipDataService.primaryClip.toString())
            tvContent.text = stringBuilder.toString()

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
            val model = Build.MODEL
            getPsdnIp()

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