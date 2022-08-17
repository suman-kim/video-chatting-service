package com.webrtc.videoChattingService.repository.user;



import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.MemberSearchParam;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    PageImpl<Member> getUserList(MemberSearchParam memberSearchParam, Pageable pageable);

    Member getEmail(String email);

    Member getNickName(String nickName);

}
