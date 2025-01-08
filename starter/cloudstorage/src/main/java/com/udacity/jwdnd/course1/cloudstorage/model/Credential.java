package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer id;
    private String username;
    private String password;
    private String url;
    private String key;
    private Integer userId;

    public Credential() {
    }

    public Credential(Integer id, String userName, String password, String url,String key, Integer userId) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.url = url;
        this.key = key;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
