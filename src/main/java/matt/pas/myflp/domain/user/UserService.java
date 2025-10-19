package matt.pas.myflp.domain.user;

import matt.pas.myflp.domain.user.dto.UserCreditialsDto;
import matt.pas.myflp.domain.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(userEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public UserDto getCurrentUserDto() {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(userEmail).map(UserMapper::mapUserToUserDto).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Optional<UserCreditialsDto> findActivUserCreditialByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(User::isActiv)
                .map(UserMapper::mapUserToUserCreditialsDto);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapUserToUserDto)
                .sorted()
                .toList();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isEmailExist(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }


}
