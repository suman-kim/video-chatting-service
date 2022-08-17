package com.webrtc.videoChattingService.service.member;


import com.webrtc.videoChattingService.advice.exception.NotFoundException;
import com.webrtc.videoChattingService.entity.Salt.SaltUtil;
import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.MemberDto;
import com.webrtc.videoChattingService.entity.member.MemberVo;
import com.webrtc.videoChattingService.mapStruct.member.MemberMapper;
import com.webrtc.videoChattingService.repository.user.MemberRepository;
import com.webrtc.videoChattingService.response.ResponseService;
import com.webrtc.videoChattingService.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final ResponseService responseService;

    private final SaltUtil saltUtil;

    public SingleResult<MemberVo> login(MemberDto memberDto){

        Member member = memberRepository.getEmail(memberDto.getEmail());
        if(member ==null){
            throw new NotFoundException("없는 이메일입니다.");

        }
        String salt = member.getSalt().getSalt();
        String password = memberDto.getPassword();

        password = saltUtil.encodePassword(salt,password);

        if(!member.getPassword().equals(password)){
            throw new NotFoundException("비밀번호가 틀립니다.");
        }

       return responseService.getSingleResult(MemberMapper.INSTANCE.toVo(member));
    }
}
