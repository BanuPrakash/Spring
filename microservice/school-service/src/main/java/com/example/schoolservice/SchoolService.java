package com.example.schoolservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolDao dao;
    private  final StudentClient client;

    public School saveSchool(School school) {
        return dao.save(school);
    }

    public List<School> getSchools() {
        return  dao.findAll();
    }

    public SchoolDTO findSchoolWithStudents(Integer id) {
        var school = dao.findById(id).get();
        var students = client.findBySchool(id); // Other microservice

        return SchoolDTO.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students)
                .build();
    }
}
