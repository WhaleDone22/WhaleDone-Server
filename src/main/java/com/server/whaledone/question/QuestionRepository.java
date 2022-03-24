package com.server.whaledone.question;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.question.entity.Question;
import com.server.whaledone.question.entity.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<List<Question>> findAllByCategoryAndStatus(QuestionCategory category, Status status);
}
