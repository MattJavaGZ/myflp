package matt.pas.myflp.web;

import jakarta.validation.Valid;
import matt.pas.myflp.domain.user.UserManagementService;
import matt.pas.myflp.domain.user.UserService;
import matt.pas.myflp.domain.user.dto.UserRegisterDto;
import matt.pas.myflp.domain.workStation.WorkStationService;
import matt.pas.myflp.domain.workStation.dto.WorkStationDto;
import matt.pas.myflp.infrastructure.email.EmailService;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegisterController {

    public final static String NOTIFICATION_ATTRIBUTE = "notification";
    private final UserService userService;
    private final UserManagementService userManagementService;
    private final WorkStationService workStationService;
    private final EmailService emailService;

    public RegisterController(UserService userService, UserManagementService userManagementService, WorkStationService workStationService, EmailService emailService) {
        this.userService = userService;
        this.userManagementService = userManagementService;
        this.workStationService = workStationService;
        this.emailService = emailService;
    }

    @GetMapping("/rejestracja")
    String registerForm(Model model) {
        final UserRegisterDto user = new UserRegisterDto();
        final List<WorkStationDto> workStations = getAllWorkStations();
        model.addAttribute("user", user);
        model.addAttribute("workStations", workStations);
        return "register-form";
    }

    @PostMapping("/rejestracja")
    String register(Model model, @Valid @ModelAttribute("user") UserRegisterDto user, BindingResult bindingResult,
                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            final List<WorkStationDto> workStations = getAllWorkStations();
            model.addAttribute("workStations", workStations);
            return "register-form";
        } else {
            userManagementService.registerUser(user);
            try {
                emailService.sendEmailsAboutNewRegistration();

            } catch (EmailException e) {
                System.err.println("Błąd wysylki wiadomości email o nowej rejestracji");
            }
            redirectAttributes.addFlashAttribute(NOTIFICATION_ATTRIBUTE,
                    "Rejestracja przebiegła pomyślnie. Konto będzie aktywne po akceptacji administratora");
            return "redirect:/login";
        }
    }

    private List<WorkStationDto> getAllWorkStations() {
        return workStationService.getAllWorkStations();
    }
}
