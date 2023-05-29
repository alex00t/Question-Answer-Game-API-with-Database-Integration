package com.springRest.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springRest.DTO.QuestionApiResponse;
import com.springRest.Entitey.Question;
import com.springRest.Repository.QuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service

public class QuestionService {

	private static final String apiUrl = "https://jservice.io/api/random?count=5";

	private final QuestionRepository questionRepository;
	@Autowired
	private EntityManager entityManager;

	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	public List<Question> fetchAndStoreRandomQuestionApi() {
		RestTemplate restTemplate = new RestTemplate();
		Question[] questions = restTemplate.getForObject(apiUrl, Question[].class);

		if (questions != null) {
			for (Question question : questions) {
				questionRepository.save(question);
			}
		}
		return fetchAndStoreRandomQuestionApi();
	}

	/* ++++++++++++fetchAndStoreRandomQuestions1+++++++++++++ */

	public List<Question> fetchAndStoreRandomQuestions1() {
		String jpql = "SELECT q FROM Question q ORDER BY RAND()";
		TypedQuery<Question> query = entityManager.createQuery(jpql, Question.class);
		// query.setMaxResults(1); // Specify the desired number of random questions
		return query.getResultList();
	}

	/* ++++++++++++++++fetchRandomQuestionsFromApi+++++++++++++++ */

	public List<QuestionApiResponse> fetchRandomQuestionsFromApi(int count) {

		RestTemplate restTemplate = new RestTemplate();
		String apiUrl = "https://jservice.io/api/random?count=5";
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
		String responseBody = response.getBody();

		// Parse the JSON response and map it to a list of QuestionApiResponse objects
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(responseBody, new TypeReference<List<QuestionApiResponse>>() {
			});
		} catch (JsonProcessingException e) {
			// Handle the exception if needed
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public List<Question> getAllQuestions(List<QuestionApiResponse> questionApiResponses) {
		return questionRepository.findAll();
	}

	/* +++++++++++++++++saveQuestions+++++++++++++++++++ */

	public void saveQuestions(List<QuestionApiResponse> questionApiResponses) {
		for (QuestionApiResponse questionApiResponse : questionApiResponses) {
			Question question = new Question();
			question.setQid(questionApiResponse.getId());
			question.setQuestion(questionApiResponse.getQuestion());
			question.setAnswer(questionApiResponse.getAnswer());

			questionRepository.save(question);
		}
	}

	/* +++++++++++++++fetchRandomQuestionsFromApi++++++++++++++++++++++ */

	public List<QuestionApiResponse> fetchRandomQuestionsFromApi() {
		// TODO Auto-generated method stub
		return fetchRandomQuestionsFromApi(5);
	}

	/* +++++++++++GetRandomQuestion+++++++++++++++++++ */

	public Question getRandomQuestion() {
		List<Question> questions = fetchAndStoreRandomQuestions1();

		if (questions.isEmpty()) {
			return null;
		}

		Random random = new Random();
		int randomIndex = random.nextInt(questions.size());
		return questions.get(randomIndex);
	}

}
