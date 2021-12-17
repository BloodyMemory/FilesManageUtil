package com.jadefinger.utils;

import com.jadefinger.utils.exception.InvalidFileNameException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class FileContext {

    private final String rootPath;
    private final File rootPathFile;
    private String currentPath;
    private String currentRelativePath;
    private File currentPathFile;
    private final Map<String, File> currentFileMap;
    private boolean currentPathIsEmpty;
    private final FileDirStatistics currentPathStatistics;

    public static FileContext newInstance(String rootPath) throws IOException {
        return new FileContext(rootPath, false);
    }

    public static FileContext newInstance(String rootPath, boolean create) throws IOException {
        return new FileContext(rootPath, create);
    }

    private FileContext(String rootPath, boolean create) throws IOException {
        this.rootPath = rootPath;
        this.rootPathFile = new File(rootPath);
        if (!this.rootPathFile.exists()) {
            if (create) {
                if (!new File(rootPathFile.getAbsolutePath()).mkdirs()) {
                    throw new IOException("根目录创建失败");
                }
            } else {
                throw new IOException("根路径不存在");
            }
        }
        if (!rootPathFile.isDirectory()) {
            throw new IOException("根路径不是目录");
        }
        this.currentPath = rootPath;
        this.currentRelativePath = File.separator;
        this.currentPathFile = rootPathFile;
        this.currentFileMap = new LinkedHashMap<>();
        this.currentPathStatistics = new FileDirStatistics();
        // 加载当前目录所有文件
        loadCurrentPathFiles();
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public String getCurrentRelativePath() {
        return currentRelativePath;
    }

    public boolean isCurrentPathIsEmpty() {
        return currentPathIsEmpty;
    }

    public String getRootPath() {
        return rootPath;
    }

    public File getRootPathFile() {
        return rootPathFile;
    }

    public File getCurrentPathFile() {
        return currentPathFile;
    }

    private void loadCurrentPathFiles() {
        this.currentPathIsEmpty = true;
        this.currentFileMap.clear();
        this.currentPathStatistics.reload();
        if (this.currentPathFile != null && this.currentPathFile.isDirectory()) {
            File[] files = this.currentPathFile.listFiles();
            this.currentPathIsEmpty = !(files != null && files.length > 0);
            if (!this.currentPathIsEmpty) {
                for (File file : files) {
                    this.currentFileMap.put(file.getName(), file);
                    this.currentPathStatistics.addFile(file);
                }
            }
        }
    }

    private void checkFileName(String fileName) {
        if (fileName == null || fileName.equals("") || fileName.contains(File.separator)) {
            throw new InvalidFileNameException("非法名称");
        }
    }

    public String showCurrentFiles() {
        StringBuffer buffer = new StringBuffer();
        for (File value : this.currentFileMap.values()) {
            buffer.append(value.getName()).append(",");
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * 进入目录
     *
     * @param dirName
     * @return
     */
    public FileContext openDir(String dirName) throws InvalidFileNameException {
        File toOpenDir = this.currentFileMap.get(dirName);
        if (toOpenDir != null && toOpenDir.isDirectory()) {
            this.currentRelativePath += dirName + File.separator;
            this.currentPathFile = toOpenDir;
            this.currentPath = this.currentPathFile.getAbsolutePath();
            loadCurrentPathFiles();
        } else {
            throw new InvalidFileNameException("目录不存在");
        }
        return this;
    }

    /**
     * 进入根目录
     *
     * @return
     */
    public FileContext openRootDir() {
        this.currentPath = this.rootPath;
        this.currentRelativePath = File.separator;
        this.currentPathFile = this.rootPathFile;
        loadCurrentPathFiles();
        return this;
    }

    /**
     * 进入多层目录
     *
     * @param dirNames
     * @return
     * @throws InvalidFileNameException
     */
    public FileContext openDirs(String dirNames) throws InvalidFileNameException {
        if (dirNames != null) {
            if (dirNames.startsWith(File.separator)) {
                dirNames = dirNames.substring(1);
            }
            String regex = "/";
            if (File.separator.equals("\\")) {
                regex = "\\\\";
            }
            String[] split = dirNames.split(regex);
            for (String dirName : split) {
                openDir(dirName);
            }
        }
        return this;
    }

    /**
     * 进入多层目录
     *
     * @param dirNames
     * @return
     * @throws InvalidFileNameException
     */
    public FileContext openDirs(String... dirNames) throws InvalidFileNameException {
        if (dirNames != null && dirNames.length > 0) {
            for (String dirName : dirNames) {
                openDir(dirName);
            }
        }
        return this;
    }

    /**
     * 跳转到上级目录
     *
     * @return
     * @throws IOException
     */
    public FileContext toParentDir() throws IOException {
        File parentFile = this.currentPathFile.getParentFile();
        if (parentFile.isDirectory()) {
            if (this.currentPathFile.getAbsolutePath().equalsIgnoreCase(this.rootPath)) {
                //当前目录是根目录已经无法再向上级目录跳转
                throw new IOException("根目录已经无法再向上级目录跳转");
            } else {
                this.currentRelativePath = this.currentRelativePath.substring(0, this.currentRelativePath.length() - 1);
                this.currentRelativePath = this.currentRelativePath.substring(0, this.currentRelativePath.lastIndexOf(File.separator) + 1);
                this.currentPathFile = parentFile;
                this.currentPath = this.currentPathFile.getAbsolutePath();
                loadCurrentPathFiles();
            }
        } else {
            throw new IOException("没有上级目录");
        }
        return this;
    }

    /**
     * 在当前目录新建文件
     *
     * @param fileName
     */
    public File newFile(String fileName) {
        checkFileName(fileName);
        File newFile = new File(this.currentPathFile.getAbsolutePath() + File.separator + fileName);
        try {
            if (newFile.createNewFile()) {
                loadCurrentPathFiles();
                return newFile;
            }
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * 在当前目录新建目录
     *
     * @param dirName
     * @return
     */
    public boolean newDir(String dirName) {
        boolean result = new File(this.currentPathFile.getAbsolutePath() + File.separator + dirName).mkdir();
        loadCurrentPathFiles();
        return result;
    }

    /**
     * 创建多层级目录
     *
     * @param dirNames
     * @return
     */
    public boolean newDirs(String dirNames) {
        boolean result;
        if (dirNames.startsWith(File.separator)) {
            //根目录开始
            result = new File(this.rootPathFile.getAbsolutePath() + dirNames).mkdirs();
        } else {
            //当前目录开始
            result = new File(this.currentPathFile.getAbsolutePath() + File.separator + dirNames).mkdirs();
        }
        loadCurrentPathFiles();
        return result;
    }

    /**
     * 当前目录创建多级目录
     *
     * @param dirNames
     * @return
     */
    public boolean newDirsCurrent(String... dirNames) {
        boolean result = false;
        if (dirNames != null && dirNames.length > 0) {
            StringBuffer buffer = new StringBuffer();
            for (String dirName : dirNames) {
                buffer.append(dirName).append(File.separator);
            }
            buffer.deleteCharAt(buffer.length() - 1);
            result = newDirs(buffer.toString());
        }
        loadCurrentPathFiles();
        return result;
    }

    /**
     * 删除当前文件或者目录
     *
     * @param fileName
     * @return
     */
    public boolean delFileOrDir(String fileName) {
        checkFileName(fileName);
        File toDelFile = new File(this.currentPathFile.getAbsolutePath() + File.separator + fileName);
        if (toDelFile.exists()) {
            boolean r = toDelFile.delete();
            loadCurrentPathFiles();
            return r;
        }
        return false;
    }

    /**
     * 重命名文件名
     *
     * @param fileName
     * @param newName
     * @return
     */
    public boolean rename(String fileName, String newName) throws IOException {
        checkFileName(fileName);
        checkFileName(newName);
        File toRenameFile = new File(this.currentPathFile.getAbsolutePath() + File.separator + fileName);
        if (!toRenameFile.exists()) {
            throw new IOException("要重命名的文件不存在");
        }
        boolean r = toRenameFile.renameTo(new File(this.currentPathFile.getAbsolutePath() + File.separator + newName));
        loadCurrentPathFiles();
        return r;
    }

    /**
     * 获取文件
     *
     * @param fileName
     * @return
     */
    public File getFile(String fileName) {
        checkFileName(fileName);
        return this.currentFileMap.get(fileName);
    }


    /**
     * 是否替换重复文件
     *
     * @param sourceFile
     * @param targetFile
     * @param replace
     */
    public void copyTo(File sourceFile, File targetFile, boolean replace) {
        if (sourceFile != null && targetFile != null && targetFile.isDirectory()) {
            if (sourceFile.isDirectory()) {
                //目录要遍历所有子目录和以下所有文件
                targetFile = new File(targetFile.getAbsolutePath() + File.separator + sourceFile.getName());
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                for (File file : Objects.requireNonNull(sourceFile.listFiles())) {
                    copyTo(file, targetFile, replace);
                }
            } else {
                //文件直接复制
                String targetFilePath = targetFile.getAbsolutePath() + File.separator + sourceFile.getName();
                File targetFileCopy = new File(targetFilePath);
                if (!targetFileCopy.exists() || replace) {
                    //复制文件
                    writeToFile(sourceFile, targetFilePath);
                }
            }
        }
    }

    private void writeToFile(File file, String filePath) {
        if (file == null) {
            return;
        }
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(filePath);
            fis = new FileInputStream(file);
            byte[] buf = new byte[1024];
            while (fis.read(buf) != -1) {
                fos.write(buf);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public FileDirStatistics getCurrentPathStatistics() {
        return this.currentPathStatistics;
    }
}
