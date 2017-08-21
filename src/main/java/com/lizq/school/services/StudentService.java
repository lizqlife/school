package com.lizq.school.services;

import com.lizq.school.mappers.StudentMapper;
import com.lizq.school.models.student.Student;
import com.lizq.school.models.student.StudentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lizq on 2017/8/15.
 */
@Service
public class StudentService {

    //注入mapper
    private StudentMapper studentMapper;
    //注入redisService
    private  RedisService redisService;

    //学生对象缓存前缀
    private static final String stuPrefix="school:student";

    @Autowired
    public StudentService(StudentMapper studentMapper,RedisService redisService){
        this.studentMapper=studentMapper;
        this.redisService=redisService;
    }

    //根据id查询学生
    public Student getStudentById(int id){
        //先从缓存中查找学生对象
        Student student=redisService.get(stuPrefix+id,Student.class);
        if(student==null){
            student=studentMapper.selectStudnetById(id);
            if(student!=null){
                redisService.set(stuPrefix+id,student,60*30);
            }
        }
        return student;
    }

    //更新学生
    public void updateStudent(Student student){
        //如果学生信息更新成功，需要清除缓存数据
        int rowAffected=studentMapper.updateStudent(student);
        if(rowAffected<1){
            throw new RuntimeException("Failed update student");
        }else{
            //删除该学生的缓存
            redisService.del(stuPrefix+student.getId());
            //删除学生列表的缓存
            redisService.del("school:student:All");

        }
    }

    //增加学生
    public void insertStudent(Student student){
        //如果学生信息增加成功，需要清除缓存数据
        int rowAffected= studentMapper.insertStudent(student);
        if(rowAffected<1){
            throw new RuntimeException("Failed insert student");
        }else{
            //删除该学生的缓存
            redisService.del(stuPrefix+student.getId());
            //删除学生列表的缓存
            redisService.del("school:student:All");

        }
    }

    //删除学生
    public void deleteStudent(int id){
        studentMapper.deleteStudentById(id);
        //如果学生信息删除成功，需要清除缓存数据
        int rowAffected=studentMapper.deleteStudentById(id);
        if(rowAffected<1){
            throw new RuntimeException("Failed delete student");
        }else{
            //删除该学生的缓存
            redisService.del(stuPrefix+id);
            //删除学生列表的缓存
            redisService.del("school:student:All");

        }
    }

    //查询所有学生
    public StudentList getAllStudents(){
        StudentList studentList=redisService.get("school:student:All",StudentList.class);
        if(studentList==null){
            studentList=new StudentList();
            studentList.setStudentList(studentMapper.selectStudentAll());
            redisService.set("school:student:All",studentList,60*30);
        }
        return  studentList;
    }
}
