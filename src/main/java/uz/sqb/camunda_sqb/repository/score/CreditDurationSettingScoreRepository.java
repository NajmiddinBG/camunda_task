package uz.sqb.camunda_sqb.repository.score;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.score.CreditDurationScoreSetting;

public interface CreditDurationSettingScoreRepository extends JpaRepository<CreditDurationScoreSetting, Integer> {
}
