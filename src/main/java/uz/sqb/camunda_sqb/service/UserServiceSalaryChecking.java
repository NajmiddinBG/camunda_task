package uz.sqb.camunda_sqb.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.dto.SalaryResponseDTO;
import uz.sqb.camunda_sqb.entity.score.SalaryScoreSetting;
import uz.sqb.camunda_sqb.entity.score_value.SalaryScoring;
import uz.sqb.camunda_sqb.entity.user.User;
import uz.sqb.camunda_sqb.entity.user.UserSalary;
import uz.sqb.camunda_sqb.repository.UserRepository;
import uz.sqb.camunda_sqb.repository.UserSalaryRepository;
import uz.sqb.camunda_sqb.repository.score.SalaryScoreSettingRepository;
import uz.sqb.camunda_sqb.repository.score_value.SalaryScoringRepository;
import uz.sqb.camunda_sqb.service.utils.SalaryService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("userServiceSalaryChecking")
@RequiredArgsConstructor
public class UserServiceSalaryChecking implements JavaDelegate {

    private final SalaryService salaryService;
    private final SalaryScoreSettingRepository salaryScoreSettingRepository;
    private final SalaryScoringRepository salaryScoringRepository;
    private final UserSalaryRepository userSalaryRepository;
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) {

        String pnfl = (String) execution.getVariable("user_pnfl");
        Long orderId = (Long) execution.getVariable("order_id");

        User userData = userRepository.findByPnfl(pnfl).orElseThrow(()-> new RuntimeException("User not found"));

//        Integer orderType = (Integer) execution.getVariable("order_type");
        List<SalaryResponseDTO> salaryDetails = salaryService.getSalaryDetails(pnfl);
        Long totalMonthlyAmount = 0L;
        int activeCount = 0;
        for (SalaryResponseDTO salaryDetail : salaryDetails) {
            UserSalary userSalary = new UserSalary(null, orderId, salaryDetail.getAmount(), salaryDetail.getCompanyName(), salaryDetail.getInn(), salaryDetail.getDate());
            UserSalary savedUserSalary = userSalaryRepository.save(userSalary);
            if (salaryDetail.getIsActive() == 1) {
                activeCount++;
                totalMonthlyAmount += savedUserSalary.getAmount();
            }
        }

        Long salaryValue = totalMonthlyAmount/activeCount;

        Optional<SalaryScoreSetting> salaryScoreSetting = salaryScoreSettingRepository.findByBetween(salaryValue);
        if (salaryScoreSetting.isEmpty()) throw new RuntimeException("Salary Score setting not found");

        SalaryScoring scoring = new SalaryScoring();
        scoring.setSalary(salaryValue);
        scoring.setSalaryScoreId(salaryScoreSetting.get().getId());
        scoring.setOrderId(orderId);
        scoring.setScore(salaryScoreSetting.get().getScore());

        SalaryScoring savedScoring = salaryScoringRepository.save(scoring);

        execution.setVariable("salary_scoring_object", savedScoring);
        execution.setVariable("salary_score", savedScoring.getScore());

    }
}
