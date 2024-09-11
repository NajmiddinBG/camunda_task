package uz.sqb.camunda_sqb.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.entity.order.Order;
import uz.sqb.camunda_sqb.entity.user.User;
import uz.sqb.camunda_sqb.repository.OrderRepository;
import uz.sqb.camunda_sqb.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service("insertClientData")
@RequiredArgsConstructor
public class InsertClientData implements JavaDelegate {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        try {
            Long orderId = (Long) execution.getVariable("order_id");
            Integer orderType = (Integer) execution.getVariable("order_type");;
            Long orderAmount = (Long) execution.getVariable("order_amount");
            String phone = (String) execution.getVariable("phone");
            String pnfl = (String) execution.getVariable("pnfl");
            String birthdateT = (String) execution.getVariable("birthdate");
            LocalDate birthdate = parseFromStringToLocalDate(birthdateT);
            String firstname = (String) execution.getVariable("firstname");
            String lastname = (String) execution.getVariable("lastname");
            String middleName = (String) execution.getVariable("middle_name");
            String passportNumber = (String) execution.getVariable("passport_number");
            String passportSeries = (String) execution.getVariable("passport_series");
            Integer sex = (Integer) execution.getVariable("sex");

            if (orderRepository.findByOrderId(orderId).isPresent()) {
                throw new RuntimeException("order id already exists");
            }

            Optional<User> userOptional = userRepository.findByPnfl(pnfl);
            User userData;
            if (userOptional.isEmpty()) {
                User user = User.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .middleName(middleName)
                        .phone(phone)
                        .pnfl(pnfl)
                        .passportNumber(passportNumber)
                        .passportSeries(passportSeries)
                        .birthdate(birthdate)
                        .sex(sex)
                        .build();

                userData = userRepository.save(user);
            } else userData = userOptional.get();

            Order order = Order.builder()
                    .orderId(orderId)
                    .orderAmount(orderAmount)
                    .orderType(orderType)
                    .pnfl(pnfl)
                    .dateTime(LocalDateTime.now())
                    .build();
            Order savedOrder = orderRepository.save(order);

            execution.setVariable("order_id", savedOrder.getOrderId());
            execution.setVariable("user_pnfl", userData.getPnfl());
            execution.setVariable("age_score", 0);
            execution.setVariable("credit_duration_score", 0);
            execution.setVariable("credit_count_score", 0);
            execution.setVariable("salary_score", 0);
            execution.setVariable("sex_score", 0);
            execution.setVariable("total_score", 0);


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public LocalDate parseFromStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
