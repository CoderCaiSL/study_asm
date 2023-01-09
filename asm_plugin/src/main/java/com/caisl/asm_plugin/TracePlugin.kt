package com.caisl.asm_plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*


/**
 * @author: CaiSongL
 * @date: 2023/1/2 22:55
 */
class TracePlugin :  Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            println("=============start add kotlin============")
        }
//        val appExtension = project.extensions.findByType(
//            AppExtension::class.java
//        )
        //可以依据这个名字（methodCallRecordExtension），在依赖module的 gradle 中创建一些配置参数
        project.extensions.create(
            "methodCallRecordExtension",
            MethodCallRecordExtension::class.java
        )
        val isDebug = isDebugBuildType(project)
        println("==============================TracePlugin Plugin apply========================================")
        if (isDebug){
//            val android = project.extensions.getByType(AppExtension::class.java)
            val appExtension = project.properties["android"] as AppExtension
            appExtension.registerTransform(
                MethodCallRecordTransform(project),
                Collections.EMPTY_LIST
            )
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