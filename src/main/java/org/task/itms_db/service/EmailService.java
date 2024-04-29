package org.task.itms_db.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.task.itms_db.entity.FeedbackEntity;
import org.task.itms_db.entity.QuestionEntity;
import org.task.itms_db.enums.TalentStatus;
import org.task.itms_db.exception.UserCantAccessException;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.jwt.JwtService;
import org.task.itms_db.repository.EmployeeRepository;
import org.task.itms_db.repository.FeedbackRepository;

import java.util.List;

@Service
public class EmailService {

    private final EmployeeRepository employeeRepository;
    private final FeedbackRepository feedbackRepository;
    private final JwtService service;

    public EmailService(EmployeeRepository repository, FeedbackRepository feedbackRepository, JwtService service) {
        this.employeeRepository = repository;
        this.feedbackRepository = feedbackRepository;
        this.service = service;
    }

    public void validateData(QuestionEntity entity, HttpServletRequest request) throws UserNotFoundException, UserCantAccessException {
        String token = request.getServletPath().substring(6);
        List<Integer> claims;
        try {
            claims = service.extractEmployeeAndFeedback(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, "Токен просрочений");
        }
        if (employeeRepository.existsById(Long.valueOf(claims.getFirst()))) {
            FeedbackEntity feedback = feedbackRepository.findById(Long.valueOf(claims.get(1))).get();
            String status = feedback.getStatus();
            if (status.equals(TalentStatus.ANSWERED.getValue()) || status.equals(TalentStatus.EXPIRED.getValue())) {
                throw new UserCantAccessException("Користувач не має доступу");
            } else {
                feedback.setStatus(TalentStatus.ANSWERED.getValue());
                feedback.setAnswers(entity.getQuestion1() + " " + entity.getQuestion2() + " " + entity.getQuestion3() +
                    " " + entity.getQuestion4() + " " + entity.getQuestion5()
                );
                feedbackRepository.save(feedback);
            }
        } else {
            throw new UserNotFoundException("Користувач не знайдений");
        }
    }
}
