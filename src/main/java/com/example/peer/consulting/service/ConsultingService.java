package com.example.peer.consulting.service;

import com.example.peer.consulting.dto.request.ConsultingRequest;
import com.example.peer.consulting.dto.response.ConsultingDetailResponse;
import com.example.peer.consulting.entity.Consulting;
import com.example.peer.consulting.entity.ConsultingDetail;
import com.example.peer.consulting.entity.TeamComposition;
import com.example.peer.consulting.repository.ConsultingDetailRepository;
import com.example.peer.consulting.repository.ConsultingRepository;
import com.example.peer.user.exception.UserErrorCode;
import com.example.peer.user.exception.UserException;
import com.example.peer.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConsultingService {

    private final ConsultingRepository consultingRepository;
    private final ConsultingDetailRepository consultingDetailRepository;
    private final UserRepository userRepository;

    /*
    맨티-새로운 상담을 신청
     */
    public ConsultingDetailResponse CreateConsulting(ConsultingRequest consultingRequest, Long menteeId) {
        return ConsultingDetailResponse.builder()
                .consulting(consultingRepository.save(Consulting.builder()
                                .mentor(userRepository.findById(consultingRequest.getMentorId()).orElseThrow(
                                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                                ))
                                .mentee(userRepository.findById(menteeId).orElseThrow(
                                        () -> new UserException(UserErrorCode.USER_NOT_FOUND)
                                ))
                                .consultingDateTime(consultingRequest.getConsultingDateTime())
                                .consultingDetail(ConsultingDetail.builder()
                                        .message(consultingRequest.getMessage())
                                        .teamComposition(TeamComposition.builder()
                                                .managerCount(consultingRequest.getManagerCount())
                                                .designerCount(consultingRequest.getDesignerCount())
                                                .frontendCount(consultingRequest.getFrontendCount())
                                                .backendCount(consultingRequest.getBackendCount())
                                                .build())
                                        .build())
                        .build()))
                .build();
    }
}
