package com.example.backend.login.repository;

import com.example.backend.login.domain.Friend;
import com.example.backend.login.domain.Member;
import com.example.backend.login.enums.FriendType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByUuidAndStatus(String uuid, FriendType friendType);

    Optional<Friend> findByUuidAndFriend(String uuid, Member member);

    List<Friend> findByUuidAndStar(String uuid, boolean b);
}
