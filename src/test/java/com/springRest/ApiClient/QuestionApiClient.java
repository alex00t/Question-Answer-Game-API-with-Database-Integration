package com.springRest.ApiClient;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.springRest.Entitey.Question;

public class QuestionApiClient {
    private static final String API_ENDPOINT = "https://jservice.io/api/random?count=5";

    public List<Question> fetchRandomQuestionsFromApi1() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Question[]> response = restTemplate.getForEntity(API_ENDPOINT, Question[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Arrays.asList(response.getBody());
        } else {
            // Handle API error response
            throw new RuntimeException("Failed to fetch random questions from API. Status code: " + response.getStatusCodeValue());
        }
    }
}