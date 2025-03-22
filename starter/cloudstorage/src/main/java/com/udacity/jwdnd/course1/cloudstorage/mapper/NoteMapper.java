package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userid}")
    List<Note> getNotesByUserId(Integer userid);

    @Select("SELECT * FROM notes WHERE noteid = #{noteid}")
    Note getNoteById(Integer noteid);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    int insertNote(Note note);

    @Update("UPDATE notes SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int updateNote(Note note);

    @Delete("DELETE FROM notes WHERE noteid = #{noteid}")
    int deleteNote(Integer noteid);

}