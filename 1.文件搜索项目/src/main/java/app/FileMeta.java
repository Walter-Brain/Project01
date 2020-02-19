package app;

import util.Util;

import java.io.File;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/5-9:24
 * Version: 1.0
 **/

public class FileMeta {
    public boolean getDirectory;
    private String name;
    private String path;
    private long size;
    private long lastModified;
    private boolean isDirectory;
    private String sizeText;
    private String lastModifiedText;

    public FileMeta(String name, String path, long size, long lastModified, boolean isDirectory) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.lastModified = lastModified;
        this.isDirectory = isDirectory;
        this.sizeText = Util.parseSize(size);
        this.lastModifiedText = Util.parseDate(lastModified);
    }

    public FileMeta(File child) {
        this.name = name;
        this.path = path;
        this.isDirectory = isDirectory;
        this.sizeText = sizeText;
        this.lastModifiedText = lastModifiedText;
    }

    @Override
    public String toString() {
        return "FileMeta{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", isDirectory=" + isDirectory +
                ", sizeText='" + sizeText + '\'' +
                ", lastModifiedText='" + lastModifiedText + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getSizeText() {
        return sizeText;
    }

    public void setSizeText(String sizeText) {
        this.sizeText = sizeText;
    }

    public String getLastModifiedText() {
        return lastModifiedText;
    }

    public void setLastModifiedText(String lastModifiedText) {
        this.lastModifiedText = lastModifiedText;
    }
}
