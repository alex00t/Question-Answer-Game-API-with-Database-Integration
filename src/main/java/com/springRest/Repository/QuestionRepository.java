package com.springRest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springRest.Entitey.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}

