package uz.sqb.camunda_sqb.repository.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.sqb.camunda_sqb.entity.score.SalaryScoreSetting;

import java.util.Optional;

public interface SalaryScoreSettingRepository extends JpaRepository<SalaryScoreSetting, Integer> {

    @Query("FROM SalaryScoreSetting s WHERE s.paramFrom >= :salaryValue AND s.paramTo < :salaryValue")
    Optional<SalaryScoreSetting> findByBetween(Long salaryValue);
}
