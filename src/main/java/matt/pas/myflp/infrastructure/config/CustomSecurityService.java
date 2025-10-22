package matt.pas.myflp.infrastructure.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
public class CustomSecurityService {

    public final static String USER_ROLE = "USER";
    public final static String ADMIN_ROLE = "ADMIN";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, Environment env) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/uzytkownicy/**").hasRole(ADMIN_ROLE)
                .requestMatchers("/produkty/dodaj/**").hasRole(ADMIN_ROLE)
                .requestMatchers("/rejestracja").permitAll()
                .requestMatchers("/przypomnienie-hasla").permitAll()
                .requestMatchers("/ustaw-nowe-haslo/**").permitAll()
                .requestMatchers("/polityka-prywatnosci").permitAll()
                .requestMatchers("/regulamin").permitAll()
                .anyRequest().authenticated()
        );

        http.formLogin(login -> login.loginPage("/login").permitAll());
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**", HttpMethod.GET.name()))
                .logoutSuccessUrl("/")
        );

        if (Arrays.asList(env.getActiveProfiles()).contains("dev")) {
            http.csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()));
            http.headers().frameOptions().sameOrigin();
        }

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/img/**",
                "/scripts/**",
                "/styles/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
