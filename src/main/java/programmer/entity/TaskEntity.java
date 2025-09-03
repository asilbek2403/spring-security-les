package programmer.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskEntity {


    @Id
    private String id;

    @Column(name = "title" , columnDefinition = "text")
    private String title;
    @Column(name = "content" , columnDefinition = "text")
    private String content;

   @Column(name = "create_date")
   private LocalDateTime createdDate = LocalDateTime.now();

   //Egasi taskning *********

    @Column(name = "profile_id")
    private String profileId;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id" , insertable = false , updatable = false) // kop tasklar bitta userda
    private ProfileEntity profile;






}
