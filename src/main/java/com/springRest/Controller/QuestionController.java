package com.springRest.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springRest.DTO.QuestionApiResponse;
import com.springRest.Entitey.Question;
import com.springRest.Service.QuestionService;

@RestController
@RequestMapping("/")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	/* ____________________FETCH___________________ */
	@PostMapping("/fetch")

	public ResponseEntity<String> fetchRandomQuestionsFromApi() {

		List<QuestionApiResponse> questionApiResponses = questionService.fetchRandomQuestionsFromApi();
		if (questionApiResponses.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No questions available");
		}

		QuestionApiResponse randomQuestion = questionApiResponses.get(0);
		Map<String, Object> response = new HashMap<>();
		response.put("questionId", randomQuestion.getId());
		response.put("question", randomQuestion.getQuestion());
		response.put("answer", randomQuestion.getAnswer());
		questionService.saveQuestions(questionApiResponses);
		return ResponseEntity.ok("Questions fetched and saved successfully.");

	}

	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	/*--------------------------PLAY API-------------------------------*/

	@GetMapping("/play")
	public ResponseEntity<Map<String, Object>> play() {
		Question question = questionService.getRandomQuestion();

		if (question == null) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		}

		Map<String, Object> response = new HashMap<>();
		response.put("question_id", question.getQid());
		response.put("question", question.getQuestion());

		return ResponseEntity.ok(response);
	}

	/* ++++++++++++++++++++NEXT API+++++++++++++++++++++++++++++++ */

	@PostMapping("/next")
	public ResponseEntity<Map<String, Object>> getNextQuestion(@RequestBody Map<String, Object> request) {
		// Long questionId = Long.parseLong(request.get("question_id").toString());
		// String answer = request.get("answer").toString();

		String questionIdString = request.get("question_id").toString();
		String answer = request.get("answer").toString();

		Long questionId = null;
		try {
			questionId = Long.parseLong(questionIdString);
		} catch (NumberFormatException e) {
			// Handle the case when the question_id is not a valid numeric value
		}

		answer = null;
		if (request.get("answer") != null) {
			answer = request.get("answer").toString();
		}

		Question currentQuestion = questionService.getRandomQuestion();
		if (currentQuestion == null) {
			return ResponseEntity.notFound().build();
		}

		Map<String, Object> response = new HashMap<>();
		response.put("correct_answer", currentQuestion.getAnswer());

		// Get the next question
		Question nextQuestion = questionService.getRandomQuestion();
		if (nextQuestion != null) {
			Map<String, Object> nextQuestionData = new HashMap<>();
			nextQuestionData.put("question_id", nextQuestion.getQid());
			nextQuestionData.put("question", nextQuestion.getQuestion());
			response.put("next_question", nextQuestionData);
		}

		return ResponseEntity.ok(response);
	}

	/* ////////////////////////////RANDOM+++++++++++++++++++++++++++++++++++++++ */

	/*
	 * private Question getRandomQuestion() { // Fetch all questions from the
	 * database List<Question> questions =
	 * questionService.fetchAndStoreRandomQuestionApi(); try { if
	 * (questions.isEmpty()) { return null; // Return null if no questions are
	 * available } } catch (NullPointerException e) { e.printStackTrace(); }
	 * 
	 * Random random = new Random(); int randomIndex =
	 * random.nextInt(questions.size()); return questions.get(randomIndex); }
	 */
}
