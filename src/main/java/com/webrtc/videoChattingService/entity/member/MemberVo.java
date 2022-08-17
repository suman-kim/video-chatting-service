package com.webrtc.videoChattingService.entity.member;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberVo {

    private Integer id;
    private String email;
    private String nickName;
    private String imgUrl;
    private String address;
    private String phone;
    private LocalDateTime regDate;
    // private Set<UserRoom> userRooms;

}

