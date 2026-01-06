package com.example.carrent.controllers.front;

import com.example.carrent.dtos.team.TeamMemberDto;
import com.example.carrent.services.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AboutController {

    private final TeamMemberService teamMemberService;

    @GetMapping("/about")
    public String getAboutPage(Model model) {
        List<TeamMemberDto> team = teamMemberService.getAllTeamMembers();
        model.addAttribute("teamMembers", team);
        return "front/about";
    }
}
