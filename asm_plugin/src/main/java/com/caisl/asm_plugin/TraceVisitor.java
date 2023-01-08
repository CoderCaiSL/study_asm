package com.caisl.asm_plugin;


import groovyjarjarasm.asm.Opcodes;

import org.gradle.internal.resource.TextUriResourceLoader;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;

import static org.objectweb.asm.Opcodes.ASM7;


/**
 * 对继承自AppCompatActivity的Activity进行插桩
 * @author: CaiSongL
 * @date: 2023/1/2 22:56
 */
public class TraceVisitor extends ClassVisitor {

    /**
     * 类名
     */
    private String className;

    /**
     * 父类名
     */
    private String superName;

    /**
     * 该类实现的接口
     */
    private String[] interfaces;

    public TraceVisitor(String className, ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {

        return super.visitModule(name, access, version);
    }

    /**
     * ASM进入到类的方法时进行回调
     *
     * @param access
     * @param name       方法名
     * @param desc
     * @param signature
     * @param exceptions
     * @return
     */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     String[] exceptions) {
        MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
        List<String> mLdcList = new ArrayList<>();
        methodVisitor = new AdviceAdapter(ASM7, methodVisitor, access, name, desc) {

            @Override
            public void visitLdcInsn(Object cst) {//访问一些常量
                if(cst instanceof  String){
                    mLdcList.add((String) cst);
                }
                super.visitLdcInsn(cst);
            }

            private boolean isInject() {
                //如果父类名是AppCompatActivity则拦截这个方法,实际应用中可以换成自己的父类例如BaseActivity
                if (superName.contains("AppCompatActivity")) {
                    return true;
                }
                return false;
            }

            private boolean isPermissionClass(){
                if ("android/telephony/TelephonyManager".contains(name)){
                    return true;
                }
                return false;
            }

            @Override
            public void visitCode() {
                super.visitCode();

            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return super.visitAnnotation(desc, visible);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                super.visitFieldInsn(opcode, owner, name, desc);
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                System.out.println("输出visitM："+owner+"//"+name);
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            }

            /**
             * 方法开始之前回调
             */
            @Override
            protected void onMethodEnter() {
                if (!(superName + name).contains("onActivityPrintln")) {
                    mv.visitLdcInsn(name+"//"+desc);
                    mv.visitMethodInsn(INVOKESTATIC,
                            "com/caisl/study_asm/traceutils/TraceUtil",
                            "onActivityPrintln", "(Ljava/lang/String;)V",
                            false);
                }
                if (isInject()) {
                    if ("onCreate".equals(name)) {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKESTATIC,
                                "com/caisl/study_asm/traceutils/TraceUtil",
                                "onActivityCreate", "(Landroid/app/Activity;)V",
                                false);
                    } else if ("onDestroy".equals(name)) {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKESTATIC, "com/caisl/study_asm/traceutils/TraceUtil"
                                , "onActivityDestroy", "(Landroid/app/Activity;)V", false);
                    }
                }
                if (isPermissionClass()){
                    if ("getLine1Number".contains(name) || name.contains("getLine1Number")) {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKESTATIC,
                                "com/caisl/study_asm/traceutils/TraceUtil",
                                "onActivityLog", "(Landroid/app/Activity;)V",
                                false);
                    }
                }

            }

            /**
             * 方法结束时回调
             * @param i
             */
            @Override
            protected void onMethodExit(int i) {
                super.onMethodExit(i);
            }
        };
        return methodVisitor;

    }

    /**
     * 当ASM进入类时回调
     *
     * @param version
     * @param access
     * @param name       类名
     * @param signature
     * @param superName  父类名
     * @param interfaces 实现的接口名
     */
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
        this.interfaces = interfaces;
    }
}
