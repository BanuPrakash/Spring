package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Movie;

public interface MovieDao extends CrudRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

}

