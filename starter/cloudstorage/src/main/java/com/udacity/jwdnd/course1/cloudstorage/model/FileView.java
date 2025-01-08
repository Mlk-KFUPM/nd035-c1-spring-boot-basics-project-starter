package com.udacity.jwdnd.course1.cloudstorage.model;

public class FileView {
    private Integer fileId;
    private String filename;

    public FileView(Integer fileId, String filename) {
        this.fileId = fileId;
        this.filename = filename;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
