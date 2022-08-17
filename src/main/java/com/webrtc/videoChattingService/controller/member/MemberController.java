package com.webrtc.videoChattingService.controller.member;



import com.webrtc.videoChattingService.entity.member.MemberDto;
import com.webrtc.videoChattingService.entity.member.MemberVo;
import com.webrtc.videoChattingService.entity.member.MemberSearchParam;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.PageResult;
import com.webrtc.videoChattingService.response.ResponseService;
import com.webrtc.videoChattingService.response.SingleResult;
import com.webrtc.videoChattingService.service.member.MemberService;
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
public class MemberController {

    private final MemberService memberService;

    private final ResponseService responseService;

    @ApiOperation(value = "유저 전체 조회", notes = "유저 전체 조회")
    @GetMapping("/members")
    public PageResult<MemberVo> findAll(MemberSearchParam memberSearchParam, Pageable pageable) {
        return responseService.getPageListResult(memberService.findAll(memberSearchParam,pageable));
    }

    @ApiOperation(value = "유저 ID로 단건 조회", notes = "유저 ID로 단건 조회")
    @GetMapping("/members/{id}")
    public SingleResult<MemberVo> findById(@PathVariable Integer id) {
        return responseService.getSingleResult(memberService.findById(id));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
    @PutMapping("/members/{id}")
    public CommonResult update(@PathVariable Integer id, @RequestBody MemberDto memberDto) {
        memberDto.setId(id);
        return memberService.update(memberDto);
    }
}
