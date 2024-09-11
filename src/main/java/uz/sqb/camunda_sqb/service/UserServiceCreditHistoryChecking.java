package uz.sqb.camunda_sqb.service;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.dto.CreditHistoryResponseDTO;
import uz.sqb.camunda_sqb.entity.order.Order;
import uz.sqb.camunda_sqb.entity.user.UserCredit;
import uz.sqb.camunda_sqb.repository.OrderRepository;
import uz.sqb.camunda_sqb.repository.ResultRepository;
import uz.sqb.camunda_sqb.repository.UserCreditRepository;
import uz.sqb.camunda_sqb.service.utils.CreditService;

import java.util.List;


@Service("userServiceCreditHistoryChecking")
@RequiredArgsConstructor
public class UserServiceCreditHistoryChecking implements JavaDelegate {

    private final CreditService creditService;
    private final ResultRepository resultRepository;
    private final OrderRepository orderRepository;
    private final UserCreditRepository userCreditRepository;

    @Override
    public void execute(DelegateExecution execution) {

        Long orderId = (Long) execution.getVariable("order_id");

        Order orderData = orderRepository.findByOrderId(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        List<CreditHistoryResponseDTO> creditDetails = creditService.generateRandomCreditHistory(orderData.getPnfl());
        for (CreditHistoryResponseDTO creditDetail : creditDetails) {
            UserCredit userCredit = new UserCredit(null, orderId, creditDetail.getBank(), creditDetail.getDate(), creditDetail.getAmountAll(), creditDetail.getAmountRemain(), creditDetail.getAmountExpired(), creditDetail.getMonthlyPayment());
            UserCredit savedUserCredit = userCreditRepository.save(userCredit);
        }
        int creditCount = creditDetails.size();
        execution.setVariable("credit_count", creditCount);
    }


}
