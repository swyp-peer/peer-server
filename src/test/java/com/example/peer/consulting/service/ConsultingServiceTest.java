package com.example.peer.consulting.service;

import com.example.peer.consulting.dto.request.ConsultingRequest;
import com.example.peer.consulting.dto.response.ConsultingDetailResponse;
import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.State;
import com.example.peer.consulting.exception.ConsultingErrorCode;
import com.example.peer.consulting.exception.ConsultingException;
import com.example.peer.consulting.repository.ConsultingRepository;
import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.repository.ScheduleRuleRepository;
import com.example.peer.schedule.service.ScheduleService;
import com.example.peer.user.entity.MentorDetail;
import com.example.peer.user.entity.Role;
import com.example.peer.user.entity.User;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.MentorDetailRepository;
import com.example.peer.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class ConsultingServiceTest {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MentorDetailRepository mentorDetailRepository;
    @Autowired
    ScheduleRuleRepository scheduleRuleRepository;
    @Autowired
    ConsultingService consultingService;
    @Autowired
    ConsultingRepository consultingRepository;

    @BeforeEach
    public void initTest() {
        User mentee = User.builder()
                .name("peer_mentee")
                .email("qwer1234@gmail.com")
                .role(Role.MENTEE)
                .phoneNumber("01098765432")
                .profileImageUrl("x")
                .build();
        userRepository.save(mentee);

        User mentor = User.builder()
                .name("peer_mentor")
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
        for (int i=17;i<23;i++) {
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

        scheduleService.CreateScheduleRule(scheduleRuleRequest, mentor.getId());
    }

    @Test
    public void 멘티_새로운_상담_신청_올바른_날짜() {
        //given
        User mentee = userRepository.findByEmail("qwer1234@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        LocalDateTime consultingDateTime = LocalDateTime.now().toLocalDate().plusDays(1).atTime(17,0);

        ConsultingRequest consultingRequest = ConsultingRequest.builder()
                .consultingDateTime(consultingDateTime)
                .message("x")
                .managerCount(1)
                .designerCount(2)
                .frontendCount(3)
                .backendCount(3)
                .mentorId(mentor.getId())
                .phoneNumber("01000000000")
                .build();

        //when
        ConsultingDetailResponse consultingDetailResponse = consultingService.CreateConsulting(consultingRequest, mentee.getId());

        //then
        Consulting consulting = consultingRepository.findById(consultingDetailResponse.getId()).orElseThrow(
                () -> new ConsultingException(ConsultingErrorCode.CONSULTING_NOT_FOUND)
        );
        Assertions.assertEquals(consulting.getConsultingDateTime(), consultingDateTime);
        Assertions.assertEquals(consulting.getState(), State.WAITING);
        Assertions.assertEquals(consulting.getMentee(), mentee);
        Assertions.assertEquals(consulting.getMentor(), mentor);
        Assertions.assertEquals(consulting.getConsultingDetail().getTeamComposition().getBackendCount(), 3);
    }

    @Test
    public void 멘티_새로운_상담_신청_잘못된_날짜() {
        //given
        User mentee = userRepository.findByEmail("qwer1234@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );
        LocalDateTime consultingDateTime = LocalDateTime.now().toLocalDate().plusDays(1).atTime(23, 0);

        ConsultingRequest consultingRequest = ConsultingRequest.builder()
                .consultingDateTime(consultingDateTime)
                .message("x")
                .managerCount(1)
                .designerCount(2)
                .frontendCount(3)
                .backendCount(3)
                .mentorId(mentor.getId())
                .phoneNumber("01000000000")
                .build();

        //when, then
        Assertions.assertThrows(ConsultingException.class, () ->
                consultingService.CreateConsulting(consultingRequest, mentee.getId()));
    }
}