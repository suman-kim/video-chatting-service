package com.webrtc.videoChattingService.entity.user;



import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Entity
@ToString
@Table(name = "user")
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false, length = 45)
    private String email;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "nick_name", nullable = false, length = 45)
    private String nickName;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "address", length = 45)
    private String address;

    @Column(name = "phone", length = 45)
    private String phone;


    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;


    public void updateUser(UserDto userDto) {
        this.nickName = userDto.getNickName();
        this.imgUrl = userDto.getImgUrl();
        this.address = userDto.getAddress();
        this.phone = userDto.getPhone();
    }

    public void insertRegDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String datetime = LocalDateTime.now().format(dateTimeFormatter);
        this.regDate = LocalDateTime.parse(datetime, dateTimeFormatter);
    }

}
