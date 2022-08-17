package com.webrtc.videoChattingService.repository.user;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import com.webrtc.videoChattingService.entity.room.QRoom;
import com.webrtc.videoChattingService.entity.member.Member;
import com.webrtc.videoChattingService.entity.member.QMember;
import com.webrtc.videoChattingService.entity.member.MemberSearchParam;
import com.webrtc.videoChattingService.entity.userRoom.QUserRoom;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom {


    private final JPAQueryFactory queryFactory;


    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QMember member = QMember.member;

    QUserRoom userRoom = QUserRoom.userRoom;

    QRoom room = QRoom.room;

    @Override
    public PageImpl<Member> getUserList(MemberSearchParam memberSearchParam, Pageable pageable) {
        List<Member> results = queryFactory
                .selectFrom(member)
                .leftJoin(member.userRooms,userRoom)
                .fetchJoin()
                .distinct()
                .leftJoin(userRoom.room,room)
                .fetchJoin()
                .distinct()
                .where(nickNameEq(memberSearchParam.getNickName()),
                        emailEq(memberSearchParam.getEmail()))
                .offset(pageable.getOffset())   //N 번부터 시작
                .limit(pageable.getPageSize()) //조회 개수
                .fetch();
        return new PageImpl<>(results,pageable, results.size());
    }

    @Override
    public Member getEmail(String email){
        Member results = queryFactory
                .selectFrom(member)
                .where(member.email.in(email))
                .fetchOne();
        return results;
    }

    @Override
    public Member getNickName(String nickName){
        Member results = queryFactory
                .selectFrom(member)
                .where(member.nickName.in(nickName))
                .fetchOne();
        return results;
    }


    private BooleanExpression nickNameEq(String nickName) {
        return StringUtils.hasText(nickName) ? member.nickName.contains(nickName) : null;
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? member.email.contains(email) : null;
    }

}