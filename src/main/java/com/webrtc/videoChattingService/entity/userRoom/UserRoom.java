package com.webrtc.videoChattingService.entity.userRoom;
import com.webrtc.videoChattingService.entity.room.Room;
import com.webrtc.videoChattingService.entity.user.User;
import javax.persistence.*;

@Entity
@Table(name = "user_room")
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room")
    private Room room;
}
