package programmer.service;


import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import programmer.config.CustomUserDetails;
import programmer.dto.JwtDTO;
import programmer.dto.ProfileAuth;
import programmer.dto.ProfileDto;
import programmer.dto.TokenDto;
import programmer.entity.ProfileEntity;

import programmer.enumL.GeneralStatus;
import programmer.exp.AppBadRequestException;
import programmer.repository.ProfileRepository;
import programmer.util.JwtUtil;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private BCryptPasswordEncoder  bCryptPasswordEncoder ;


//jwtda springga
    @Autowired
    private AuthenticationManager authenticationManager;


    public ProfileDto regist(ProfileDto profileDto) {

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(profileDto.getPhone());
        if (optional.isPresent()) {
            return null;
        }








        ProfileEntity profileEntity = new ProfileEntity();
        profileEntity.setName(profileDto.getName());
        profileEntity.setSurname(profileDto.getSurname());
        profileEntity.setPhone(profileDto.getPhone()); // phone Yagona unique holda boladi.
//        profileEntity.setPassword(MD5Util.getMd5(profileDto.getPassword()));
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

        profileEntity.setPassword(bc.encode(profileDto.getPassword()));
        profileEntity.setRoles(profileDto.getProfileRole());

        profileEntity.setId(UUID.randomUUID().toString());
        profileRepository.save(profileEntity);

        profileDto.setId(profileEntity.getId());


        return profileDto;
    }



    public ProfileDto authorizationm(ProfileAuth dto){


       try {
           Authentication authentication = authenticationManager
                   .authenticate(new UsernamePasswordAuthenticationToken(dto.getPhone(), dto.getPassword()));// bu hamma tekshirishni qilaoladi

           if (authentication.isAuthenticated()) {
               CustomUserDetails profileEntity = (CustomUserDetails) authentication.getPrincipal();
               ProfileDto profileDto = new ProfileDto();
               profileDto.setName(profileEntity.getName());
               profileDto.setSurname(profileEntity.getSurname());
               profileDto.setPhone(profileEntity.getUsername());
               profileDto.setProfileRole(profileEntity.getRole());

               profileDto.setAccessToken(JwtUtil.encode(profileEntity.getUsername(), profileEntity.getRole().name()));
               profileDto.setRefreshToken(JwtUtil.generateRefreshT(profileEntity.getUsername(), profileEntity.getRole().name()));
               return profileDto;

           }
           throw new AppBadRequestException("Phone or password is incorrect");

       }catch (BadCredentialsException e){
                throw new AppBadRequestException("Phone or password is incorrect");
       }

//       Optional<ProfileEntity> entity = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
//        if(entity.isEmpty()){
//            throw new AppBadRequestException("Phone not found"); // ExceptionHandler ga tashadik
//        }
//        ProfileEntity profileEntity = entity.get();
////        if (!bCryptPasswordEncoder.matches(profileEntity.getPassword()dto.getPassword())) {
//        if (!bCryptPasswordEncoder.matches(dto.getPassword(), profileEntity.getPassword())) {
//        throw new AppBadRequestException("Wrong phone v password");
//        }
//        if(!profileEntity.getStatus().equals(GeneralStatus.ACTIVE)){
//            throw new AppBadRequestException("Phone or password error");
//        } // shularni authent qilaoladi}

    }




    public TokenDto getNewAccessToken(TokenDto dto){
        try{
            if(JwtUtil.isValid(dto.getRefreshToken())){
                JwtDTO jwtDTO = JwtUtil.decode(dto.getRefreshToken());

                Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(jwtDTO.getUsername());

                if(optional.isPresent()){
                    ProfileEntity profileEntity = optional.get();

                    if(profileEntity.getStatus().equals(GeneralStatus.NOT_ACTIVE)){
                        throw new AppBadRequestException("Phone or password is incorrect Invalid token");
                    }
                    TokenDto response = new TokenDto();
                    response.setAccessToken(JwtUtil.encode(profileEntity.getPhone(), profileEntity.getRoles().name()));
                    response.setRefreshToken(JwtUtil.generateRefreshT(profileEntity.getPhone(), profileEntity.getRoles().name()));
                    return response;

                }
            }
        } catch (JwtException e){

        }
        throw new AppBadRequestException("Phone or password is incorrect Invalid token");


    }


}
