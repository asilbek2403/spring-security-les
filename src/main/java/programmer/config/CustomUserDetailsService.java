package programmer.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import programmer.entity.ProfileEntity;
import programmer.repository.ProfileRepository;

import java.util.Optional;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(username);
        if(optional.isEmpty()){
            throw new UsernameNotFoundException(username);
        }

        ProfileEntity profile = optional.get();

        CustomUserDetails user = new CustomUserDetails(profile);
        return user;
    }


}
