package programmer.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import programmer.dto.JwtDTO;
import programmer.util.JwtUtil;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher matcher = new AntPathMatcher();
        return Arrays
                .stream(SpringConfig.AUTH_WHITELIST)
                .anyMatch(m->matcher.match(m , request.getContextPath()));
    }// bunga Alohida metodga GET POST ga tekshirish bilan qiyinrqo +++






    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
         String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);//continue securty filters
            return;
        }

        try {
            final String token = authHeader.substring(7).trim();//jwt token
            JwtDTO  jwtDTO = JwtUtil.decode(token);
            String username = jwtDTO.getUsername();//phone oldik
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );
            SecurityContextHolder.getContext().setAuthentication( authentication );

            filterChain.doFilter(request, response);//continue xatolik bo'lsa ham barbir secury bor qilaveramiz


        }catch (JwtException  | UsernameNotFoundException s){
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);400
            filterChain.doFilter(request, response);//continue xatolik bo'lsa ham barbir secury bor qilaveramiz

        }




    }
}
