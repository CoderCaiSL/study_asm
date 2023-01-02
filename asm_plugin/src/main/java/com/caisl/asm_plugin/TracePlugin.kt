package com.caisl.asm_plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * @author: CaiSongL
 * @date: 2023/1/2 22:55
 */
class TracePlugin :  Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            println("=============start add kotlin============")
        }
        val isDebug = isDebugBuildType(project)
        println("==============================TracePlugin Plugin apply========================================")
        if (isDebug){
            val android = project.extensions.getByType(AppExtension::class.java)
            android.registerTransform(LogTransform())
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