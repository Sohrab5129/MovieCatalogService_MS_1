package com.sohrab.moviecatalogservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sohrab.moviecatalogservice.model.CatalogItem;
import com.sohrab.moviecatalogservice.model.Movie;
import com.sohrab.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;
	
//	@Autowired
//	private DiscoveryClient discoveryClient;

//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@GetMapping("/{userid}")
	public List<CatalogItem> getCatalog(@PathVariable("userid") String userId) {

		//List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new Rating("1234", 4));
		
		UserRating userRating = restTemplate.getForObject("http://data-rating-service/ratingdata/users/"+userId, UserRating.class);
		
		return userRating.getUserRating().stream().map(rating -> {

			 Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" +
			 rating.getMovieId(), Movie.class);
			 
//			Movie movie = webClientBuilder
//					.build().get()
//					.uri("http://localhost:8082/movies/" + rating.getMovieId())
//					.retrieve()
//					.bodyToMono(Movie.class)
//					.block();

			return new CatalogItem(movie.getName(), "Desc", rating.getRating());

		}).collect(Collectors.toList());

//		return ratings.stream().map(rating -> {
//			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
//
//			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
//
//		}).collect(Collectors.toList());
		
	}
}
