package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO notes (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    void insertNote(Note note);

    @Select("SELECT * FROM notes WHERE userId = #{userId}")
    List<Note> findByUserId(int userId);

    @Select("SELECT * FROM notes WHERE id = #{id}")
    Note findById(int id);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(@Param("noteId") Integer noteId,
                    @Param("noteTitle") String noteTitle,
                    @Param("noteDescription") String noteDescription);

    @Delete("DELETE FROM notes WHERE noteid = #{noteid}")
    void deleteNote(int noteid);
}
