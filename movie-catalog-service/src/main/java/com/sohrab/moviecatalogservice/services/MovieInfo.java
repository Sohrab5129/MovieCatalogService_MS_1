package com.sohrab.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sohrab.moviecatalogservice.model.CatalogItem;
import com.sohrab.moviecatalogservice.model.Movie;
import com.sohrab.moviecatalogservice.model.Rating;

@Service
public class MovieInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem", 
			groupKey = "StoreSubmission", commandKey = "StoreSubmission", threadPoolKey = "StoreSubmission", 
			commandProperties = {
			        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
			        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
			        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "6000"),
			        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "18000") }, 
			threadPoolProperties = {
			        @HystrixProperty(name = "coreSize", value = "30"),
			        @HystrixProperty(name = "maxQueueSize", value = "10"),
			        @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "18000") })
	
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());

	}

	public CatalogItem getFallbackCatalogItem(Rating rating) {
		return new CatalogItem("Not found", "", rating.getRating());
	}

}
