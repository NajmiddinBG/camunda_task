package uz.sqb.camunda_sqb.service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.dto.CreditHistoryResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreditService {

    public List<CreditHistoryResponseDTO> generateRandomCreditHistory(String pnfl) {
        Random random = new Random();
        List<String> banks = List.of("Bank A", "Bank B", "Bank C", "Bank D", "Bank E");
        List<CreditHistoryResponseDTO> creditHistoryList = new ArrayList<>();

        // Randomly choose the number of elements (0 to 4)
        int numberOfElements = random.nextInt(5);

        for (int i = 0; i < numberOfElements; i++) {
            String randomBank = banks.get(random.nextInt(banks.size()));

            // Random date between 2020 and 2024
            int year = 2020 + random.nextInt(5);
            int dayOfYear = 1 + random.nextInt(365); // dayOfYear from 1 to 365
            LocalDate randomDate = LocalDate.ofYearDay(year, dayOfYear);

            // Random amounts and monthly payments
            String randomAmountAll = String.valueOf(10_000 + random.nextInt(90_001)); // Amount between 10,000 and 100,000
            String randomAmountRemain = String.valueOf(1_000 + random.nextInt(9_001)); // Amount between 1,000 and 10,000
            String randomAmountExpired = String.valueOf(random.nextInt(5_001)); // Amount between 0 and 5,000
            Long randomMonthlyPayment = (long) (500 + random.nextInt(4_501)); // Monthly payment between 500 and 5,000

            // Create CreditHistoryResponseDTO and add it to the list
            creditHistoryList.add(new CreditHistoryResponseDTO(
                    randomBank, randomDate, randomAmountAll, randomAmountRemain, randomAmountExpired, randomMonthlyPayment
            ));
        }

        return creditHistoryList;
    }

}
