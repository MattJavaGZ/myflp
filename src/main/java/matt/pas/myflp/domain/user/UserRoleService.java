package matt.pas.myflp.domain.user;

import matt.pas.myflp.infrastructure.config.CustomSecurityService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole getRegularUserRole() {
        return userRoleRepository.findByName(CustomSecurityService.USER_ROLE).orElseThrow();
    }

    public UserRole getAdminUserRole() {
        return userRoleRepository.findByName(CustomSecurityService.ADMIN_ROLE).orElseThrow();
    }
}
