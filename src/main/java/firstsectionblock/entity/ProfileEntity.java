package firstsectionblock.entity;


import firstsectionblock.Enumlist.ProfileStatusEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private ProfileStatusEnum profileStatus=ProfileStatusEnum.ACTIVE;
    @Column
    private String photo_id;
    @Column
    private Boolean visible=true;
    @CreationTimestamp
    @Setter(AccessLevel.NONE)//bu now() uchun mi?
    private LocalDateTime createdDate;


}
