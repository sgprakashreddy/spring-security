package com.javatechie.springsecurity.controller;

import com.javatechie.springsecurity.entity.UserInfo;
import com.javatechie.springsecurity.model.Student;
import com.javatechie.springsecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>();

//    @Autowired
//    private ProductService productService;

    @GetMapping("/students")
    public List<Student> getStudents(){

        Student st1 = new Student(1,"Rama","Krishna");
        Student st2 = new Student(2,"Sita","Maha");
        students.clear();
        students.add(st1);
        students.add(st2);

        return students;
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student){
        System.out.println("****************");
        System.out.println("student = " + student);
        students.add(student);
        return student;
    }

//    @PostMapping("/new")
//    public String addNewUser(@RequestBody UserInfo userInfo){
//        return productService.addUser(userInfo);
//    }

}
