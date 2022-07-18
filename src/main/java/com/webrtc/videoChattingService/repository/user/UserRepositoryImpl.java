package com.webrtc.videoChattingService.repository.user;


import com.querydsl.jpa.impl.JPAQueryFactory;

import com.webrtc.videoChattingService.entity.user.QUser;
import com.webrtc.videoChattingService.entity.user.User;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {


    private final JPAQueryFactory queryFactory;


    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QUser user = QUser.user;

    @Override
    public PageImpl<User> getUserList(Pageable pageable) {
        List<User> results = queryFactory
                .selectFrom(user)
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

}