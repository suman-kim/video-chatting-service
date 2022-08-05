package com.webrtc.videoChattingService.service.user;


import com.webrtc.videoChattingService.advice.exception.NotFoundException;
import com.webrtc.videoChattingService.entity.Salt.SaltUtil;
import com.webrtc.videoChattingService.entity.user.User;
import com.webrtc.videoChattingService.entity.user.UserDto;
import com.webrtc.videoChattingService.entity.user.UserVo;
import com.webrtc.videoChattingService.mapStruct.user.UserMapper;
import com.webrtc.videoChattingService.repository.user.UserRepository;
import com.webrtc.videoChattingService.response.ResponseService;
import com.webrtc.videoChattingService.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final ResponseService responseService;

    private final SaltUtil saltUtil;

    public SingleResult<UserVo> login(UserDto userDto){

        User user = userRepository.getEmail(userDto.getEmail());
        if(user==null){
            throw new NotFoundException("없는 이메일입니다.");

        }
        System.out.println(user.getPassword());
        System.out.println(userDto.getPassword());
        if(!user.getPassword().equals(userDto.getPassword())){
            throw new NotFoundException("비밀번호가 틀립니다.");
        }

       return responseService.getSingleResult(UserMapper.INSTANCE.toVo(user));
    }
}
