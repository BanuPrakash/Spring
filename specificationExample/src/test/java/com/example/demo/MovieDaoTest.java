package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.demo.dao.MovieDao;
import com.example.demo.entity.Movie;

@DataJpaTest
public class MovieDaoTest {

	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private TestEntityManager entityManager;
	
	 @Test
	  public void emptyMovieRecords() {
	    Iterable<Movie> movies = movieDao.findAll();
	    assertThat(movies).isNotEmpty();
	  }
	 
	 
}
