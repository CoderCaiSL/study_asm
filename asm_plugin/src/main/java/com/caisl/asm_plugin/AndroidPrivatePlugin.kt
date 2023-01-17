package com.caisl.asm_plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*


/**
 * @author: CaiSongL
 * @date: 2023/1/2 22:55
 */
class AndroidPrivatePlugin :  Plugin<Project> {
    override fun apply(project: Project) {
//        val appExtension = project.extensions.findByType(
//            AppExtension::class.java
//        )
        //可以依据这个名字（methodCallRecordExtension），在依赖module的 gradle 中创建一些配置参数
        project.extensions.create(
            "androidPrivateRecordExtension",
            AndroidPrivateRecordExtension::class.java
        )
        val isDebug = isDebugBuildType(project)
        if (isDebug){
            println("==============================隐私方法检测注入开始========================================")
//            val android = project.extensions.getByType(AppExtension::class.java)
            val appExtension = project.properties["android"] as AppExtension
            appExtension.registerTransform(
                AndroidPrivateRecordTransform(project),
                Collections.EMPTY_LIST
            )
            project.afterEvaluate {
                println("==============================隐私方法检测注入结束========================================")
            }
//            android.registerTransform(MethodCallRecordTransform())
        }else{
            println("==============================检测正式包：跳过字节码注入========================================")
        }
    }
    private fun isDebugBuildType(project: Project) : Boolean{
        project.gradle.startParameter.taskNames.forEach {
            if (it.contains("debug",true)){
                return true
            }
        }
        return false
    }

}