package uz.sqb.camunda_sqb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sqb.camunda_sqb.entity.user.User;
import uz.sqb.camunda_sqb.entity.user.UserCredit;
import uz.sqb.camunda_sqb.entity.user.UserSalary;
import uz.sqb.camunda_sqb.repository.UserCreditRepository;
import uz.sqb.camunda_sqb.repository.UserRepository;
import uz.sqb.camunda_sqb.repository.UserSalaryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserCreditRepository userCreditRepository;
    private final UserRepository userRepository;
    private final UserSalaryRepository userSalaryRepository;

    @GetMapping("/get-credits-by-order-id/{order-id}")
    public ResponseEntity<List<UserCredit>> userCredits(@PathVariable(name = "order-id") Long orderId) {
        List<UserCredit> userCredits = userCreditRepository.findAllByOrderId(orderId);
        return ResponseEntity.ok(userCredits);
    }

    @GetMapping("/get-salaries-by-order-id/{order-id}")
    public ResponseEntity<List<UserSalary>> userSalaries(@PathVariable(name = "order-id") Long orderId) {
        List<UserSalary> userSalaries = userSalaryRepository.findAllByOrderId(orderId);
        return ResponseEntity.ok(userSalaries);
    }

    @GetMapping("/get-users-by-pnfl/{pnfl}")
    public ResponseEntity<User> getUserByPnfl(@PathVariable(name = "pnfl") String pnfl) {
        User user = userRepository.findByPnfl(pnfl).orElseThrow(()-> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }


    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
