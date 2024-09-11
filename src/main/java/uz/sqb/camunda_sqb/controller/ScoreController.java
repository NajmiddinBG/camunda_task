package uz.sqb.camunda_sqb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sqb.camunda_sqb.entity.score.*;
import uz.sqb.camunda_sqb.repository.score.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/score")
@RequiredArgsConstructor
public class ScoreController {

    private final AgeScoreSettingRepository ageScoreSettingRepository;
    private final CreditDurationSettingScoreRepository creditDurationSettingScoreRepository;
    private final CreditNumberScoreSettingRepository creditNumberScoreSettingRepository;
    private final SalaryScoreSettingRepository salaryScoreSettingRepository;
    private final SexScoreSettingRepository sexScoreSettingRepository;

    @GetMapping("/get-age-score")
    public ResponseEntity<List<AgeScoreSetting>> getAgeScore() {
        return ResponseEntity.ok(ageScoreSettingRepository.findAll());
    }

    @GetMapping("/get-credit-duration-score")
    public ResponseEntity<List<CreditDurationScoreSetting>> getCreditDurationScore() {
        return ResponseEntity.ok(creditDurationSettingScoreRepository.findAll());
    }

    @GetMapping("/get-credit-number-score")
    public ResponseEntity<List<CreditNumberScoreSetting>> getCreditNumberScore() {
        return ResponseEntity.ok(creditNumberScoreSettingRepository.findAll());
    }

    @GetMapping("/get-salary-score")
    public ResponseEntity<List<SalaryScoreSetting>> getSalaryScore() {
        return ResponseEntity.ok(salaryScoreSettingRepository.findAll());
    }

    @GetMapping("/get-sex-score")
    public ResponseEntity<List<SexScoreSetting>> getSexScore() {
        return ResponseEntity.ok(sexScoreSettingRepository.findAll());
    }


}
