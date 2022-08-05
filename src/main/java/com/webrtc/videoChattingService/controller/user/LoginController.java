package com.webrtc.videoChattingService.controller.user;

import com.webrtc.videoChattingService.entity.user.UserDto;
import com.webrtc.videoChattingService.entity.user.UserVo;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.SingleResult;
import com.webrtc.videoChattingService.service.user.LoginService;
import com.webrtc.videoChattingService.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final UserService userService;

    @ApiOperation(value = "회원 생성", notes = "회원 생성")
    @PostMapping("/users")
    public CommonResult create(@RequestBody UserDto userDto) {
        return userService.create(userDto);

    }

    @ApiOperation(value="유저 로그인", notes = "유저 로그인")
    @PostMapping("/users/login")
    public SingleResult<UserVo> userLogin(@RequestBody UserDto userDto){
        return loginService.login(userDto);
    }
    //게스트로 로그인
    @ApiOperation(value="게스트 로그인", notes = "게스트 생성 및 로그인")
    @PostMapping("/users/guest-login")
    public SingleResult<UserVo> guestLogin(@RequestBody UserDto userDto){
        return userService.guestLogin(userDto);
    }


}
