package com.example.carrent.services;

import com.example.carrent.dtos.team.TeamMemberCreateDto;
import com.example.carrent.dtos.team.TeamMemberDto;
import com.example.carrent.dtos.team.TeamMemberUpdateDto;
import jakarta.validation.Valid;
import org.aspectj.apache.bcel.generic.LocalVariableGen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.management.LockInfo;
import java.util.List;

public interface TeamMemberService {
    List<TeamMemberDto> getAllTeamMembers();


    Page<TeamMemberDto> getAll(Pageable pageable);

    boolean createTeamMember(@Valid TeamMemberCreateDto teamMemberCreateDto);

    TeamMemberUpdateDto getTeamMemberById(Long id);

    boolean updateTeamMember(Long id,  TeamMemberUpdateDto teamMemberUpdateDto);

    boolean deleteTeamMember(Long id);
}
