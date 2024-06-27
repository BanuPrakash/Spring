package com.example.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentDao dao;

    public Student saveStudent(Student s) {
        return dao.save(s);
    }

    public List<Student> getStudents() {
        return  dao.findAll();
    }

    public List<Student> getStudentsBySchool(Integer schoolId) {
        return  dao.findBySchoolId(schoolId);
    }
}
