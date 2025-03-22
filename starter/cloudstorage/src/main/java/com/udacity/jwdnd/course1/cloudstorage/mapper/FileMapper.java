package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files WHERE userid = #{userId}")
    List<File> getFilesByUserId(Integer userId);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Select("SELECT * FROM files WHERE fileid = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM files WHERE filename = #{filename} AND userid = #{userId}")
    File getFileByNameAndUserId(String filename, Integer userId);

    @Delete("DELETE FROM files WHERE fileid = #{fileId}")
    int deleteFile(Integer fileId);
}