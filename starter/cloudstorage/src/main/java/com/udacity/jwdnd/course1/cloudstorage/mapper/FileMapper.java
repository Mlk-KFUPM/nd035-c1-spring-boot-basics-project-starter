package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileView;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insertFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    @Results({
            @Result(property = "fileId", column = "fileid"),
            @Result(property = "filename", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "fileSize", column = "filesize"),
            @Result(property = "fileData", column = "filedata", javaType = byte[].class), // Explicit mapping for byte[]
            @Result(property = "userId", column = "userid")
    })
    File getFileById(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userid} LIMIT 1")
    Optional<File> findFileByFilenameAndUserId(@Param("filename") String filename, @Param("userid") Integer userid);

    @Select("SELECT fileid AS fileId, filename AS filename FROM FILES WHERE userid = #{userId}")
    @Results({
            @Result(property = "fileId", column = "fileid"),
            @Result(property = "filename", column = "filename")
    })
    List<FileView> getFilesViewByUserId(@Param("userId") Integer userId);

}
