package firstsectionblock.Dto.profileD;

import firstsectionblock.Enumlist.ProfileRoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDto {
    private String name;
    private String surname;
    private String phone;
    private ProfileRoleEnum roles;
    private LocalDate createdDateFrom;
    private LocalDate createdDateTo;

}

