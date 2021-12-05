package com.jadefinger.utils;

import java.io.File;

public class FileDirStatistics {
    private Integer filesCount;
    private Integer dirsCount;
    private Long filesTotalVolume;

    public FileDirStatistics() {
        this.filesCount = 0;
        this.dirsCount = 0;
        this.filesTotalVolume = 0L;
    }

    public void reload() {
        this.filesCount = 0;
        this.dirsCount = 0;
        this.filesTotalVolume = 0L;
    }

    public String getFilesTotalVolume(String unit) {
        switch (unit) {
            case "KB":
                return FileUnitConvert.getKB(this.filesTotalVolume) + "";
            case "MB":
                return FileUnitConvert.getMB(this.filesTotalVolume) + "";
            case "GB":
                return FileUnitConvert.getGB(this.filesTotalVolume) + "";
            case "TB":
                return FileUnitConvert.getTB(this.filesTotalVolume) + "";
        }
        return this.filesTotalVolume + "";
    }

    @Override
    public String toString() {
        return "FileDirStatistics{" +
                "filesCount=" + filesCount +
                ", dirsCount=" + dirsCount +
                ", filesTotalVolume(KB)=" + getFilesTotalVolume("KB") +
                ", filesTotalVolume(MB)=" + getFilesTotalVolume("MB") +
                '}';
    }

    public FileDirStatistics addFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                this.filesCount++;
                this.filesTotalVolume += file.length();
            } else {
                this.dirsCount++;
            }

        }
        return this;
    }

    public Integer getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }

    public Integer getDirsCount() {
        return dirsCount;
    }

    public void setDirsCount(Integer dirsCount) {
        this.dirsCount = dirsCount;
    }

    public Long getFilesTotalVolume() {
        return filesTotalVolume;
    }

    public void setFilesTotalVolume(Long filesTotalVolume) {
        this.filesTotalVolume = filesTotalVolume;
    }
}
