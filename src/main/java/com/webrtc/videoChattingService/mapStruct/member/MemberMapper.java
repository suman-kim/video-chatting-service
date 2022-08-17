package com.webrtc.videoChattingService.mapStruct.member;



import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.MemberDto;
import com.webrtc.videoChattingService.entity.member.MemberVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

//매핑되지 않은 속성을 무시
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);


    //@Mapping(target = "base64", ignore = true)
    MemberDto toDto(Member entity);

    MemberVo toVo(Member entity);

    //@Mapping(target = "videoList", ignore = true)
    Member toEntity(MemberDto dto);

    Member toEntity2(MemberVo vo);

    default List<MemberDto> toDtoList(List<Member> list) {
        return list
                .stream()
                .map(MemberMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    default List<Member> toEntityList(List<MemberDto> list) {
        return list
                .stream()
                .map(MemberMapper.INSTANCE::toEntity)
                .collect(Collectors.toList());
    }

    default List<MemberVo> toVoList(List<Member> list) {
        return list
                .stream()
                .map(MemberMapper.INSTANCE::toVo)
                .collect(Collectors.toList());
    }


}
