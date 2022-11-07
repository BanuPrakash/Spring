package com.adobe.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adobe.demo.dao.BookDao;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao; 
	
	public void doTask() {
		bookDao.addBook();
	}
	
}
