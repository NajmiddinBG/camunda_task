package uz.sqb.camunda_sqb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.score_value.Result;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByOrderId(Long orderId);
}
