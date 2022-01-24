package com.jadefinger.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class TestMain {


    public static void main(String[] args) throws IOException, URISyntaxException {
        FileContext fileContext = FileContext.newInstance(new File(Thread.currentThread().getContextClassLoader().getResource("").toURI()));
        System.out.println("Root:" + fileContext.getRootPath());
        System.out.println(fileContext.showCurrentFiles());
        fileContext.newDirs("/user");

    }
}
