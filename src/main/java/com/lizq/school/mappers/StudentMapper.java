package com.lizq.school.mappers;

import com.lizq.school.models.Student;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lizq on 2017/8/15.
 */
public interface StudentMapper {

    @Select("select * from student where id=#{param1}")
    Student selectStudnetById(int id);

    @Select("select * from student order by id")
    List<Student> selectStudentAll();

    @Insert("insert into student(name, gender, birthday) VALUE (#{name},#{gender},#{birthday})")
    int insertStudent(Student student);

    @Update("update student set name=#{name},gender=#{gender},birthday=#{birthday} where id=#{id}")
    int updateStudent(Student student);

    @Delete("delete from student where id=#{id}")
    int deleteStudentById(int id);


}
