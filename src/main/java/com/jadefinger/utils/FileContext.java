package com.jadefinger.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileContext {

    private String rootPath;
    private File rootPathFile;
    private String currentPath;
    private Map<String, File> currentFileMap;
    private boolean currentPathIsEmpty;

    public static FileContext newInstance(String rootPath) throws IOException {
        return new FileContext(rootPath, false);
    }

    public static FileContext newInstance(String rootPath, boolean create) throws IOException {
        return new FileContext(rootPath, create);
    }

    private FileContext(String rootPath, boolean create) throws IOException {
        this.rootPath = rootPath;
        rootPathFile = new File(rootPath);
        if (!create && !rootPathFile.exists()) {
            throw new IOException("根路径不存在");
        } else {
            if (!new File(rootPathFile.getAbsolutePath() + File.separator + "tmp").mkdirs()) {
                throw new IOException("根目录创建失败");
            }
        }
        if (!rootPathFile.isDirectory()) {
            throw new IOException("根路径不是目录");
        }

        this.currentPath = rootPath;
        this.currentFileMap = new LinkedHashMap<String, File>();
        this.currentPathIsEmpty = true;
        // 加载当前目录所有文件

    }

    private void loadFiles(File loadFilePath) {
        if (loadFilePath != null && loadFilePath.isDirectory()) {
            File[] files = loadFilePath.listFiles();
            this.currentPathIsEmpty = !(files != null && files.length > 0);
            if (!this.currentPathIsEmpty) {
                for (File file : files) {
                    //TODO file
                }
            }
        }
    }

}
