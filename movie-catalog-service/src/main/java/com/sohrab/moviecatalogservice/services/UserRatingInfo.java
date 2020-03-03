package com.sohrab.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sohrab.moviecatalogservice.model.Rating;
import com.sohrab.moviecatalogservice.model.UserRating;

@Service
public class UserRatingInfo {

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	public UserRating getUserRating(@PathVariable("userid") String userId) {
		return restTemplate.getForObject("http://data-rating-service/ratingdata/users/" + userId, UserRating.class);
	}

	public UserRating getFallbackUserRating(@PathVariable("userid") String userId) {
		UserRating userRating = new UserRating();
		userRating.setUserRating(Arrays.asList(new Rating("0", 0)));

		return userRating;
	}
}
