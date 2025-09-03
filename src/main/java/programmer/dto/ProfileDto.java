package programmer.dto;


import lombok.Getter;
import lombok.Setter;
import programmer.enumL.RoleEnum;

@Getter
@Setter
public class ProfileDto {

    private String id;
    private String name;
    private String surname;
    private String phone;
    private String password ;
    private RoleEnum profileRole;

    private String jwt;
    private String refreshToken;
    private String accessToken;
}
