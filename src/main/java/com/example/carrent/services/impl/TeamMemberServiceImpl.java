package com.example.carrent.services.impl;

import com.example.carrent.dtos.about.TeamMemberDto;
import com.example.carrent.models.TeamMember;
import com.example.carrent.repositories.TeamMemberRepository;
import com.example.carrent.services.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TeamMemberDto> getAllTeamMembers() {
        List<TeamMember> teamMemberList = teamMemberRepository.findAll();
        if (teamMemberList != null) {
            return teamMemberList.stream().map(teamMember -> modelMapper.map(teamMember, TeamMemberDto.class)).toList();
        }
        return List.of();
    }
}
