package uz.sqb.camunda_sqb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRequestDTO implements Serializable {

    @JsonProperty("order_id")
    private Integer orderId;
    @JsonProperty("order_type")
    private Integer orderType;
    @JsonProperty("order_amount")
    private Long orderAmount;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("pnfl")
    private String pnfl;
    @JsonProperty("birthdate")
    private LocalDate birthdate;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("passport_number")
    private String passportNumber;
    @JsonProperty("passport_series")
    private String passportSeries;
    @JsonProperty("sex")
    private Integer sex;

}
