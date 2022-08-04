package com.webrtc.videoChattingService.repository.user;


import com.webrtc.videoChattingService.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>,UserRepositoryCustom  {

}
