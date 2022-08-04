package com.webrtc.videoChattingService.controller.user;


import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("v1")
@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 404, message = "데이터 없음"),
        @ApiResponse(code = 500, message = "서버 문제 발생")
})
public class LoginController {
}
