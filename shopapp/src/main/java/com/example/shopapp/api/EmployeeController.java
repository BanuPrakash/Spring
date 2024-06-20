package com.example.shopapp.api;

import com.example.shopapp.dto.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    Employee employee = new Employee();
    public EmployeeController() {
        employee.setId(1);
        employee.setTitle("Sr. Software Engineer");
        employee.getSkills().add("Spring Boot");
        employee.getSkills().add("React JS");
        employee.getCommunication().put("email","abc@gmail.com");
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Employee updateEmployee(@PathVariable("id") int id , @RequestBody JsonPatch patch) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        var target = patch.apply(mapper.readTree(mapper.writeValueAsString(employee)));
        System.out.println(target);
        return  mapper.treeToValue(target, Employee.class);
    }
}
