package com.webrtc.videoChattingService.mapStruct.user;



import com.webrtc.videoChattingService.entity.user.User;
import com.webrtc.videoChattingService.entity.user.UserDto;
import com.webrtc.videoChattingService.entity.user.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

//매핑되지 않은 속성을 무시
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    //@Mapping(target = "base64", ignore = true)
    UserDto toDto(User entity);

    UserVo toVo(User entity);

    //@Mapping(target = "videoList", ignore = true)
    User toEntity(UserDto dto);

    default List<UserDto> toDtoList(List<User> list) {
        return list
                .stream()
                .map(UserMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    default List<User> toEntityList(List<UserDto> list) {
        return list
                .stream()
                .map(UserMapper.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    default List<UserVo> toVoList(List<User> list) {
        return list
                .stream()
                .map(UserMapper.INSTANCE::toVo)
                .collect(Collectors.toList());
    }


}
