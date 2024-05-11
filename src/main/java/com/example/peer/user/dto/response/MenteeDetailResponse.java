package com.example.peer.user.dto.response;

import com.example.peer.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenteeDetailResponse {

    private Long menteeId;

    private String name;

    private String email;

    private String phoneNumber;

    @Builder
    public MenteeDetailResponse(User mentee) {
        this.menteeId = mentee.getId();
        this.name = mentee.getName();
        this.email = mentee.getEmail();
        this.phoneNumber = mentee.getPhoneNumber();
    }
}
