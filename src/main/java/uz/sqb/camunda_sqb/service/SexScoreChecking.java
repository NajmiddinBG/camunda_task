package uz.sqb.camunda_sqb.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.entity.score.SexScoreSetting;
import uz.sqb.camunda_sqb.entity.score_value.SexScoring;
import uz.sqb.camunda_sqb.entity.user.User;
import uz.sqb.camunda_sqb.repository.UserRepository;
import uz.sqb.camunda_sqb.repository.score.SexScoreSettingRepository;
import uz.sqb.camunda_sqb.repository.score_value.SexScoringRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SexScoreChecking implements JavaDelegate {

    private final UserRepository userRepository;
    private final SexScoreSettingRepository sexScoreSettingRepository;
    private final SexScoringRepository sexScoringRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Long orderId = (Long) execution.getVariable("order_id");
        String pnfl = (String) execution.getVariable("user_pnfl");

        User userData = userRepository.findByPnfl(pnfl).orElseThrow(()-> new RuntimeException("User not found"));

        Optional<SexScoreSetting> sexScoreSetting = sexScoreSettingRepository.findBySex(userData.getSex());
        if (sexScoreSetting.isEmpty()) throw new RuntimeException("Gender setting value not found");

        int genderScore = sexScoreSetting.get().getScore();

        SexScoring scoring = new SexScoring();
        scoring.setSexScoreId(sexScoreSetting.get().getId());
        scoring.setScore(genderScore);
        scoring.setSex(sexScoreSetting.get().getSex());
        scoring.setOrderId(orderId);

        SexScoring savedGenderScoring = sexScoringRepository.save(scoring);

        execution.setVariable("sex_score", genderScore);
        execution.setVariable("gender_scoring_object", savedGenderScoring);

    }
}
