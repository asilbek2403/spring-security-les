package programmer.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import programmer.util.MD5Util;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SpringConfig {


//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;//asosiysi Otasini ishlatamiz  CHunkiotasining ichida bor
    @Autowired
    private UserDetailsService userDetailsService;//


//    @Autowired
//    private AuthenticationManager authenticationManager;


    public static final String [] AUTH_WHITELIST = {
            "/v2/api-docs","/task",
            "profile/**","profile/authorization","profile/refreshToken",
            "/task/jwt-create","/task/parse-jwt/**"



    };



    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication - Foydalanuvchining identifikatsiya qilish.
        // Ya'ni berilgan login va parolli user bor yoki yo'qligini aniqlash.
//
//        String password = "1132";//UUID.randomUUID().toString();
//        System.out.println("USing generated security password VVVVV >>>>: "+ password);
//
//        UserDetails user = User.builder()
//                .username("useraka")
//                .password("{noop}" + password) //{bcrypt} shtrixlangan kod
//                .roles("USER")
//                .build();// install ni securitydan qildik   BIZ securityga userni berib yuboramiz

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService( new InMemoryUserDetailsManager(user) );//InMemory da user lar turadi security undan olib ishlatadi
        authenticationProvider.setUserDetailsService( userDetailsService );//endi bunga murojaat qilsak Tepada MAIN user kerakmas
//        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());//parolni shifrlashini sevurityga aytib qoyamiz
//        authenticationProvider.setPasswordEncoder(passwordEncoder());//parolni shifrlashini sevurityga aytib qoyamiz
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());//parolni shifrlashini sevurityga aytib qoyamiz

        return authenticationProvider;

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        // authorization - Foydalanuvchining tizimdagi huquqlarini tekshirish.
        // Ya'ni foydalanuvchi murojat qilayotgan API-larni ishlatishga ruxsati bor yoki yo'qligini tekshirishdir.

        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->{
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers("/task").permitAll()
                    .requestMatchers(AUTH_WHITELIST).permitAll()
//                    .requestMatchers(  "profile/**").permitAll()
//                    .requestMatchers(  "profile/authorization").permitAll()
//                    .requestMatchers("/task/jwt-create").permitAll() // JWT create ochiq
//                    .requestMatchers("/task/parse-jwt/**").permitAll() // JWT create ochiq
//                    .requestMatchers(HttpMethod.GET,"/task/all/*").permitAll()
//                    .requestMatchers(HttpMethod.GET,"/task/all/*").permitAll() HttpMethod.Get ,"/task/**
//                    bunda httpni 2 ta ** ni faqat metodning o'ziga bog'laymiz FAQAT GET uchun hamma***
                    .requestMatchers(  HttpMethod.DELETE,"/task/admin-delete-s" , "/task/admin-delete-s/**").hasAnyRole("ADMIN")
//                    .requestMatchers("task/").hasRole("ADMIN")///task/admin-delete-s

                    .anyRequest()
                    .authenticated();
        }).addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class);//.formLogin(Customizer.withDefaults());//FORMlOGIN sECURITY TAQDIM ETGAN LOGGINGA OBORADI status302 edi
//endi projectda o'zimiz configuration logginni ishlatamiz BUNI yozamiz


//        http.httpBasic(Customizer.withDefaults());//defaultda shunday bo'ladi
        http.csrf(AbstractHttpConfigurer::disable);//disable bu o'chiq degani yani ishlatmaydi
//        http.cors(AbstractHttpConfigurer::disable);

        http.cors(httpSecurityCorsConfigurer -> {
           CorsConfiguration corsConfiguration = new CorsConfiguration();
           corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*"));
           corsConfiguration.setAllowedMethods(Arrays.asList("*"));
           corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

           UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
           source.registerCorsConfiguration("/**", corsConfiguration);

        httpSecurityCorsConfigurer.configurationSource(source);
        });


        return http.build();


    }

//Password bilan ishlaydi MD5 da ham bCrypt da ham

    @Bean
    public PasswordEncoder passwordEncoder() {//MD5U uchun kerak BCrYPga shartmas
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String md5 = MD5Util.getMd5(rawPassword.toString());
                return md5.equals(encodedPassword);
            }
        };
    }


    //Springga jwt bilan qilgan configuration
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();}


}
