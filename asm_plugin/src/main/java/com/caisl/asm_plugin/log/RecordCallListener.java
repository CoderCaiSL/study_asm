package com.caisl.asm_plugin.log;

/**
 * @author: CaiSongL
 * @date: 2023/1/8 19:22
 */
public interface RecordCallListener {
    void onRecordMethodCall(String from);
    void onRecordLoadFiled(String field);
}