package com.example.backend.login.repository;

import com.example.backend.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRespository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Member findByUuid(String uuid);
}
