package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


@Entity
public class Employee {
	 @Id
//	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name = "id", updatable = false, nullable = false)
	    private Long id = null;

	    private String name;

	    private String surname;

	    @OneToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "address_id")
	    private Address address;

	    @ManyToOne(fetch = FetchType.LAZY)
	    private Department department;

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

		public String getSurname() {
			return surname;
		}

		public void setSurname(String surname) {
			this.surname = surname;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public Department getDepartment() {
			return department;
		}

		public void setDepartment(Department department) {
			this.department = department;
		}
	    
}
