package com.lizq.school.controller;

import com.lizq.school.exceptions.NotFoundException;
import com.lizq.school.models.student.Student;
import com.lizq.school.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lizq on 2017/8/14.
 */
@RestController
@RequestMapping("students")
public class StudentController {

       private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //根据id获取学生
    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public ResponseEntity getStudentById(@PathVariable int id){
        Student student=studentService.getStudentById(id);
        if(student==null){
            throw  new NotFoundException("");
        }
        return ResponseEntity.ok(student);
    }

    //查询所有学生
    @RequestMapping(value = "all",method = RequestMethod.GET)
    public ResponseEntity getAllStudnets(){
        return ResponseEntity.ok(studentService.getAllStudents());

    }

    //增加学生
    @RequestMapping(value = "",method = RequestMethod.PUT)
    public  ResponseEntity insertStudent(@RequestBody Student student){
        studentService.insertStudent(student);
        return ResponseEntity.ok("student created!");
    }

    //更改学生
    @RequestMapping(value = "",method = RequestMethod.POST)
    public  ResponseEntity updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
        return ResponseEntity.ok("student update!");
    }

    //删除学生
    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public  ResponseEntity deleteStudent(@PathVariable int id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok("student delete!");
    }


}
