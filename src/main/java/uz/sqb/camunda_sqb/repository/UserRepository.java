package uz.sqb.camunda_sqb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sqb.camunda_sqb.entity.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPnfl(String pnfl);

}
