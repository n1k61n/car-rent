package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.team.TeamMemberCreateDto;
import com.example.carrent.dtos.team.TeamMemberDto;
import com.example.carrent.dtos.team.TeamMemberUpdateDto;
import com.example.carrent.services.TeamMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dashboard/team")
public class DashboardTeamController {


    private final TeamMemberService teamMemberService;

    @GetMapping("/index")
    public String dashboard(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "10") int size
    ){

        Pageable pageable = PageRequest.of(page, size);

        Page<TeamMemberDto> teamMemberDtoPage = teamMemberService.getAll(pageable);
        model.addAttribute("teams", teamMemberDtoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", teamMemberDtoPage.getTotalPages());
        model.addAttribute("totalItems", teamMemberDtoPage.getTotalElements());
        return "dashboard/team/index";
    }

    @GetMapping("/create")
    public String showCreate(){
        return "dashboard/team/create";
    }

    @PostMapping("/create")
    public String createTeamMember(@Valid @ModelAttribute TeamMemberCreateDto teamMemberCreateDto){
        boolean result = teamMemberService.createTeamMember(teamMemberCreateDto);
        return "redirect:/dashboard/team/index";
    }

    @GetMapping("/update/{id}")
    public String showUpdae(@PathVariable Long id, Model model){
        TeamMemberUpdateDto teamMemberUpdateDto = teamMemberService.getTeamMemberById(id);
        model.addAttribute("updateMember", teamMemberUpdateDto);
        return "dashboard/team/update";
    }

    @PostMapping("/update/{id}")
    public String ureateTeamMember(@PathVariable Long id, @Valid TeamMemberUpdateDto teamMemberUpdateDto){
        boolean result = teamMemberService.updateTeamMember(id , teamMemberUpdateDto);
        return "redirect:/dashboard/team/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeamMember(@PathVariable Long id){
        boolean result = teamMemberService.deleteTeamMember(id);
        return "redirect:/dashboard/team/index";
    }

}
