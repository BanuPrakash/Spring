package com.example.schoolservice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {
    private final SchoolService service;

    @PostMapping
    public  School save(@RequestBody School school) {
        return  service.saveSchool(school);
    }

    @GetMapping
    public List<School> getSchools() {
        return service.getSchools();
    }

    @GetMapping("/with-students/{school-id}")
    public  SchoolDTO findSchoolsAndStudents(@PathVariable("school-id") Integer id) {
        return service.findSchoolWithStudents(id);
    }

}
