package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Car {
	 @Id
	    @Column(name = "id", updatable = false, nullable = false)
	    private Long id = null;

	    private String registrationNumber;

	    @ManyToOne(fetch = FetchType.LAZY)
	    private Company company;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getRegistrationNumber() {
			return registrationNumber;
		}

		public void setRegistrationNumber(String registrationNumber) {
			this.registrationNumber = registrationNumber;
		}

		public Company getCompany() {
			return company;
		}

		public void setCompany(Company company) {
			this.company = company;
		}
	    
	    
}
