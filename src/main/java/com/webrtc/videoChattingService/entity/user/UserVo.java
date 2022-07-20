package com.webrtc.videoChattingService.entity.user;


import com.webrtc.videoChattingService.entity.room.Room;
import com.webrtc.videoChattingService.entity.userRoom.UserRoom;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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
    // private Set<UserRoom> userRooms;

}

