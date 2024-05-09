package com.example.peer.schedule.service;

import com.example.peer.consulting.repository.ConsultingRepository;
import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.dto.response.PossibleSchedulesResponse;
import com.example.peer.schedule.dto.response.ScheduleRuleResponse;
import com.example.peer.schedule.entity.ScheduleRule;
import com.example.peer.schedule.exception.ScheduleErrorCode;
import com.example.peer.schedule.exception.ScheduleException;
import com.example.peer.schedule.repository.ScheduleRuleRepository;
import com.example.peer.user.entity.User;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRuleRepository scheduleRuleRepository;
    private final UserRepository userRepository;
    private final ConsultingRepository consultingRepository;

    /*
    멘토-요일별 일정 규칙 생성 로직
     */
    @Transactional
    public ScheduleRuleResponse CreateScheduleRule(ScheduleRuleRequest scheduleRuleRequest, Long mentorId) {
        return ScheduleRuleResponse.builder()
                .scheduleRule(scheduleRuleRepository.save(ScheduleRule.builder()
                                .mondayScheduleRule(scheduleRuleRequest.getMondayScheduleRule())
                                .tuesdayScheduleRule(scheduleRuleRequest.getTuesdayScheduleRule())
                                .wednesdayScheduleRule(scheduleRuleRequest.getWednesdayScheduleRule())
                                .thursdayScheduleRule(scheduleRuleRequest.getThursdayScheduleRule())
                                .fridayScheduleRule(scheduleRuleRequest.getFridayScheduleRule())
                                .saturdayScheduleRule(scheduleRuleRequest.getSaturdayScheduleRule())
                                .sundayScheduleRule(scheduleRuleRequest.getSundayScheduleRule())
                                .mentorDetail(userRepository.findById(mentorId)
                                        .orElseThrow(
                                                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                                        )
                                        .getMentorDetail())
                        .build()))
                .build();
    }

    /*
    멘토-요일별 일정 규칙 수정 로직
     */
    @Transactional
    public ScheduleRuleResponse UpdateScheduleRule(ScheduleRuleRequest scheduleRuleRequest, Long mentorId) {
        return ScheduleRuleResponse.builder()
                .scheduleRule(scheduleRuleRepository.findByMentorDetail(userRepository.findById(mentorId)
                                .orElseThrow(
                                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                                ).getMentorDetail())
                        .orElseThrow(
                                () -> new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND)
                        ).UpdateScheduleRule(scheduleRuleRequest))
                .build();
    }

    /*
    멘토-자신의 일정 규칙을 조회
     */
    public ScheduleRuleResponse ViewScheduleRule(Long mentorId) {
        return ScheduleRuleResponse.builder()
                .scheduleRule(scheduleRuleRepository.findByMentorDetail(userRepository.findById(mentorId)
                                .orElseThrow(
                                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                                ).getMentorDetail())
                        .orElseThrow(
                                () -> new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND)
                        ))
                .build();
    }

    /*
    멘티-일정 규칙을 참고해 멘토의 새로운 상담 가능 일정 조회
        다음날부터 2주치의 상담 가능 일정 조회
     */
    public PossibleSchedulesResponse ViewPossibleSchedules(Long mentorId) {
        List<LocalDateTime> possibleSchedules = new ArrayList<>();
        ScheduleRule scheduleRule = scheduleRuleRepository.findByMentorDetail(userRepository.findById(mentorId)
                .orElseThrow(
                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                ).getMentorDetail())
                .orElseThrow(
                        () -> new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND)
                );

        List<LocalDateTime> consultingDateTimeByIsAccepted = consultingRepository.findConsultingDateTimeByIsAccepted(mentorId);

        for(int i=1 ; i<15; i++){
            LocalDate consultingDate = LocalDate.now().plusDays(i);
            for(LocalTime localTime : DaytoScheduleRule(consultingDate.getDayOfWeek().getValue(), scheduleRule)) {
                if(consultingDateTimeByIsAccepted.contains(consultingDate.atTime(localTime))) {
                    continue;
                }
                possibleSchedules.add(consultingDate.atTime(localTime));
            }
        }
        return PossibleSchedulesResponse.builder()
                .possibleSchedules(possibleSchedules)
                .build();
    }

    /*
    요일을 통해 필요한 일정 규칙을 반환
     */
    private List<LocalTime> DaytoScheduleRule(int dayOfWeek, ScheduleRule scheduleRule) {
        if(dayOfWeek == 1) {
            return scheduleRule.getMondayScheduleRule();
        } else if (dayOfWeek == 2) {
            return scheduleRule.getTuesdayScheduleRule();
        } else if (dayOfWeek == 3) {
            return scheduleRule.getWednesdayScheduleRule();
        } else if (dayOfWeek == 4) {
            return scheduleRule.getThursdayScheduleRule();
        } else if (dayOfWeek == 5) {
            return scheduleRule.getFridayScheduleRule();
        } else if (dayOfWeek == 6) {
            return scheduleRule.getSaturdayScheduleRule();
        } else{
            return scheduleRule.getSundayScheduleRule();
        }
    }
}
