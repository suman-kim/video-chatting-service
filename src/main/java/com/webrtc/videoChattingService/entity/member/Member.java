package com.webrtc.videoChattingService.entity.member;




import com.webrtc.videoChattingService.entity.Salt.Salt;
import com.webrtc.videoChattingService.entity.userRoom.UserRoom;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(name = "member")
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Setter
    @Column(name = "nick_name", unique = true, nullable = false, length = 45)
    private String nickName;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "phone", length = 45)
    private String phone;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @Column(name = "login_status")
    private Integer loginStatus;

    @Column(name = "login_date")
    private LocalDateTime loginDate;

    @Column(name = "logout_date")
    private LocalDateTime logoutDate;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private Set<UserRoom> userRooms;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "salt_id")
    private Salt salt;




    public void updateUser(MemberDto memberDto) {
        this.nickName = memberDto.getNickName();
        this.imgUrl = memberDto.getImgUrl();
        this.address = memberDto.getAddress();
        this.phone = memberDto.getPhone();
    }

    public void insertRegDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = LocalDateTime.now().format(dateTimeFormatter);
        this.regDate = LocalDateTime.parse(datetime, dateTimeFormatter);
    }

}
