package com.webrtc.videoChattingService.repository.user;



import com.webrtc.videoChattingService.entity.user.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    PageImpl<User> getUserList(Pageable pageable);

    User getEmail(String email);

}
