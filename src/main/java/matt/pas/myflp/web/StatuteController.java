package matt.pas.myflp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatuteController {

    @GetMapping("/polityka-prywatnosci")
    String privacyPolicy(){
        return "privacy-policy";
    }

    @GetMapping("/regulamin")
    String statute() {
        return "statute";
    }
}
