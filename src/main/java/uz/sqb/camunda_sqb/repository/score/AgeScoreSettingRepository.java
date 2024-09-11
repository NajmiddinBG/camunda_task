package uz.sqb.camunda_sqb.repository.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.sqb.camunda_sqb.entity.score.AgeScoreSetting;

import java.util.Optional;

public interface AgeScoreSettingRepository extends JpaRepository<AgeScoreSetting, Integer> {

    @Query("FROM AgeScoreSetting a WHERE a.paramFrom >= :userAge AND a.paramTo < :userAge")
    Optional<AgeScoreSetting> findFirstByBetween(int userAge);
}
