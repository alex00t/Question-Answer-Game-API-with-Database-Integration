package com.springRest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class QuestionApiResponse {
	//public static final String getQuestion ;
	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "question")
	private String Question;
	@JsonProperty(value = "answer")
    private String Answer;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return Question;
	}
	public void setQuestion(String question) {
		Question = question;
	}
	public String getAnswer() {
		return Answer;
	}
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	public QuestionApiResponse(int id, String question, String answer) {
		super();
		this.id = id;
		Question = question;
		Answer = answer;
	}
	public QuestionApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
   
	
	}