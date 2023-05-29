package com.springRest.Entitey;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.springRest.DTO.QuestionApiResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import javax.persistence.*;

@Entity

public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EmbeddedId 
	
	    private int Qid;

	private String question;

	@Column(nullable = false)
	private String answer;

	public Question(String question) {
		super();
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	/*
	 * private String difficulty;
	 * 
	 * private String category;
	 */

	public Question() {
		super();

	}

	public Long getQid() {
		return (long) Qid;
	}

	public void setQid(int Qid) {
		this.Qid = Qid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public QuestionApiResponse setId(QuestionApiResponse questionApiResponse) {
		// TODO Auto-generated method stub
		return null;
	}

}
