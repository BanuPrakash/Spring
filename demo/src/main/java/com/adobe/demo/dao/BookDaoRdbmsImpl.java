package com.adobe.demo.dao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "book-dao", havingValue = "rdbms")
public class BookDaoRdbmsImpl implements BookDao {

	@Override
	public void addBook() {
		System.out.println("Stored in RDBMS!!!");
	}

}
