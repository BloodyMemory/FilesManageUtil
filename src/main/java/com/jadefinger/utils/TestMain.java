package com.jadefinger.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class TestMain {

    /*
    public static void main(String[] args) throws IOException {
        FileContext fileContext = FileContext.newInstance("D:\\temp\\abc");
        System.out.println("统计信息：" + fileContext.getCurrentPathStatistics());
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        for (int i = 0; i < 10; i++) {
            fileContext.newFile("NewFile" + i + ".txt");
        }
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        //删除偶数文件
        for (int i = 0; i < 10; i += 2) {
            fileContext.delFileOrDir("NewFile" + i + ".txt");
        }
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        //修改第一个文件名
        fileContext.rename("NewFile1.txt", "RenameFile1.txt");
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        //新建文件夹
        fileContext.newDir("xxx");
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        //进入文件夹
        fileContext.openDir("xxx");
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        //新建文件
        for (int i = 0; i < 5; i++) {
            fileContext.newFile("xxx" + i + ".txt");
        }
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        //跳转到上级目录
        fileContext.toParentDir();
        System.out.println("展示文件：" + fileContext.showCurrentFiles());
        System.out.println("结束...");
    }
    */

    public static void main(String[] args) throws IOException, URISyntaxException {
        //测试复制文件
//        FileContext fileContext1 = FileContext.newInstance("D:\\temp\\root");
//        fileContext1.newFile("NewFile1.txt");
//        System.out.println("展示文件：" + fileContext1.showCurrentFiles());
//        File sourceFile = fileContext1.getFile("NewFile1.txt");
//        FileContext fileContext2 = FileContext.newInstance("D:\\temp\\root2", true);
//        fileContext2.newDir("test-copy");
//        File targetFile = fileContext2.getFile("test-copy");
//        fileContext2.copyTo(sourceFile, targetFile, false);
//        fileContext1.copyTo(targetFile, fileContext1.getRootPathFile(), false);
//        FileContext fileContext1 = FileContext.newInstance("D:\\temp\\root");
//        fileContext1.openDir("a");
//        fileContext1.newDirsCurrent("f1", "f2", "f3");
//        fileContext1.newDirs("\\a\\b\\c");
//        fileContext1.openDir("a").newDirs("a1\\a2");
//        fileContext1.openRootDir().openDirs("a", "b");
//        fileContext1.newFile("FileInB.txt");
//        fileContext1.openRootDir().openDir("a").openDirs("b\\c").newFile("FileInC.txt");
//        System.out.println(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath());
        FileContext fileContext = FileContext.newInstance(new File(Thread.currentThread().getContextClassLoader().getResource("").toURI()));
        System.out.println("Root:" + fileContext.getRootPath());
        System.out.println(fileContext.showCurrentFiles());
    }
}
