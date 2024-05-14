package com.example.peer.user.repository;

import com.example.peer.user.dto.response.MentorSummary;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.peer.user.entity.QMentorDetail.mentorDetail;
import static com.example.peer.user.entity.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> findMentorByIsAccepted() {
        return jpaQueryFactory.selectFrom(user)
                .join(user.mentorDetail, mentorDetail).fetchJoin()
                .where(user.role.eq(Role.MENTOR),
                        user.mentorDetail.isAccepted.eq(Boolean.TRUE))
                .orderBy(user.createdDate.desc())
                .fetch();
    }
}
