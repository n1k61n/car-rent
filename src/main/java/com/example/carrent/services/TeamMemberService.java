package com.example.carrent.services;

import com.example.carrent.dtos.about.TeamMemberDto;

import java.util.List;

public interface TeamMemberService {
    List<TeamMemberDto> getAllTeamMembers();
}
