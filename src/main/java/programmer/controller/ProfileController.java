package programmer.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programmer.dto.ProfileAuth;
import programmer.dto.ProfileDto;
import programmer.dto.TokenDto;
import programmer.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/registr")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto profileDto) {
        ProfileDto res = profileService.regist(profileDto);
        return ResponseEntity.ok(res);
    }
    @PostMapping("/authorization")
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileAuth profileDto) {
        ProfileDto res = profileService.authorizationm(profileDto);
        return ResponseEntity.ok(res);
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<TokenDto> refreshToken(@RequestBody TokenDto profileDto) {
        TokenDto res = profileService.getNewAccessToken(profileDto);
        return ResponseEntity.ok(res);
    }







}
