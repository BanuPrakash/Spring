package com.example.demo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Department {
	 @Id
	    @Column(name = "id", updatable = false, nullable = false)
	    private Long id = null;

	    private String name;

	    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	    private Set<Employee> employees = new HashSet<>();

	    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
	    private Set<Office> offices = new HashSet<>();

	    @ManyToOne(fetch = FetchType.LAZY)
	    private Company company;

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

		public Set<Employee> getEmployees() {
			return employees;
		}

		public void setEmployees(Set<Employee> employees) {
			this.employees = employees;
		}

		public Set<Office> getOffices() {
			return offices;
		}

		public void setOffices(Set<Office> offices) {
			this.offices = offices;
		}

		public Company getCompany() {
			return company;
		}

		public void setCompany(Company company) {
			this.company = company;
		}
	    
	    
}
