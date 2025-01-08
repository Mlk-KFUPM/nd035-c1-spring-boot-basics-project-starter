package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO credentials (username, password, key, url, userId) VALUES (#{username}, #{password}, #{key}, #{url}, #{userId})")
    void insertCredential(Credential credential);

    @Select("SELECT * FROM credentials WHERE userId = #{userId}")
    List<Credential> findByUserId(int userId);

    @Select("SELECT * FROM credentials WHERE id = #{id}")
    Credential findById(int id);

    @Update("UPDATE credentials SET username = #{username}, password = #{password}, url = #{url}, key = #{key} WHERE id = #{id}")
    void updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE id = #{id}")
    void deleteCredential(int id);
}
