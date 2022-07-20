package com.webrtc.videoChattingService.repository.user;



import com.webrtc.videoChattingService.entity.user.User;
import com.webrtc.videoChattingService.entity.user.UserSearchParam;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {
    PageImpl<User> getUserList(UserSearchParam userSearchParam, Pageable pageable);

    User getEmail(String email);

}
