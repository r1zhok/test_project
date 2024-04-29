package org.task.itms_db.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.task.itms_db.entity.EmployeeEntity;
import org.task.itms_db.entity.FeedbackEntity;
import org.task.itms_db.enums.TalentStatus;
import org.task.itms_db.repository.EmployeeRepository;
import org.task.itms_db.repository.FeedbackRepository;
import org.task.itms_db.service.EmailSenderService;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableScheduling
public class ScheduledTask {

    private final EmployeeRepository employeeRepository;
    private final FeedbackRepository feedbackRepository;
    private final EmailSenderService service;

    public ScheduledTask(EmployeeRepository repository, FeedbackRepository feedbackRepository, EmailSenderService service) {
        this.employeeRepository = repository;
        this.feedbackRepository = feedbackRepository;
        this.service = service;
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void sendEmail() {
        for (EmployeeEntity employee : employeeRepository.findAll()) {
            if (!employee.getFeedbacks().isEmpty()) {
                FeedbackEntity feedback = employee.getFeedbacks().getLast();
                if (ChronoUnit.WEEKS.between(feedback.getCreated().toLocalDate(), LocalDate.now()) >=
                        parseFrequency(employee.getFeedbackFrequency())) {
                    service.sendMessage(employee.getEmail(), "Будь ласка,пройди цю форму", "....",
                            employee.getId(), feedbackRepository.save(new FeedbackEntity(TalentStatus.SEND.getValue(),
                                    Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), employee)).getId());
                }
            } else {
                if (ChronoUnit.WEEKS.between(employee.getStartDate().toLocalDate(), LocalDate.now()) >=
                        parseFrequency(employee.getFeedbackFrequency())) {
                    service.sendMessage(employee.getEmail(), "Будь ласка,пройди цю форму", "....",
                            employee.getId(), feedbackRepository.save(new FeedbackEntity(TalentStatus.SEND.getValue(),
                                    Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), employee)).getId());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 9 * * WED")
    public void resendEmail() {
        for (EmployeeEntity employee : employeeRepository.findAll()) {
            if (!employee.getFeedbacks().isEmpty()) {
                FeedbackEntity feedback = employee.getFeedbacks().getLast();
                if (feedback.getStatus().equals(TalentStatus.SEND.getValue())) {
                    service.sendMessage(employee.getEmail(), "Ти маєш пройти цю форму", "....",
                            employee.getId(), feedback.getId());
                    feedback.setUpdated(Date.valueOf(LocalDate.now()));
                    feedback.setStatus(TalentStatus.RESEND.getValue());
                    feedbackRepository.save(feedback);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 9 * * FRI")
    public void expiredEmail() {
        for (EmployeeEntity employee : employeeRepository.findAll()) {
            if (!employee.getFeedbacks().isEmpty()) {
                FeedbackEntity feedback = employee.getFeedbacks().getLast();
                if (feedback.getStatus().equals(TalentStatus.RESEND.getValue())) {
                    feedback.setStatus(TalentStatus.EXPIRED.getValue());
                    feedback.setAnswers(null);
                    feedbackRepository.save(feedback);
                }
            }
        }
    }

    private long parseFrequency(String frequency) {
        return switch (frequency) {
            case "1V" -> 1;
            case "2V" -> 2;
            case "M" -> 4;
            default -> 0;
        };
    }
}