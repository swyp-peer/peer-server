package com.example.peer.consulting.service;

import com.example.peer.consulting.dto.request.ConsultingRequest;
import com.example.peer.consulting.dto.response.ConsultingDetailResponse;
import com.example.peer.consulting.dto.response.ConsultingSummariesResponse;
import com.example.peer.consulting.dto.response.ConsultingSummary;
import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.ConsultingDetail;
import com.example.peer.consulting.entity.State;
import com.example.peer.consulting.entity.TeamComposition;
import com.example.peer.consulting.exception.ConsultingErrorCode;
import com.example.peer.consulting.exception.ConsultingException;
import com.example.peer.consulting.repository.ConsultingDetailRepository;
import com.example.peer.consulting.repository.ConsultingRepository;
import com.example.peer.schedule.entity.ScheduleRule;
import com.example.peer.schedule.exception.ScheduleException;
import com.example.peer.schedule.repository.ScheduleRuleRepository;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultingService {

    private final ConsultingRepository consultingRepository;
    private final ConsultingDetailRepository consultingDetailRepository;
    private final UserRepository userRepository;
    private final ScheduleRuleRepository scheduleRuleRepository;

    /*
    맨티-새로운 상담을 신청
     */
    @Transactional
    public ConsultingDetailResponse CreateConsulting(ConsultingRequest consultingRequest, Long menteeId) {
        if(!checkValidationConsultingDateTime(consultingRequest.getConsultingDateTime(), consultingRequest.getMentorId())) {
            throw new ConsultingException(ConsultingErrorCode.CANNOT_REQUEST_CONSULTING_DURING_THIS_SCHEDULE);
        }
        Consulting consulting = consultingRepository.save(Consulting.builder()
                .mentor(userRepository.findById(consultingRequest.getMentorId()).orElseThrow(
                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                ))
                .mentee(userRepository.findById(menteeId).orElseThrow(
                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                ))
                .consultingDateTime(consultingRequest.getConsultingDateTime())
                .consultingDetail(consultingDetailRepository.save(ConsultingDetail.builder()
                        .message(consultingRequest.getMessage())
                        .teamComposition(TeamComposition.builder()
                                .managerCount(consultingRequest.getManagerCount())
                                .designerCount(consultingRequest.getDesignerCount())
                                .frontendCount(consultingRequest.getFrontendCount())
                                .backendCount(consultingRequest.getBackendCount())
                                .build())
                        .build()))
                .build());
        return ConsultingDetailResponse.builder()
                .consulting(consulting)
                .consultingDetail(consulting.getConsultingDetail())
                .teamComposition(consulting.getConsultingDetail().getTeamComposition())
                .mentorDetail(consulting.getMentor().getMentorDetail())
                .mentee(consulting.getMentee())
                .build();
    }

    /*
    상담 신청한 시간대가 신청이 가능한 시간인지 체크하는 로직
     */
    private boolean checkValidationConsultingDateTime(LocalDateTime consultingDateTime, Long mentorId) {
        ScheduleRule findScheduleRule = scheduleRuleRepository.findByMentorDetail(userRepository.findById(mentorId).orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        ).getMentorDetail()).orElseThrow(
                () -> new UserException(UserErrorCode.MENTOR_DETAIL_NOT_FOUND)
        );
        if (DaytoScheduleRule(consultingDateTime.getDayOfWeek().getValue(), findScheduleRule).contains(consultingDateTime.toLocalTime())
                &&consultingDateTime.isAfter(LocalDateTime.now())
                &&consultingRepository.findByConsultingDateTimeAndMentorId(consultingDateTime, mentorId).isEmpty()) {
            return true;
        }
        return false;
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

    /*
    멘토-상담 세부 내역 조회 로직
     */
    public ConsultingDetailResponse ViewConsultingDetailMentor(Long consultingId, Long mentorId) {
        Consulting consulting = consultingRepository.findById(consultingId).orElseThrow(
                () -> new ConsultingException(ConsultingErrorCode.CONSULTING_NOT_FOUND)
        );
        if(!consulting.getMentor().getId().equals(mentorId)) {
            throw new ConsultingException(ConsultingErrorCode.CANNOT_ACCESS_CONSULTING);
        }
        return ConsultingDetailResponse.builder()
                .consulting(consulting)
                .consultingDetail(consulting.getConsultingDetail())
                .teamComposition(consulting.getConsultingDetail().getTeamComposition())
                .mentorDetail(consulting.getMentor().getMentorDetail())
                .mentee(consulting.getMentee())
                .build();
    }

    /*
    멘티-상담 세부 내역 조회 로직
     */
    public ConsultingDetailResponse ViewConsultingDetailMentee(Long consultingId, Long menteeId) {
        Consulting consulting = consultingRepository.findById(consultingId).orElseThrow(
                () -> new ConsultingException(ConsultingErrorCode.CONSULTING_NOT_FOUND)
        );
        if(!consulting.getMentee().getId().equals(menteeId)) {
            throw new ConsultingException(ConsultingErrorCode.CANNOT_ACCESS_CONSULTING);
        }
        return ConsultingDetailResponse.builder()
                .consulting(consulting)
                .consultingDetail(consulting.getConsultingDetail())
                .teamComposition(consulting.getConsultingDetail().getTeamComposition())
                .mentorDetail(consulting.getMentor().getMentorDetail())
                .mentee(consulting.getMentee())
                .build();
    }

    /*
    멘토-상담을 수락하는 로직
    상담 상세 정보들을 리턴
     */
    @Transactional
    public ConsultingDetailResponse AcceptConsulting(Long consultingId, Long mentorId) {
        Consulting consulting = consultingRepository.findById(consultingId).orElseThrow(
                () -> new ConsultingException(ConsultingErrorCode.CONSULTING_NOT_FOUND)
        );
        if(!consulting.getMentor().getId().equals(mentorId)) {
            throw new ConsultingException(ConsultingErrorCode.CANNOT_ACCESS_CONSULTING);
        }
        if(consultingRepository.findByConsultingDateTimeAndMentorIdAndState(consulting.getConsultingDateTime(), mentorId, State.ACCEPTED).isPresent()) {
            throw new ConsultingException(ConsultingErrorCode.CANNOT_CONSULT_DURING_THIS_SCHEDULE);
        }
        consulting.UpdateState(State.ACCEPTED);
        return ConsultingDetailResponse.builder()
                .consulting(consulting)
                .consultingDetail(consulting.getConsultingDetail())
                .teamComposition(consulting.getConsultingDetail().getTeamComposition())
                .mentorDetail(consulting.getMentor().getMentorDetail())
                .mentee(consulting.getMentee())
                .build();
    }

    /*
    멘토-상담을 거절하는 로직
    상담 상세 정보들을 리턴
     */
    @Transactional
    public ConsultingDetailResponse RejectConsulting(Long consultingId, Long mentorId) {
        Consulting consulting = consultingRepository.findById(consultingId).orElseThrow(
                () -> new ConsultingException(ConsultingErrorCode.CONSULTING_NOT_FOUND)
        );
        if(!consulting.getMentor().getId().equals(mentorId)) {
            throw new ConsultingException(ConsultingErrorCode.CANNOT_ACCESS_CONSULTING);
        }
        consulting.UpdateState(State.REJECTED);
        return ConsultingDetailResponse.builder()
                .consulting(consulting)
                .consultingDetail(consulting.getConsultingDetail())
                .teamComposition(consulting.getConsultingDetail().getTeamComposition())
                .mentorDetail(consulting.getMentor().getMentorDetail())
                .mentee(consulting.getMentee())
                .build();
    }

    /*
    멘토-자신의 지난 상담 내역을 조회
     */
    public ConsultingSummariesResponse ViewPastConsultingMentor(Long mentorId) {
        ConsultingSummariesResponse consultingSummariesResponse = ConsultingSummariesResponse.builder().build();
        for(Consulting consulting : consultingRepository.findPastConsultingsByMentorId(mentorId)) {
            consultingSummariesResponse.UpdateConsultingSummary(ConsultingSummary.builder().consulting(consulting)
                    .mentorDetail(consulting.getMentor().getMentorDetail())
                    .mentee(consulting.getMentee())
                    .build());
        }
        return consultingSummariesResponse;
    }

    /*
    멘티-자신의 지난 상담 내역을 조회
     */
    public ConsultingSummariesResponse ViewPastConsultingMentee(Long menteeId) {
        ConsultingSummariesResponse consultingSummariesResponse = ConsultingSummariesResponse.builder().build();
        for(Consulting consulting : consultingRepository.findPastConsultingsByMenteeId(menteeId)) {
            consultingSummariesResponse.UpdateConsultingSummary(ConsultingSummary.builder().consulting(consulting)
                    .mentorDetail(consulting.getMentor().getMentorDetail())
                    .mentee(consulting.getMentee())
                    .build());
        }
        return consultingSummariesResponse;
    }

    /*
    멘토-자신의 진행 전인 상담 내역을 상태에 따라 조회
     */
    public ConsultingSummariesResponse ViewPresentConsultingMentor(Long mentorId, State state) {
        ConsultingSummariesResponse consultingSummariesResponse = ConsultingSummariesResponse.builder().build();
        for(Consulting consulting : consultingRepository.findPresentConsultingsByMentorIdAndState(mentorId, state)) {
            consultingSummariesResponse.UpdateConsultingSummary(ConsultingSummary.builder().consulting(consulting)
                    .mentorDetail(consulting.getMentor().getMentorDetail())
                    .mentee(consulting.getMentee())
                    .build());
        }
        return consultingSummariesResponse;
    }

    /*
    멘티-자신의 진행 전인 상담 내역을 상태에 따라 조회
     */
    public ConsultingSummariesResponse ViewPresentConsultingMentee(Long menteeId, State state) {
        ConsultingSummariesResponse consultingSummariesResponse = ConsultingSummariesResponse.builder().build();
        for(Consulting consulting : consultingRepository.findPresentConsultingsByMenteeIdAndState(menteeId, state)) {
            consultingSummariesResponse.UpdateConsultingSummary(ConsultingSummary.builder().consulting(consulting)
                    .mentorDetail(consulting.getMentor().getMentorDetail())
                    .mentee(consulting.getMentee())
                    .build());
        }
        return consultingSummariesResponse;
    }


}
