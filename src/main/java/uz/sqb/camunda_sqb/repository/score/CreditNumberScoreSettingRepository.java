package uz.sqb.camunda_sqb.repository.score;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.score.CreditNumberScoreSetting;

public interface CreditNumberScoreSettingRepository extends JpaRepository<CreditNumberScoreSetting, Integer> {
}
