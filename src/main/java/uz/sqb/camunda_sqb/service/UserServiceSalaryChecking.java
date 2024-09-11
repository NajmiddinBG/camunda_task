package uz.sqb.camunda_sqb.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.dto.SalaryResponseDTO;
import uz.sqb.camunda_sqb.entity.score_value.Result;
import uz.sqb.camunda_sqb.entity.user.UserSalary;
import uz.sqb.camunda_sqb.enums.CreditType;
import uz.sqb.camunda_sqb.enums.ResultStatus;
import uz.sqb.camunda_sqb.repository.ResultRepository;
import uz.sqb.camunda_sqb.repository.score.SalaryScoreSettingRepository;
import uz.sqb.camunda_sqb.repository.UserSalaryRepository;
import uz.sqb.camunda_sqb.service.utils.SalaryService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("userServiceSalaryChecking")
@RequiredArgsConstructor
public class UserServiceSalaryChecking implements JavaDelegate {

    private final SalaryService salaryService;
    private final SalaryScoreSettingRepository salaryScoreSettingRepository;
    private final ResultRepository resultRepository;
    private final UserSalaryRepository userSalaryRepository;

    @Override
    public void execute(DelegateExecution execution) {

        String pnfl = (String) execution.getVariable("user_pnfl");
        Long orderId = (Long) execution.getVariable("order_id");
        Integer orderType = (Integer) execution.getVariable("order_type");
        List<SalaryResponseDTO> salaryDetails = salaryService.getSalaryDetails(pnfl);
        Long totalMonthlyAmount = 0L;
        for (SalaryResponseDTO salaryDetail : salaryDetails) {
            UserSalary userSalary = new UserSalary(null, orderId, salaryDetail.getAmount(), salaryDetail.getCompanyName(), salaryDetail.getInn(), salaryDetail.getDate());
            UserSalary savedUserSalary = userSalaryRepository.save(userSalary);
            totalMonthlyAmount += savedUserSalary.getAmount();
        }

        Optional<LocalDate> oldestDate = salaryDetails.stream()
                .map(SalaryResponseDTO::getDate)
                .min(LocalDate::compareTo);

        if (oldestDate.isPresent()) {
            LocalDate date = oldestDate.get();
            LocalDate today = LocalDate.now();

            // Eng uzoq o'tgan sanadan bugungi kungacha o‘tgan vaqtni hisoblash
            Period period = Period.between(date, today);
            int monthsBetween = period.getYears() * 12 + period.getMonths();

            Result result = new Result();
            result.setOrderId(orderId);
            result.setStatus(ResultStatus.PROCESSING);
            result.setTotalScore(0);

            result.setCreditType(orderType == 1 ? CreditType.OVERDRAFT : CreditType.MICROZAYM);
            execution.setVariable("working_month", monthsBetween);
            execution.setVariable("monthly_amount", totalMonthlyAmount / salaryDetails.size());
        }
    }
}
