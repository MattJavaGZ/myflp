package matt.pas.myflp.infrastructure.email;

import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.domain.user.UserManagementService;
import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final EmailSender emailSender;
    private final EmailTemplate emailTemplate;

    private final UserManagementService userManagementService;

    public EmailService(EmailSender emailSender, EmailTemplate emailTemplate, UserManagementService userManagementService) {
        this.emailSender = emailSender;
        this.emailTemplate = emailTemplate;
        this.userManagementService = userManagementService;
    }

    public void sendEmailsAboutNewRegistration() throws EmailException {
        final List<String> adminEmails = userManagementService.getAdminEmails();
        final String tittle = emailTemplate.generateTittleForNewRegisterEmail();
        final String text = emailTemplate.generateTextForNewRegisterEmail();

        for (String email : adminEmails) {
            emailSender.sendEmail(email, tittle, text);
        }
    }

    public void sendEmailAfterActivationAccount(String email) throws EmailException {
        final String tittle = emailTemplate.generateTittleForAccountActivationEmail();
        final String text = emailTemplate.generateTextForAccountActivationEmail();

        emailSender.sendEmail(email, tittle, text);
    }

    public void sendEmailWithPasswordReset(User user) throws EmailException {
        final String tittle = emailTemplate.generateTittleForPasswordResetEmail();
        final String text = emailTemplate.generateTextForPasswordResetEmail(user);

        emailSender.sendEmail(user.getEmail(), tittle, text);
    }
}
