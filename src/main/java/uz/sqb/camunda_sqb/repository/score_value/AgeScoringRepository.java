package uz.sqb.camunda_sqb.repository.score_value;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.score_value.AgeScoring;

public interface AgeScoringRepository extends JpaRepository<AgeScoring, Long> {
}
