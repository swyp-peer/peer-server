package com.example.peer.schedule.service;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.entity.ScheduleRule;
import com.example.peer.schedule.repository.ScheduleRuleRepository;
import com.example.peer.user.entity.MentorDetail;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.MentorDetailRepository;
import com.example.peer.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MentorDetailRepository mentorDetailRepository;
    @Autowired
    ScheduleRuleRepository scheduleRuleRepository;

    @Test
    public void 요일별_일정_규칙_생성() {
        //given
        User mentor = User.builder()
                .name("peer")
                .email("abc123@gmail.com")
                .role(Role.MENTOR)
                .phoneNumber("01012345678")
                .profileImageUrl("x")
                .build();
        userRepository.save(mentor);

        MentorDetail mentorDetail = MentorDetail.builder()
                .nickname("peer123")
                .introduction("hello")
                .position("backend")
                .build();
        mentorDetailRepository.save(mentorDetail);
        mentor.UpdateMentorDetail(mentorDetail);

        List<LocalTime> scheduleRule = new ArrayList<>(24);
        for (int i=0;i<24;i++) {
            scheduleRule.add(LocalTime.of(i,0));
        }
        ScheduleRuleRequest scheduleRuleRequest = ScheduleRuleRequest.builder()
                .mondayScheduleRule(scheduleRule)
                .tuesdayScheduleRule(scheduleRule)
                .wednesdayScheduleRule(scheduleRule)
                .thursdayScheduleRule(scheduleRule)
                .fridayScheduleRule(scheduleRule)
                .saturdayScheduleRule(scheduleRule)
                .sundayScheduleRule(scheduleRule)
                .build();

        //when
        scheduleService.CreateScheduleRule(scheduleRuleRequest, mentor.getId());

        //then
        Optional<User> findUser = userRepository.findById(mentor.getId());
        if(findUser.isEmpty()) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        ScheduleRule findScheduleRule = findUser.get().getMentorDetail().getScheduleRule();

        Assertions.assertEquals(findScheduleRule.getMentorDetail(), mentorDetail);
        org.assertj.core.api.Assertions.assertThat(findScheduleRule.getMondayScheduleRule()).contains(LocalTime.of(0,0));
        org.assertj.core.api.Assertions.assertThat(findScheduleRule.getMondayScheduleRule()).contains(LocalTime.of(23,0));

    }
}