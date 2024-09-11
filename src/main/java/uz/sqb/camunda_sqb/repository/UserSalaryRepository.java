package uz.sqb.camunda_sqb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.user.UserSalary;

import java.util.List;

public interface UserSalaryRepository extends JpaRepository<UserSalary, Long> {
    List<UserSalary> findAllByOrderId(Long orderId);
}
