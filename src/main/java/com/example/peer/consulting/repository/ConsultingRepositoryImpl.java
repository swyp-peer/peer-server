package com.example.peer.consulting.repository;

import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.State;
import com.example.peer.user.entity.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.peer.consulting.entity.QConsulting.consulting;
import static com.example.peer.consulting.entity.State.ACCEPTED;
import static com.example.peer.user.entity.QMentorDetail.mentorDetail;
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

    @Override
    public List<Consulting> findPastConsultingsByMentorId(Long id) {
        return jpaQueryFactory.selectFrom(consulting)
                .leftJoin(consulting.mentee).on(consulting.mentee.role.eq(Role.MENTEE))
                .leftJoin(consulting.mentor).on(consulting.mentor.role.eq(Role.MENTOR))
                .leftJoin(consulting.mentor.mentorDetail, mentorDetail)
                .where(consulting.consultingDateTime.before(LocalDateTime.now()),
                        consulting.mentor.id.eq(id),
                        consulting.state.eq(ACCEPTED))
                .orderBy(consulting.consultingDateTime.desc())
                .fetch();
    }

    @Override
    public List<Consulting> findPastConsultingsByMenteeId(Long id) {
        return jpaQueryFactory.selectFrom(consulting)
                .leftJoin(consulting.mentee).on(consulting.mentee.role.eq(Role.MENTEE))
                .leftJoin(consulting.mentor).on(consulting.mentor.role.eq(Role.MENTOR))
                .leftJoin(consulting.mentor.mentorDetail, mentorDetail)
                .where(consulting.consultingDateTime.before(LocalDateTime.now()),
                        consulting.mentee.id.eq(id),
                        consulting.state.eq(ACCEPTED))
                .orderBy(consulting.consultingDateTime.desc())
                .fetch();
    }

    @Override
    public List<Consulting> findPresentConsultingsByMentorIdAndState(Long id, State state) {
        return jpaQueryFactory.selectFrom(consulting)
                .leftJoin(consulting.mentee).on(consulting.mentee.role.eq(Role.MENTEE))
                .leftJoin(consulting.mentor).on(consulting.mentor.role.eq(Role.MENTOR))
                .leftJoin(consulting.mentor.mentorDetail, mentorDetail)
                .where(consulting.consultingDateTime.after(LocalDateTime.now()),
                        consulting.mentor.id.eq(id),
                        consulting.state.eq(state))
                .orderBy(consulting.consultingDateTime.asc())
                .fetch();
    }

    @Override
    public List<Consulting> findPresentConsultingsByMenteeIdAndState(Long id, State state) {
        return jpaQueryFactory.selectFrom(consulting)
                .leftJoin(consulting.mentee).on(consulting.mentee.role.eq(Role.MENTEE))
                .leftJoin(consulting.mentor).on(consulting.mentor.role.eq(Role.MENTOR))
                .leftJoin(consulting.mentor.mentorDetail, mentorDetail)
                .where(consulting.consultingDateTime.after(LocalDateTime.now()),
                        consulting.mentee.id.eq(id),
                        consulting.state.eq(state))
                .orderBy(consulting.consultingDateTime.asc())
                .fetch();
    }
}
