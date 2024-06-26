package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.dao.MovieDao;
import com.example.demo.dao.MovieSpecification;
import com.example.demo.dao.SearchCriteria;
import com.example.demo.dao.SearchOperation;
import com.example.demo.entity.Movie;

@SpringBootApplication
public class SpecificationExampleApplication implements CommandLineRunner {

	@Autowired
	MovieDao movieDao;
	
	public static void main(String[] args) {
		SpringApplication.run(SpecificationExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		movieDao.saveAll(Arrays.asList(
                new Movie("Troy", "Drama", 7.2, 196, 2004),
                new Movie("The Godfather", "Crime", 9.2, 178, 1972),
                new Movie("Invictus", "Sport", 7.3, 135, 2009),
                new Movie("Black Panther", "Action", 7.3, 135, 2018),
                new Movie("Joker", "Drama", 8.9, 122, 2018),
                new Movie("Iron Man", "Action", 8.9, 126, 2008)
        ));

        // search movies by `genre`
		System.out.println("search movies by `genre`");
        MovieSpecification msGenre = new MovieSpecification();
        msGenre.add(new SearchCriteria("genre", "Action", SearchOperation.EQUAL));
        List<Movie> msGenreList = movieDao.findAll(msGenre);
        msGenreList.forEach(System.out::println);
        System.out.println();
        
        // search movies by `title` and `rating` > 7
        System.out.println("search movies by `title` and `rating` > 7");
        MovieSpecification msTitleRating = new MovieSpecification();
        msTitleRating.add(new SearchCriteria("title", "black", SearchOperation.MATCH));
        msTitleRating.add(new SearchCriteria("rating", 7, SearchOperation.GREATER_THAN));
        List<Movie> msTitleRatingList = movieDao.findAll(msTitleRating);
        msTitleRatingList.forEach(System.out::println);
        System.out.println();
        
        // search movies by release year < 2010 and rating > 8
        System.out.println("search movies by release year < 2010 and rating > 8");
        MovieSpecification msYearRating = new MovieSpecification();
        msYearRating.add(new SearchCriteria("releaseYear", 2010, SearchOperation.LESS_THAN));
        msYearRating.add(new SearchCriteria("rating", 8, SearchOperation.GREATER_THAN));
        List<Movie> msYearRatingList = movieDao.findAll(msYearRating);
        msYearRatingList.forEach(System.out::println);
        System.out.println();
        
        // search movies by watch time >= 150 and sort by `title`
        System.out.println("search movies by watch time >= 150 and sort by `title`");
        MovieSpecification msWatchTime = new MovieSpecification();
        msWatchTime.add(new SearchCriteria("watchTime", 150, SearchOperation.GREATER_THAN_EQUAL));
        List<Movie> msWatchTimeList = movieDao.findAll(msWatchTime, Sort.by("title"));
        msWatchTimeList.forEach(System.out::println);
        System.out.println();
        
        // search movies by title <> 'white' and paginate results
        System.out.println("search movies by title <> 'white' and paginate result");
        MovieSpecification msTitle = new MovieSpecification();
        msTitle.add(new SearchCriteria("title", "white", SearchOperation.NOT_EQUAL));

        Pageable pageable = PageRequest.of(0, 3, Sort.by("releaseYear").descending());
        Page<Movie> msTitleList = movieDao.findAll(msTitle, pageable);

        msTitleList.forEach(System.out::println);
		
	}

}
