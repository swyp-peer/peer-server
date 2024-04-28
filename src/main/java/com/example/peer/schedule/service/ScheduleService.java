package com.example.peer.schedule.service;

import com.example.peer.schedule.dto.request.ScheduleRuleRequest;
import com.example.peer.schedule.entity.ScheduleRule;
import com.example.peer.schedule.repository.ScheduleRepository;
import com.example.peer.schedule.repository.ScheduleRuleRepository;
import com.example.peer.user.entity.User;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = findUser.get();
        if(user.getMentorDetail() == null) {
            throw new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND);
        }
        ScheduleRule scheduleRule = ScheduleRule.builder()
                .scheduleStartDate(scheduleRuleRequest.getScheduleStartDate())
                .scheduleEndDate(scheduleRuleRequest.getScheduleEndDate())
                .build();
        scheduleRule.UpdateScheduleRule(scheduleRuleRequest);
        scheduleRuleRepository.save(scheduleRule);
        user.getMentorDetail().UpdateScheduleRule(scheduleRule);
    }

    /*
    멘토-일정 규칙을 참고하여서 새로운 상담 가능 일정 생성 로직
     */
    public void CreateSchedule() {

    }
}
