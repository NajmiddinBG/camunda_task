package uz.sqb.camunda_sqb.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.entity.score.AgeScoreSetting;
import uz.sqb.camunda_sqb.entity.score_value.AgeScoring;
import uz.sqb.camunda_sqb.entity.user.User;
import uz.sqb.camunda_sqb.repository.UserRepository;
import uz.sqb.camunda_sqb.repository.score.AgeScoreSettingRepository;
import uz.sqb.camunda_sqb.repository.score_value.AgeScoringRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service("ageSalaryChecking")
@RequiredArgsConstructor
public class AgeScoreChecking implements JavaDelegate {

    private final UserRepository userRepository;
    private final AgeScoreSettingRepository ageScoreSettingRepository;
    private final AgeScoringRepository ageScoringRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String userPnfl = (String) execution.getVariable("user_pnfl");
        Long orderId = (Long) execution.getVariable("order_id");

        User userData = userRepository.findByPnfl(userPnfl).orElseThrow(()-> new RuntimeException("User not found"));
        int userAge = Period.between(userData.getBirthdate(), LocalDate.now()).getYears();

        Optional<AgeScoreSetting> ageScoreSetting = ageScoreSettingRepository.findFirstByBetween(userAge);
        if (ageScoreSetting.isEmpty()) throw new RuntimeException("Age scoring setting value not found");
        Integer ageScore = ageScoreSetting.get().getScore();

        AgeScoring ageScoring = new AgeScoring();
        ageScoring.setAge(userAge);
        ageScoring.setAgeScoreId(ageScoreSetting.get().getId());
        ageScoring.setOrderId(orderId);

        ageScoring.setScore(ageScore);

        AgeScoring savedAgeScoreResult = ageScoringRepository.save(ageScoring);

        execution.setVariable("age_score", ageScore);
        execution.setVariable("age_score_result_object", savedAgeScoreResult);
    }
}
