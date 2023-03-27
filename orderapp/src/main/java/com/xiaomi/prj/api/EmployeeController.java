package com.xiaomi.prj.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.xiaomi.prj.entity.Employee;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
	@Autowired
	ObjectMapper mapper;
	
	Employee e = null;
	public EmployeeController() {
		e = new Employee();
		e.setId(123);
		e.setTelephone("+918232333");
		List<String> skills = Arrays.asList("Java", "PHP", "TypeScript");
		e.setSkills(skills);
	}
	
	// PATCH http://localhost:8080/api/employees/123
	@PatchMapping(path="/{id}", consumes = "application/json-patch+json")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception {
		Employee patchedEmployee = applyPatchToEmployee(patch, e);
		//send patchedEmployee to DB using JpaRepository
		return ResponseEntity.ok(patchedEmployee);
	}
	
	private Employee applyPatchToEmployee(JsonPatch patch, Employee target) throws Exception {
		JsonNode patched = patch.apply(mapper.convertValue(target, JsonNode.class));
		return mapper.treeToValue(patched, Employee.class);
	}
}
