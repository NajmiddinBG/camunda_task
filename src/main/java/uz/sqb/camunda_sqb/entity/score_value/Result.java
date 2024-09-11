package uz.sqb.camunda_sqb.entity.score_value;

import lombok.*;
import uz.sqb.camunda_sqb.enums.CreditType;
import uz.sqb.camunda_sqb.enums.ResultStatus;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ResultStatus status;

    @Column(name = "credit_type")
    @Enumerated(EnumType.STRING)
    private CreditType creditType;
}
