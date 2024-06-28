package com.example.schoolservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolDao dao;
   // private  final StudentClient client;
    private final WebClient.Builder webclient;
    public School saveSchool(School school) {
        return dao.save(school);
    }

    public List<School> getSchools() {
        return  dao.findAll();
    }

    public SchoolDTO findSchoolWithStudents(Integer id) {
        var school = dao.findById(id).get();
       // var students = client.findBySchool(id); // Other microservice
        var students = webclient.build().get()
                .uri("http://localhost:1234/api/students/school/"+id).retrieve()
                .bodyToMono(Student[].class)
                .block();

        return SchoolDTO.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(Arrays.asList(students))
                .build();
    }
}
