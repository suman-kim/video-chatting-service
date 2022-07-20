package com.webrtc.videoChattingService.entity.room;


import com.webrtc.videoChattingService.entity.chat.Chat;
import com.webrtc.videoChattingService.entity.userRoom.UserRoom;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(name = "room")
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment(value = "room number")
    private Integer id;


    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Builder.Default
    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private Set<UserRoom> userRooms;

}

