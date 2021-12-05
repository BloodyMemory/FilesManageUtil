package com.jadefinger.utils;

public class FileUnitConvert {

    public static long getKB(long bytes) {
        return bytes / 1024;
    }

    public static long getMB(long bytes) {
        return getKB(bytes) / 1024;
    }

    public static long getGB(long bytes) {
        return getMB(bytes) / 1024;
    }

    public static long getTB(long bytes) {
        return getGB(bytes) / 1024;
    }
}
