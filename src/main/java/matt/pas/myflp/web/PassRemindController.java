package matt.pas.myflp.web;

import matt.pas.myflp.domain.user.UserManagementService;
import matt.pas.myflp.domain.user.UserService;
import matt.pas.myflp.infrastructure.email.EmailService;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PassRemindController {

    private final UserService userService;
    private final UserManagementService userManagementService;
    private final EmailService emailService;

    public PassRemindController(UserService userService, UserManagementService userManagementService, EmailService emailService) {
        this.userService = userService;
        this.userManagementService = userManagementService;
        this.emailService = emailService;
    }

    @GetMapping("/przypomnienie-hasla")
    String passRemindForm() {
        return "password-remind-form";
    }

    @PostMapping("/przypomnienie-hasla")
    String passRemind(@RequestParam String email, RedirectAttributes redirectAttributes) {
        userService.findUserByEmail(email).ifPresentOrElse(
                user -> {
                    try {
                        emailService.sendEmailWithPasswordReset(user);
                        redirectAttributes.addFlashAttribute(
                                RegisterController.NOTIFICATION_ATTRIBUTE,
                                "Na adres email %s została wysłana wiadomość służąca do zresetowania hasła".formatted(email)
                        );
                    } catch (EmailException e) {
                        System.err.println("Błąd podczas wysyłania wiadomości email. Spróbuj ponownie");
                    }
                },
                () -> redirectAttributes.addFlashAttribute(
                        RegisterController.NOTIFICATION_ATTRIBUTE,
                        "Błędny adres email. Spróuj ponownie"
                )
        );
        return "redirect:/przypomnienie-hasla";
    }

    @GetMapping("/ustaw-nowe-haslo/{id}")
    String setPassForm(Model model, @PathVariable Long id, @RequestParam String activKey) {
        model.addAttribute("id", id);
        model.addAttribute("activKey", activKey);
        return "password-remind-set-form";
    }

    @PostMapping("/ustaw-nowe-haslo/{id}")
    String setNewPass(@PathVariable long id, @RequestParam String activKey, @RequestParam String password,
                      RedirectAttributes redirectAttributes){

        final boolean isSetNewPass = userManagementService.setNewPass(id, activKey, password);

        if (isSetNewPass){
            redirectAttributes.addFlashAttribute(
                    RegisterController.NOTIFICATION_ATTRIBUTE,
                    "Hasło zostało poprawnie zmienione. Zaloguj się"
            );
            return "redirect:/login";
        }
        else {
            redirectAttributes.addFlashAttribute(
                    RegisterController.NOTIFICATION_ATTRIBUTE,
                    "Błąd podczas zmiany hasła. " +
                            "Użyj linku służącego do przypomnienia hasła bez zmiany jego zawartosci albo spróbuj ponownie"
            );
            return "redirect:/przypomnienie-hasla";
        }

    }
}
