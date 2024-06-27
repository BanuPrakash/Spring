package com.example.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class StudentController {
    private  final StudentService service;

    @PostMapping
    public Student save(@RequestBody Student s) {
        return  service.saveStudent(s);
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getStudents();
    }

    @GetMapping("/school/{school-id}")
    public List<Student> getStudentsBySchool(@PathVariable("school-id") Integer id) {
        return service.getStudentsBySchool(id);
    }

}
