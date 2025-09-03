package programmer.util;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import programmer.config.CustomUserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SpringSecurityUtil {

    public static String getCurrentProfileId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();//customuserdetail obyekti
        return userDetails.getId();

    }

    public static Collection<GrantedAuthority> getCurrentRoles(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Collection<GrantedAuthority> roled = (Collection<GrantedAuthority>)userDetails.getAuthorities();

        return roled;///rollar
    }

    public static List<String> getCarentRoles(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Collection< ? extends GrantedAuthority> roled = userDetails.getAuthorities();
        List<String> roleList = new ArrayList<String>();
        for(GrantedAuthority role : roled){
            roleList.add(role.getAuthority());
        }

        return roleList;///rollar
    }



}
