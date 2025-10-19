package matt.pas.myflp.web;

import matt.pas.myflp.domain.user.UserManagementService;
import matt.pas.myflp.domain.user.UserService;
import matt.pas.myflp.domain.user.dto.UserDto;
import matt.pas.myflp.infrastructure.email.EmailService;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/uzytkownicy")
public class UserAdminController {

    private final UserService userService;
    private final UserManagementService userManagementService;
    private final EmailService emailService;

    public UserAdminController(UserService userService, UserManagementService userManagementService, EmailService emailService) {
        this.userService = userService;
        this.userManagementService = userManagementService;
        this.emailService = emailService;
    }

    @GetMapping("")
    public String userPanel(Model model) {
        final List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-user-panel";
    }

    @GetMapping("aktywuj/{id}")
    public String activateUser(@PathVariable Long id) {
        final String userEmail = userManagementService.activateUserAccount(id);
        try {
            emailService.sendEmailAfterActivationAccount(userEmail);
        } catch (EmailException e) {
            System.err.println("Błąd wysyłki wiadomości informującej o aktywacji konta na adres " + userEmail);
        }
        return "redirect:/uzytkownicy";
    }

    @GetMapping("dezaktywuj/{id}")
    public String deactivateUser(@PathVariable Long id) {
        userManagementService.deactivateUserAccount(id);
        return "redirect:/uzytkownicy";
    }


}
