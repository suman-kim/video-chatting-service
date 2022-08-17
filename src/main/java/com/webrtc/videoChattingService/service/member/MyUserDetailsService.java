package com.webrtc.videoChattingService.service.member;

import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.SecurityMember;
import com.webrtc.videoChattingService.repository.user.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {

        Member member = memberRepository.getNickName(nickName);
        if(member == null){
            throw new UsernameNotFoundException(nickName + " : 사용자 존재하지 않음");
        }

        return new SecurityMember(member);
    }
}