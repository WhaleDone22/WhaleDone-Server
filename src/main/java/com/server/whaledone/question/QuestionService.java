package com.server.whaledone.question;

import com.server.whaledone.config.Entity.Status;
import com.server.whaledone.config.response.exception.CustomException;
import com.server.whaledone.config.response.exception.CustomExceptionStatus;
import com.server.whaledone.question.dto.QuestionResponseDto;
import com.server.whaledone.question.entity.Question;
import com.server.whaledone.question.entity.QuestionCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<QuestionResponseDto> getQuestionsPerCategory() {
        List<QuestionResponseDto> result = new ArrayList<>();
        for (QuestionCategory category : QuestionCategory.values()) {
            List<Question> questions = questionRepository.findAllByCategoryAndStatus(category, Status.ACTIVE)
                    .orElseThrow(() -> new CustomException(CustomExceptionStatus.QUESTION_NOT_EXISTS_IN_CATEGORY));
            Collections.shuffle(questions);
            result.add(new QuestionResponseDto(questions.get(0)));
        }

        return result;
    }
}
