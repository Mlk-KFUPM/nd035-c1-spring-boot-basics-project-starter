package com.udacity.jwdnd.course1.cloudstorage.model;

import java.util.Arrays;

public class File {

    private Integer fileId;
    private String filename; // Renamed to match the database column
    private String contentType;
    private String fileSize;
    private byte[] fileData;
    private Integer userId;

    public File(Integer fileId, String filename, String contentType, String fileSize, byte[] fileData, Integer userId) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() { // Updated getter
        return filename;
    }

    public void setFilename(String filename) { // Updated setter
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileId=" + fileId +
                ", fileName='" + filename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileData=" + fileData.length +
                ", userId=" + userId +
                '}';
    }
}
