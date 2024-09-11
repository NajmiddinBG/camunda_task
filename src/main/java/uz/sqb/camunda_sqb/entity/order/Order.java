package uz.sqb.camunda_sqb.entity.order;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", unique = true)
    private Long orderId;

    private Long orderAmount;

    private Integer orderType;

    private String pnfl;

    private LocalDateTime dateTime;
}
