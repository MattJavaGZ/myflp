package matt.pas.myflp.infrastructure.email;

import matt.pas.myflp.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplate {

    @Value("${spring.app.email.url}")
    private String url;

    String generateTittleForNewRegisterEmail() {
        return "Nowa rejestracja aloesowej agentki";
    }

    String generateTextForNewRegisterEmail() {
        return """
                Witaj,
                
                odnotowaliśmy rejestrację nowego agenta. Sprawdź:
                
                %s
                
                Pozdrawiamy,
                """.formatted(url + "/uzytkownicy");
    }

    String generateTittleForAccountActivationEmail() {
        return "Twoje konto jest już aktywne";
    }

    String generateTextForAccountActivationEmail() {
        return """
                Witaj,
                
                Twoje konto zostało już aktywowane. Zaloguj się i korzystaj z portalu.
                
                %s
                
                Pozdrawiamy,
                """.formatted(url);
    }

    String generateTittleForPasswordResetEmail() {
        return "Zresetuj swoje hasło";
    }

    String generateTextForPasswordResetEmail(User user) {
        return """
                Witaj,
                
                otrzymaliśmy prośbę o zresetowanie hasła.
                Link do przypomnienia hasła:
                %s
                
                Zignoruj wiadomość jeżeli nie chcesz zresetować hasła.
                
                Pozdrawiamy,
                """.formatted(generateUrlToPassReset(user));
    }

    private String generateUrlToPassReset(User user) {
        return String.format("%s/ustaw-nowe-haslo/%d?activKey=%s", url, user.getId(), user.getActivKey());
    }
}
