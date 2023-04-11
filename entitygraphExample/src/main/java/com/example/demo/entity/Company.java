package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;


@Entity

@NamedEntityGraphs({
        @NamedEntityGraph(name = "companyWithDepartmentsGraph",
                attributeNodes = {@NamedAttributeNode("departments")}),
        
        @NamedEntityGraph(name = "companyWithDepartmentsAndEmployeesGraph",
                attributeNodes = {@NamedAttributeNode(value = "departments", subgraph = "departmentsWithEmployees")},
                subgraphs = @NamedSubgraph(
                        name = "departmentsWithEmployees",
                        attributeNodes = @NamedAttributeNode("employees"))),
        
        @NamedEntityGraph(name = "companyWithDepartmentsAndEmployeesAndOfficesGraph",
                attributeNodes = {@NamedAttributeNode(value = "departments", subgraph = "departmentsWithEmployeesAndOffices")},
                subgraphs = @NamedSubgraph(
                        name = "departmentsWithEmployeesAndOffices",
                        attributeNodes = {@NamedAttributeNode("employees"), @NamedAttributeNode("offices")}))
})
public class Company {
	 @Id
	    @Column(name = "id", updatable = false, nullable = false)
	    private Long id = null;

	    private String name;

	    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	    private Set<Department> departments = new HashSet<>();

	    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	    private Set<Car> cars = new HashSet<>();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Set<Department> getDepartments() {
			return departments;
		}

		public void setDepartments(Set<Department> departments) {
			this.departments = departments;
		}

		public Set<Car> getCars() {
			return cars;
		}

		public void setCars(Set<Car> cars) {
			this.cars = cars;
		}
	    
	    
}
