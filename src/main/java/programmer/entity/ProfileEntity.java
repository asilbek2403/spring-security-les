package programmer.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import programmer.enumL.GeneralStatus;
import programmer.enumL.RoleEnum;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {


    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private String name;
    private String surname;
    private String phone;
    private String password;
//    private String email;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status = GeneralStatus.ACTIVE;

   @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

   @Column(name = "visible")
   private Boolean visible=Boolean.TRUE;
   @Column(name = "create_date")
   private LocalDateTime createdDate = LocalDateTime.now();

}
