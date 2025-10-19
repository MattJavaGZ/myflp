package matt.pas.myflp.web;

import jakarta.validation.Valid;
import matt.pas.myflp.domain.client.ClientService;
import matt.pas.myflp.domain.client.dto.CilentToSaveDto;
import matt.pas.myflp.domain.client.dto.ClientDto;
import matt.pas.myflp.domain.clientGroup.ClientGroupService;
import matt.pas.myflp.domain.clientGroup.dto.ClientGroupDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/klienci")
public class ClientController {

    private final ClientService clientService;
    private final ClientGroupService clientGroupService;

    public ClientController(ClientService clientService, ClientGroupService clientGroupService) {
        this.clientService = clientService;
        this.clientGroupService = clientGroupService;
    }

    @GetMapping()
    String getAllClientsByUser(Model model){
        final List<ClientDto> clients = clientService.findAllCientsByUser();
        model.addAttribute("clients", clients);
        return "clients-list";
    }

    @GetMapping("/{clientId}")
    String clientPage(@PathVariable Long clientId, Model model){
        final ClientDto client = clientService.findClientDtoById(clientId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("client", client);
        return "client";
    }

    @GetMapping("/szukaj")
    String clientSearch(Model model, @RequestParam String clientSearch){
        final List<ClientDto> clients = clientService.findClientsByUserWord(clientSearch);
        model.addAttribute("clients", clients);
        return "clients-list";
    }

    @GetMapping("/dodaj")
    String clientAddForm(Model model){
        final CilentToSaveDto clientToSave = new CilentToSaveDto();
        final List<ClientGroupDto> allGroups = clientGroupService.findAllClientGroups();
        model.addAttribute("clientToSave", clientToSave);
        model.addAttribute("allGroups", allGroups);
        return "client-add-form";
    }
    @PostMapping("/dodaj")
    String clientAdd(@Valid @ModelAttribute("clientToSave") CilentToSaveDto cilentToSave, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "client-add-form";
        } else {
            clientService.addNewClient(cilentToSave);
            return "redirect:/klienci";
        }
    }

    @GetMapping("/edytuj/{id}")
    String clientEditForm(Model model, @PathVariable long id) {
        if (!clientService.verifiClient(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        clientService.verifiClient(id);
        final CilentToSaveDto cilentToSaveDto = clientService.findClientToSaveById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final List<ClientGroupDto> allGroups = clientGroupService.findAllClientGroups();
        model.addAttribute("clientToEdit", cilentToSaveDto);
        model.addAttribute("clientId", id);
        model.addAttribute("allGroups", allGroups);
        return "client-edit-form";
    }

    @PostMapping("/edytuj/{id}")
    String clientEdit(Model model, @PathVariable long id, @Valid @ModelAttribute("clientToEdit") CilentToSaveDto cilentToSave, BindingResult bindingResult) {
        if (!clientService.verifiClient(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("clientId", id);
            return "client-edit-form";
        } else {
            clientService.editClient(id, cilentToSave);
            return "redirect:/klienci/" + id;
        }
    }

}
