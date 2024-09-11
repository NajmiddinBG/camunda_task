package uz.sqb.camunda_sqb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditHistoryResponseDTO implements Serializable {

    private String bank;
    private LocalDate date;
    @JsonProperty("amount_all")
    private String amountAll;
    @JsonProperty("amount_remain")
    private String amountRemain;
    @JsonProperty("amount_expired")
    private String amountExpired;
    @JsonProperty("monthly_payment")
    private Long monthlyPayment;
}
