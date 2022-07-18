package com.webrtc.videoChattingService.entity.user;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserVo {

    private Integer id;
    private String email;
    private String nickName;
    private String imgUrl;
    private String address;
    private String phone;
    private LocalDateTime regDate;

}

