package matt.pas.myflp.web;

import matt.pas.myflp.domain.user.UserManagementService;
import matt.pas.myflp.domain.user.UserService;
import matt.pas.myflp.domain.user.dto.UserDto;
import matt.pas.myflp.domain.workStation.WorkStationService;
import matt.pas.myflp.domain.workStation.dto.WorkStationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("uzytkownik")
public class UserController {

    private final UserService userService;
    private final UserManagementService userManagementService;
    private final WorkStationService workStationService;

    public UserController(UserService userService, UserManagementService userManagementService, WorkStationService workStationService) {
        this.userService = userService;
        this.userManagementService = userManagementService;
        this.workStationService = workStationService;
    }

    @GetMapping
    String getUserData(Model model){
        final UserDto user = userService.getCurrentUserDto();
        final List<WorkStationDto> workStations = workStationService.getAllWorkStations();
        model.addAttribute("workStations", workStations);
        model.addAttribute("user", user);
        return "user-panel";
    }

    @PostMapping("/zmiana-stanowiska")
    String setUserWorkStation(@RequestParam String workStation){
        userManagementService.editUserWorkStation(workStation);
        return "redirect:/uzytkownik";
    }
}
