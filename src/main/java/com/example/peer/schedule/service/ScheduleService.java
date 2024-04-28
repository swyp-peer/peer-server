package com.example.peer.schedule.service;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.entity.Schedule;
import com.example.peer.schedule.entity.ScheduleRule;
import com.example.peer.schedule.exception.ScheduleErrorCode;
import com.example.peer.schedule.exception.ScheduleException;
import com.example.peer.schedule.repository.ScheduleRepository;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleRuleRepository scheduleRuleRepository;
    private final UserRepository userRepository;

    /*
    멘토-요일별 일정 규칙 생성 로직
     */
    @Transactional
    public void CreateScheduleRule(ScheduleRuleRequest scheduleRuleRequest, Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.isEmpty()){
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        User mentor = findUser.get();
        if(mentor.getMentorDetail() == null) {
            throw new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND);
        }
        ScheduleRule scheduleRule = ScheduleRule.builder()
                .build();
        scheduleRule.UpdateScheduleRule(scheduleRuleRequest);
        scheduleRuleRepository.save(scheduleRule);
        scheduleRule.UpdateMentorDetail(mentor.getMentorDetail());
    }

    /*
    멘토-일정 규칙을 참고해 새로운 상담 가능 일정 생성
        4주치의 상담 가능 일정 생성
     */
    public void CreateSchedules(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.isEmpty()){
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }
        User mentor = findUser.get();
        if(mentor.getMentorDetail() == null) {
            throw new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND);
        }
        Optional<ScheduleRule> findScheduleRule = scheduleRuleRepository.findByMentorDetail(mentor.getMentorDetail());
        if(findScheduleRule.isEmpty()){
            throw new ScheduleException(ScheduleErrorCode.SCHEDULE_RULE_NOT_FOUND);
        }
        ScheduleRule scheduleRule = findScheduleRule.get();
        for(int i = 0; i < 28; i++) {
            List<Boolean> scheduleRules = DaytoScheduleRule(LocalDate.now().plusDays(i).getDayOfWeek().getValue(), scheduleRule);
            for(int j = 0; j<24; j++) {
                if(scheduleRules.get(j)) {
                    LocalDateTime possibleSchedule = LocalDate.now().plusDays(i).atStartOfDay().plusHours(j);
                    if (scheduleRepository.existsByPossibleSchedule(possibleSchedule)) {
                        continue;
                    }
                    Schedule schedule = Schedule.builder()
                            .possibleSchedule(possibleSchedule)
                            .build();
                    scheduleRepository.save(schedule);
                    schedule.UpdateMentor(mentor);
                }
            }
        }
    }

    private List<Boolean> DaytoScheduleRule(int dayOfWeek, ScheduleRule scheduleRule) {
        if(dayOfWeek == 1) {
            return scheduleRule.getMondayScheduleRule();
        } else if (dayOfWeek == 2) {
            return scheduleRule.getTuesdayScheduleRule()    ;
        } else if (dayOfWeek == 3) {
            return scheduleRule.getWednesdayScheduleRule()    ;
        } else if (dayOfWeek == 4) {
            return scheduleRule.getThursdayScheduleRule()    ;
        } else if (dayOfWeek == 5) {
            return scheduleRule.getFridayScheduleRule()    ;
        } else if (dayOfWeek == 6) {
            return scheduleRule.getSaturdayScheduleRule()    ;
        } else{
            return scheduleRule.getSundayScheduleRule()    ;
        }
    }
}
