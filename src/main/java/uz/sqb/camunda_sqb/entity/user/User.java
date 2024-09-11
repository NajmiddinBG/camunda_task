package uz.sqb.camunda_sqb.entity.user;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstname;

    private String lastname;

    private String middleName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "pnfl", unique = true)
    private String pnfl;

    private Integer sex;

    private String passportNumber;

    private String passportSeries;

    private LocalDate birthdate;

}
