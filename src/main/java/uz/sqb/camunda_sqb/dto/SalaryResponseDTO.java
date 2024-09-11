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
public class SalaryResponseDTO implements Serializable {

    private Long amount;
    @JsonProperty("company_name")
    private String companyName;
    private String inn;
    private LocalDate date;

}
