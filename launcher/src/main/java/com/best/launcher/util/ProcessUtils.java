package com.best.launcher.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ProcessUtils {

    public static boolean isMianProcess(Context context) {
        return TextUtils.equals(context.getPackageName(), getProcessName());
    }

    public static int getProcessId(){
        return android.os.Process.myPid();
    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
