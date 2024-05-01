package com.example.peer.consulting.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.peer.consulting.entity.QConsulting.consulting;
import static com.example.peer.consulting.entity.State.ACCEPTED;
import static com.example.peer.user.entity.QUser.user;

public class ConsultingRepositoryImpl implements ConsultingRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public ConsultingRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LocalDateTime> findConsultingDateTimeByIsAccepted(Long id) {
        return jpaQueryFactory.select(consulting.consultingDateTime)
                .from(consulting)
                .leftJoin(consulting.mentor, user)
                .where(consulting.mentor.id.eq(id),
                        consulting.state.eq(ACCEPTED),
                        consulting.consultingDateTime.after(LocalDateTime.now()))
                .fetch();
    }
}
