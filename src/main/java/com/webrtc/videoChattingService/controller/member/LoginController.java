package com.webrtc.videoChattingService.controller.member;

import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.MemberDto;
import com.webrtc.videoChattingService.entity.member.MemberVo;
import com.webrtc.videoChattingService.jwt.CookieUtil;
import com.webrtc.videoChattingService.jwt.JwtUtil;
import com.webrtc.videoChattingService.jwt.RedisUtil;
import com.webrtc.videoChattingService.mapStruct.member.MemberMapper;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.SingleResult;
import com.webrtc.videoChattingService.service.member.LoginService;
import com.webrtc.videoChattingService.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("auth")
@RequiredArgsConstructor
@RestController
@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "데이터 없음"),
        @ApiResponse(code = 500, message = "서버 문제 발생")
})
public class LoginController {

    private final LoginService loginService;

    private final MemberService memberService;

    private final JwtUtil jwtUtil;

    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;


    @ApiOperation(value = "회원 생성", notes = "회원 생성")
    @PostMapping("/members")
    public CommonResult create(@RequestBody MemberDto memberDto) {
        return memberService.create(memberDto);

    }

    @ApiOperation(value="유저 로그인", notes = "유저 로그인")
    @PostMapping("/members/login")
    public SingleResult<MemberVo> userLogin(@RequestBody MemberDto memberDto, HttpServletRequest req, HttpServletResponse res){

        SingleResult<MemberVo> result = loginService.login(memberDto);
        Member member = MemberMapper.INSTANCE.toEntity2(result.getData());

        final String token = jwtUtil.generateToken(member);
        final String refreshJwt = jwtUtil.generateRefreshToken(result.getData());
        Cookie accessToken = cookieUtil.createCookie(JwtUtil.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtil.createCookie(JwtUtil.REFRESH_TOKEN_NAME, refreshJwt);
        redisUtil.setDataExpire(refreshJwt, member.getNickName(), JwtUtil.REFRESH_TOKEN_VALIDATION_SECOND);
        res.addCookie(accessToken);
        res.addCookie(refreshToken);
        System.out.println(token);
        System.out.println(refreshJwt);

        return result;
    }
    //게스트로 로그인
    @ApiOperation(value="게스트 로그인", notes = "게스트 생성 및 로그인")
    @PostMapping("/members/guest-login")
    public SingleResult<MemberVo> guestLogin(@RequestBody MemberDto memberDto){
        return memberService.guestLogin(memberDto);
    }


}
