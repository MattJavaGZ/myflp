package matt.pas.myflp.domain.user;

import jakarta.transaction.Transactional;
import matt.pas.myflp.domain.user.dto.UserRegisterDto;
import matt.pas.myflp.domain.workStation.WorkStation;
import matt.pas.myflp.domain.workStation.WorkStationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserManagementService {

    private final UserRepository userRepository;
    private final WorkStationService workStationService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserManagementService(UserRepository userRepository, WorkStationService workStationService, UserRoleService userRoleService, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.workStationService = workStationService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    public void registerUser(UserRegisterDto userRegister) {
        final User userToSave = new User();
        userToSave.setFirstName(userRegister.getFirstName());
        userToSave.setLastName(userRegister.getLastName());
        userToSave.setEmail(userRegister.getEmail());
        final String encodePassword = passwordEncoder.encode(userRegister.getPassword());
        userToSave.setPassword(encodePassword);
        userToSave.setActivKey(generateActivKey());
        final WorkStation workStation = workStationService.getWorkStationByName(userRegister.getWorkStation()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userToSave.setWorkStation(workStation);
        userToSave.setActiv(false);
        userToSave.setDateAdded(LocalDateTime.now());
        final UserRole userRole = userRoleService.getRegularUserRole();
        userToSave.setRoles(List.of(userRole));
        userRepository.save(userToSave);
    }

    private String generateActivKey() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public String activateUserAccount(long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setActiv(true);
        return user.getEmail();
    }

    @Transactional
    public void deactivateUserAccount(long id) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setActiv(false);
    }

    @Transactional
    public void editUserWorkStation(String workStationName) {
        final User user = userService.getCurrentUser();
        final WorkStation workStation = workStationService.getWorkStationByName(workStationName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setWorkStation(workStation);
    }

    public List<String> getAdminEmails() {
        final UserRole adminUserRole = userRoleService.getAdminUserRole();

        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(adminUserRole))
                .map(User::getEmail)
                .toList();
    }

    @Transactional
    public boolean setNewPass(long id, String activKey, String password) {
        final User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (user.getActivKey().equals(activKey)) {
            final String encodePass = passwordEncoder.encode(password);
            user.setPassword(encodePass);
            return true;
        }
        return false;
    }
}
