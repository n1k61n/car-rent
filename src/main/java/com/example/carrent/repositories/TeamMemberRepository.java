package com.example.carrent.repositories;

import com.example.carrent.models.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository  extends JpaRepository<TeamMember, Long> {
}
