package com.webrtc.videoChattingService.repository.user;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.webrtc.videoChattingService.entity.room.QRoom;
import com.webrtc.videoChattingService.entity.user.QUser;
import com.webrtc.videoChattingService.entity.user.User;
import com.webrtc.videoChattingService.entity.user.UserSearchParam;
import com.webrtc.videoChattingService.entity.userRoom.QUserRoom;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {


    private final JPAQueryFactory queryFactory;


    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QUser user = QUser.user;

    QUserRoom userRoom = QUserRoom.userRoom;

    QRoom room = QRoom.room;

    @Override
    public PageImpl<User> getUserList(UserSearchParam userSearchParam,Pageable pageable) {
        List<User> results = queryFactory
                .selectFrom(user)
                .leftJoin(user.userRooms,userRoom)
                .fetchJoin()
                .distinct()
                .leftJoin(userRoom.room,room)
                .fetchJoin()
                .distinct()
                .where(nickNameEq(userSearchParam.getNickName()),
                        emailEq(userSearchParam.getEmail()))
                .offset(pageable.getOffset())   //N 번부터 시작
                .limit(pageable.getPageSize()) //조회 개수
                .fetch();
        return new PageImpl<>(results,pageable, results.size());
    }

    @Override
    public User getEmail(String email){
        User results = queryFactory
                .selectFrom(user)
                .where(user.email.in(email))
                .fetchOne();
        return results;
    }


    private BooleanExpression nickNameEq(String nickName) {
        return StringUtils.hasText(nickName) ? user.nickName.contains(nickName) : null;
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? user.email.contains(email) : null;
    }

}