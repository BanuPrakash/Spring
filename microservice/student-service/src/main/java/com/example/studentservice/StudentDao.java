package com.example.studentservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDao extends JpaRepository<Student, Integer> {
    List<Student> findBySchoolId(Integer schoolId);
}
