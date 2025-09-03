package programmer.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class TaskDto {

    private String id;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    public TaskDto() {
    }

    public TaskDto(String id, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }


}

