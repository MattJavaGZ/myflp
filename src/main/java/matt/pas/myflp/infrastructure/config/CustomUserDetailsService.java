package matt.pas.myflp.infrastructure.config;

import matt.pas.myflp.domain.user.UserService;
import matt.pas.myflp.domain.user.dto.UserCreditialsDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findActivUserCreditialByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User o adresie email %s nie istnieje", username)));

    }

    private UserDetails createUserDetails(UserCreditialsDto userCreditialsDto){
        return User.builder()
                .username(userCreditialsDto.getEmail())
                .password(userCreditialsDto.getPassword())
                .roles(userCreditialsDto.getRoles().toArray(String[]::new))
                .build();
    }
}
