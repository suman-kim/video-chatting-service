package com.webrtc.videoChattingService.controller.user;



import com.webrtc.videoChattingService.entity.user.UserDto;
import com.webrtc.videoChattingService.entity.user.UserSearchParam;
import com.webrtc.videoChattingService.entity.user.UserVo;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.PageResult;
import com.webrtc.videoChattingService.response.ResponseService;
import com.webrtc.videoChattingService.response.SingleResult;
import com.webrtc.videoChattingService.service.user.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "데이터 없음"),
        @ApiResponse(code = 500, message = "서버 문제 발생")
})
public class UserController {

    private final UserService userService;

    private final ResponseService responseService;

    @ApiOperation(value = "유저 전체 조회", notes = "유저 전체 조회")
    @GetMapping("/users")
    public PageResult<UserVo> findAll(UserSearchParam userSearchParam, Pageable pageable) {
        return responseService.getPageListResult(userService.findAll(userSearchParam,pageable));
    }

    @ApiOperation(value = "유저 ID로 단건 조회", notes = "유저 ID로 단건 조회")
    @GetMapping("/users/{id}")
    public SingleResult<UserVo> findById(@PathVariable Integer id) {
        return responseService.getSingleResult(userService.findById(id));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
    @PutMapping("/users/{id}")
    public CommonResult update(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }

    @ApiOperation(value = "회원 생성", notes = "회원 생성")
    @PostMapping("/users")
    public CommonResult create(@RequestBody UserDto userDto) {
        return userService.create(userDto);

    }

    //게스트로 로그인
    @ApiOperation(value="게스트 로그인", notes = "게스트 생성 및 로그인")
    @PostMapping("/users/guest-login")
    public SingleResult<UserVo> guestLogin(@RequestBody UserDto userDto){
        return userService.guestLogin(userDto);
    }



}
