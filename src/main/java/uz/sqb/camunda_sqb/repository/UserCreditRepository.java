package uz.sqb.camunda_sqb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.user.UserCredit;

import java.util.List;

public interface UserCreditRepository extends JpaRepository<UserCredit, Long> {
    List<UserCredit> findAllByOrderId(Long orderId);
}
