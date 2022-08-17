package com.webrtc.videoChattingService.repository.user;


import com.webrtc.videoChattingService.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {

}
