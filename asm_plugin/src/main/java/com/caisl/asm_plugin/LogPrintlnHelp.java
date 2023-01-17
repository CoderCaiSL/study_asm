package com.caisl.asm_plugin;

/**
 * @author: CaiSongL
 * @date: 2023/1/17 14:14
 */
public class LogPrintlnHelp {
    private String classPath;
    private String methodName;
    private String parameterName;

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public LogPrintlnHelp(String classPath, String methodName, String parameterName) {
        this.classPath = classPath;
        this.methodName = methodName;
        this.parameterName = parameterName;
    }


}
