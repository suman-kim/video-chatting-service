package com.webrtc.videoChattingService.entity.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webrtc.videoChattingService.entity.room.Room;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(name = "chat")
@NoArgsConstructor
@Builder
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    private Room room;

    @Column(name = "content")
    private String content;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

}
