package com.caisl.asm_plugin.log;

/**
 * @author: CaiSongL
 * @date: 2023/1/8 19:21
 */
public class MethodLogHelp {
    private static RecordCallListener recordCallListener = new DefaultRecordListener();

    /**
     * 记录加载的敏感字段实现
     * @param filed
     */
    public synchronized static void recordLoadFiled(String filed) {
        recordCallListener.onRecordLoadFiled(filed);
    }

    /**
     * 记录敏感函数调用
     * 对于实例方法，可以简单通过插入我们的方法记录堆栈
     *
     * @param from
     */
    public synchronized static void recordMethodCall(String from) {
        recordCallListener.onRecordMethodCall(from);
    }

    /**
     * 设置回调
     * @param recordCallListener
     */
    public static void setRecordCallListener(RecordCallListener recordCallListener) {
        MethodLogHelp.recordCallListener = recordCallListener;
    }
}
