package com.example.carrent.services.impl;

import com.example.carrent.dtos.team.TeamMemberCreateDto;
import com.example.carrent.dtos.team.TeamMemberDto;
import com.example.carrent.dtos.team.TeamMemberUpdateDto;
import com.example.carrent.models.TeamMember;
import com.example.carrent.repositories.TeamMemberRepository;
import com.example.carrent.services.TeamMemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<TeamMemberDto> getAll(Pageable pageable) {
        Page<TeamMember> pages = teamMemberRepository.findAll(pageable);
        return pages.map(page -> modelMapper.map(page, TeamMemberDto.class));
    }

    @Override
    public boolean createTeamMember(TeamMemberCreateDto teamMemberCreateDto) {
        try {
            TeamMember teamMember = modelMapper.map(teamMemberCreateDto, TeamMember.class);
            teamMemberRepository.save(teamMember);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public TeamMemberUpdateDto getTeamMemberById(Long id) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElse(null);
        if (teamMember != null) {
            return modelMapper.map(teamMember, TeamMemberUpdateDto.class);
        }
        return null;
    }

    @Override
    public boolean updateTeamMember(Long id, TeamMemberUpdateDto teamMemberUpdateDto) {
        TeamMember teamMember = teamMemberRepository.findById(id).orElse(null);
        if (teamMember != null) {
            teamMember.setName(teamMemberUpdateDto.getName());
            teamMember.setRole(teamMemberUpdateDto.getRole());
            teamMember.setBio(teamMemberUpdateDto.getBio());
            teamMember.setImageUrl(teamMemberUpdateDto.getImageUrl());
            teamMemberRepository.save(teamMember);
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteTeamMember(Long id) {
        if(teamMemberRepository.existsById(id)){
            teamMemberRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
