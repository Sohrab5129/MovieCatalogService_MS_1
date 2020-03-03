package com.sohrab.moviecatalogservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sohrab.moviecatalogservice.model.CatalogItem;
import com.sohrab.moviecatalogservice.model.UserRating;
import com.sohrab.moviecatalogservice.services.MovieInfo;
import com.sohrab.moviecatalogservice.services.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

	@Autowired
	MovieInfo movieInfo;

	@Autowired
	UserRatingInfo userRatingInfo;

//	@Autowired
//	private DiscoveryClient discoveryClient;

//	@Autowired
//	private WebClient.Builder webClientBuilder;

	@GetMapping("/{userid}")
//	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<CatalogItem> getCatalog(@PathVariable("userid") String userId) {

		UserRating userRating = userRatingInfo.getUserRating(userId);

		return userRating.getUserRating().stream().map(rating -> movieInfo.getCatalogItem(rating))
				.collect(Collectors.toList());
	}

//	public List<CatalogItem> getFallbackCatalog(@PathVariable("userid") String userId) {
//		return Arrays.asList(new CatalogItem("No movie", "", 0));
//	}

//	// List<Rating> ratings = Arrays.asList(new Rating("1234", 4), new
//	// Rating("1234", 4));
//
//	UserRating userRating = restTemplate.getForObject("http://data-rating-service/ratingdata/users/" + userId,
//			UserRating.class);
//
//	return userRating.getUserRating().stream().map(rating -> {
//
//		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(),
//				Movie.class);
//
////		Movie movie = webClientBuilder
////				.build().get()
////				.uri("http://localhost:8082/movies/" + rating.getMovieId())
////				.retrieve()
////				.bodyToMono(Movie.class)
////				.block();
//
//		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
//
//	}).collect(Collectors.toList());
//
////	return ratings.stream().map(rating -> {
////		Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
////
////		return new CatalogItem(movie.getName(), "Desc", rating.getRating());
////
////	}).collect(Collectors.toList());

}
