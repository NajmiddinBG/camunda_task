package uz.sqb.camunda_sqb.repository.score;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.score.SexScoreSetting;

import java.util.Optional;

public interface SexScoreSettingRepository extends JpaRepository<SexScoreSetting, Integer> {
    Optional<SexScoreSetting> findBySex(Integer sex);
}
