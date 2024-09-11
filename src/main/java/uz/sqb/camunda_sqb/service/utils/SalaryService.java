package uz.sqb.camunda_sqb.service.utils;

import org.springframework.stereotype.Service;
import uz.sqb.camunda_sqb.dto.SalaryResponseDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SalaryService {

    private final Random random = new Random();

    // Kompaniya nomlari ro'yxati
    private final List<String> companyNames = List.of("TechCorp", "SoftSolutions", "GlobalTech", "InnovateLLC", "FinServ", "BizCom");

    public List<SalaryResponseDTO> getSalaryDetails(String pnfl) {
        // 1 dan 3 gacha bo'lgan random son
        int count = random.nextInt(3) + 1;

        List<SalaryResponseDTO> salaryList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            salaryList.add(generateSalaryResponse());
        }
        return salaryList;
    }

    private SalaryResponseDTO generateSalaryResponse() {
        SalaryResponseDTO salaryResponse = new SalaryResponseDTO();

        // 1 000 000 dan 30 000 000 gacha random, 10 000 ga qoldiqsiz bo'linadigan son
        salaryResponse.setAmount((long) (random.nextInt((30_000_000 - 1_000_000) / 10_000 + 1) * 10_000 + 1_000_000));

        // Random kompaniya nomi
        salaryResponse.setCompanyName(companyNames.get(random.nextInt(companyNames.size())));

        // 8 xonali random INN
        salaryResponse.setInn(generateRandomINN());

        // 2020 va hozirgi sana orasida random sana
        salaryResponse.setDate(generateRandomDate());

        return salaryResponse;
    }

    private String generateRandomINN() {
        // 8 xonali random raqamli string yaratish
        StringBuilder inn = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            inn.append(random.nextInt(10));
        }
        return inn.toString();
    }

    private LocalDate generateRandomDate() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        long daysBetween = LocalDate.now().toEpochDay() - startDate.toEpochDay();

        // Generate a random number of days to add
        long randomDays = random.nextInt((int) (daysBetween + 1));
        // Ensure the randomDays is positive or zero

        return startDate.plusDays(randomDays);
    }

}
