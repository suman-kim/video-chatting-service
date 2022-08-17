package com.webrtc.videoChattingService.service.member;


import com.webrtc.videoChattingService.advice.exception.EmptyException;


import com.webrtc.videoChattingService.advice.exception.NotFoundException;
import com.webrtc.videoChattingService.entity.Salt.Salt;
import com.webrtc.videoChattingService.entity.Salt.SaltUtil;
import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.MemberDto;
import com.webrtc.videoChattingService.entity.member.MemberVo;
import com.webrtc.videoChattingService.entity.member.MemberSearchParam;
import com.webrtc.videoChattingService.mapStruct.member.MemberMapper;
import com.webrtc.videoChattingService.repository.room.RoomRepository;
import com.webrtc.videoChattingService.repository.user.MemberRepository;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.ResponseService;
import com.webrtc.videoChattingService.response.SingleResult;
import com.webrtc.videoChattingService.service.business.FileConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final ResponseService responseService;

    private final FileConfig fileConfig;

    private final RoomRepository roomRepository;

    private final SaltUtil saltUtil;

    // 조회 : 전체 건
    @Transactional(readOnly = true) // readOnly=true 면 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도가 개선된다.
    public PageImpl<MemberVo> findAll(MemberSearchParam memberSearchParam, Pageable pageable) {
        PageImpl<Member> memberList = memberRepository.getUserList(memberSearchParam,pageable);
        System.out.println(memberList.getContent().get(0).getUserRooms());
        List<MemberVo> memberVoList = MemberMapper.INSTANCE.toVoList(memberList.getContent());
        //dto 변환
        return new PageImpl<>(memberVoList);

    }


    // 조회 : 단일 건. 유저 id로
    @Transactional(readOnly = true)
    public MemberVo findById(Integer id) {
        validateUser(id);
        return MemberMapper.INSTANCE.toVo(memberRepository.findById(id).orElseThrow(EmptyException::new));
    }


    // 생성 : 새 유저
    public CommonResult create(MemberDto memberDto) {

        if(EmailValidateUser(memberDto.getEmail()) != null){
            throw new NotFoundException("이미 사용중인 이메일입니다.");
        }

        if(NickNameValidateUser(memberDto.getNickName()) != null){
            throw new NotFoundException("이미 사용중인 닉네임입니다.");
        }


        String imageUrl = "";
        if(memberDto.getBase64() != null && memberDto.getBase64() != ""){

            imageUrl = fileConfig.base64Decode(memberDto.getBase64());
            memberDto.setImgUrl(imageUrl);
        }

        //비밀번호 암호화
        String password = memberDto.getPassword();
        String salt = saltUtil.genSalt();
        memberDto.setSalt(new Salt(salt));
        memberDto.setPassword(saltUtil.encodePassword(salt,password));


        Member member = MemberMapper.INSTANCE.toEntity(memberDto);


        member.insertRegDate();   // 등록일시 추가
        memberRepository.save(member);


        return responseService.getSuccessResult();
    }

    //게스트 생성 및 로그인
    public SingleResult<MemberVo> guestLogin(MemberDto memberDto){

        if(EmailValidateUser(memberDto.getEmail()) != null){
            throw new NotFoundException("이미 사용중인 이메일입니다.");
        }
        if(NickNameValidateUser(memberDto.getNickName()) != null){
            throw new NotFoundException("이미 사용중인 닉네임입니다.");
        }

        String imageUrl = "";
        if(memberDto.getBase64() != null && memberDto.getBase64() != ""){
            System.out.println(memberDto.getBase64());
            imageUrl = fileConfig.base64Decode(memberDto.getBase64());
            memberDto.setImgUrl(imageUrl);
        }

        System.out.println(memberDto);
        Member member = MemberMapper.INSTANCE.toEntity(memberDto);
        System.out.println(member);
        member.insertRegDate();   // 등록일시 추가
        memberRepository.save(member);


        return responseService.getSingleResult(MemberMapper.INSTANCE.toVo(member));
    }

    // 수정 : 전체 항목 수정
    public CommonResult update(MemberDto memberDto) {

        if(NickNameValidateUser(memberDto.getNickName()) != null){
            throw new NotFoundException("이미 사용중인 닉네임입니다.");
        }

        Member member = validateUser(memberDto.getId());

        String imageUrl = "";
        if(memberDto.getBase64() != null && memberDto.getBase64() != ""){
            imageUrl = fileConfig.base64Decode(memberDto.getBase64());
            memberDto.setImgUrl(imageUrl);
        }
        member.updateUser(memberDto);
        return responseService.getSuccessResult();
    }

    // ID가 있는지 유효성 검사
    public Member validateUser(Integer id) {
        return memberRepository.findById(id).orElseThrow(EmptyException::new);
    }

    //이메일 중복검사
    public Member EmailValidateUser(String email){
        return memberRepository.getEmail(email);

    }

    //닉네임 중복검사
    public Member NickNameValidateUser(String nickName){
        return memberRepository.getNickName(nickName);
    }
}
