package com.webrtc.videoChattingService.service.user;


import com.webrtc.videoChattingService.advice.exception.NotFoundException;
import com.webrtc.videoChattingService.entity.room.Room;
import com.webrtc.videoChattingService.entity.user.User;
import com.webrtc.videoChattingService.entity.user.UserDto;
import com.webrtc.videoChattingService.entity.user.UserSearchParam;
import com.webrtc.videoChattingService.entity.user.UserVo;
import com.webrtc.videoChattingService.mapStruct.user.UserMapper;
import com.webrtc.videoChattingService.repository.room.RoomRepository;
import com.webrtc.videoChattingService.repository.user.UserRepository;
import com.webrtc.videoChattingService.response.CommonResult;
import com.webrtc.videoChattingService.response.ResponseService;
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
public class UserService {

    private final UserRepository userRepository;

    private final ResponseService responseService;

    private final FileConfig fileConfig;

    private final RoomRepository roomRepository;

    // 조회 : 전체 건
    @Transactional(readOnly = true) // readOnly=true 면 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도가 개선된다.
    public PageImpl<UserVo> findAll(UserSearchParam userSearchParam,Pageable pageable) {
        PageImpl<User> userList = userRepository.getUserList(userSearchParam,pageable);
        System.out.println(userList.getContent().get(0).getUserRooms());
        List<UserVo> userVoList = UserMapper.INSTANCE.toVoList(userList.getContent());
        //dto 변환
        return new PageImpl<>(userVoList);

    }


    // 조회 : 단일 건. 유저 id로
    @Transactional(readOnly = true)
    public UserVo findById(Integer id) {
        validateUser(id);
        return UserMapper.INSTANCE.toVo(userRepository.findById(id).orElseThrow(NotFoundException::new));
    }


    // 생성 : 새 유저
    public CommonResult create(UserDto userDto) {
        User emailValidateUser = EmailValidateUser(userDto.getEmail());

        if(emailValidateUser != null){
            return responseService.getFailResult("중복 이메일입니다.");
        }

        String imageUrl = "";
        if(userDto.getBase64() != null && userDto.getBase64() != ""){
            System.out.println(userDto.getBase64());
            imageUrl = fileConfig.base64Decode(userDto.getBase64());
            userDto.setImgUrl(imageUrl);
        }

        System.out.println(userDto);
        User user = UserMapper.INSTANCE.toEntity(userDto);
        System.out.println(user);
        user.insertRegDate();   // 등록일시 추가
        userRepository.save(user);


        return responseService.getSuccessResult();
    }

    // 수정 : 전체 항목 수정
    public CommonResult update(UserDto userDto) {    // 추후 이메일 같으면 오류발생

        User user = validateUser(userDto.getId());

        String imageUrl = "";
        if(userDto.getBase64() != null && userDto.getBase64() != ""){
            imageUrl = fileConfig.base64Decode(userDto.getBase64());
            userDto.setImgUrl(imageUrl);
        }
        user.updateUser(userDto);
        return responseService.getSuccessResult();
    }

    // ID가 있는지 유효성 검사
    public User validateUser(Integer id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    //이메일 중복검사
    public User EmailValidateUser(String email){
        return userRepository.getEmail(email);

    }
}
