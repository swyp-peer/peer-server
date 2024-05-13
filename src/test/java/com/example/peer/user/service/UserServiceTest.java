package com.example.peer.user.service;

import com.example.peer.user.dto.request.MentorDetailRequest;
import com.example.peer.user.dto.response.MenteeDetailResponse;
import com.example.peer.user.dto.response.MentorDetailResponse;
import com.example.peer.user.dto.response.MentorForMenteeResponse;
import com.example.peer.user.dto.response.MentorSummariesResponse;
import com.example.peer.user.entity.Keyword;
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

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MentorDetailRepository mentorDetailRepository;
    @Autowired
    UserService userService;

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
    }

    @Test
    public void 멘토_등록_테스트() {
        //given
        List<Keyword> keywords = Arrays.asList(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
        MentorDetailRequest mentorDetailRequest = MentorDetailRequest.builder()
                .nickname("pmentor")
                .introduction("x")
                .openTalkLink("http://xxx")
                .phoneNumber("01011111111")
                .position("bakcend")
                .keywords(keywords)
                .build();

        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        //when
        userService.CreateMentorDetail(mentorDetailRequest, mentor.getId());

        //then
        Assertions.assertEquals(mentor.getPhoneNumber(), "01011111111");
        Assertions.assertEquals(mentor.getRole(), Role.MENTOR);
        org.assertj.core.api.Assertions.assertThat(mentor.getMentorDetail().getKeywords()).contains(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
        Assertions.assertEquals(mentor.getMentorDetail().getNickname(), "pmentor");
        Assertions.assertEquals(mentor.getMentorDetail().getIsAccepted(), Boolean.TRUE);
    }

    @Test
    public void 멘토_조회_테스트() {
        //given
        List<Keyword> keywords = Arrays.asList(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
        MentorDetailRequest mentorDetailRequest = MentorDetailRequest.builder()
                .nickname("pmentor")
                .introduction("x")
                .openTalkLink("http://xxx")
                .phoneNumber("01011111111")
                .position("bakcend")
                .keywords(keywords)
                .build();

        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        //when
        userService.CreateMentorDetail(mentorDetailRequest, mentor.getId());
        MentorDetailResponse mentorDetailResponse = userService.ViewMentorDetail(mentor.getId());

        //then
        Assertions.assertEquals(mentorDetailResponse.getMentorId(), mentor.getId());
        Assertions.assertEquals(mentorDetailResponse.getIsAccepted(), Boolean.TRUE);
        Assertions.assertEquals(mentorDetailResponse.getPhoneNumber(), mentor.getPhoneNumber());
        org.assertj.core.api.Assertions.assertThat(mentorDetailResponse.getKeywords()).contains(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
    }

    @Test
    public void 멘토_수정_테스트() {
        //given
        List<Keyword> keywords = Arrays.asList(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
        MentorDetailRequest mentorDetailRequest = MentorDetailRequest.builder()
                .nickname("pmentor")
                .introduction("x")
                .openTalkLink("http://xxx")
                .phoneNumber("01011111111")
                .position("bakcend")
                .keywords(keywords)
                .build();

        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        userService.CreateMentorDetail(mentorDetailRequest, mentor.getId());

        List<Keyword> updateKeywords = Arrays.asList(Keyword.FRONTEND);
        MentorDetailRequest updateRequest = MentorDetailRequest.builder()
                .nickname("pmentor2")
                .introduction("x2")
                .openTalkLink("http://xxx2")
                .phoneNumber("01022222222")
                .position("frontend")
                .keywords(updateKeywords)
                .build();

        //when
        userService.UpdateMentorDetail(updateRequest, mentor.getId());

        //then
        Assertions.assertEquals(mentor.getPhoneNumber(), "01022222222");
        org.assertj.core.api.Assertions.assertThat(mentor.getMentorDetail().getKeywords()).contains(Keyword.FRONTEND);
        Assertions.assertEquals(mentor.getMentorDetail().getNickname(), "pmentor2");
        Assertions.assertEquals(mentor.getMentorDetail().getIsAccepted(), Boolean.TRUE);
    }

    @Test
    public void 멘티_조회_테스트() {
        //given
        User mentee = userRepository.findByEmail("qwer1234@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        //when
        MenteeDetailResponse menteeDetailResponse = userService.ViewMenteeDetail(mentee.getId());

        //then
        Assertions.assertEquals(menteeDetailResponse.getMenteeId(), mentee.getId());
        Assertions.assertEquals(menteeDetailResponse.getPhoneNumber(), mentee.getPhoneNumber());
        Assertions.assertEquals(menteeDetailResponse.getName(), mentee.getName());
    }

    @Test
    public void 멘토_리스트_조회_테스트() {
        //given
        List<Keyword> keywords = Arrays.asList(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
        MentorDetailRequest mentorDetailRequest = MentorDetailRequest.builder()
                .nickname("pmentor")
                .introduction("x")
                .openTalkLink("http://xxx")
                .phoneNumber("01011111111")
                .position("bakcend")
                .keywords(keywords)
                .build();

        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        userService.CreateMentorDetail(mentorDetailRequest, mentor.getId());

        User mentor2 = User.builder()
                .name("peer_mentor2")
                .email("abcd1234@gmail.com")
                .role(Role.MENTOR)
                .phoneNumber("01011111111")
                .profileImageUrl("x")
                .build();
        userRepository.save(mentor2);

        List<Keyword> keywords2 = Arrays.asList(Keyword.FRONTEND);
        MentorDetailRequest mentorDetailRequest2 = MentorDetailRequest.builder()
                .nickname("pmentor2")
                .introduction("x")
                .openTalkLink("http://xxx")
                .phoneNumber("01022222222")
                .position("frontend")
                .keywords(keywords)
                .build();
        userService.CreateMentorDetail(mentorDetailRequest2, mentor2.getId());

        User mentee = userRepository.findByEmail("qwer1234@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        //when
        MentorSummariesResponse mentorSummariesResponse = userService.ViewAcceptedMentorList(mentee.getId());

        //then
        Assertions.assertEquals(mentorSummariesResponse.getMentorSummaries().size(), 2);
        Assertions.assertEquals(mentorSummariesResponse.getMentorSummaries().stream().findFirst().get().getMentorId(), mentor2.getId());
    }

    @Test
    public void 멘티_멘토정보_조회() {
        //given
        List<Keyword> keywords = Arrays.asList(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
        MentorDetailRequest mentorDetailRequest = MentorDetailRequest.builder()
                .nickname("pmentor")
                .introduction("x")
                .openTalkLink("http://xxx")
                .phoneNumber("01011111111")
                .position("bakcend")
                .keywords(keywords)
                .build();

        User mentor = userRepository.findByEmail("abc123@gmail.com").orElseThrow(
                () -> new UserException(UserErrorCode.USER_NOT_FOUND)
        );

        userService.CreateMentorDetail(mentorDetailRequest, mentor.getId());

        //when
        MentorForMenteeResponse mentorForMenteeResponse = userService.ViewMentorByMentee(mentor.getId());

        //then
        Assertions.assertEquals(mentorForMenteeResponse.getMentorId(), mentor.getId());
        Assertions.assertEquals(mentorForMenteeResponse.getNickname(), mentor.getMentorDetail().getNickname());
        org.assertj.core.api.Assertions.assertThat(mentorForMenteeResponse.getKeywords()).contains(Keyword.BACKEND, Keyword.SERVICE, Keyword.PRODUCT);
    }
}