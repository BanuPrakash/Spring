package com.adobe.demo.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDaoRdbmsImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Stored in RDBMS!!!");
	}

}
